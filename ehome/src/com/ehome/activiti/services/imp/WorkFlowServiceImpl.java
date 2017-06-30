package com.ehome.activiti.services.imp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.HistoricFormPropertyEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.activiti.utils.IDGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 该service主要是对流程服务的进一步封装，便于使用
 * 
 * @author kokJuis
 * @version 1.0
 * @date 2017-1-19
 */
@Service("workFlowService")
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FormService formService;
	@Autowired
	private WorkFlowUtilService workFlowUtilService;

	@Override
	public List<Model> selectAllModel(String category, Integer iDisplayStart,
			Integer iDisplayLength) {

		List<Model> modelList = repositoryService.createModelQuery()
				.modelCategory(category).orderByCreateTime().desc()
				.listPage(iDisplayStart, iDisplayLength);

		return modelList;
	}

	@Override
	public long selectModelCount(String category) {
		return repositoryService.createModelQuery().modelCategory(category)
				.count();
	}

	@Override
	public Model createModel(String name, String key, String category,
			String description) throws Exception {
		// 创建模型资源
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.set("stencilset", stencilSetNode);

		// 创建模型
		Model modelData = repositoryService.newModel();
		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		description = StringUtils.defaultString(description);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
				description);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);
		modelData.setKey(StringUtils.defaultString(key));
		modelData.setCategory(StringUtils.defaultString(category));

		// 保存模型
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), editorNode
				.toString().getBytes("UTF-8"));

		return modelData;
	}

	@Override
	public void delModel(String ids) throws Exception {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			repositoryService.deleteModel(id);
		}
	}

	@Override
	public void deploymentModel(String modelId, String tenantId)
			throws Exception {

		Model modelData = repositoryService.getModel(modelId);

		//String category = modelData.getCategory();

//		ProcessDefinition processDefinition = workFlowUtilService
//				.findProcessDefinitionByTenantId(category, tenantId);

		// if (processDefinition != null) {
		// throw new Exception("已经存在相同的部署");
		// }

		// 获得模型JSON数据的UTF8字符串
		byte[] source = repositoryService.getModelEditorSource(modelId);
		// jackson-databind转换成Java对象树
		JsonNode editorNode = new ObjectMapper().readTree(source);
		// 将模型JSON数据的Java对象树转换成BpmnModel实例
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		BpmnModel model = jsonConverter.convertToBpmnModel(editorNode);

		jsonConverter.convertToJson(model);

		// 将BpmnModel实例转成BPMN XML数据
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model, "UTF-8");

		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);

		// 模型图名称
		String bpmnName = modelData.getName() + ".bpmn20.xml";

		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 过滤相同的流程，防止重复部署，关键是为流程部署起一个名字，没有名字不会过滤
		builder.enableDuplicateFiltering().name(modelData.getName());
		// 添加模型名称
		builder.name(modelData.getName());
		builder.category(modelData.getCategory());// 添加一个分类，用于查询
		builder.tenantId(tenantId);// 添加一个租借ID，用于跟城市关联

		// 区别在这里,添加form表单
		// List<JsonNode> forms =
		// editorNode.findValues("formkeydefinition");
		// for (JsonNode node : forms) {
		// // aaa.form
		// String formName = node.textValue();
		// if (!"".equals(formName)) {
		// // ByteArrayInputStream bi = new ByteArrayInputStream(
		// // formContent.getBytes());
		//
		// //通过forName在ClassPath获取定义的form，这个路径可以改
		// InputStream bi = getClass().getClassLoader()
		// .getResourceAsStream(formName);
		// builder.addInputStream(formName, bi);
		// continue;
		// }
		// }

		// 部署流程模型
		builder.addString(bpmnName, new String(bpmnBytes, "UTF-8")).deploy();

		// builder.addInputStream(bpmnName, in).deploy();

	}

	@Override
	public List<Map<String, Object>> selectProcessDefinitionList(
			String category, Integer iDisplayStart, Integer iDisplayLength) {
		// 查询流程定义列表
		// List<ProcessDefinition> resultList = repositoryService
		// .createProcessDefinitionQuery()
		// .processDefinitionCategory(category).orderByDeploymentId()
		// .desc().listPage(iDisplayStart, iDisplayLength);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Deployment> resultList = repositoryService.createDeploymentQuery()
				.deploymentCategory(category).orderByDeploymenTime().desc()
				.listPage(iDisplayStart, iDisplayLength);

		for (Deployment deployment : resultList) {
			Map<String, Object> tmp = new HashMap<String, Object>();

			ProcessDefinition p = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployment.getId()).singleResult();

			tmp.put("deployment", deployment);
			tmp.put("processDefinition", p);

			list.add(tmp);

		}

		return list;
	}

	@Override
	public long selectProcessDefinitionCount() {
		return repositoryService.createProcessDefinitionQuery().count();
	}

	@Override
	public long selectProcessDefinitionCount(String category) {
		return repositoryService.createDeploymentQuery()
				.deploymentCategory(category).count();
	}

	@Override
	public void delProcessDefinition(String deploymentIds) throws Exception {
		String[] idArray = deploymentIds.split(",");
		for (String deploymentId : idArray) {
			// 普通删除，如果当前规则下有正在执行的流程，则抛异常
			repositoryService.deleteDeployment(deploymentId);
			// 级联删除,会删除和当前规则相关的所有信息，包括历史
			// repositoryService.deleteDeployment(deploymentId, true);
		}
	}

	@Override
	public InputStream viewProcessDefinitionImage(String deploymentId) {

		List<String> names = repositoryService
				.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		if (imageName != null) {
			// 通过部署ID和文件名称得到文件的输入流
			InputStream is = repositoryService.getResourceAsStream(
					deploymentId, imageName);
			return is;
		}
		return null;
	}

	@Override
	public List<HistoricProcessInstance> getMyCreateTaskList(String assignee,
			Integer iDisplayStart, Integer iDisplayLength, boolean isPage) {

		List<HistoricProcessInstance> dataList = new ArrayList<HistoricProcessInstance>();

		HistoricProcessInstanceQuery hpiq = historyService
				.createHistoricProcessInstanceQuery().startedBy(assignee)
				.orderByProcessInstanceStartTime().desc();

		if (isPage) {
			dataList = hpiq.listPage(iDisplayStart, iDisplayLength);
		} else {
			dataList = hpiq.list();
		}

		return dataList;

	}

	@Override
	public List<HistoricProcessInstance> getMyCreateTaskList(String assignee,
			String processDefinitionId, Integer iDisplayStart,
			Integer iDisplayLength, boolean isPage) {

		List<HistoricProcessInstance> dataList = new ArrayList<HistoricProcessInstance>();

		HistoricProcessInstanceQuery hpiq = historyService
				.createHistoricProcessInstanceQuery().startedBy(assignee)
				.processDefinitionId(processDefinitionId)
				.orderByProcessInstanceStartTime().desc();

		if (isPage) {
			dataList = hpiq.listPage(iDisplayStart, iDisplayLength);
		} else {
			dataList = hpiq.list();
		}

		return dataList;
	}

	@Override
	public long getMyCreateTaskCount(String assignee) {
		return historyService.createHistoricProcessInstanceQuery()
				.startedBy(assignee).count();
	}

	@Override
	public long getMyCreateTaskCount(String assignee, String category) {
		return historyService.createHistoricProcessInstanceQuery()
				.startedBy(assignee).processDefinitionCategory(category)
				.count();
	}

	@Override
	public void delHistoricProcess(String ids) throws Exception {

		String[] idArray = ids.split(",");

		for (String id : idArray) {
			historyService.deleteHistoricProcessInstance(id);
		}
	}

	@Override
	public List<Task> getMyAssignTask(String assignee, Integer iDisplayStart,
			Integer iDisplayLength) {

		List<Task> tList = taskService.createTaskQuery()
				.taskCandidateOrAssigned(assignee).orderByTaskCreateTime()
				.desc().listPage(iDisplayStart, iDisplayLength);

		return tList;
	}

	@Override
	public List<Task> getMyAssignTask(String assignee, Integer iDisplayStart,
			Integer iDisplayLength, Map<String, String> variables) {
		TaskQuery tq = taskService.createTaskQuery();
		tq.taskCandidateOrAssigned(assignee);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {
				tq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		List<Task> tList = tq.orderByTaskCreateTime().desc()
				.listPage(iDisplayStart, iDisplayLength);

		return tList;
	}

	@Override
	public List<Task> getMyAssignTask(String assignee, String category,
			Integer iDisplayStart, Integer iDisplayLength,
			Map<String, String> variables) {
		TaskQuery tq = taskService.createTaskQuery();
		tq.taskCandidateOrAssigned(assignee);
		// 添加查询类别
		tq.taskCategory(category);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {
				tq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		List<Task> tList = tq.orderByTaskCreateTime().desc()
				.listPage(iDisplayStart, iDisplayLength);

		return tList;
	}

	@Override
	public long getMyAssignCount(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).count();
	}

	@Override
	public long getMyAssignCount(String assignee, Map<String, String> variables) {

		TaskQuery tq = taskService.createTaskQuery();
		tq.taskCandidateOrAssigned(assignee);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {
				tq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}
		return tq.count();
	}

	@Override
	public long getMyAssignCount(String assignee, String category,
			Map<String, String> variables) {
		TaskQuery tq = taskService.createTaskQuery();
		tq.taskCandidateOrAssigned(assignee);
		tq.taskCategory(category);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {
				tq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		return tq.count();
	}

	@Override
	public List<HistoricTaskInstance> getDisposeTaskList(String assignee,
			Integer iDisplayStart, Integer iDisplayLength) {
		HistoricTaskInstanceQuery hiq = historyService
				.createHistoricTaskInstanceQuery();

		hiq.taskInvolvedUser(assignee).finished().orderByTaskCreateTime()
				.desc();

		List<HistoricTaskInstance> hpis = hiq.listPage(iDisplayStart,
				iDisplayLength);

		return hpis;
	}

	@Override
	public List<HistoricTaskInstance> getDisposeTaskList(String assignee,
			Integer iDisplayStart, Integer iDisplayLength,
			Map<String, String> variables) {

		HistoricTaskInstanceQuery hiq = historyService
				.createHistoricTaskInstanceQuery();

		hiq.taskInvolvedUser(assignee);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {

				hiq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		hiq.finished().orderByTaskCreateTime().desc();

		List<HistoricTaskInstance> hpis = hiq.listPage(iDisplayStart,
				iDisplayLength);

		return hpis;
	}

	@Override
	public List<HistoricTaskInstance> getDisposeTaskList(String assignee,
			String category, Integer iDisplayStart, Integer iDisplayLength,
			Map<String, String> variables) {

		HistoricTaskInstanceQuery hiq = historyService
				.createHistoricTaskInstanceQuery();

		hiq.taskInvolvedUser(assignee);
		// 添加查询类别
		hiq.taskCategory(category);

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {

				hiq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		hiq.finished().orderByTaskCreateTime().desc();

		List<HistoricTaskInstance> hpis = hiq.listPage(iDisplayStart,
				iDisplayLength);

		return hpis;
	}

	@Override
	public long getDisposeTaskCount(String assignee, String category,
			Map<String, String> variables) {
		HistoricTaskInstanceQuery hiq = historyService
				.createHistoricTaskInstanceQuery();

		hiq.taskInvolvedUser(assignee);
		// 添加查询类别

		if (null != category && !"".equals(category)) {
			hiq.taskCategory(category);
		}

		if (variables != null) {
			// 添加查询条件
			for (String key : variables.keySet()) {

				hiq.taskVariableValueLikeIgnoreCase(key, variables.get(key));
			}
		}

		long count = hiq.finished().count();

		return count;
	}

	@Override
	public ProcessInstance startFormProcess(String processDefinitionId,
			String assignee, Map<String, String> variables) {

		// 设置流程发起人
		identityService.setAuthenticatedUserId(assignee);
		variables.put("userId", assignee);
		variables.put("processDefinitionId", processDefinitionId);
		variables.put("taskType", "start");

		// 启动流程实例
		// 表单启动
		ProcessInstance processInstance = formService.submitStartFormData(
				processDefinitionId, variables);

		return processInstance;
	}

	@Override
	public void commitProcess(String taskId, Map<String, Object> variables)
			throws Exception {
		// 提交流程到下一步
		taskService.complete(taskId, variables);
	}

	@Override
	public void commitProcess(String taskId, Map<String, Object> variables,
			String activityId) throws Exception {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (activityId == null) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			workFlowUtilService.turnTransition(taskId, activityId, variables);
		}
	}

	@Override
	public void commitFormProcess(String taskId, Map<String, String> variables,
			String activityId) throws Exception {

		if (variables == null) {
			variables = new HashMap<String, String>();
		}
		// 跳转节点为空，默认提交操作
		if (activityId == null) {
			formService.submitTaskFormData(taskId, variables);

		} else {// 流程转向操作
			workFlowUtilService.formTurnTransition(taskId, activityId,
					variables);
		}
	}

	@Override
	public Map<String, Object> getTaskVariables(String processInstanceId,
			String assignee) {

		// 获取当前办理人的任务ID
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstanceId)
				.taskCandidateOrAssigned(assignee).singleResult();
		Map<String, Object> obj = taskService.getVariables(task.getId());

		return obj;
	}

	@Override
	public List<Map<String, Object>> getHistoricVariableInstanceByProcessInstanceId(
			String processInstanceId, String assignee) {

		List<HistoricTaskInstance> lhis = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).taskAssignee(assignee)
				.orderByTaskCreateTime().asc().list();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (lhis.size() > 0) {
			HistoricTaskInstance hti = lhis.get(0);

			List<HistoricVariableInstance> tmp = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(hti.getProcessInstanceId())
					.taskId(hti.getId()).list();

			Map<String, Object> map = new HashMap<String, Object>();
			for (HistoricVariableInstance historicVariableInstance : tmp) {
				map.put(historicVariableInstance.getVariableName(),
						historicVariableInstance.getValue());
			}
			list.add(map);

			// Map<String, Object> tmp = new HashMap<String, Object>();
			//
			// // 根据taskId获取任务变量
			// List<HistoricDetail> listT = historyService
			// .createHistoricDetailQuery().taskId(hti.getId()).list();
			//
			// for (HistoricDetail historicDetail : listT) {
			// HistoricFormPropertyEntity variable =
			// (HistoricFormPropertyEntity) historicDetail;
			// tmp.put(variable.getPropertyId(), variable.getPropertyValue());
			// }
			//
			// list.add(tmp);

		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getApproveList(String taskId)
			throws Exception {

		List<Map<String, Object>> variableMapList = new ArrayList<Map<String, Object>>();

		// 取得流程定义
		ProcessInstance processInstance = workFlowUtilService
				.findProcessInstanceByTaskId(taskId);

		// 取得最后完成的任务Id
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstance.getId()).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();

		for (HistoricTaskInstance historicTaskInstance : htiList) {

			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("taskId", historicTaskInstance.getId());// 添加节点ID
			tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称

			List<HistoricVariableInstance> list = historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(
							historicTaskInstance.getProcessInstanceId())
					.taskId(historicTaskInstance.getId()).list();

			for (HistoricVariableInstance historicVariableInstance : list) {
				tmp.put(historicVariableInstance.getVariableName(),
						historicVariableInstance.getValue());
			}
			variableMapList.add(tmp);

			//
			// // 根据taskId获取任务变量
			// List<HistoricDetail> list = historyService
			// .createHistoricDetailQuery()
			// .taskId(historicTaskInstance.getId()).list();
			//
			// for (HistoricDetail historicDetail : list) {
			// HistoricFormPropertyEntity variable =
			// (HistoricFormPropertyEntity) historicDetail;
			// tmp.put(variable.getPropertyId(), variable.getPropertyValue());
			// }
			//
			// variableMapList.add(tmp);
		}

		return variableMapList;
	}

	@Override
	public List<Map<String, Object>> getApproveListByProcessInstanceId(
			String processInstanceId) throws Exception {

		List<Map<String, Object>> variableMapList = new ArrayList<Map<String, Object>>();

		// 取得最后完成的任务Id
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();

		for (HistoricTaskInstance historicTaskInstance : htiList) {

			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("taskId", historicTaskInstance.getId());// 添加节点ID
			tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称

			List<HistoricVariableInstance> list = historyService
					.createHistoricVariableInstanceQuery()
					.taskId(historicTaskInstance.getId()).list();

			for (HistoricVariableInstance historicVariableInstance : list) {
				tmp.put(historicVariableInstance.getVariableName(),
						historicVariableInstance.getValue());
			}
			variableMapList.add(tmp);

		}

		return variableMapList;
	}

	@Override
	public List<Map<String, Object>> getAllVariablesList(
			String processInstanceId, String assignee) throws Exception {

		List<Map<String, Object>> variableMapList = new ArrayList<Map<String, Object>>();

		// 取得全部完成的任务
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();

		// 获取我自己参与的任务
		List<HistoricTaskInstance> htiTmpList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.taskCandidateUser(assignee).finished()
				.orderByHistoricTaskInstanceEndTime().desc().list();

		HistoricTaskInstance hti = null;

		if (htiTmpList.size() > 0) {
			hti = htiTmpList.get(0);
		}

		if (hti == null) {
			return variableMapList;
		}

		for (HistoricTaskInstance historicTaskInstance : htiList) {

			// 比较处理的时间，如果是在之后处理的，不能够获取
			if (historicTaskInstance.getEndTime().getTime() <= hti.getEndTime()
					.getTime()) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("taskId", historicTaskInstance.getId());// 添加节点ID
				tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称
				// 根据taskId获取任务变量
				List<HistoricDetail> list = historyService
						.createHistoricDetailQuery()
						.taskId(historicTaskInstance.getId()).list();

				for (HistoricDetail historicDetail : list) {
					HistoricFormPropertyEntity variable = (HistoricFormPropertyEntity) historicDetail;
					tmp.put(variable.getPropertyId(),
							variable.getPropertyValue());
				}

				variableMapList.add(tmp);
			}

		}

		return variableMapList;

	}

	@Override
	public List<Map<String, Object>> getAllVariablesListByProcessInstanceId(
			String processInstanceId, String assignee) throws Exception {

		List<Map<String, Object>> variableMapList = new ArrayList<Map<String, Object>>();

		// 取得全部完成的任务
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();

		// 获取我自己参与的任务
		List<HistoricTaskInstance> htiTmpList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.taskCandidateUser(assignee).finished()
				.orderByHistoricTaskInstanceEndTime().desc().list();

		HistoricTaskInstance hti = null;

		if (htiTmpList.size() > 0) {
			hti = htiTmpList.get(0);
		}

		if (hti == null) {
			return variableMapList;
		}

		for (HistoricTaskInstance historicTaskInstance : htiList) {

			// 比较处理的时间，如果是在之后处理的，不能够获取
			if (historicTaskInstance.getEndTime().getTime() <= hti.getEndTime()
					.getTime()) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("taskId", historicTaskInstance.getId());// 添加节点ID
				tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称
				// 根据taskId获取任务变量
				List<HistoricDetail> list = historyService
						.createHistoricDetailQuery()
						.taskId(historicTaskInstance.getId()).list();

				for (HistoricDetail historicDetail : list) {
					HistoricFormPropertyEntity variable = (HistoricFormPropertyEntity) historicDetail;
					tmp.put(variable.getPropertyId(),
							variable.getPropertyValue());
				}

				variableMapList.add(tmp);
			}

		}

		return variableMapList;
	}

	/**
	 * 驳回上一步
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-13
	 * @return
	 */
	public void backToLast(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {

		// 查询本节点发起的会签任务，并结束
		List<Task> tasks = taskService.createTaskQuery().taskId(taskId)
				.taskDescription("jointProcess").list();

		for (Task task : tasks) {
			commitProcess(task.getId(), null, null);
		}

		// 查找所有并行任务节点，同时驳回
		List<Task> taskList = workFlowUtilService
				.findTaskListByKey(workFlowUtilService
						.findProcessInstanceByTaskId(taskId).getId(),
						workFlowUtilService.findTaskById(taskId)
								.getTaskDefinitionKey());

		for (Task task : taskList) {
			commitProcess(task.getId(), variables, activityId);
			// TaskEntity te=workFlowUtilService.findTaskById(task.getId());
			// taskFlowControlService.jump(te, activityId);

		}

	}

	@Override
	public void backFormToLast(String taskId, String activityId,
			Map<String, String> variables) throws Exception {
		// 查询本节点发起的会签任务，并结束
		List<Task> tasks = taskService.createTaskQuery().taskId(taskId)
				.taskDescription("jointProcess").list();

		for (Task task : tasks) {
			commitFormProcess(task.getId(), null, null);
		}

		// 查找所有并行任务节点，同时驳回
		List<Task> taskList = workFlowUtilService
				.findTaskListByKey(workFlowUtilService
						.findProcessInstanceByTaskId(taskId).getId(),
						workFlowUtilService.findTaskById(taskId)
								.getTaskDefinitionKey());

		for (Task task : taskList) {
			commitFormProcess(task.getId(), variables, activityId);

		}

	}

	@Override
	public void countersignProcess(String taskId, List<String> userCodes)
			throws Exception {
		for (String userCode : userCodes) {
			TaskEntity task = (TaskEntity) taskService.newTask(IDGenerator
					.getUUID());
			task.setAssignee(userCode);
			task.setName(workFlowUtilService.findTaskById(taskId).getName()
					+ "-会签");
			task.setProcessDefinitionId(workFlowUtilService
					.findProcessDefinitionEntityByTaskId(taskId).getId());
			task.setProcessInstanceId(workFlowUtilService
					.findProcessInstanceByTaskId(taskId).getId());
			task.setParentTaskId(taskId);
			task.setDescription("jointProcess");
			taskService.saveTask(task);
		}
	}

	/**
	 * 转办流程
	 * 
	 * @param taskId
	 *            当前任务节点ID
	 * @param userCode
	 *            被转办人Code
	 */
	public void transferAssignee(String taskId, String userCode) {
		taskService.setAssignee(taskId, userCode);
	}

	@Override
	public Integer processIsEnd(String processInstanceId) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		if (pi == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public void submitTaskFormData(ProcessInstance processInstance,
			Map<String, String> formProperties, String assignee) {

		formProperties.put("processDefinitionId",
				processInstance.getProcessDefinitionId());

		// app发起流程后，马上提交到下一步
		Task task = workFlowUtilService
				.findTaskByProcessInstanceId(processInstance.getId());

		for (String key : formProperties.keySet()) {
			taskService.setVariableLocal(task.getId(), key,
					formProperties.get(key));
		}

		formProperties.put("taskType", "");

		formService.submitTaskFormData(task.getId(), formProperties);

	}

	@Override
	public void submitTaskFormData(ProcessInstance processInstance,
			List<String> approverList, Map<String, String> formProperties,
			String assignee) {

		// app发起流程后，马上提交到下一步
		Task task = workFlowUtilService
				.findTaskByProcessInstanceId(processInstance.getId());

		formProperties.put("processDefinitionId",
				processInstance.getProcessDefinitionId());

		if (approverList != null && approverList.size() > 0) {
			String tmp = "";
			for (String approver : approverList) {
				// 添加组用户
				tmp += approver + ",";
			}
			tmp = tmp.substring(0, tmp.lastIndexOf(","));
			formProperties.put("approverList", tmp);
		}

		for (String key : formProperties.keySet()) {
			taskService.setVariableLocal(task.getId(), key,
					formProperties.get(key));
		}

		formProperties.put("taskType", "dynamic");

		formService.submitTaskFormData(task.getId(), formProperties);

	}

	@Override
	public void submitTaskFormData(String taskId, List<String> approverList,
			Map<String, String> formProperties, String assignee) {

		try {

			ProcessDefinitionEntity pde = workFlowUtilService
					.findProcessDefinitionEntityByTaskId(taskId);

			formProperties.put("processDefinitionId", pde.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (approverList != null && approverList.size() > 0) {
			String tmp = "";
			for (String approver : approverList) {
				// 添加组用户
				tmp += approver + ",";
			}
			tmp = tmp.substring(0, tmp.lastIndexOf(","));
			formProperties.put("approverList", tmp);
		}

		for (String key : formProperties.keySet()) {
			taskService.setVariableLocal(taskId, key, formProperties.get(key));
		}

		formProperties.put("taskType", "dynamic");
		formService.submitTaskFormData(taskId, formProperties);

	}

	@Override
	public void submitTaskFormData(String taskId,
			Map<String, String> formProperties, String assignee) {

		try {

			ProcessDefinitionEntity pde = workFlowUtilService
					.findProcessDefinitionEntityByTaskId(taskId);

			formProperties.put("processDefinitionId", pde.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String key : formProperties.keySet()) {
			taskService.setVariableLocal(taskId, key, formProperties.get(key));
		}

		formProperties.put("taskType", "");
		formService.submitTaskFormData(taskId, formProperties);

	}

	@Override
	public List<Map<String, Object>> getTaskAndVariables(
			String processInstanceId) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		// ProcessInstance processInstance = runtimeService
		// .createProcessInstanceQuery()
		// .processInstanceId(processInstanceId).singleResult();
		//

		// 判断该流程是否结束了
		Integer isEnd = processIsEnd(processInstanceId);

		HistoricProcessInstance hpi = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		Collection<FlowElement> collection = workFlowUtilService
				.getFlowElement(hpi.getProcessDefinitionId());
		//
		// List<ActivityImpl> alist = workFlowUtilService
		// .findActivitiImplList(processInstance.getProcessDefinitionId());

		// 取得最后完成的任务Id
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();

		// // 循环节点
		// for (FlowElement flowElement : collection) {
		//
		// if (flowElement instanceof UserTask) {
		// String elementId = flowElement.getId();
		//
		// Map<String, Object> tmp = new HashMap<String, Object>();
		// tmp.put("elementName", flowElement.getName());// 添加节点名称
		// tmp.put("priority", ((UserTask) flowElement).getPriority());
		//
		// // 循环实例
		// for (HistoricTaskInstance historicTaskInstance : htiList) {
		//
		// // 判断是否相等
		// if (historicTaskInstance.getTaskDefinitionKey().equals(
		// elementId)) {
		// tmp.put("elementName", flowElement.getName());// 添加节点名称
		// tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称
		//
		// // 获取节点变量
		// List<HistoricVariableInstance> list = historyService
		// .createHistoricVariableInstanceQuery()
		// .taskId(historicTaskInstance.getId()).list();
		//
		// for (HistoricVariableInstance historicVariableInstance : list) {
		// tmp.put(historicVariableInstance.getVariableName(),
		// historicVariableInstance.getValue());
		// }
		//
		// break;
		// }
		// }
		//
		// result.add(tmp);
		// }
		// }

		// 循环实例
		for (HistoricTaskInstance historicTaskInstance : htiList) {

			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("elementName", historicTaskInstance.getName());// 添加节点名称
			tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称

			// 获取节点变量
			List<HistoricVariableInstance> list = historyService
					.createHistoricVariableInstanceQuery()
					.taskId(historicTaskInstance.getId()).list();

			for (HistoricVariableInstance historicVariableInstance : list) {
				tmp.put(historicVariableInstance.getVariableName(),
						historicVariableInstance.getValue());
			}

			result.add(tmp);
		}


//		// 冒泡排序
//		for (int i = 0; i < result.size() - 1; i++) {
//			for (int j = 0; j < result.size() - 1 - i; j++) {
//
//				Map<String, Object> temp = new HashMap<String, Object>();
//
//				Map<String, Object> tmpA = result.get(j);
//				Map<String, Object> tmpB = result.get(j + 1);
//
//				// 如果不存在就直接过了
//				if (tmpA.get("priority") == null
//						|| tmpB.get("priority") == null) {
//					continue;
//				}
//
//				Integer tmpAp = Integer
//						.valueOf(tmpA.get("priority").toString());
//				Integer tmpBp = Integer
//						.valueOf(tmpB.get("priority").toString());
//
//				if (tmpAp > tmpBp) {
//					temp = tmpA;
//					result.set(j, tmpB);
//					result.set(j + 1, temp);
//				}
//			}
//		}

		Map<String, Object> start = new HashMap<String, Object>();
		start.put("elementName", "开始");
		result.add(0, start);

		Map<String, Object> end = new HashMap<String, Object>();
		end.put("elementName", "结束");
		end.put("flag", isEnd);
		result.add(end);

		return result;

	}

	@Override
	public List<Map<String, Object>> getAllProcessVariables(
			String processInstanceId) {
		List<Map<String, Object>> variableMapList = new ArrayList<Map<String, Object>>();

		// 取得最后完成的任务Id
		List<HistoricTaskInstance> htiList = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricTaskInstanceEndTime().asc().list();

		for (HistoricTaskInstance historicTaskInstance : htiList) {

			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("taskId", historicTaskInstance.getId());// 添加节点ID
			tmp.put("taskName", historicTaskInstance.getName());// 添加节点名称

			List<HistoricVariableInstance> list = historyService
					.createHistoricVariableInstanceQuery()
					.taskId(historicTaskInstance.getId()).list();

			for (HistoricVariableInstance historicVariableInstance : list) {
				tmp.put(historicVariableInstance.getVariableName(),
						historicVariableInstance.getValue());
			}
			variableMapList.add(tmp);

		}

		return variableMapList;
	}

	@Override
	public Map<String, Object> getHistoricTaskVariables(String historicTaskId) {

		Map<String, Object> tmp = new HashMap<String, Object>();

		List<HistoricVariableInstance> list = historyService
				.createHistoricVariableInstanceQuery().taskId(historicTaskId)
				.list();

		for (HistoricVariableInstance historicVariableInstance : list) {
			tmp.put(historicVariableInstance.getVariableName(),
					historicVariableInstance.getValue());
		}

		return tmp;
	}

	@Override
	public int judgeTaskById(String taskId) {

		int reuslt = 0;

		String tempId;

		Collection<FlowElement> collection;

		// 获取任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task != null) {
			// 获取流程路线
			collection = workFlowUtilService.getFlowElement(task
					.getProcessDefinitionId());

			tempId = task.getTaskDefinitionKey();

		} else {
			HistoricTaskInstance htask = historyService
					.createHistoricTaskInstanceQuery().taskId(taskId)
					.singleResult();
			// 获取流程路线
			collection = workFlowUtilService.getFlowElement(htask
					.getProcessDefinitionId());

			tempId = htask.getTaskDefinitionKey();
		}

		// 循环节点
		for (FlowElement flowElement : collection) {

			// 判断是否是连线
			if (flowElement instanceof SequenceFlow) {

				// 获取目标节点
				String targetRef = ((SequenceFlow) flowElement).getTargetRef();

				// 判断目标节点是不是本节点
				if (targetRef.equals(tempId)) {

					// 获取来源节点
					String sourceRef = ((SequenceFlow) flowElement)
							.getSourceRef();

					for (FlowElement flowElement2 : collection) {
						String id = flowElement2.getId();
						// 判断来源节点是否是开始节点
						if (id.equals(sourceRef)) {
							if (flowElement2 instanceof StartEvent) {
								reuslt = 1;
							}
						}
					}
				}
			}
		}

		return reuslt;
	}

	@Override
	public void stopProcess(String taskId, Map<String, String> variables)
			throws Exception {

		ActivityImpl activityImpl = workFlowUtilService
				.findEndActivitiImpl(taskId);
		commitFormProcess(taskId, variables, activityImpl.getId());
	}

	@Override
	public void stopAndDeleteProcess(String taskId) throws Exception {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		ActivityImpl activityImpl = workFlowUtilService
				.findEndActivitiImpl(taskId);
		commitFormProcess(taskId, null, activityImpl.getId());

		// 删除流程实例
		historyService.deleteHistoricProcessInstance(processInstanceId);

	}
	
	/**
     * 根据条件判断下一个任务节点是不是需要动态指定（判断下一个任务节点的assign为空）
     * 
     * @param taskId
     * @param condition(条件表达式如${direction == "驳回上一步"},则可以传入“驳回”)
     * @return
     */
	@Override
	public int isNextTaskDynamicAssign(String taskId, String condition) {
        int result = 0;
        String tempId;
        Collection<FlowElement> collection;
        // 获取任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task != null) {
            // 获取流程路线
            collection = workFlowUtilService.getFlowElement(task.getProcessDefinitionId());
            tempId = task.getTaskDefinitionKey();

        } else {
            HistoricTaskInstance htask = historyService
                    .createHistoricTaskInstanceQuery().taskId(taskId)
                    .singleResult();
            // 获取流程路线
            collection = workFlowUtilService.getFlowElement(htask.getProcessDefinitionId());

            tempId = htask.getTaskDefinitionKey();
        }
        // 循环节点
        for (FlowElement flowElement : collection) {
            // 判断是否是连线
            if (flowElement instanceof SequenceFlow) {
                // 获取源节点
                String sourceRef = ((SequenceFlow) flowElement).getSourceRef();
                // 判断源节点是不是本节点
                if (sourceRef.equals(tempId)) {
                    //判断条件
                    if(((SequenceFlow) flowElement).getConditionExpression().indexOf(condition) > -1){
                        String targetRef = ((SequenceFlow) flowElement).getTargetRef();
                        
                        for (FlowElement flowElement2 : collection) {
                            //判断是不是任务节点
                            if(flowElement2 instanceof UserTask && flowElement2.getId().equals(targetRef)){
                                //是任务节点且审批人为空，则表示需要指定审批人
                                if(((UserTask) flowElement2).getAssignee() == null || "".equals(((UserTask) flowElement2).getAssignee())){
                                    result = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
	}

}

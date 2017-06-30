package com.ehome.activiti.services.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehome.activiti.listener.TaskListenerImpl;
import com.ehome.activiti.services.WorkFlowUtilService;

@Service("workFlowUtilService")
public class WorkFlowUtilServiceImp implements WorkFlowUtilService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private FormService formService;

	@Override
	public Deployment getDeploymentById(String deploymentId) {
		Deployment deployment = repositoryService.createDeploymentQuery()
				.deploymentId(deploymentId).singleResult();

		return deployment;
	}

	@Override
	public Collection<FlowElement> getFlowElement(String processDefinitionId) {
		BpmnModel model = repositoryService.getBpmnModel(processDefinitionId);

		Collection<FlowElement> flowElements = new ArrayList<FlowElement>();

		if (model != null) {
			flowElements = model.getMainProcess().getFlowElements();
		}

		return flowElements;

	}

	@Override
	public TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery()
				.taskId(taskId).singleResult();

		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	public List<Task> findTaskListByKey(String processInstanceId, String key) {
		return taskService.createTaskQuery()
				.processInstanceId(processInstanceId).taskDefinitionKey(key)
				.list();
	}

	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId)
			throws Exception {
		// 找到流程实例

		List<ProcessInstance> list = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(findTaskById(taskId).getProcessInstanceId())
				.list();

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
			// throw new Exception("流程实例未找到!");
		}
	}

	@Override
	public ProcessDefinition findProcessDefinitionByDefinitionName(String name)
			throws Exception {
		// 找到流程实例
		// List<ProcessDefinition> list = repositoryService
		// .createProcessDefinitionQuery().processDefinitionName(name)
		// .orderByProcessDefinitionVersion().desc().list();
		//
		// if (list.size() <= 0) {
		// throw new Exception("流程实例未找到!");
		// }
		// return list.get(0);

		// 找到流程实例

		List<Deployment> resultList = repositoryService.createDeploymentQuery()
				.deploymentName(name).orderByDeploymenTime().desc().list();

		if (resultList.size() <= 0) {
			throw new Exception("流程实例未找到!");
		} else {

			List<ProcessDefinition> list = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(resultList.get(0).getId()).list();

			if (list.size() > 0) {

				return list.get(0);
			} else {
				throw new Exception("流程实例未找到!");
			}
		}

	}

	@Override
	public ProcessDefinition findProcessDefinitionByDefinitionId(String Id)
			throws Exception {
		// 找到流程实例
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(Id)
				.singleResult();

		if (processDefinition == null) {
			throw new Exception("流程实例未找到!");
		}
		return processDefinition;
	}

	@Override
	public ProcessDefinition findProcessDefinitionByTenantId(String category,
			String tenantId) throws Exception {
		// 找到流程实例

		List<Deployment> resultList = repositoryService.createDeploymentQuery()
				.deploymentTenantId(tenantId).deploymentCategory(category)
				.orderByDeploymenTime().desc().list();

		if (resultList.size() <= 0) {
			return null;
		} else {

			List<ProcessDefinition> list = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(resultList.get(0).getId()).list();

			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	}

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws Exception {
		// 取得流程定义
		ProcessInstance pi = findProcessInstanceByTaskId(taskId);

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(pi.getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 获取上一步taskId
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public String findLastTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 取得最后完成的任务Id
		HistoricTaskInstance hti = historyService
				.createHistoricTaskInstanceQuery()
				.processDefinitionId(processDefinition.getId())
				.orderByHistoricTaskInstanceEndTime().desc().list().get(0);

		return hti.getId();
	}

	@Override
	public String findLastActivityId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 取得最后完成的任务Id
		HistoricTaskInstance hti = historyService
				.createHistoricTaskInstanceQuery()
				.processDefinitionId(processDefinition.getId())
				.orderByHistoricTaskInstanceEndTime().desc().list().get(0);

		return hti.getTaskDefinitionKey();
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (activityId == null) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}

		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl
						.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = processDefinition.findActivity(activityId);

		return activityImpl;
	}

	@Override
	public ActivityImpl findEndActivitiImpl(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		for (ActivityImpl activityImpl : processDefinition.getActivities()) {
			List<PvmTransition> pvmTransitionList = activityImpl
					.getOutgoingTransitions();
			if (pvmTransitionList.isEmpty()) {
				return activityImpl;
			}
		}

		return null;
	}

	@Override
	public List<ActivityImpl> findActivitiImplList(String processDefinitionId) {
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);

		if (processDefinition != null) {

			List<ActivityImpl> list = processDefinition.getActivities();
			return list;
		}

		return null;

	}

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	public List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	public void restoreTransition(ActivityImpl activityImpl,
			List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl
				.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}

	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	public void turnTransition(String taskId, String activityId,
			Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);

		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	@Override
	public void formTurnTransition(String taskId, String activityId,
			Map<String, String> variables) throws Exception {

		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		formService.submitTaskFormData(taskId, variables);

		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	/**
	 * 流程图高亮追踪
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param processInstanceId
	 * @param response
	 */
	public InputStream getProcessView(String processInstanceId) {

		// 获取历史流程实例
		HistoricProcessInstance processInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance
				.getProcessDefinitionId());

		ProcessDiagramGenerator diagramGenerator = processEngine
				.getProcessEngineConfiguration().getProcessDiagramGenerator();

		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processInstance.getProcessDefinitionId());

		List<HistoricActivityInstance> highLightedActivitList = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).list();
		// 高亮环节id集合
		List<String> highLightedActivitis = new ArrayList<String>();
		// 高亮线路id集合
		List<String> highLightedFlows = getHighLightedFlows(definitionEntity,
				highLightedActivitList);

		for (HistoricActivityInstance tempActivity : highLightedActivitList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}

		// 中文显示的是口口口，设置字体就好了
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel,
				"png", highLightedActivitis, highLightedFlows, processEngine
						.getProcessEngineConfiguration().getActivityFontName(),
				processEngine.getProcessEngineConfiguration()
						.getLabelFontName(), null, null, 1.0);

		return imageStream;

	}

	/**
	 * 获取需要高亮的线
	 * 
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	private List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1)
							.getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances
						.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances
						.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(
						activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl
					.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}

	@Override
	public String getTaskFormKey(Task task) {
		return formService.getTaskFormKey(task.getProcessDefinitionId(),
				task.getTaskDefinitionKey());
	}

	@Override
	public Task findTaskByProcessInstanceId(String processInstanceId) {
		TaskQuery taskQuery = taskService.createTaskQuery()
				.processInstanceId(processInstanceId)
				.orderByProcessInstanceId().desc();

		List<Task> tasks = taskQuery.list();
		if (tasks.size() == 0) {
			return null;
		}
		Task task = tasks.get(0);

		return task;
	}

	@Override
	public StartEvent createStartEvent() {
		StartEvent startEvent = new StartEvent();
		startEvent.setId("start");

		return startEvent;
	}

	@Override
	public EndEvent createEndEvent() {
		EndEvent endEvent = new EndEvent();
		endEvent.setId("end");
		return endEvent;
	}

	@Override
	public UserTask createUserTask(String id, String name) {
		UserTask userTask = new UserTask();
		userTask.setName(name);
		userTask.setId(id);
		// 通过设置这里，传到监听，用来判断权限
		ActivitiListener al = new ActivitiListener();
		al.setEvent("create");
		al.setImplementationType("class");
		al.setImplementation(TaskListenerImpl.class.getName());

		userTask.getTaskListeners().add(al);
		return userTask;
	}

	@Override
	public SequenceFlow createSequenceFlow(String from, String to,
			String conditionExpression) {
		SequenceFlow flow = new SequenceFlow();
		flow.setSourceRef(from);
		flow.setTargetRef(to);
		if (conditionExpression != null) {
			// 添加流转条件
			flow.setConditionExpression(conditionExpression);
		}
		return flow;
	}

	@Override
	public String getHistoricFormKeyById(String historicTaskId) {

		HistoricTaskInstance hti = historyService
				.createHistoricTaskInstanceQuery().taskId(historicTaskId)
				.singleResult();

		if (hti != null) {
			return hti.getFormKey();
		}

		return null;
	}

	@Override
	public String getHistoricFormKeyByProcessInstanceId(
			String processInstanceId, String assignee) {

		List<HistoricTaskInstance> list = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.taskInvolvedUser(assignee).orderByTaskCreateTime().desc()
				.list();

		if (list.size() > 0) {
			return list.get(0).getFormKey();
		}

		return null;
	}

	@Override
	public ProcessInstance findProcessInstanceById(String processInstanceId) {

		// 找到流程实例
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		return processInstance;
	}

	@Override
	public Map<String, Object> findHistoryVariablesById(String historicTaskId) {

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
	public Map<String, Object> findRuntimeVariablesById(String taskId) {

		// Map<String, Object> tmp=runtimeService.getVariables(taskId);
		Map<String, Object> tmp = taskService.getVariables(taskId);
		return tmp;
	}
}

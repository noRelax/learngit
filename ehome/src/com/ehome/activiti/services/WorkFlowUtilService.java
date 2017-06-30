package com.ehome.activiti.services;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface WorkFlowUtilService {

	/**
	 * 通过ID查找部署模型
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-28
	 * @return
	 */
	public Deployment getDeploymentById(String deploymentId);

	/**
	 * 获取全部节点信息
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-28
	 * @param processDefinitionId
	 * @return
	 */
	public Collection<FlowElement> getFlowElement(String processDefinitionId);

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public TaskEntity findTaskById(String taskId) throws Exception;

	/**
	 * 根据流程实例ID和任务key值查询所有同级任务集合
	 * 
	 * @param processInstanceId
	 * @param key
	 * @return
	 */
	public List<Task> findTaskListByKey(String processInstanceId, String key);

	/**
	 * 根据任务ID获取对应的流程实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessInstance findProcessInstanceByTaskId(String taskId)
			throws Exception;

	/**
	 * 更加部署名称获取部署实例
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-21
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public ProcessDefinition findProcessDefinitionByDefinitionName(String name)
			throws Exception;

	public ProcessDefinition findProcessDefinitionByDefinitionId(String Id)
			throws Exception;

	/**
	 * 根据工会ID获取流程部署
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-29
	 * @param tenantId
	 *            租借ID，即工会ID，就是组织机构
	 * @return
	 * @throws Exception
	 */
	public ProcessDefinition findProcessDefinitionByTenantId(String category,
			String tenantId) throws Exception;

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
			String taskId) throws Exception;

	/**
	 * 获取上一步taskId
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	public String findLastTaskId(String taskId) throws Exception;

	/**
	 * 获取上一步ActivityId
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-8
	 * @param taskId
	 * @return
	 */
	public String findLastActivityId(String taskId) throws Exception;

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl findActivitiImpl(String taskId, String activityId)
			throws Exception;

	/**
	 * 查找终止节点
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-4-6
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl findEndActivitiImpl(String taskId) throws Exception;

	/**
	 * 获取全部节点信息
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-28
	 * @param processDefinitionId
	 * @return
	 */
	public List<ActivityImpl> findActivitiImplList(String processDefinitionId);

	/**
	 * 获取配置的formKey
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-21
	 * @param task
	 * @return
	 */
	public String getTaskFormKey(Task task);

	/**
	 * 获取任务Task
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-21
	 * @param processInstanceId
	 * @return
	 */
	public Task findTaskByProcessInstanceId(String processInstanceId);

	// **********************流程转向操作*****************************

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	public List<PvmTransition> clearTransition(ActivityImpl activityImpl);

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	public void restoreTransition(ActivityImpl activityImpl,
			List<PvmTransition> oriPvmTransitionList);

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
			Map<String, Object> variables) throws Exception;

	public void formTurnTransition(String taskId, String activityId,
			Map<String, String> variables) throws Exception;

	// ************************绘制高亮路线***********************************

	/**
	 * 流程图高亮追踪
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param processInstanceId
	 * @param response
	 */
	public InputStream getProcessView(String processInstanceId)
			throws Exception;

	// ************************流程动态创建**********************************

	/**
	 * 创建开始节点
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-3
	 * @return
	 */
	public StartEvent createStartEvent();

	/**
	 * 创建结束节点
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-3
	 * @return
	 */
	public EndEvent createEndEvent();

	/**
	 * 创建task:标识、名称、经办人
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-3
	 * @param id
	 * @param name
	 * @return
	 */
	public UserTask createUserTask(String id, String name);

	/**
	 * 创建箭头：从哪来、到哪去
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-3
	 * @param from
	 * @param to
	 * @param conditionExpression
	 *            流转条件
	 * @return
	 */
	public SequenceFlow createSequenceFlow(String from, String to,
			String conditionExpression);

	/**
	 * 获取历史表单编号
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-29
	 * @param historicTaskId
	 * @return
	 */
	public String getHistoricFormKeyById(String historicTaskId);

	public String getHistoricFormKeyByProcessInstanceId(
			String processInstanceId, String assignee);

	/**
	 * 通过流程实例ID查找流程实例
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInstance findProcessInstanceById(String processInstanceId);

	/**
	 * 获取历史节点变量根据id
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-4-11
	 * @param taskId
	 */
	public Map<String, Object> findHistoryVariablesById(String historicTaskId);
	
	
	/**
	 * 获取运行时变量
	 * @param taskId
	 * @return
	 */
	public  Map<String, Object> findRuntimeVariablesById(String taskId);

}

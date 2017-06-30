package com.ehome.activiti.utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehome.activiti.services.WorkFlowUtilService;

/**
 * 节点自由跳转
 * 
 * @author kokJuis
 * @version 1.0
 * @date 2017-1-21
 */

@Service("taskFlowControlService")
public class TaskFlowControlService {

	@Autowired
	private WorkFlowUtilService workFlowUtilService;
	@Autowired
	private ProcessEngine processEngine;

	/**
	 * 跳转至指定活动节点
	 * 
	 * @param targetTaskDefinitionKey
	 * @throws Exception
	 */
	public void jump(TaskEntity currentTaskEntity,
			String targetTaskDefinitionKey) throws Exception {
		jump2(currentTaskEntity, targetTaskDefinitionKey);
	}

	/**
	 * 
	 * @param currentTaskEntity
	 *            当前任务节点
	 * @param targetTaskDefinitionKey
	 *            目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	private void jump2(final TaskEntity currentTaskEntity,
			String targetTaskDefinitionKey) throws Exception {

		// 当前活动节点ID
		String activityId = workFlowUtilService.findTaskById(
				currentTaskEntity.getId()).getTaskDefinitionKey();

		final ActivityImpl activity = workFlowUtilService.findActivitiImpl(
				currentTaskEntity.getId(), activityId);

		final ExecutionEntity execution = (ExecutionEntity) processEngine
				.getRuntimeService().createExecutionQuery()
				.executionId(currentTaskEntity.getExecutionId()).singleResult();

		final TaskService taskService = processEngine.getTaskService();

		// 包装一个Command对象
		((RuntimeServiceImpl) processEngine.getRuntimeService())
				.getCommandExecutor().execute(new Command<java.lang.Void>() {
					@Override
					public Void execute(CommandContext commandContext) {
						// 创建新任务
						execution.setActivity(activity);
						execution.executeActivity(activity);

						// 删除当前的任务
						// 不能删除当前正在执行的任务，所以要先清除掉关联
						currentTaskEntity.setExecutionId(null);
						taskService.saveTask(currentTaskEntity);
						taskService.deleteTask(currentTaskEntity.getId(), true);

						return null;
					}
				});
	}
}

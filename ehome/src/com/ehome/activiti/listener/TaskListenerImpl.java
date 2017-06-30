package com.ehome.activiti.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.cloud.sys.service.impl.OrgainServiceImpl;
import com.ehome.core.frame.SpringContextHolder;

public class TaskListenerImpl implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask task) {

		// 获取service
		IOrgainService orgainService = (IOrgainService) SpringContextHolder
				.getBean(OrgainServiceImpl.class);

		IdentityService identityService = (IdentityService) SpringContextHolder
				.getBean(IdentityService.class);

		WorkFlowUtilService workFlowUtilService = SpringContextHolder
				.getBean(WorkFlowUtilService.class);

		try {

			Object taskType = task.getVariable("taskType");

			if (null != taskType) {
				task.setVariableLocal("taskType", taskType);
			}

			Object processDefinitionId = task
					.getVariable("processDefinitionId");
			if (processDefinitionId != null) {
				ProcessDefinition pd = workFlowUtilService
						.findProcessDefinitionByDefinitionId(processDefinitionId
								.toString());

				Deployment deployment = workFlowUtilService
						.getDeploymentById(pd.getDeploymentId());
				if (deployment != null) {
					task.setCategory(deployment.getCategory());
				}

			}

			// 看看是否存在动态指定人，不存在则按机构查找
			Object approverList = task.getVariable("approverList");

			if (approverList != null) {

				String[] tmp = approverList.toString().split(",");

				for (String string : tmp) {
					task.setAssignee(null);
					// 添加组用户
					task.addCandidateUser(string);
				}

				// 设置参与者

				task.setAssignee(null);
				// 设置以后及时清楚，否则会影响其他节点的运行
				task.removeVariable("approverList");

			} else {

				// 查找工作流设置的机构id
				String assignee = task.getAssignee();

				if (assignee != null) {
					// 查找该机构下的人员
					Integer tmp = Integer.parseInt(assignee.trim());
					// List<Map<String, Object>> orgList = orgainService
					// .queryUsersByOrgainId(tmp);

					List<Map<String, Object>> orgList = orgainService
							.queryUsersByDeptId(tmp);

					if (orgList.size() > 0) {

						task.setAssignee(null);

						for (Map<String, Object> map : orgList) {
							if (map != null) {
								String id = String.valueOf(map.get("id"));
								// 添加组用户
								task.addCandidateUser(id);
								// 添加组织机构ID
								task.setVariableLocal("organizationId",
										assignee);
							}
						}
					} else {
						// 没有人员弹出个异常
						throw new Exception("该机构下没有任何人员");
					}

				} else {

					Object userId = task.getVariable("userId");

					if (userId != null) {
						// 设置流程发起人
						identityService.setAuthenticatedUserId(userId + "");
						// task.setAssignee(userId + "");
						task.addCandidateUser(userId + "");
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("指定任务代办人失败：" + e.getMessage());
		}
	}

}

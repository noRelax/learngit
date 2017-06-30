package com.ehome.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ehome.activiti.general.Code;
import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.util.DateUtils;

@Controller
@RequestMapping("/process/*")
public class ProcessController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;

	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private WorkFlowUtilService workFlowUtilService;

	// 引用水洪的
	@Resource
	private IOrgainService orgainService;

	/**
	 * 跳转到流程定义列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-6
	 * @return
	 */
	@RequestMapping(value = "/goProcessList")
	public ModelAndView goProcessList(HttpServletRequest request) {

		String category = request.getParameter("category");
		ModelAndView mav = new ModelAndView("activiti/processList.html");
		mav.addObject("category", category);
		return mav;
	}

	/**
	 * 到我发起的任务列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "goMyTaskList")
	public ModelAndView goMyTaskList() {
		ModelAndView mv = new ModelAndView("activiti/myTaskList.html");
		return mv;
	}

	/**
	 * 到我待办任务列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "goTaskAgentsList")
	public ModelAndView goTaskAgentsList() {

		ModelAndView mv = new ModelAndView("activiti/taskAgentsList.html");
		return mv;
	}

	/**
	 * 我处理过的流程
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-21
	 * @return
	 */
	@RequestMapping(value = "goDisposeTaskList")
	public ModelAndView goDisposeTaskList() {

		ModelAndView mv = new ModelAndView("activiti/disposeTaskList.html");
		return mv;

	}

	/**
	 * 到任务详情
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-22
	 * @return
	 */
	@RequestMapping(value = "goTaskDetails")
	public ModelAndView goTaskDetails(HttpServletRequest request) {

		String processInstanceId = request.getParameter("processInstanceId");

		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		LoginInfoDto user = (LoginInfoDto) session
				.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户
		String assignee = user.getId() + "";

		List<Map<String, Object>> approveList = getAllVariablesList(
				processInstanceId, assignee);

		ModelAndView mv = new ModelAndView("activiti/taskDetailsl.html");
		mv.addObject("approveList", approveList);

		return mv;

	}

	// **************************获取组织结构列表***************************

	/**
	 * 查询组织结构树 词牌名
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-9
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryTreelist")
	@ResponseBody
	public List<Map<String, Object>> queryTreelist(HttpServletRequest request) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("baseOrgId", 0);
		List<Map<String, Object>> varList = orgainService.queryTreelist(map);

		// List<Map<String, Object>> tmp = new ArrayList<Map<String, Object>>();
		//
		// for (Map<String, Object> map2 : varList) {
		// if (((int) map2.get("type")) != 2) {
		// tmp.add(map2);
		// }
		// }

		return varList;

	}

	// *************************流程定义列表操作***************************

	/**
	 * 获取流程定义列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectProcessList")
	public Map<String, Object> selectProcessList(HttpServletRequest request) {

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数

		Integer iDisplayStart = request.getParameter("iDisplayStart") == null ? 0
				: Integer.valueOf(request.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = request.getParameter("iDisplayLength") == null ? 10
				: Integer.valueOf(request.getParameter("iDisplayLength"));// 每页显示的size

		String category = request.getParameter("category");

		Map<String, Object> map = new HashMap<String, Object>();

		// 查询流程定义列表
		List<Map<String, Object>> resultList = workFlowService
				.selectProcessDefinitionList(category, iDisplayStart,
						iDisplayLength);
		// 新建一个保存流程定义，不知为什么，直接返回上面的list,有循环引用问题，估计是框架有bug
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		try {
			for (Map<String, Object> result : resultList) {
				Map<String, Object> tmp = new HashMap<String, Object>();

				ProcessDefinition processDefinition = (ProcessDefinition) result
						.get("processDefinition");
				Deployment deployment = (Deployment) result.get("deployment");
				tmp.put("id", processDefinition.getId());
				tmp.put("name", processDefinition.getName());
				tmp.put("key", processDefinition.getKey());
				tmp.put("version", processDefinition.getVersion());
				tmp.put("resourceName", processDefinition.getResourceName());
				tmp.put("deploymentId", processDefinition.getDeploymentId());
				tmp.put("deploymentTime", deployment.getDeploymentTime());

				String tenantId = processDefinition.getTenantId();
				tmp.put("tenantId", tenantId);
				OrgainModel om = orgainService.queryById(Integer
						.valueOf(tenantId));
				tmp.put("orgainName", om.getOrgainName());

				dataList.add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 为操作次数加1
		int initEcho = sEcho + 1;

		map.put("sEcho", initEcho);
		map.put("iTotalRecords",
				workFlowService.selectProcessDefinitionCount(category));
		map.put("iTotalDisplayRecords",
				workFlowService.selectProcessDefinitionCount(category));
		map.put("aData", dataList);

		return map;
	}

	/**
	 * 删除流程定义
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-7
	 * @param deploymentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delProcessDefinition")
	public Map<String, String> delProcessDefinition(String deploymentIds) {

		Map<String, String> result = new HashMap<String, String>();

		try {

			workFlowService.delProcessDefinition(deploymentIds);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "删除失败，有正在执行的流程");
		}
		return result;
	}

	/**
	 * 输出流程图片到jsp
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-7
	 * @param deploymentId
	 * @param response
	 */
	@RequestMapping("viewProcessDefinitionImage")
	public void viewProcessDefinitionImage(String deploymentId,
			HttpServletResponse response) {

		try {

			InputStream is = workFlowService
					.viewProcessDefinitionImage(deploymentId);

			response.setCharacterEncoding("UTF-8");

			// 获取输出流
			ServletOutputStream out = response.getOutputStream();

			int size = -1;
			byte[] bytes = new byte[1024];
			int len = 0;
			// 开始读取图片信息
			while (-1 != (len = is.read(bytes))) {
				out.write(bytes, 0, len);
				size += len;
			}
			out.flush();
			out.close();
			is.close();
			is = null;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ********************个人流程操作*********************

	/**
	 * 发起普通表单流程
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-13
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "startFormProcess")
	public String startFormProcess(HttpServletRequest request) {

		try {

			Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
			Session session = currentUser.getSession();
			LoginInfoDto user = (LoginInfoDto) session
					.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

			String processDefinitionId = request
					.getParameter("processDefinitionId");
			//
			//
			// ProcessDefinition processDefinition = workFlowUtilService
			// .findProcessDefinitionByDefinitionName("123");
			// String processDefinitionId = processDefinition.getId();

			Map<String, String> formProperties = new HashMap<String, String>();

			// 从request中读取参数然后转换
			Map<String, String[]> parameterMap = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
			for (Entry<String, String[]> entry : entrySet) {
				String key = entry.getKey();

				/*
				 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
				 */
				if (StringUtils.defaultString(key).startsWith("fp_")) {
					String[] paramSplit = key.split("_");
					formProperties.put(paramSplit[1], entry.getValue()[0]);
				}
			}

			String flag = formProperties.get("deptLeaderPass");

			// 判断审批意见，同意执行下一步，不同意打回到发起人
			if (flag != null && flag.equalsIgnoreCase("true")) {
				formProperties.put("direction", "go");
			} else {
				formProperties.put("direction", "start");
			}
			// 添加审批时间
			formProperties.put("approveTime", DateUtils.getTime());

			// 表单启动
			ProcessInstance processInstance = workFlowService.startFormProcess(
					processDefinitionId, user.getId() + "", formProperties);

			// app发起流程后，马上提交到下一步
			// Task task = workFlowUtilService
			// .findTaskByProcessInstanceId(processInstance.getId());
			// task.setCategory(processDefinition.getCategory());
			// formService.submitTaskFormData(task.getId(), formProperties);

			return "redirect:/process/get-form/task/" + processInstance.getId();

			// return
			// "redirect:/process/task/complete/{taskId}/"+processInstance.getId();

			// ProcessInstance processInstance = runtimeService
			// .startProcessInstanceById(processDefinitionId, variables);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询我的历史任务，我发起的任务
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTaskList")
	public Map<String, Object> getTaskList(String userId,
			HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		LoginInfoDto user = (LoginInfoDto) session
				.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数

		Integer iDisplayStart = request.getParameter("iDisplayStart") == null ? 0
				: Integer.valueOf(request.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = request.getParameter("iDisplayLength") == null ? 10
				: Integer.valueOf(request.getParameter("iDisplayLength"));// 每页显示的size

		Map<String, Object> map = new HashMap<String, Object>();

		// List<Task> dataList = taskService.createTaskQuery()
		// .taskAssignee("1234").listPage(iDisplayStart,
		// iDisplayLength);

		List<HistoricProcessInstance> dataList = workFlowService
				.getMyCreateTaskList(user.getId() + "", iDisplayStart,
						iDisplayLength, true);

		// 为操作次数加1
		int initEcho = sEcho + 1;

		map.put("sEcho", initEcho);
		map.put("iTotalRecords",
				workFlowService.getMyCreateTaskCount(user.getId() + ""));
		map.put("iTotalDisplayRecords",
				workFlowService.getMyCreateTaskCount(user.getId() + ""));
		map.put("aData", dataList);

		return map;

	}

	/**
	 * 删除历史流程任务
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delHistoricProcess")
	public Map<String, String> delHistoricProcess(String ids) {
		Map<String, String> result = new HashMap<String, String>();

		try {

			workFlowService.delHistoricProcess(ids);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "删除失败,流程正在运行");
		}
		return result;
	}

	/**
	 * 查询我的待办事项
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRuntimeTask")
	public Map<String, Object> getRuntimeTask(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		LoginInfoDto user = (LoginInfoDto) session
				.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数

		Integer iDisplayStart = request.getParameter("iDisplayStart") == null ? 0
				: Integer.valueOf(request.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = request.getParameter("iDisplayLength") == null ? 10
				: Integer.valueOf(request.getParameter("iDisplayLength"));// 每页显示的size

		Map<String, Object> map = new HashMap<String, Object>();

		List<Task> tList = workFlowService.getMyAssignTask(user.getId() + "",
				iDisplayStart, iDisplayLength);

		// 新建一个保存流程定义，不知为什么，直接返回上面的list,有循环引用问题，估计是框架有bug
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		try {

			for (Task task : tList) {

				ProcessInstance pi = workFlowUtilService
						.findProcessInstanceByTaskId(task.getId());

//				List<Map<String, Object>> list = workFlowService
//						.getTaskAndVariables(pi.getId());

				// String
				// formKey=workFlowUtilService.getHistoricFormKeyByProcessInstanceId(pi.getId(),
				// "102");

				// workFlowService.stopAndDeleteProcess("ef3eb3f4-1a71-11e7-8f3b-f8bc1274a850");

				Map<String, Object> obj = workFlowService.getTaskVariables(
						pi.getId(), user.getId() + "");

				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("definitionName", pi.getProcessDefinitionName());// 获取流程定义名称
				tmp.put("id", task.getId());// id
				tmp.put("instanceId", task.getProcessInstanceId());
				tmp.put("name", task.getName());// 流程节点
				tmp.put("assignee", task.getAssignee());// 当前经办人
				tmp.put("description", task.getDescription());// 备注
				tmp.put("dueDate", task.getDueDate());// 到期时间
				tmp.put("data", obj);
				dataList.add(tmp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 为操作次数加1
		int initEcho = sEcho + 1;

		map.put("sEcho", initEcho);
		map.put("iTotalRecords",
				workFlowService.getMyAssignCount(user.getId() + ""));
		map.put("iTotalDisplayRecords",
				workFlowService.getMyAssignCount(user.getId() + ""));
		map.put("aData", dataList);

		return map;

	}

	/**
	 * 查询我处理过的任务
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDisposeTaskList")
	public Map<String, Object> getDisposeTaskList(HttpServletRequest request) {

		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		LoginInfoDto user = (LoginInfoDto) session
				.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数

		Integer iDisplayStart = request.getParameter("iDisplayStart") == null ? 0
				: Integer.valueOf(request.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = request.getParameter("iDisplayLength") == null ? 10
				: Integer.valueOf(request.getParameter("iDisplayLength"));// 每页显示的size

		Map<String, Object> map = new HashMap<String, Object>();

		// List<Task> dataList = taskService.createTaskQuery()
		// .taskAssignee("1234").listPage(iDisplayStart,
		// iDisplayLength);

		List<HistoricTaskInstance> dataList = workFlowService
				.getDisposeTaskList(user.getId() + "", iDisplayStart,
						iDisplayLength);

		for (HistoricTaskInstance historicProcessInstance : dataList) {

			System.out
					.println(historicProcessInstance.getProcessDefinitionId());

			try {
				System.out.println(workFlowService
						.getAllVariablesListByProcessInstanceId(
								historicProcessInstance.getId(), user.getId()
										+ ""));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// 为操作次数加1
		int initEcho = sEcho + 1;

		map.put("sEcho", initEcho);
		map.put("iTotalRecords",
				workFlowService.getMyCreateTaskCount(user.getId() + ""));
		map.put("iTotalDisplayRecords",
				workFlowService.getMyCreateTaskCount(user.getId() + ""));
		map.put("aData", dataList);

		return map;

	}

	/**
	 * 普通处理处理流程
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-9
	 * @param taskId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "compliteTask")
	public Map<String, Object> compliteTask(String taskId, String opinion) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 添加流程变量
		Map<String, Object> variables = new HashMap<String, Object>();

		Map<String, String> data = new HashMap<String, String>();
		data.put("opinion", opinion);
		// 携带的信息
		variables.put("data", data);

		// 流程方向
		variables.put("direction", "go");

		try {

			// 当前活动节点ID
			String activityId = workFlowUtilService.findTaskById(taskId)
					.getTaskDefinitionKey();
			variables.put("activityId", activityId);

			// 提交流程到下一步
			workFlowService.commitProcess(taskId, variables);

			result.put("code", Code.SUCCESS);
			result.put("msg", "提交成功");

		} catch (Exception e) {
			result.put("code", Code.SERVER_EXCEPTION);
			result.put("msg", "提交异常");
		}

		return result;

	}

	/**
	 * 获取流程变量
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-12
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getProcessVariables")
	public ModelAndView getProcessVariables(String processInstanceId,
			String assignee) {

		Map<String, Object> obj = workFlowService.getTaskVariables(
				processInstanceId, assignee);

		// 获取流程变量【基本类型】
		Map<String, String> data = (Map<String, String>) obj.get("data");

		String activityId = (String) obj.get("activityId");

		ModelAndView mv = new ModelAndView("process/approveProcess");
		mv.addObject("activityId", activityId);
		mv.addObject("data", data);
		return mv;
	}

	/**
	 * 获取流程审批结果
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-16
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getApproveList(String taskId) {

		try {

			List<Map<String, Object>> variableMapList = workFlowService
					.getApproveList(taskId);
			return variableMapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取当前节点之前的全部流程变量
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-22
	 * @param taskId
	 * @return
	 */
	public List<Map<String, Object>> getAllVariablesList(
			String processInstanceId, String assignee) {

		try {

			List<Map<String, Object>> variableMapList = workFlowService
					.getAllVariablesList(processInstanceId, assignee);
			return variableMapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// **********************驳回流程操作*****************************

	/**
	 * 驳回上一步或第一步
	 * 这个方法是正常的提交，需要在流程定义里面画返回上一步和第一步的路径，配置跳转条件${direction==}，正常下一步是go,
	 * 上一步是back,第一步是start
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-13
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "backProcess")
	public Map<String, Object> backProcess(HttpServletRequest request,
			String taskId, Integer tag) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Map<String, String> formProperties = new HashMap<String, String>();

			// 从request中读取参数然后转换
			Map<String, String[]> parameterMap = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
			for (Entry<String, String[]> entry : entrySet) {
				String key = entry.getKey();

				/*
				 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
				 */
				if (StringUtils.defaultString(key).startsWith("fp_")) {
					String[] paramSplit = key.split("_");
					formProperties.put(paramSplit[1], entry.getValue()[0]);
				}
			}

			if (tag == 1) {
				// 驳回上一步
				formProperties.put("direction", "back");
				formService.submitTaskFormData(taskId, formProperties);
			} else {
				// 驳回第一步
				formProperties.put("direction", "start");
				formService.submitTaskFormData(taskId, formProperties);
			}

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "驳回成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "驳回异常");
		}

		return result;

	}

	/**
	 * 驳回上一步 非正常手段提交，代码强制跳转到指定的节点
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-1-13
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "backLast")
	public Map<String, String> backLast(String taskId, String activityId) {

		Map<String, String> result = new HashMap<String, String>();

		if (activityId == null || activityId.equals("")) {
			result.put("code", Code.PARAMETER_LOST + "");
			result.put("msg", "activityId参数不能为空");
			return result;
		}

		try {

			// 获取上一步的任务Id
			String lastActivityId = workFlowUtilService
					.findLastActivityId(taskId);

			// 添加流程变量
			Map<String, Object> variables = new HashMap<String, Object>();

			Map<String, String> data = new HashMap<String, String>();
			data.put("msg", "驳回了流程");
			data.put("opinion", "不同意");

			variables.put("data", data);

			workFlowService.backToLast(taskId, lastActivityId, variables);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "驳回成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "驳回失败");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "backFormLast/{taskId}/{processInstanceId}")
	public Map<String, String> backFormLast(
			@PathVariable("taskId") String taskId,
			@PathVariable("processInstanceId") String processInstanceId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Map<String, String> result = new HashMap<String, String>();

		try {
			Map<String, String> formProperties = new HashMap<String, String>();

			// 从request中读取参数然后转换
			Map<String, String[]> parameterMap = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
			for (Entry<String, String[]> entry : entrySet) {
				String key = entry.getKey();

				/*
				 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
				 */
				if (StringUtils.defaultString(key).startsWith("fp_")) {
					String[] paramSplit = key.split("_");
					formProperties.put(paramSplit[1], entry.getValue()[0]);
				}
			}

			// 驳回操作
			String activityId = workFlowUtilService.findLastActivityId(taskId);
			workFlowUtilService.formTurnTransition(taskId, activityId,
					formProperties);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "驳回成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "驳回失败");
		}

		return result;

	}

	// ****************转办、会签操作*************************

	/**
	 * 会签操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param userCodes
	 *            会签人账号集合
	 * @throws Exception
	 */
	public void jointProcess(String taskId, List<String> userCodes)
			throws Exception {
		workFlowService.countersignProcess(taskId, userCodes);
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
		workFlowService.transferAssignee(taskId, userCode);
	}

	// *************************表单操作************************************

	/**
	 * 读取Task的表单
	 * 
	 * @RequestMapping(value = "get-form/task/{processDefinitionkey}")
	 * @PathVariable("processDefinitionkey") String processDefinitionkey
	 */
	@RequestMapping(value = "/get-form/task/{processInstanceId}")
	@ResponseBody
	public ModelAndView findTaskForm(
			@PathVariable("processInstanceId") String processInstanceId,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		// 获取当前登陆人信息。
		/* User user = UserUtil.getUserFromSession(request.getSession()); */

		TaskQuery taskQuery = taskService.createTaskQuery()
				.processInstanceId(processInstanceId)
				.orderByProcessInstanceId().desc();

		List<Task> tasks = taskQuery.list();
		if (tasks.size() == 0) {
			ModelAndView mav2 = new ModelAndView("activiti/formKey/error.html");
			return mav2;
		}
		Task task = tasks.get(0);

		// 获取外置表单
		// Object renderedTaskForm =
		// formService.getRenderedTaskForm(task.getId());

		// 当前活动节点ID
		String activityId = workFlowUtilService.findTaskById(task.getId())
				.getTaskDefinitionKey();
		// 获取配置的formKey
		String formKey = formService.getTaskFormKey(
				task.getProcessDefinitionId(), task.getTaskDefinitionKey());

		// 获取表单提交的变量
		Map<String, Object> formProperties = taskService.getVariables(task
				.getId());

		List<Map<String, Object>> approveList = getApproveList(task.getId());

		// mav.addObject("renderedTaskForm", renderedTaskForm.toString());//
		// 整个页面，参数已经赋值（外置表单，普通表单不适用）
		mav.addObject("taskId", task.getId());
		mav.addObject("processInstanceId", processInstanceId);
		mav.addObject("activityId", activityId);
		mav.addObject("data", formProperties);
		mav.addObject("approveList", approveList);
		mav.setViewName("activiti/formKey/" + formKey + ".html");
		return mav;
	}

	/**
	 * 办理任务，提交task的并保存form
	 */
	@ResponseBody
	@RequestMapping(value = "task/complete/{taskId}/{processInstanceId}")
	public Map<String, String> completeTask(
			@PathVariable("taskId") String taskId,
			@PathVariable("processInstanceId") String processInstanceId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Map<String, String> result = new HashMap<String, String>();

		try {
			Map<String, String> formProperties = new HashMap<String, String>();

			// 从request中读取参数然后转换
			Map<String, String[]> parameterMap = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
			for (Entry<String, String[]> entry : entrySet) {
				String key = entry.getKey();

				/*
				 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
				 */
				if (StringUtils.defaultString(key).startsWith("fp_")) {
					String[] paramSplit = key.split("_");
					formProperties.put(paramSplit[1], entry.getValue()[0]);
				}
			}

			String flag = formProperties.get("deptLeaderPass");

			// 判断审批意见，同意执行下一步，不同意打回到发起人
			if (flag != null && flag.equalsIgnoreCase("true")) {
				formProperties.put("direction", "go");
			} else {
				formProperties.put("direction", "start");
			}
			// 添加审批时间
			formProperties.put("approveTime", DateUtils.getTime());

			formService.submitTaskFormData(taskId, formProperties);

			result.put("code", Code.SUCCESS + "");
			result.put("msg", "提交成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", Code.SERVER_EXCEPTION + "");
			result.put("msg", "提交失败");
		}

		redirectAttributes
				.addFlashAttribute("message", "任务完成：taskId=" + taskId);
		return result;

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
	@RequestMapping(value = "getProcessView")
	public void getProcessView(String processInstanceId,
			HttpServletResponse response) {

		try {
			InputStream imageStream = workFlowUtilService
					.getProcessView(processInstanceId);
			byte[] b = new byte[1024];
			int len;
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

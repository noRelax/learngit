/**
 * @Project:ZGHome
 * @FileName:AppRightsApplyController.java
 */
package com.ehome.cloud.app.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Condition;

import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.RSA;
import com.ehome.core.util.TokenUtil;

/**
 * @Title:AppRightsApplyController
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年3月21日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/help/rightApply")
public class AppRightsApplyController extends BaseController {

	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private WorkFlowUtilService workFlowUtilService;
	@Resource
	private IUploadFileService iUploadFileService;
	@Autowired
	private TaskService taskService;
	@Resource
	private IMemberService iMemberService;
	@Resource
	private IAppUserService iAppUserService;
	@Resource
	private IOrgainService iOrgainService;

	/**
	 * 保存法律援助数据
	 * 
	 * @param request
	 * @param time
	 * @param apptype
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

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
					if (paramSplit[1].equals("name") || paramSplit[1].equals("IDcard")
							|| paramSplit[1].equals("homeTel") || paramSplit[1].equals("workPhone")) {
						formProperties.put(paramSplit[1], RSA.decryptPrivateKey(entry.getValue()[0].replace(" ", "+")));// url转码会把+变空格
					} else {
						formProperties.put(paramSplit[1], entry.getValue()[0]);
					}
				}
			}
			// 申请时间
			formProperties.put("applicationTime", DateUtils.getTime());

			// 条件查询
			Condition example = new Condition(Member.class);
			Condition.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("appUserId", Integer.parseInt(formProperties.get("userId")));

			ProcessDefinition processDefinition = null;
			try {
				processDefinition = workFlowUtilService.findProcessDefinitionByTenantId("flwq",
						iMemberService.selectByCondition(example).get(0).getUpperUnionId() + "");
				// 没有流程直接返回
				if (processDefinition == null) {
					map.put("datas", "");
					map.put("status", ResponseCode.approveProcess_not_exist.getCode());
					map.put("message", ResponseCode.approveProcess_not_exist.getMsg());
					return map;
				}
			} catch (Exception e) {
				// e.printStackTrace();
				map.put("datas", "");
				map.put("status", ResponseCode.member_not_exist.getCode());
				map.put("message", ResponseCode.member_not_exist.getMsg());
				return map;
			}

			String processDefinitionId = processDefinition.getId();

			// 表单启动
			ProcessInstance processInstance = workFlowService.startFormProcess(processDefinitionId,
					formProperties.get("userId") + "", formProperties);

			// app发起流程后，马上提交到下一步
			formProperties.put("direction", "已提交");
			formProperties.put("approveTime", DateUtils.getTime());
			workFlowService.submitTaskFormData(processInstance, formProperties, formProperties.get("userId") + "");

			map.put("processInstanceId", processInstance.getId());
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}
		return map;
	}

	/**
	 * 保存法律援助数据
	 * 
	 * @param request
	 * @param time
	 * @param apptype
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/reSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reSave(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token, @RequestParam String processInstanceId,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

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
					if (paramSplit[1].equals("name") || paramSplit[1].equals("IDcard")
							|| paramSplit[1].equals("homeTel") || paramSplit[1].equals("workPhone")) {
						formProperties.put(paramSplit[1], RSA.decryptPrivateKey(entry.getValue()[0].replace(" ", "+")));// url转码会把+变空格
					} else {
						formProperties.put(paramSplit[1], entry.getValue()[0]);
					}
				}
			}
			// 申请时间
			// formProperties.put("applicationTime", DateUtils.getTime());

			// // 条件查询
			// Condition example = new Condition(Member.class);
			// Condition.Criteria criteria = example.createCriteria();
			// criteria.andEqualTo("appUserId",
			// Integer.parseInt(formProperties.get("userId")));
			//
			// ProcessDefinition processDefinition = null;
			// try {
			// processDefinition =
			// workFlowUtilService.findProcessDefinitionByTenantId("flwq",
			// iMemberService.selectByCondition(example).get(0).getUpperUnionId()
			// + "");
			// // 没有流程直接返回
			// if (processDefinition == null) {
			// map.put("datas", new ArrayList());
			// map.put("status", ResponseCode.fail.getCode());
			// map.put("message", "没有设置此用户对应的审批流程");
			// return map;
			// }
			// } catch (Exception e) {
			// // e.printStackTrace();
			// map.put("datas", new ArrayList());
			// map.put("status", ResponseCode.fail.getCode());
			// map.put("message", "此用户的归属工会为空");
			// return map;
			// }

			// String processDefinitionId = processDefinition.getId();

			// app发起流程后，马上提交到下一步
			formProperties.put("direction", "已提交");
			formProperties.put("approveTime", DateUtils.getTime());
			workFlowService.submitTaskFormData(
					workFlowUtilService.findTaskByProcessInstanceId(processInstanceId).getId(), formProperties,
					formProperties.get("userId") + "");

			map.put("processInstanceId", processInstanceId);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}
		return map;
	}

	/**
	 * 根据流程id获取表单和审批信息
	 * 
	 * @param processInstanceId
	 * @param time
	 * @param apptype
	 * @param token
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get(@RequestParam String processInstanceId,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		try {

			TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId)
					.orderByProcessInstanceId().desc();

			List<Task> tasks = taskQuery.list();
			Map<String, Object> formProperties = new HashMap<String, Object>();
			try {
				Task task = tasks.get(0);
				// 获取表单提交的变量 流程中
				formProperties = taskService.getVariables(task.getId());

			} catch (Exception e) {
				// e.printStackTrace();
				// 获取表单提交的变量 流程结束
				List<Map<String, Object>> approveList = workFlowService
						.getApproveListByProcessInstanceId(processInstanceId);
				if (approveList.size() > 0)
					formProperties = approveList.get(0);
			}

			// 身份证图片
			if (formProperties.get("idCardPics") != null) {
				String[] idCards = formProperties.get("idCardPics").toString().split(",");
				List<Map<String, String>> idcardsList = new ArrayList<Map<String, String>>();
				for (String pic : idCards) {
					String url = "";
					if (iUploadFileService.selectByKey(Integer.parseInt(pic)) != null)
						url = iUploadFileService.selectByKey(Integer.parseInt(pic)).getFilePath();
					Map<String, String> picMap = new HashMap<String, String>();
					picMap.put("id", pic);
					picMap.put("url", url);
					idcardsList.add(picMap);
				}

				formProperties.put("idCardPics", idcardsList);
			}

			// 证明图片
			if (formProperties.get("proofPics") != null) {
				String[] proofPics = formProperties.get("proofPics").toString().split(",");
				List<Map<String, String>> proofPicsList = new ArrayList<Map<String, String>>();
				for (String pic : proofPics) {
					String url = "";
					if (iUploadFileService.selectByKey(Integer.parseInt(pic)) != null)
						url = iUploadFileService.selectByKey(Integer.parseInt(pic)).getFilePath();
					Map<String, String> picMap = new HashMap<String, String>();
					picMap.put("id", pic);
					picMap.put("url", url);
					proofPicsList.add(picMap);
				}

				formProperties.put("proofPics", proofPicsList);
			}

			// 几个字段加密
			if (formProperties.get("name") != null)
				formProperties.put("name", RSA.encryptPublicKey(formProperties.get("name").toString()));
			if (formProperties.get("IDcard") != null)
				formProperties.put("IDcard", RSA.encryptPublicKey(formProperties.get("IDcard").toString()));
			if (formProperties.get("homeTel") != null)
				formProperties.put("homeTel", RSA.encryptPublicKey(formProperties.get("homeTel").toString()));
			if (formProperties.get("workPhone") != null)
				formProperties.put("workPhone", RSA.encryptPublicKey(formProperties.get("workPhone").toString()));

			try {
				List<Map<String, Object>> approveElements = new ArrayList<Map<String, Object>>();
				approveElements = workFlowService.getTaskAndVariables(processInstanceId);
				formProperties.put("approveElements", approveElements);
				int status = workFlowService.processIsEnd(processInstanceId);
				formProperties.put("status", status);
				
				//设置审核状态（包括审核中，通过，不通过，退回通过isBack判断）
				if(status == 0){
				    //审核完成
				    formProperties.put("result", approveElements.get(approveElements.size()-2).get("direction"));
				}else{
				    formProperties.put("result", "审核中");
				}
				
				// 取一下待办列表判断是否打回到提交人
				List<Task> tList = workFlowService.getMyAssignTask(formProperties.get("userId").toString(), "flwq", 0, 10, null);
				int isBack = 0;
				if (tList.size() > 0) {
				    ProcessInstance pi = workFlowUtilService.findProcessInstanceByTaskId(tList.get(0).getId());
				    if (pi.getId().equals(processInstanceId)) {
				        isBack = 1;
				    }
				}
				formProperties.put("isBack", isBack);
				
			} catch (Exception e) {
			}
			

			map.put("datas", formProperties);
			// map.put("approveElements", approveElements);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}

		return map;
	}

	/**
	 * 获取提交的法律援助数据
	 * 
	 * @param request
	 * @param time
	 * @param apptype
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(@RequestParam String userId,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token,
			// 分页暂时不用
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String rows, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		try {
			Condition example = new Condition(Member.class);
			Condition.Criteria criteria = example.createCriteria();
			// 条件查询
			criteria.andEqualTo("appUserId", Integer.parseInt(userId));
			ProcessDefinition processDefinition = null;
			try {
				processDefinition = workFlowUtilService.findProcessDefinitionByTenantId("flwq",
						iMemberService.selectByCondition(example).get(0).getUpperUnionId() + "");

				// 没有流程直接返回
				if (processDefinition == null) {
					map.put("datas", new ArrayList<>());
					map.put("status", ResponseCode.approveProcess_not_exist.getCode());
					map.put("message", ResponseCode.approveProcess_not_exist.getMsg());
					return map;
				}
			} catch (Exception e) {
				// e.printStackTrace();
				map.put("datas", new ArrayList<>());
				map.put("status", ResponseCode.member_not_exist.getCode());
				map.put("message", ResponseCode.member_not_exist.getMsg());
				return map;
			}

			List<HistoricProcessInstance> myProcessList = workFlowService.getMyCreateTaskList(userId,
					processDefinition.getId(), Integer.parseInt(page) - 1, Integer.parseInt(rows), false);
			for (HistoricProcessInstance hpt : myProcessList) {

				// 获取流程信息
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("definitionName", hpt.getProcessDefinitionName());
				tmp.put("startTime", hpt.getStartTime());
				tmp.put("processInstanceId", hpt.getId());
				List<Map<String, Object>> approveList = workFlowService.getApproveListByProcessInstanceId(hpt.getId());

				// 存在取到空的情况，跳过
				if (approveList.size() < 1)
					continue;
				if(workFlowService.processIsEnd(hpt.getId()) == 0){
					//审核完成
					tmp.put("status", "0");
					tmp.put("result", approveList.get(approveList.size()-1).get("direction"));
				}else{
					tmp.put("status", "1");
					tmp.put("result", "审核中");
				}

				if (approveList.get(0) != null && approveList.get(0).get("applicationTime") != null)
					tmp.put("applicationTime", approveList.get(0).get("applicationTime"));

				// 取一下待办列表判断是否打回到提交人
				List<Task> tList = workFlowService.getMyAssignTask(userId, "flwq", 0, 10, null);
				int isBack = 0;
				if (tList.size() > 0) {
					ProcessInstance pi = workFlowUtilService.findProcessInstanceByTaskId(tList.get(0).getId());
					if (pi.getId().equals(hpt.getId())) {
						isBack = 1;
					}
				}
				tmp.put("isBack", isBack);

				dataList.add(tmp);
			}
			map.put("datas", dataList);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}

		return map;
	}

	/**
	 * 停止流程
	 * @param processInstanceId
	 * @param time
	 * @param apptype
	 * @param token
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stop(@RequestParam String processInstanceId,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token, 
			HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		
		try{
			workFlowService.stopProcess(workFlowUtilService.findTaskByProcessInstanceId(processInstanceId).getId(), null);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
		}catch(Exception e){
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}
		return map;
	}

	/**
	 * 获取流程审批结果
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getApproveList(String taskId) {

		try {

			List<Map<String, Object>> variableMapList = workFlowService.getApproveList(taskId);
			return variableMapList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

/**
 * @Project:ZGHome
 * @FileName:AppHelpApplyController.java
 */
package com.ehome.cloud.app.help;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import tk.mybatis.mapper.entity.Condition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.app.help.dto.RequestHelpApplyDto;
import com.ehome.cloud.app.help.dto.RequestHelpApplyFamilyDto;
import com.ehome.cloud.app.help.dto.RequestMemberDto;
import com.ehome.cloud.area.model.AreaModel;
import com.ehome.cloud.area.service.IAreaService;
import com.ehome.cloud.help.model.HelpApplyFamilyModel;
import com.ehome.cloud.help.model.HelpApplyModel;
import com.ehome.cloud.help.model.MemberHelpModel;
import com.ehome.cloud.help.service.IHelpApplyFamilyService;
import com.ehome.cloud.help.service.IHelpApplyService;
import com.ehome.cloud.help.service.IMemberHelpService;
import com.ehome.cloud.member.dto.MemberDto;
import com.ehome.cloud.member.dto.MemberHomeDto;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.cloud.member.service.IMemberHomeService;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.StringUtils;
import com.ehome.core.util.TokenUtil;

/**
 * 帮扶审批接口
 * 
 * @Title:AppHelpApplyController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月27日 下午6:16:27
 * @version:
 */
@Controller
@RequestMapping(value = "/app/help/helpApply")
public class AppHelpApplyController extends BaseController {

	private final static Logger logger = LoggerFactory
			.getLogger(AppHelpApplyController.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private IHelpApplyService helpApplyService;

	@Resource
	private IHelpApplyFamilyService helpApplyFamilyService;

	@Resource
	private IDictionaryService dictionaryService;

	@Resource
	private IMemberHelpService memberHelpService;

	@Resource
	private IAreaService areaService;

	@Resource
	private IUploadFileService uploadFileService;

	@Resource
	private IMemberHomeService memberHomeService;

	@Resource
	private IUserService userService;

	@Autowired(required = false)
	private WorkFlowService workFlowService;

	@Autowired(required = false)
	private WorkFlowUtilService workFlowUtilService;

	@Resource
	private IOrgainService orgainService;

	/**
	 * 获取当前APP登录账户的帮扶申请
	 * 
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	// @CrossOrigin
	@RequestMapping(value = "/myApplyList", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult myApplyList(NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer userId,
			@RequestParam(required = false, defaultValue = "1") Integer start,
			@RequestParam(required = false, defaultValue = "10") Integer limit,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(time) && StringUtils.isNotBlank(apptype)
				&& StringUtils.isNotBlank(token)) {
			if (!TokenUtil.validateToken("", Long.parseLong(time.trim()),
					apptype.trim(), token.trim())) {
				map.put("status", ResponseCode.invalidtoken.getCode());
				map.put("message", ResponseCode.invalidtoken.getMsg());
				return new AjaxResult(ResponseCode.invalidtoken.getCode(),
						ResponseCode.invalidtoken.getMsg(), map);
			}
		}
		Integer appUserId = 0;
		if (!NumberUtils.isNull(userId))
			appUserId = userId;
		else
			appUserId = this.getCurrentAppUserId();
		List<Map<String, Object>> dataList = new ArrayList<>();
		Condition memberCon = new Condition(Member.class);
		memberCon.createCriteria().andEqualTo("appUserId", appUserId);
		List<Member> memberList = memberService.selectByCondition(memberCon);
		if (CollectionUtils.isNotEmpty(memberList)) {
			ProcessDefinition processDefinition = workFlowUtilService
					.findProcessDefinitionByTenantId("knbf", memberList.get(0)
							.getUpperUnionId() + "");
			if (null == processDefinition) {
				//throw new BusinessException("没有设置此用户对应的审批流程");
				return new AjaxResult(
						String.valueOf(ResponseCode.approveProcess_not_exist
								.getCode()),
						ResponseCode.approveProcess_not_exist.getMsg(),
						ResponseCode.approveProcess_not_exist.getMsg());
			}
			List<HistoricProcessInstance> myProcessList = workFlowService
					.getMyCreateTaskList(appUserId + "",
							processDefinition.getId(), start - 1, limit, false);
			for (HistoricProcessInstance hpt : myProcessList) {
				// 获取流程信息
				String approvalStatus = "", helpType = "";
				Integer helpApplyId = 0;
				// 获取流程变量
				List<Map<String, Object>> approveList = workFlowService
						.getApproveListByProcessInstanceId(hpt.getId());
				Set<String> state = new HashSet<>();
				if (CollectionUtils.isNotEmpty(approveList)) {
					if (approveList.get(approveList.size() - 1).get(
							"approvalStatus") != null) {
						approvalStatus = approveList
								.get(approveList.size() - 1)
								.get("approvalStatus").toString();
						state.add(approvalStatus);
					}
					if (null != approveList.get(approveList.size() - 1).get(
							"helpType")) {
						helpType = approveList.get(approveList.size() - 1)
								.get("helpType").toString();
					}
					if (null != approveList.get(approveList.size() - 1).get(
							"helpApplyId")) {
						helpApplyId = NumberUtils.toInt(approveList
								.get(approveList.size() - 1).get("helpApplyId")
								.toString());
					}
				}
				if (!NumberUtils.eqZero(helpApplyId)) {
					// 判断是否有退回
					List<Task> taskList = workFlowService.getMyAssignTask(
							appUserId + "", "knbf", 0, Integer.MAX_VALUE, null);
					String taskId = "";
					// 取一下代办列表判断是否打回到提交人
					int isBack = 0;
					if (taskList.size() > 0) {
						for (Task task : taskList) {
							ProcessInstance pi = workFlowUtilService
									.findProcessInstanceByTaskId(task.getId());
							if (pi != null) {
								if (pi.getId().equals(hpt.getId())) {
									isBack = 1;
									taskId = task.getId();
									break;
								}
							}
						}
					}
					//					if (CollectionUtils.isNotEmpty(taskList)) {
					//						ProcessInstance pi = workFlowUtilService
					//								.findProcessInstanceByTaskId(taskList.get(0)
					//										.getId());
					//						if (pi.getId().equals(hpt.getId())) {
					//							taskId = taskList.get(0).getId();
					//						}
					//					}
					Map<String, Object> tmp = new HashMap<>();
					tmp.put("isBack", isBack);
					tmp.put("title", hpt.getProcessDefinitionName());
					tmp.put("createtime", DateUtils.getTime(hpt.getStartTime()));
					tmp.put("flowId", hpt.getId());
					tmp.put("taskId", taskId);
					// 判断流程节点是不是到了最后一点
					Integer processIsEnd = workFlowService.processIsEnd(hpt
							.getId());
					if (processIsEnd == 1) {
						if (state.contains("已退回，等待下次审核")) {
							tmp.put("status", "退回");
						} else {
							tmp.put("status", "审核中");
						}
					} else {
						tmp.put("status", approvalStatus);
					}
					tmp.put("helpType", helpType);
					tmp.put("id", helpApplyId);
					dataList.add(tmp);
				}
			}
		} else {
			//throw new BusinessException("请先申请入会");
			return new AjaxResult(String.valueOf(ResponseCode.member_not_exist
					.getCode()), ResponseCode.member_not_exist.getMsg(),
					ResponseCode.member_not_exist.getMsg());
		}
		return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
				ResponseCode.success.getMsg(), dataList);
	}

	/**
	 * 根据身份证号码自动带出会员信息
	 * 
	 * @param request
	 * @param idCard
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getMemberInfo", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getMemberInfo(NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String idCard,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token)
			throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(time) && StringUtils.isNotBlank(apptype)
				&& StringUtils.isNotBlank(token)) {
			if (!TokenUtil.validateToken("", Long.parseLong(time.trim()),
					apptype.trim(), token.trim())) {
				map.put("status", ResponseCode.invalidtoken.getCode());
				map.put("message", ResponseCode.invalidtoken.getMsg());
				return new AjaxResult(ResponseCode.invalidtoken.getCode(),
						ResponseCode.invalidtoken.getMsg(), map);
			}
		}
		response.setContentType("text/html; charset=utf-8");
		// 跨域头
		// response.addHeader("Access-Control-Allow-Origin", "*");
		// response.addHeader("Access-Control-Allow-Methods", "POST");
		// response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		// response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		String baseUnitName = "";
		if (StringUtils.isNotBlank(idCard)) {
			// 获取会员信息
			Member member = new Member();
			member.setIdCard(idCard);
			member = memberService.selectOne(member);
			if (null != member) {
				if (!NumberUtils.isNull(member.getBasicUnionId())
						&& !NumberUtils.eqZero(member.getBasicUnionId())) {
					OrgainModel orgModel = orgainService.selectByKey(member
							.getBasicUnionId());
					if (null != orgModel)
						baseUnitName = orgModel.getOrgainName();
				}
				MemberDto memberDto = new MemberDto();
				BeanUtils.copyProperties(member, memberDto);
				DictoryCodeUtils.renderCode(memberDto);
				// 获取帮扶信息
				MemberHelpModel memberHelp = new MemberHelpModel();
				memberHelp.setMemberId(member.getId());
				memberHelp = memberHelpService.selectOne(memberHelp);
				// 查找到帮扶信息
				if (null != memberHelp) {
					HelpApplyModel helpApply = new HelpApplyModel();
					BeanUtils.copyProperties(memberHelp, helpApply);
					RequestHelpApplyDto requestHelpApplyDto = getHelpApplyDto(helpApply);
					requestHelpApplyDto.setName(member.getMemberName());
					requestHelpApplyDto.setSex(memberDto.getSexName());
					requestHelpApplyDto.setNation(memberDto.getNationName());
					requestHelpApplyDto.setPolitical(memberDto
							.getPoliticalName());
					requestHelpApplyDto.setIdCard(memberDto.getIdCard());
					requestHelpApplyDto.setMaritalStatus(memberDto
							.getMaritalStatusName());
					requestHelpApplyDto.setAddress(memberDto.getAddress());
					String seatRegistered = "";
					if (!NumberUtils.isNull(member.getProvince())
							&& !NumberUtils.eqZero(member.getProvince())) {
						AreaModel pro = areaService.selectByKey(member
								.getProvince());
						seatRegistered += pro.getAreaName();
					}
					if (!NumberUtils.isNull(member.getCity())
							&& !NumberUtils.eqZero(member.getCity())) {
						AreaModel city = areaService.selectByKey(member
								.getCity());
						seatRegistered += city.getAreaName();
					}
					requestHelpApplyDto.setSeatRegistered(seatRegistered);
					requestHelpApplyDto.setBasicUnionId(member
							.getBasicUnionId());
					requestHelpApplyDto.setBasicUnionName(baseUnitName);
					return new AjaxResult(String.valueOf(ResponseCode.success
							.getCode()), ResponseCode.success.getMsg(),
							requestHelpApplyDto);
				} else {
					// 没有帮扶信息直接返回会员信息
					RequestMemberDto requestMemberDto = new RequestMemberDto();
					requestMemberDto.setId(member.getId());
					requestMemberDto.setName(member.getMemberName());
					if (!NumberUtils.isNull(member.getSex())) {
						requestMemberDto.setSex(memberDto.getSexName());
					}
					requestMemberDto.setIdCard(member.getIdCard());
					if (!NumberUtils.isNull(member.getNation())) {
						requestMemberDto.setNation(memberDto.getNationName());
					}
					if (!NumberUtils.isNull(member.getPolitical())) {
						requestMemberDto.setPoliticall(memberDto
								.getPoliticalName());
					}
					if (!NumberUtils.isNull(member.getMaritalStatus())) {
						requestMemberDto.setMaritalStatus(memberDto
								.getMaritalStatusName());
					}
					String seatRegistered = "";
					if (!NumberUtils.isNull(member.getProvince())
							&& !NumberUtils.eqZero(member.getProvince())) {
						AreaModel pro = areaService.selectByKey(member
								.getProvince());
						seatRegistered += pro.getAreaName();
					}
					if (!NumberUtils.isNull(member.getCity())
							&& !NumberUtils.eqZero(member.getCity())) {
						AreaModel city = areaService.selectByKey(member
								.getCity());
						seatRegistered += city.getAreaName();
					}
					requestMemberDto.setSeatRegistered(seatRegistered);
					requestMemberDto.setAddress(member.getAddress());
					// 获取家庭成员
					Condition homeCon = new Condition(MemberHome.class);
					homeCon.createCriteria().andEqualTo("memberId",
							member.getId());
					List<MemberHome> homeList = memberHomeService
							.selectByCondition(homeCon);
					List<RequestHelpApplyFamilyDto> listHelpApplyFamily = new ArrayList<>();
					if (CollectionUtils.isNotEmpty(homeList)) {
						for (MemberHome home : homeList) {
							RequestHelpApplyFamilyDto familyDto = new RequestHelpApplyFamilyDto();
							MemberHomeDto homeDto = new MemberHomeDto();
							BeanUtils.copyProperties(home, homeDto);
							DictoryCodeUtils.renderCode(homeDto);
							familyDto.setName(homeDto.getMembers());
							familyDto.setMemberId(homeDto.getMemberId());
							familyDto.setFamilyRelationship(homeDto
									.getFamilyRelationshipName());
							familyDto.setSex(homeDto.getSexName());
							familyDto.setPolitical(homeDto.getPoliticalName());
							familyDto.setIdCard(homeDto.getIdCard());
							familyDto.setHealth(homeDto.getHealthName());
							if (homeDto.getMonthlyIncome() != null)
								familyDto.setMonthlyIncome(homeDto
										.getMonthlyIncome().toString());
							familyDto.setIdentity(homeDto.getIdentity());
							familyDto.setMedicalInsurance(homeDto
									.getMedicalInsuranceName());
							familyDto.setUnitSchool(homeDto.getUnitSchool());
							listHelpApplyFamily.add(familyDto);
						}
					}
					requestMemberDto
							.setListHelpApplyFamily(listHelpApplyFamily);
					RequestHelpApplyDto requestHelpApplyDto = new RequestHelpApplyDto();
					BeanUtils.copyProperties(requestMemberDto,
							requestHelpApplyDto);
					requestHelpApplyDto.setBasicUnionId(member
							.getBasicUnionId());
					requestHelpApplyDto.setBasicUnionName(baseUnitName);
					return new AjaxResult(String.valueOf(ResponseCode.success
							.getCode()), ResponseCode.success.getMsg(),
							requestHelpApplyDto);
				}
			} else {
				return new AjaxResult(
						String.valueOf(ResponseCode.member_not_exist.getCode()),
						ResponseCode.member_not_exist.getMsg(),
						ResponseCode.member_not_exist.getMsg());
			}
		} else {
			return new AjaxResult(String.valueOf(ResponseCode.idcard_not_exist
					.getCode()), ResponseCode.idcard_not_exist.getMsg(),
					ResponseCode.idcard_not_exist.getMsg());
		}
	}

	/**
	 * 根据Code和Value获取字典Key
	 * 
	 * @param code
	 * @param value
	 * @return
	 */
	public Integer getDictKey(String code, String value) {
		value = dictionaryService.getFieldKeyByCode(code, value);
		return NumberUtils.toInt(value);
	}

	/**
	 * 根据Code和Key获取字典Value
	 * 
	 * @param code
	 * @param key
	 * @return
	 */
	public String getDictValue(String code, Integer key) {
		String renderValue = dictionaryService.getRenderFieldValue(code,
				String.valueOf(key));
		if (StringUtils.isNotBlank(renderValue))
			return renderValue;
		else
			return "";
	}

	/**
	 * 发起帮扶流程
	 * 
	 * @author tcr
	 * @version 1.0
	 * @date 2017-1-13
	 * @param request
	 * @return
	 */
	// @CrossOrigin
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult startFormProcess(
			NativeWebRequest request,
			RequestHelpApplyDto requestHelpApplyDto,
			@RequestParam(required = false, defaultValue = "") Integer userId,
			@RequestParam(required = false, defaultValue = "") String helpApplyFamilyList,
			@RequestParam(required = false, defaultValue = "") String flowId,
			@RequestParam(required = false, defaultValue = "") String taskId,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(time) && StringUtils.isNotBlank(apptype)
				&& StringUtils.isNotBlank(token)) {
			if (!TokenUtil.validateToken("", Long.parseLong(time.trim()),
					apptype.trim(), token.trim())) {
				map.put("status", ResponseCode.invalidtoken.getCode());
				map.put("message", ResponseCode.invalidtoken.getMsg());
				return new AjaxResult(ResponseCode.invalidtoken.getCode(),
						ResponseCode.invalidtoken.getMsg(), map);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("发起帮扶流程");
		}
		List<RequestHelpApplyFamilyDto> listHelpApplyFamily = new ArrayList<>();
		if (StringUtils.isNotBlank(helpApplyFamilyList)) {
			JSONArray familyJsonList = JSON.parseArray(helpApplyFamilyList);
			if (!familyJsonList.isEmpty()) {
				for (Object familyJson : familyJsonList) {
					RequestHelpApplyFamilyDto requestFamilyDto = JSONObject
							.toJavaObject(JSONObject.parseObject(familyJson
									.toString()),
									RequestHelpApplyFamilyDto.class);
					listHelpApplyFamily.add(requestFamilyDto);
				}
			}
		}
		requestHelpApplyDto.setListHelpApplyFamily(listHelpApplyFamily);
		Integer appUserId = 0;
		if (!NumberUtils.isNull(userId))
			appUserId = userId;
		else
			appUserId = this.getCurrentAppUserId();
		HelpApplyModel helpApply = new HelpApplyModel();
		// ID
		if (!NumberUtils.isNull(requestHelpApplyDto.getId())
				&& !NumberUtils.eqZero(requestHelpApplyDto.getId())) {
			helpApply.setId(requestHelpApplyDto.getId());
		}
		helpApply.setName(requestHelpApplyDto.getName());
		helpApply.setMemberId(requestHelpApplyDto.getMemberId());
		helpApply.setIdCard(requestHelpApplyDto.getIdCard());
		helpApply.setHousingArea(requestHelpApplyDto.getHousingArea());
		helpApply.setPhone(requestHelpApplyDto.getPhone());
		helpApply.setConcatType(requestHelpApplyDto.getConcatType());
		helpApply.setPostcode(requestHelpApplyDto.getPostcode());
		helpApply.setAddress(requestHelpApplyDto.getAddress());
		helpApply.setCompany(requestHelpApplyDto.getCompany());
		helpApply.setFamilyNum(requestHelpApplyDto.getFamilyNum());
		helpApply.setSeatRegistered(requestHelpApplyDto.getSeatRegistered());
		// 困难类型
		if (StringUtils.isNotBlank(requestHelpApplyDto.getDifficultType())) {
			helpApply.setDifficultType(this.getDictKey("CODE_DIFFICULT_TYPE",
					requestHelpApplyDto.getDifficultType()));
		}
		// 性别
		if (StringUtils.isNotBlank(requestHelpApplyDto.getSex())) {
			helpApply.setSex(this.getDictKey("CODE_SEX",
					requestHelpApplyDto.getSex()));
		}
		// 民族
		if (StringUtils.isNotBlank(requestHelpApplyDto.getNation())) {
			helpApply.setNation(this.getDictKey("CODE_NATION",
					requestHelpApplyDto.getNation()));
		}
		// 政治面貌
		if (StringUtils.isNotBlank(requestHelpApplyDto.getPolitical())) {
			helpApply.setPolitical(this.getDictKey("CODE_POLITICAL_LANDSCAPE",
					requestHelpApplyDto.getPolitical()));
		}
		// 出生日期
		if (StringUtils.isNotBlank(requestHelpApplyDto.getBrithday())) {
			helpApply.setBrithday(DateUtils.getDate(requestHelpApplyDto
					.getBrithday()));
		}
		// 健康状况
		if (StringUtils.isNotBlank(requestHelpApplyDto.getHealth())) {
			helpApply.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
					requestHelpApplyDto.getHealth()));
		}
		// 残疾类别
		if (StringUtils.isNotBlank(requestHelpApplyDto.getDisabilityType())) {
			helpApply.setDisabilityType(this.getDictKey("CODE_DISABILITY_TYPE",
					requestHelpApplyDto.getDisabilityType()));
		}
		// 工作状况
		if (StringUtils.isNotBlank(requestHelpApplyDto.getWorkStatus())) {
			helpApply.setWorkStatus(this.getDictKey("CODE_WORK_STATUS",
					requestHelpApplyDto.getWorkStatus()));
		}
		// 劳模类型
		if (StringUtils.isNotBlank(requestHelpApplyDto.getModelType())) {
			helpApply.setModelType(this.getDictKey("CODE_WORKER_MODEL_TYPE",
					requestHelpApplyDto.getModelType()));
		}
		// 住房类型
		if (StringUtils.isNotBlank(requestHelpApplyDto.getHousingType())) {
			helpApply.setHousingType(this.getDictKey("CODE_HOUSING_TYPE",
					requestHelpApplyDto.getHousingType()));
		}
		// 工作年限
		if (StringUtils.isNotBlank(requestHelpApplyDto.getWorkYear())) {
			helpApply.setWorkYear(DateUtils.getDate(requestHelpApplyDto
					.getWorkYear()));
		}
		// 所属行业
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIndustry())) {
			helpApply.setIndustry(this.getDictKey("CODE_INDUSTRY_INVOLVED",
					requestHelpApplyDto.getIndustry()));
		}
		// 婚姻状况
		if (StringUtils.isNotBlank(requestHelpApplyDto.getMaritalStatus())) {
			helpApply.setMaritalStatus(this.getDictKey("CODE_MARITAL_STATUS",
					requestHelpApplyDto.getMaritalStatus()));
		}
		// 户口类别
		if (StringUtils.isNotBlank(requestHelpApplyDto.getCardType())) {
			helpApply.setCardType(this.getDictKey("CODE_CARD_TYPE",
					requestHelpApplyDto.getCardType()));
		}
		// 单位性质
		if (StringUtils.isNotBlank(requestHelpApplyDto.getUnitProperties())) {
			helpApply.setUnitProperties(this.getDictKey("CODE_UNIT_NATURE",
					requestHelpApplyDto.getUnitProperties()));
		}
		// 公司状况
		if (StringUtils.isNotBlank(requestHelpApplyDto.getCompanyStatus())) {
			helpApply.setCompanyStatus(this.getDictKey(
					"CODE_COMPANY_CONDITION",
					requestHelpApplyDto.getCompanyStatus()));
		}
		// 是否单亲
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIsSingleParent())) {
			helpApply.setIsSingleParent(this.getDictKey("CODE_SINGLE_PARENT",
					requestHelpApplyDto.getIsSingleParent()));
		}
		// 本人月平均收入
		if (StringUtils.isNotBlank(requestHelpApplyDto.getMonthlyIncome())) {
			helpApply.setMonthlyIncome(new BigDecimal(requestHelpApplyDto
					.getMonthlyIncome()));
		}
		// 家庭其他非薪资年收入
		if (StringUtils.isNotBlank(requestHelpApplyDto.getFamilySalaryIncome())) {
			helpApply.setFamilySalaryIncome(new BigDecimal(requestHelpApplyDto
					.getFamilySalaryIncome()));
		}
		// 家庭年度总收入
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIncomeTotal())) {
			helpApply.setIncomeTotal(new BigDecimal(requestHelpApplyDto
					.getIncomeTotal()));
		}
		// 家庭月人均收入
		if (StringUtils.isNotBlank(requestHelpApplyDto.getAvgMonthlyIncome())) {
			helpApply.setAvgMonthlyIncome(new BigDecimal(requestHelpApplyDto
					.getAvgMonthlyIncome()));
		}
		// 医保状况
		if (StringUtils.isNotBlank(requestHelpApplyDto.getMedicalInsurance())) {
			helpApply.setMedicalInsurance(this.getDictKey(
					"CODE_SURANCE_STATUS",
					requestHelpApplyDto.getMedicalInsurance()));
		}
		// 是否有一定自救能力
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIsHasSelfSave())) {
			helpApply.setIsHasSelfSave(this.getDictKey(
					"CODE_HELP_ONESELF_ABILITY",
					requestHelpApplyDto.getIsHasSelfSave()));
		}
		// 是否为零就业家庭
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIsZeroJob())) {
			helpApply.setIsZeroJob(this.getDictKey("CODE_ZERO_WORK_FAMILY",
					requestHelpApplyDto.getIsZeroJob()));
		}
		// 致困原因
		if (StringUtils.isNotBlank(requestHelpApplyDto.getPovertyCauses())) {
			String[] povertyId = requestHelpApplyDto.getPovertyCauses().split(
					",");
			if (!CollectionUtils.isEmpty(povertyId)) {
				for (int i = 0; i < povertyId.length; i++) {
					povertyId[i] = this.getDictKey("CODE_STUCK_REASON",
							povertyId[i]) + "";
				}
			}
			helpApply.setPovertyCauses(StringUtils.join(povertyId, ","));
		}
		// 帮扶类型
		if (StringUtils.isNotBlank(requestHelpApplyDto.getHelpType())) {
			helpApply.setHelpType(this.getDictKey("CODE_HELP_TYPE",
					requestHelpApplyDto.getHelpType()));
		}
		// 家庭年人均收入
		if (StringUtils.isNotBlank(requestHelpApplyDto.getIncomeAvg())) {
			helpApply.setIncomeAvg(new BigDecimal(requestHelpApplyDto
					.getIncomeAvg()));
		}
		helpApply.setOpenBank(requestHelpApplyDto.getOpenBank());
		helpApply.setBranchBank(requestHelpApplyDto.getBranchBank());
		helpApply.setCardNum(requestHelpApplyDto.getCardNum());
		helpApply.setHelpUnit(requestHelpApplyDto.getHelpUnit());
		helpApply.setIsSign(0);
		helpApply.setCreateTime(new Date());
		helpApply.setAppUserId(appUserId);
		helpApply.setReasons(requestHelpApplyDto.getReasons());
		helpApply.setUnionChairId(requestHelpApplyDto.getBasicUnionId());
		helpApply.setIdCardImgId(requestHelpApplyDto.getIdCardImgId());
		helpApply.setFamIdCardImgId(requestHelpApplyDto.getFamIdCardImgId());
		helpApply.setPovertyImgId(requestHelpApplyDto.getPovertyImgId());
		List<HelpApplyFamilyModel> familyList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(requestHelpApplyDto
				.getListHelpApplyFamily())) {
			for (RequestHelpApplyFamilyDto familyDto : requestHelpApplyDto
					.getListHelpApplyFamily()) {
				HelpApplyFamilyModel familyModel = new HelpApplyFamilyModel();
				familyModel.setName(familyDto.getName());
				familyModel.setIdCard(familyDto.getIdCard());
				familyModel.setIdentity(familyDto.getIdentity());
				familyModel.setUnitSchool(familyDto.getUnitSchool());
				familyModel.setMemberId(helpApply.getMemberId());
				if (StringUtils.isNotBlank(familyDto.getFamilyRelationship())) {
					familyModel.setFamilyRelationship(this.getDictKey(
							"CODE_FAMILY_RELATIONSHIP",
							familyDto.getFamilyRelationship()));
				}
				if (StringUtils.isNotBlank(familyDto.getSex())) {
					familyModel.setSex(this.getDictKey("CODE_SEX",
							familyDto.getSex()));
				}
				if (StringUtils.isNotBlank(familyDto.getPolitical())) {
					familyModel.setPolitical(this.getDictKey(
							"CODE_POLITICAL_LANDSCAPE",
							familyDto.getPolitical()));
				}
				if (StringUtils.isNotBlank(familyDto.getHealth())) {
					familyModel.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
							familyDto.getHealth()));
				}
				if (StringUtils.isNotBlank(familyDto.getMonthlyIncome())) {
					familyModel.setMonthlyIncome(new BigDecimal(familyDto
							.getMonthlyIncome()));
				}
				if (StringUtils.isNotBlank(familyDto.getMedicalInsurance())) {
					familyModel.setMedicalInsurance(this.getDictKey(
							"CODE_SURANCE_STATUS",
							familyDto.getMedicalInsurance()));
				}
				familyList.add(familyModel);
			}
		}
		Map<String, Object> resultMap = helpApplyService.saveHelpApply(
				helpApply, familyList, appUserId, flowId, taskId);
		if (resultMap.containsKey("error")) {
			return new AjaxResult(
					String.valueOf(ResponseCode.approveProcess_not_exist
							.getCode()),
					ResponseCode.approveProcess_not_exist.getMsg(),
					ResponseCode.approveProcess_not_exist.getMsg());
		} else {
			return new AjaxResult(
					String.valueOf(ResponseCode.success.getCode()),
					ResponseCode.success.getMsg(), resultMap);
		}
	}

	/**
	 * 根据id获取帮扶相关数据
	 * 
	 * @param id
	 * @return
	 */
	public RequestHelpApplyDto getHelpApplyDto(HelpApplyModel helpApply) {
		RequestHelpApplyDto requestHelpApplyDto = new RequestHelpApplyDto();
		BeanUtils.copyProperties(helpApply, requestHelpApplyDto);
		// 困难类型
		if (!NumberUtils.isNull(helpApply.getDifficultType())) {
			requestHelpApplyDto.setDifficultType(this.getDictValue(
					"CODE_DIFFICULT_TYPE", helpApply.getDifficultType()));
		} else {
			requestHelpApplyDto.setDifficultType("");
		}
		// 性别
		if (!NumberUtils.isNull(helpApply.getSex())) {
			requestHelpApplyDto.setSex(this.getDictValue("CODE_SEX",
					helpApply.getSex()));
		} else {
			requestHelpApplyDto.setSex("");
		}
		// 民族
		if (!NumberUtils.isNull(helpApply.getNation())) {
			requestHelpApplyDto.setNation(this.getDictValue("CODE_NATION",
					helpApply.getNation()));
		} else {
			requestHelpApplyDto.setNation("");
		}
		// 政治面貌
		if (!NumberUtils.isNull(helpApply.getPolitical())) {
			requestHelpApplyDto.setPolitical(this.getDictValue(
					"CODE_POLITICAL_LANDSCAPE", helpApply.getPolitical()));
		} else {
			requestHelpApplyDto.setPolitical("");
		}
		// 出生日期
		if (null != helpApply.getBrithday()) {
			requestHelpApplyDto.setBrithday(DateUtils.getDay(helpApply
					.getBrithday()));
		} else {
			requestHelpApplyDto.setBrithday("");
		}
		// 健康状况
		if (!NumberUtils.isNull(helpApply.getHealth())) {
			requestHelpApplyDto.setHealth(this.getDictValue(
					"CODE_HEALTH_STATUS", helpApply.getHealth()));
		} else {
			requestHelpApplyDto.setHealth("");
		}
		// 残疾类别
		if (!NumberUtils.isNull(helpApply.getDisabilityType())) {
			requestHelpApplyDto.setDisabilityType(this.getDictValue(
					"CODE_DISABILITY_TYPE", helpApply.getDisabilityType()));
		} else {
			requestHelpApplyDto.setDisabilityType("");
		}
		// 工作状况
		if (!NumberUtils.isNull(helpApply.getWorkStatus())) {
			requestHelpApplyDto.setWorkStatus(this.getDictValue(
					"CODE_WORK_STATUS", helpApply.getWorkStatus()));
		} else {
			requestHelpApplyDto.setWorkStatus("");
		}
		// 劳模类型
		if (!NumberUtils.isNull(helpApply.getModelType())) {
			requestHelpApplyDto.setModelType(this.getDictValue(
					"CODE_WORKER_MODEL_TYPE", helpApply.getModelType()));
		} else {
			requestHelpApplyDto.setModelType("");
		}
		// 住房类型
		if (!NumberUtils.isNull(helpApply.getHousingType())) {
			requestHelpApplyDto.setHousingType(this.getDictValue(
					"CODE_HOUSING_TYPE", helpApply.getHousingType()));
		} else {
			requestHelpApplyDto.setHousingType("");
		}
		// 工作年限
		if (null != helpApply.getWorkYear()) {
			requestHelpApplyDto.setWorkYear(DateUtils.getDay(helpApply
					.getWorkYear()));
		} else {
			requestHelpApplyDto.setWorkYear("");
		}
		// 所属行业
		if (!NumberUtils.isNull(helpApply.getIndustry())) {
			requestHelpApplyDto.setIndustry(this.getDictValue(
					"CODE_INDUSTRY_INVOLVED", helpApply.getIndustry()));
		} else {
			requestHelpApplyDto.setIndustry("");
		}
		// 婚姻状况
		if (!NumberUtils.isNull(helpApply.getMaritalStatus())) {
			requestHelpApplyDto.setMaritalStatus(this.getDictValue(
					"CODE_MARITAL_STATUS", helpApply.getMaritalStatus()));
		} else {
			requestHelpApplyDto.setMaritalStatus("");
		}
		// 户口类别
		if (!NumberUtils.isNull(helpApply.getCardType())) {
			requestHelpApplyDto.setCardType(this.getDictValue("CODE_CARD_TYPE",
					helpApply.getCardType()));
		} else {
			requestHelpApplyDto.setCardType("");
		}
		// 单位性质
		if (!NumberUtils.isNull(helpApply.getUnitProperties())) {
			requestHelpApplyDto.setUnitProperties(this.getDictValue(
					"CODE_UNIT_NATURE", helpApply.getUnitProperties()));
		} else {
			requestHelpApplyDto.setUnitProperties("");
		}
		// 公司状况
		if (!NumberUtils.isNull(helpApply.getCompanyStatus())) {
			requestHelpApplyDto.setCompanyStatus(this.getDictValue(
					"CODE_COMPANY_CONDITION", helpApply.getCompanyStatus()));
		} else {
			requestHelpApplyDto.setCompanyStatus("");
		}
		// 是否单亲
		if (!NumberUtils.isNull(helpApply.getIsSingleParent())) {
			requestHelpApplyDto.setIsSingleParent(this.getDictValue(
					"CODE_SINGLE_PARENT", helpApply.getIsSingleParent()));
		} else {
			requestHelpApplyDto.setIsSingleParent("");
		}
		// 本人月平均收入
		if (null != helpApply.getMonthlyIncome()) {
			requestHelpApplyDto.setMonthlyIncome(helpApply.getMonthlyIncome()
					.toString());
		} else {
			requestHelpApplyDto.setMonthlyIncome("0.00");
		}
		// 家庭其他非薪资年收入
		if (null != helpApply.getFamilySalaryIncome()) {
			requestHelpApplyDto.setFamilySalaryIncome(helpApply
					.getFamilySalaryIncome().toString());
		} else {
			requestHelpApplyDto.setFamilySalaryIncome("0.00");
		}
		// 家庭年度总收入
		if (null != helpApply.getIncomeTotal()) {
			requestHelpApplyDto.setIncomeTotal(helpApply.getIncomeTotal()
					.toString());
		} else {
			requestHelpApplyDto.setIncomeTotal("0.00");
		}
		// 家庭月人均收入
		if (null != helpApply.getAvgMonthlyIncome()) {
			requestHelpApplyDto.setAvgMonthlyIncome(helpApply
					.getAvgMonthlyIncome().toString());
		} else {
			requestHelpApplyDto.setAvgMonthlyIncome("0.00");
		}
		// 医保状况
		if (!NumberUtils.isNull(helpApply.getMedicalInsurance())
				&& !NumberUtils.eqZero(helpApply.getMedicalInsurance())) {
			requestHelpApplyDto.setMedicalInsurance(this.getDictValue(
					"CODE_SURANCE_STATUS", helpApply.getMedicalInsurance()));
		} else {
			requestHelpApplyDto.setMedicalInsurance("");
		}
		// 是否有一定自救能力
		if (!NumberUtils.isNull(helpApply.getIsHasSelfSave())) {
			requestHelpApplyDto.setIsHasSelfSave(this.getDictValue(
					"CODE_HELP_ONESELF_ABILITY", helpApply.getIsHasSelfSave()));
		} else {
			requestHelpApplyDto.setIsHasSelfSave("");
		}
		// 是否为零就业家庭
		if (!NumberUtils.isNull(helpApply.getIsZeroJob())) {
			requestHelpApplyDto.setIsZeroJob(this.getDictValue(
					"CODE_ZERO_WORK_FAMILY", helpApply.getIsZeroJob()));
		} else {
			requestHelpApplyDto.setIsZeroJob("");
		}
		// 致困原因
		if (StringUtils.isNotBlank(helpApply.getPovertyCauses())) {
			String[] povertyId = helpApply.getPovertyCauses().split(",");
			if (!CollectionUtils.isEmpty(povertyId)) {
				for (int i = 0; i < povertyId.length; i++) {
					povertyId[i] = this.getDictValue("CODE_STUCK_REASON",
							NumberUtils.toInt(povertyId[i]));
				}
			}
			requestHelpApplyDto.setPovertyCauses(StringUtils.join(povertyId,
					","));
		} else {
			requestHelpApplyDto.setPovertyCauses("");
		}
		// 帮扶类型
		if (!NumberUtils.isNull(helpApply.getHelpType())) {
			requestHelpApplyDto.setHelpType(this.getDictValue("CODE_HELP_TYPE",
					helpApply.getHelpType()));
		} else {
			requestHelpApplyDto.setHelpType("");
		}
		// 家庭年人均收入
		if (null != helpApply.getIncomeAvg()) {
			requestHelpApplyDto.setIncomeAvg(helpApply.getIncomeAvg()
					.toString());
		} else {
			requestHelpApplyDto.setIncomeAvg("0.00");
		}
		// 获取帮扶家庭成员信息
		Condition condition = new Condition(HelpApplyFamilyModel.class);
		condition.createCriteria().andEqualTo("helpApplyId", helpApply.getId());
		List<HelpApplyFamilyModel> familyModelList = helpApplyFamilyService
				.selectByCondition(condition);
		List<RequestHelpApplyFamilyDto> helpApplyFamilyList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(familyModelList)) {
			for (HelpApplyFamilyModel familyModel : familyModelList) {
				RequestHelpApplyFamilyDto requestFamilyDto = new RequestHelpApplyFamilyDto();
				BeanUtils.copyProperties(familyModel, requestFamilyDto);
				if (!NumberUtils.isNull(familyModel.getFamilyRelationship())
						&& !NumberUtils.eqZero(familyModel
								.getFamilyRelationship())) {
					requestFamilyDto.setFamilyRelationship(this.getDictValue(
							"CODE_FAMILY_RELATIONSHIP",
							familyModel.getFamilyRelationship()));
				}
				if (!NumberUtils.isNull(familyModel.getSex())
						&& !NumberUtils.eqZero(familyModel.getSex())) {
					requestFamilyDto.setSex(this.getDictValue("CODE_SEX",
							familyModel.getSex()));
				}
				if (!NumberUtils.isNull(familyModel.getPolitical())
						&& !NumberUtils.eqZero(familyModel.getPolitical())) {
					requestFamilyDto.setPolitical(this.getDictValue(
							"CODE_POLITICAL_LANDSCAPE",
							familyModel.getPolitical()));
				}
				if (!NumberUtils.isNull(familyModel.getHealth())
						&& !NumberUtils.eqZero(familyModel.getHealth())) {
					requestFamilyDto.setHealth(this.getDictValue(
							"CODE_HEALTH_STATUS", familyModel.getHealth()));
				}
				if (null != familyModel.getMonthlyIncome()) {
					requestFamilyDto.setMonthlyIncome(familyModel
							.getMonthlyIncome().toString());
				} else {
					requestFamilyDto.setMonthlyIncome("0.00");
				}
				if (!NumberUtils.isNull(familyModel.getMedicalInsurance())
						&& !NumberUtils.eqZero(familyModel
								.getMedicalInsurance())) {
					requestFamilyDto.setMedicalInsurance(this.getDictValue(
							"CODE_SURANCE_STATUS",
							familyModel.getMedicalInsurance()));
				}
				helpApplyFamilyList.add(requestFamilyDto);
			}
		} else {
			// 获取家庭成员
			Condition homeCon = new Condition(MemberHome.class);
			homeCon.createCriteria().andEqualTo("memberId", helpApply.getId());
			List<MemberHome> homeList = memberHomeService
					.selectByCondition(homeCon);
			if (CollectionUtils.isNotEmpty(homeList)) {
				for (MemberHome home : homeList) {
					RequestHelpApplyFamilyDto familyDto = new RequestHelpApplyFamilyDto();
					MemberHomeDto homeDto = new MemberHomeDto();
					BeanUtils.copyProperties(home, homeDto);
					DictoryCodeUtils.renderCode(homeDto);
					familyDto.setName(homeDto.getMembers());
					familyDto.setMemberId(homeDto.getMemberId());
					familyDto.setFamilyRelationship(homeDto
							.getFamilyRelationshipName());
					familyDto.setSex(homeDto.getSexName());
					familyDto.setPolitical(homeDto.getPoliticalName());
					familyDto.setIdCard(homeDto.getIdCard());
					familyDto.setHealth(homeDto.getHealthName());
					if (homeDto.getMonthlyIncome() != null)
						familyDto.setMonthlyIncome(homeDto.getMonthlyIncome()
								.toString());
					familyDto.setIdentity(homeDto.getIdentity());
					familyDto.setMedicalInsurance(homeDto
							.getMedicalInsuranceName());
					familyDto.setUnitSchool(homeDto.getUnitSchool());
					helpApplyFamilyList.add(familyDto);
				}
			}
		}
		if (StringUtils.isNotBlank(helpApply.getIdCardImgId())) {
			List<String> idCardImgId = Arrays.asList(helpApply.getIdCardImgId()
					.split(","));
			if (!CollectionUtils.isEmpty(idCardImgId)) {
				Integer[] foo = new Integer[idCardImgId.size()];
				for (int i = 0; i < idCardImgId.size(); i++) {
					foo[i] = NumberUtils.toInt(idCardImgId.get(i), 0);
				}
				Iterable<Integer> iterable = Arrays.asList(foo);
				Condition con = new Condition(UploadFile.class);
				con.createCriteria().andIn("id", iterable);
				List<UploadFile> uploadFiled = uploadFileService
						.selectByCondition(con);
				String[] idCardImgUrlArray = new String[uploadFiled.size()];
				if (CollectionUtils.isNotEmpty(uploadFiled)) {
					for (int i = 0; i < uploadFiled.size(); i++) {
						idCardImgUrlArray[i] = uploadFiled.get(i).getFilePath();
					}
					requestHelpApplyDto.setIdCardImgUrl(StringUtils.join(
							idCardImgUrlArray, ","));
				}
			}
		} else {
			requestHelpApplyDto.setIdCardImgUrl("");
		}
		if (StringUtils.isNotBlank(helpApply.getFamIdCardImgId())) {
			List<String> famIdCardImgId = Arrays.asList(helpApply
					.getFamIdCardImgId().split(","));
			if (!CollectionUtils.isEmpty(famIdCardImgId)) {
				Integer[] foo = new Integer[famIdCardImgId.size()];
				for (int i = 0; i < famIdCardImgId.size(); i++) {
					foo[i] = NumberUtils.toInt(famIdCardImgId.get(i), 0);
				}
				Iterable<Integer> iterable = Arrays.asList(foo);
				Condition con = new Condition(UploadFile.class);
				con.createCriteria().andIn("id", iterable);
				List<UploadFile> uploadFiled = uploadFileService
						.selectByCondition(con);
				String[] famIdCardImgUrlArray = new String[uploadFiled.size()];
				if (CollectionUtils.isNotEmpty(uploadFiled)) {
					for (int i = 0; i < uploadFiled.size(); i++) {
						famIdCardImgUrlArray[i] = uploadFiled.get(i)
								.getFilePath();
					}
					requestHelpApplyDto.setFamIdCardImgUrl(StringUtils.join(
							famIdCardImgUrlArray, ","));
				}
			}
		} else {
			requestHelpApplyDto.setFamIdCardImgUrl("");
		}
		if (StringUtils.isNotBlank(helpApply.getPovertyImgId())) {
			List<String> povertyImgId = Arrays.asList(helpApply
					.getPovertyImgId().split(","));
			if (!CollectionUtils.isEmpty(povertyImgId)) {
				Integer[] foo = new Integer[povertyImgId.size()];
				for (int i = 0; i < povertyImgId.size(); i++) {
					foo[i] = NumberUtils.toInt(povertyImgId.get(i), 0);
				}
				Iterable<Integer> iterable = Arrays.asList(foo);
				Condition con = new Condition(UploadFile.class);
				con.createCriteria().andIn("id", iterable);
				List<UploadFile> uploadFiled = uploadFileService
						.selectByCondition(con);
				String[] povertyImgUrlArray = new String[uploadFiled.size()];
				if (CollectionUtils.isNotEmpty(uploadFiled)) {
					for (int i = 0; i < uploadFiled.size(); i++) {
						povertyImgUrlArray[i] = uploadFiled.get(i)
								.getFilePath();
					}
					requestHelpApplyDto.setPovertyImgUrl(StringUtils.join(
							povertyImgUrlArray, ","));
				}
			}
		} else {
			requestHelpApplyDto.setPovertyImgUrl("");
		}
		requestHelpApplyDto.setListHelpApplyFamily(helpApplyFamilyList);
		return requestHelpApplyDto;
	}

	/**
	 * 根据帮扶ID和流程ID获取帮扶详情
	 * 
	 * @param request
	 * @param idCard
	 * @return
	 * @throws Exception
	 */
	// @CrossOrigin
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findHelpApply", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getHelpInfo(NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") String flowId,
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(time) && StringUtils.isNotBlank(apptype)
				&& StringUtils.isNotBlank(token)) {
			if (!TokenUtil.validateToken("", Long.parseLong(time.trim()),
					apptype.trim(), token.trim())) {
				map.put("status", ResponseCode.invalidtoken.getCode());
				map.put("message", ResponseCode.invalidtoken.getMsg());
				return new AjaxResult(ResponseCode.invalidtoken.getCode(),
						ResponseCode.invalidtoken.getMsg(), map);
			}
		}
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			HelpApplyModel helpApply = helpApplyService.selectByKey(id);
			RequestHelpApplyDto requestHelpApplyDto = getHelpApplyDto(helpApply);
			requestHelpApplyDto.setBasicUnionId(helpApply.getUnionChairId());
			if (!NumberUtils.isNull(helpApply.getUnionChairId())
					&& !NumberUtils.eqZero(helpApply.getUnionChairId())) {
				OrgainModel orgModel = orgainService.selectByKey(helpApply
						.getUnionChairId());
				if (null != orgModel) {
					requestHelpApplyDto.setBasicUnionName(orgModel
							.getOrgainName());
				}
			} else {
				requestHelpApplyDto.setBasicUnionName("");
			}
			List<Map<String, Object>> nodeList = workFlowService
					.getTaskAndVariables(flowId);
			List<Map<String, Object>> workFlowList = new ArrayList<>();
			// String taskType = "";
			if (CollectionUtils.isNotEmpty(nodeList)) {
				for (Map<String, Object> node : nodeList) {
					String taskName = node.get("elementName").toString();
					Map<String, Object> workflow = new HashMap<>();
					workflow.put("taskName", taskName);
					if (null != node.get("approvalStatus")) {
						workflow.put("approvalStatus",
								node.get("approvalStatus").toString());
					} else {
						workflow.put("approvalStatus", "");
					}
					if (null != node.get("approveTime")) {
						workflow.put("approveTime", node.get("approveTime")
								.toString());
					} else {
						workflow.put("approveTime", "");
					}
					if (null != node.get("flag")) {
						workflow.put("isEnd", node.get("flag").toString());
					} else {
						workflow.put("isEnd", 1);
					}
					// if (null != node.get("taskType")) {
					// taskType = node.get("taskType").toString();
					// }
					workFlowList.add(workflow);
				}
				// requestHelpApplyDto.setTaskType(taskType);
				requestHelpApplyDto.setWorkFlowList(workFlowList);
			} else {
				requestHelpApplyDto.setWorkFlowList(Collections.EMPTY_LIST);
			}
			return new AjaxResult(
					String.valueOf(ResponseCode.success.getCode()),
					ResponseCode.success.getMsg(), requestHelpApplyDto);
		} else {
			throw new BusinessException("帮扶id不能为空");
		}
	}
}
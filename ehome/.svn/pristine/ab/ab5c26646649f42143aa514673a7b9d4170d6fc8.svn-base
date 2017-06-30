package com.ehome.cloud.help.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

import com.ehome.activiti.services.WorkFlowService;
import com.ehome.activiti.services.WorkFlowUtilService;
import com.ehome.cloud.help.dto.HelpApplyDto;
import com.ehome.cloud.help.dto.HelpApplyUserDto;
import com.ehome.cloud.help.mapper.HelpApplyMapper;
import com.ehome.cloud.help.model.HelpApplyFamilyModel;
import com.ehome.cloud.help.model.HelpApplyModel;
import com.ehome.cloud.help.model.MemberHelpModel;
import com.ehome.cloud.help.service.IHelpApplyFamilyService;
import com.ehome.cloud.help.service.IHelpApplyService;
import com.ehome.cloud.help.service.IMemberHelpService;
import com.ehome.cloud.member.dto.HelpMemberImportDto;
import com.ehome.cloud.member.dto.MemberDto;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.cloud.member.service.IAppMemberService;
import com.ehome.cloud.member.service.IMemberHomeService;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.SpringContextHolder;
import com.ehome.core.service.IEhCacheService;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.push.PushUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:HelpApplyServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月22日 下午12:23:14
 * @version:
 */
@Service("helpApplyService")
public class HelpApplyServiceImpl extends BaseService<HelpApplyModel> implements
		IHelpApplyService {

	@Resource
	private IHelpApplyFamilyService helpApplyFamilyService;

	@Resource
	private IDictionaryService dictionaryService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IMemberHelpService memberHelpService;

	@Resource
	private HelpApplyMapper helpApplyMapper;

	@Resource
	private IHelpApplyService helpApplyService;

	@Resource
	private IUserService userService;

	@Resource
	private IMemberHomeService memberHomeService;

	@Resource
	private IAppMemberService appMemberService;

	@Resource
	private IOrgainService orgainService;

	@Resource
	private IEhCacheService ehCacheService;

	@Override
	public Map<String, Object> saveHelpApply(HelpApplyModel helpApply,
			List<HelpApplyFamilyModel> familyList, Integer userId,
			String flowId, String taskId) throws Exception {
		int helpApplyId = 0;
		boolean isUpdate = false;
		Integer memberId = helpApply.getMemberId();
		Map<String, Object> resultMap = new HashMap<>();
		WorkFlowUtilService workFlowUtilService = SpringContextHolder
				.getBean(WorkFlowUtilService.class);
		Member member = new Member();
		member.setId(memberId);
		Member memberinfo = memberService.selectOne(member);
		Integer baseUnionId = memberinfo.getBasicUnionId();
		Integer upperUnionId = memberinfo.getUpperUnionId();
		// 根据归属工会查询流程实例
		ProcessDefinition processDefinition = workFlowUtilService
				.findProcessDefinitionByTenantId("knbf", upperUnionId + "");
		if (null == processDefinition) {
			//throw new BusinessException("没有设置此用户对应的审批流程");
			resultMap.put("error", "没有设置此用户对应的审批流程");
			return resultMap;
		}
		if (!NumberUtils.isNull(helpApply.getId())
				&& !NumberUtils.eqZero(helpApply.getId())) {
			isUpdate = true;
			helpApplyId = helpApply.getId();
			// 修改帮扶申请信息
			this.updateNotNull(helpApply);
			Condition condition = new Condition(HelpApplyFamilyModel.class);
			condition.createCriteria().andEqualTo("helpApplyId", helpApplyId);
			helpApplyFamilyService.deleteByCondition(condition);
			if (CollectionUtils.isNotEmpty(familyList)) {
				for (HelpApplyFamilyModel family : familyList) {
					family.setHelpApplyId(helpApplyId);
					helpApplyFamilyService.save(family);
				}
			}
		} else {
			// 新增帮扶申请信息
			this.saveNotNull(helpApply);
			helpApplyId = helpApply.getId();
			if (CollectionUtils.isNotEmpty(familyList)) {
				for (HelpApplyFamilyModel family : familyList) {
					family.setHelpApplyId(helpApplyId);
					helpApplyFamilyService.save(family);
				}
			}
			// 同步会员帮扶信息
			MemberHelpModel memberHelp = new MemberHelpModel();
			memberHelp.setMemberId(helpApply.getMemberId());
			memberHelp = memberHelpService.selectOne(memberHelp);
			if (null == memberHelp) {
				memberHelp = new MemberHelpModel();
				BeanUtils.copyProperties(helpApply, memberHelp);
				memberHelp.setHelpApplyId(helpApplyId);
				memberHelpService.save(memberHelp);
			}
		}
		// 发起流程
		Map<String, String> formProperties = new HashMap<>();
		List<String> deptIdList = new ArrayList<>(), approverList = new ArrayList<>();
		List<Map<String, Object>> varList = orgainService.getChilds(baseUnionId
				.toString());// 查找该节点下的所有数据
		if (CollectionUtils.isNotEmpty(varList)) {
			for (Map<String, Object> var : varList) {
				deptIdList.add(var.get("id").toString());
			}
		}
		if (CollectionUtils.isNotEmpty(deptIdList)) {
			approverList.addAll(userService.selectUserByDeptId(deptIdList));
		}
		String processDefinitionId = processDefinition.getId();
		formProperties.put("helpApplyId", helpApplyId + "");
		formProperties.put("userId", userId + "");
		formProperties.put("approvalStatus", "已提交");
		formProperties.put("helpType",
				this.getDictValue("CODE_HELP_TYPE", helpApply.getHelpType()));
		formProperties.put("name", helpApply.getName());
		// 添加审批时间
		formProperties.put("approveTime", DateUtils.getTime());
		// 表单启动
		WorkFlowService workFlowService = SpringContextHolder
				.getBean(WorkFlowService.class);
		if (isUpdate) {
			formProperties.put("direction", "go");
			// workFlowService.submitTaskFormData(taskId, approverList,
			// formProperties, userId + "");
			ProcessInstance processInstance = workFlowUtilService
					.findProcessInstanceById(flowId);
			workFlowService.submitTaskFormData(processInstance, approverList,
					formProperties, userId + "");
			resultMap.put("id", helpApplyId);
			resultMap.put("flowId", flowId);
			resultMap.put("approveTime",
					DateUtils.getTime(helpApply.getCreateTime()));
		} else {
			formProperties.put("direction", "go");
			ProcessInstance processInstance = workFlowService.startFormProcess(
					processDefinitionId, formProperties.get("userId") + "",
					formProperties);
			workFlowService.submitTaskFormData(processInstance, approverList,
					formProperties, userId + "");
			resultMap.put("id", helpApplyId);
			resultMap.put("flowId", processInstance.getId());
			resultMap.put("approveTime",
					DateUtils.getTime(helpApply.getCreateTime()));
			//跟新帮扶维权流程id，用于归档
			helpApply.setProcessInstanceId(processInstance.getId());
			this.updateNotNull(helpApply);
		}
		return resultMap;
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

	public List<MemberHome> getMemberHomeList(HelpMemberImportDto helpMember,
			Integer memberId) {
		List<MemberHome> memberHomeList = new ArrayList<>();
		if (StringUtils.isNotBlank(helpMember.getMembers1())) {
			MemberHome memberHome = new MemberHome();
			memberHome.setMemberId(memberId);
			memberHome.setMembers(helpMember.getMembers1());
			memberHome.setIdCard(helpMember.getIdCard1());
			memberHome.setIdentity(helpMember.getIdentity1());
			memberHome.setUnitSchool(helpMember.getUnitSchool1());
			if (StringUtils.isNotBlank(helpMember.getMonthlyIncome1())) {
				memberHome.setMonthlyIncome(new BigDecimal(helpMember
						.getMonthlyIncome1()));
			}
			if (StringUtils.isNotBlank(helpMember.getFamilyRelationship1())) {
				memberHome.setFamilyRelationship(this.getDictKey(
						"CODE_FAMILY_RELATIONSHIP",
						helpMember.getFamilyRelationship1()));
			}
			if (StringUtils.isNotBlank(helpMember.getSex1())) {
				memberHome.setSex(this.getDictKey("CODE_SEX",
						helpMember.getSex1()));
			}
			if (StringUtils.isNotBlank(helpMember.getPolitical1())) {
				memberHome
						.setPolitical(this.getDictKey(
								"CODE_POLITICAL_LANDSCAPE",
								helpMember.getPolitical1()));
			}
			if (StringUtils.isNotBlank(helpMember.getHealth1())) {
				memberHome.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
						helpMember.getHealth1()));
			}
			memberHomeList.add(memberHome);
		}
		if (StringUtils.isNotBlank(helpMember.getMembers2())) {
			MemberHome memberHome = new MemberHome();
			memberHome.setMemberId(memberId);
			memberHome.setMembers(helpMember.getMembers2());
			memberHome.setIdCard(helpMember.getIdCard1());
			memberHome.setIdentity(helpMember.getIdentity2());
			memberHome.setUnitSchool(helpMember.getUnitSchool2());
			if (StringUtils.isNotBlank(helpMember.getMonthlyIncome2())) {
				memberHome.setMonthlyIncome(new BigDecimal(helpMember
						.getMonthlyIncome2()));
			}
			if (StringUtils.isNotBlank(helpMember.getFamilyRelationship2())) {
				memberHome.setFamilyRelationship(this.getDictKey(
						"CODE_FAMILY_RELATIONSHIP",
						helpMember.getFamilyRelationship2()));
			}
			if (StringUtils.isNotBlank(helpMember.getSex2())) {
				memberHome.setSex(this.getDictKey("CODE_SEX",
						helpMember.getSex2()));
			}
			if (StringUtils.isNotBlank(helpMember.getPolitical2())) {
				memberHome
						.setPolitical(this.getDictKey(
								"CODE_POLITICAL_LANDSCAPE",
								helpMember.getPolitical2()));
			}
			if (StringUtils.isNotBlank(helpMember.getHealth2())) {
				memberHome.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
						helpMember.getHealth2()));
			}
			memberHomeList.add(memberHome);
		}
		if (StringUtils.isNotBlank(helpMember.getMembers3())) {
			MemberHome memberHome = new MemberHome();
			memberHome.setMemberId(memberId);
			memberHome.setMembers(helpMember.getMembers3());
			memberHome.setIdCard(helpMember.getIdCard3());
			memberHome.setIdentity(helpMember.getIdentity3());
			memberHome.setUnitSchool(helpMember.getUnitSchool3());
			if (StringUtils.isNotBlank(helpMember.getMonthlyIncome3())) {
				memberHome.setMonthlyIncome(new BigDecimal(helpMember
						.getMonthlyIncome3()));
			}
			if (StringUtils.isNotBlank(helpMember.getFamilyRelationship3())) {
				memberHome.setFamilyRelationship(this.getDictKey(
						"CODE_FAMILY_RELATIONSHIP",
						helpMember.getFamilyRelationship3()));
			}
			if (StringUtils.isNotBlank(helpMember.getSex3())) {
				memberHome.setSex(this.getDictKey("CODE_SEX",
						helpMember.getSex3()));
			}
			if (StringUtils.isNotBlank(helpMember.getPolitical3())) {
				memberHome
						.setPolitical(this.getDictKey(
								"CODE_POLITICAL_LANDSCAPE",
								helpMember.getPolitical3()));
			}
			if (StringUtils.isNotBlank(helpMember.getHealth3())) {
				memberHome.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
						helpMember.getHealth3()));
			}
			memberHomeList.add(memberHome);
		}
		if (StringUtils.isNotBlank(helpMember.getMembers4())) {
			MemberHome memberHome = new MemberHome();
			memberHome.setMemberId(memberId);
			memberHome.setMembers(helpMember.getMembers4());
			memberHome.setIdCard(helpMember.getIdCard4());
			memberHome.setIdentity(helpMember.getIdentity4());
			memberHome.setUnitSchool(helpMember.getUnitSchool4());
			if (StringUtils.isNotBlank(helpMember.getMonthlyIncome4())) {
				memberHome.setMonthlyIncome(new BigDecimal(helpMember
						.getMonthlyIncome4()));
			}
			if (StringUtils.isNotBlank(helpMember.getFamilyRelationship4())) {
				memberHome.setFamilyRelationship(this.getDictKey(
						"CODE_FAMILY_RELATIONSHIP",
						helpMember.getFamilyRelationship4()));
			}
			if (StringUtils.isNotBlank(helpMember.getSex4())) {
				memberHome.setSex(this.getDictKey("CODE_SEX",
						helpMember.getSex4()));
			}
			if (StringUtils.isNotBlank(helpMember.getPolitical4())) {
				memberHome
						.setPolitical(this.getDictKey(
								"CODE_POLITICAL_LANDSCAPE",
								helpMember.getPolitical4()));
			}
			if (StringUtils.isNotBlank(helpMember.getHealth4())) {
				memberHome.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
						helpMember.getHealth4()));
			}
			memberHomeList.add(memberHome);
		}
		if (StringUtils.isNotBlank(helpMember.getMembers5())) {
			MemberHome memberHome = new MemberHome();
			memberHome.setMemberId(memberId);
			memberHome.setMembers(helpMember.getMembers5());
			memberHome.setIdCard(helpMember.getIdCard5());
			memberHome.setIdentity(helpMember.getIdentity5());
			memberHome.setUnitSchool(helpMember.getUnitSchool5());
			if (StringUtils.isNotBlank(helpMember.getMonthlyIncome5())) {
				memberHome.setMonthlyIncome(new BigDecimal(helpMember
						.getMonthlyIncome5()));
			}
			if (StringUtils.isNotBlank(helpMember.getFamilyRelationship5())) {
				memberHome.setFamilyRelationship(this.getDictKey(
						"CODE_FAMILY_RELATIONSHIP",
						helpMember.getFamilyRelationship5()));
			}
			if (StringUtils.isNotBlank(helpMember.getSex5())) {
				memberHome.setSex(this.getDictKey("CODE_SEX",
						helpMember.getSex5()));
			}
			if (StringUtils.isNotBlank(helpMember.getPolitical5())) {
				memberHome
						.setPolitical(this.getDictKey(
								"CODE_POLITICAL_LANDSCAPE",
								helpMember.getPolitical5()));
			}
			if (StringUtils.isNotBlank(helpMember.getHealth5())) {
				memberHome.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
						helpMember.getHealth5()));
			}
			memberHomeList.add(memberHome);
		}
		return memberHomeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveImportHelpMember(
			List<HelpMemberImportDto> listHelpMember, Integer userId,
			Integer baseOrgId, Integer upOrgId) {
		Map<String, Object> result = new HashMap<>();
		if (CollectionUtils.isNotEmpty(listHelpMember)) {
			ehCacheService.setCache(IEhCacheService.CACHE_HELP_IMPORT,
					"HELP_TOTAL_COUNT", listHelpMember.size());
			int count = 0;
			for (HelpMemberImportDto helpMember : listHelpMember) {
				// 组装会员信息
				MemberDto member = new MemberDto();
				BeanUtils.copyProperties(helpMember, member);
				if (StringUtils.isNotBlank(helpMember.getBrithday())) {
					member.setBirthday(DateUtils.getDate(helpMember
							.getBrithday()));
				}
				member.setMemberName(helpMember.getName());
				member.setStatus(1);
				member.setTel(helpMember.getPhone());
				member.setSex(this.getDictKey("CODE_SEX", helpMember.getSex()));
				member.setNation(this.getDictKey("CODE_NATION",
						helpMember.getNation()));
				member.setPolitical(this.getDictKey("CODE_POLITICAL_LANDSCAPE",
						helpMember.getPolitical()));
				member.setMaritalStatus(this.getDictKey("CODE_MARITAL_STATUS",
						helpMember.getMaritalStatus()));
				member.setTel(helpMember.getPhone());
				member.setAddress(helpMember.getAddress());
				member.setUpperUnionId(upOrgId);
				int memberId = memberService.insertMember(userId, baseOrgId,
						member);
				// 组装会员家庭成员信息
				List<MemberHome> homeList = getMemberHomeList(helpMember,
						memberId);
				// 插入会员家庭成员表
				if (CollectionUtils.isNotEmpty(homeList))
					memberHomeService.insertList4Mysql(homeList);
				// 组装会员帮扶关联表信息
				MemberHelpModel memberHelp = new MemberHelpModel();
				BeanUtils.copyProperties(helpMember, memberHelp);
				memberHelp.setMemberId(memberId);
				if (!NumberUtils.isNull(helpMember.getFamilyNum()))
					memberHelp.setFamilyNum(helpMember.getFamilyNum());
				if (StringUtils.isNotBlank(helpMember.getCardType())) {
					memberHelp.setCardType(this.getDictKey("CODE_CARD_TYPE",
							helpMember.getCardType()));
				}
				if (StringUtils.isNotBlank(helpMember.getBrithday())) {
					memberHelp.setBrithday(DateUtils.getDate(helpMember
							.getBrithday()));
				}
				if (StringUtils.isNotBlank(helpMember.getDifficultType())) {
					memberHelp.setDifficultType(this.getDictKey(
							"CODE_DIFFICULT_TYPE",
							helpMember.getDifficultType()));
				}
				if (StringUtils.isNotBlank(helpMember.getHealth())) {
					memberHelp.setHealth(this.getDictKey("CODE_HEALTH_STATUS",
							helpMember.getHealth()));
				}
				if (StringUtils.isNotBlank(helpMember.getDisabilityType())) {
					memberHelp.setDisabilityType(this.getDictKey(
							"CODE_DISABILITY_TYPE",
							helpMember.getDisabilityType()));
				}
				if (StringUtils.isNotBlank(helpMember.getWorkStatus())) {
					memberHelp.setWorkStatus(this.getDictKey(
							"CODE_WORK_STATUS", helpMember.getWorkStatus()));
				}
				if (StringUtils.isNotBlank(helpMember.getModelType())) {
					memberHelp
							.setModelType(this.getDictKey(
									"CODE_WORKER_MODEL_TYPE",
									helpMember.getModelType()));
				}
				if (StringUtils.isNotBlank(helpMember.getHousingType())) {
					memberHelp.setHousingType(this.getDictKey(
							"CODE_HOUSING_TYPE", helpMember.getHousingType()));
				}
				if (StringUtils.isNotBlank(helpMember.getWorkYear())) {
					memberHelp.setWorkYear(DateUtils.getDate(helpMember
							.getWorkYear()));
				}
				if (StringUtils.isNotBlank(helpMember.getIndustry())) {
					memberHelp
							.setIndustry(this.getDictKey(
									"CODE_INDUSTRY_INVOLVED",
									helpMember.getIndustry()));
				}
				if (StringUtils.isNotBlank(helpMember.getUnitProperties())) {
					memberHelp
							.setUnitProperties(this.getDictKey(
									"CODE_UNIT_NATURE",
									helpMember.getUnitProperties()));
				}
				if (StringUtils.isNotBlank(helpMember.getCompanyStatus())) {
					memberHelp.setCompanyStatus(this.getDictKey(
							"CODE_COMPANY_CONDITION",
							helpMember.getCompanyStatus()));
				}
				if (StringUtils.isNotBlank(helpMember.getIsSingleParent())) {
					memberHelp.setIsSingleParent(this.getDictKey(
							"CODE_SINGLE_PARENT",
							helpMember.getIsSingleParent()));
				}
				if (StringUtils.isNotBlank(helpMember.getMedicalInsurance())) {
					memberHelp.setMedicalInsurance(this.getDictKey(
							"CODE_SURANCE_STATUS",
							helpMember.getMedicalInsurance()));
				}
				if (StringUtils.isNotBlank(helpMember.getIsHasSelfSave())) {
					memberHelp.setIsHasSelfSave(this.getDictKey(
							"CODE_HELP_ONESELF_ABILITY",
							helpMember.getIsHasSelfSave()));
				}
				if (StringUtils.isNotBlank(helpMember.getIsZeroJob())) {
					memberHelp
							.setIsZeroJob(this.getDictKey(
									"CODE_ZERO_WORK_FAMILY",
									helpMember.getIsZeroJob()));
				}
				if (StringUtils.isNotBlank(helpMember.getPovertyCauses())) {
					memberHelp.setPovertyCauses(this.getDictKey(
							"CODE_STUCK_REASON", helpMember.getPovertyCauses())
							+ "");
				}
				if (StringUtils.isNotBlank(helpMember.getCreateTime())) {
					memberHelp.setCreateTime(DateUtils.getDate(helpMember
							.getCreateTime()));
				}
				// 插入会员帮扶表
				memberHelpService.save(memberHelp);
				ehCacheService.setCache(IEhCacheService.CACHE_HELP_IMPORT,
						"MEMBER_HELP_COUNT", count++);
			}
			result.put("success", listHelpMember.size());
			return result;
		} else {
			return Collections.EMPTY_MAP;
		}
	}

	@Override
	public void updateMoney(Integer helpApplyId, String moneyFrom,
			String moneyNum) {
		HelpApplyModel helpApply = this.selectByKey(helpApplyId);
		if (null != helpApply) {
			helpApply.setAmountSource(NumberUtils.toInt(moneyFrom));
			if (StringUtils.isNotBlank(moneyNum)) {
				helpApply.setAmountNumber(new BigDecimal(moneyNum));
			}
			this.updateByKey(helpApply);
		}
	}

	/**
	 * 审批操作
	 */
	@Override
	public void saveApproval(String applyMsg, String resultId,
			String moneyFrom, String moneyNum, String taskId,
			String processInstanceId, Integer helpApplyId, String userId,
			Integer deptId, String userName) throws BusinessException {
		Map<String, String> formProperties = new HashMap<>();
		formProperties.put("applyMsg", applyMsg);
		if (resultId.equals("1")) {
			formProperties.put("direction", "go");
			resultId = "同意";
		} else if (resultId.equals("2")) {
			formProperties.put("direction", "end");
			resultId = "退回";
			//归档状态更新为0
			helpApplyService.updateApplyFileEndStatus(helpApplyId);
		} else if (resultId.equals("3")) {
			resultId = "不同意";
			formProperties.put("direction", "over");
			//归档状态更新为0
			helpApplyService.updateApplyFileEndStatus(helpApplyId);
		} else if (resultId.equals("4")) {
			formProperties.put("direction", "end");
			resultId = "退回到发起人";
			//更新归档状态0为未归档1为归档
			helpApplyService.updateApplyFileEndManStatus(helpApplyId);
		}
		if (moneyFrom.equals("1")) {
			moneyFrom = "中央资金";
		} else if (moneyFrom.equals("2")) {
			moneyFrom = "省财政";
		} else if (moneyFrom.equals("3")) {
			moneyFrom = "市财政";
		}
		//更新t_help_apply_user帮扶和用户中间表help_apply_id和user_id字段，以便于查询各用户审批归档的帮扶数据
		helpApplyService.updateUserId(helpApplyId,
				NumberUtils.toInteger(userId));
		HelpApplyModel helpApply = helpApplyService.selectByKey(helpApplyId);
		if (null != helpApply) {
			formProperties.put("helpType", this.getDictValue("CODE_HELP_TYPE",
					helpApply.getHelpType()));
		}
		formProperties.put("name", helpApply.getName());
		formProperties.put("userName", userName);
		formProperties.put("sex",
				this.getDictValue("CODE_SEX", helpApply.getSex()));
		formProperties.put("political", helpApply.getPolitical().toString());
		formProperties.put("phone", helpApply.getPhone());
		formProperties.put("unionChairId", helpApply.getUnionChairId()
				.toString());
		formProperties.put("company", helpApply.getCompany());
		formProperties.put("approvalStatus", resultId);
		formProperties.put("moneyFrom", moneyFrom);
		formProperties.put("moneyNum", moneyNum);
		formProperties.put("helpApplyId", helpApplyId + "");
		// 添加审批时间
		formProperties.put("approveTime", DateUtils.getTime());
		WorkFlowService workFlowService = SpringContextHolder
				.getBean(WorkFlowService.class);
		Map<String, Object> map = workFlowService.getTaskVariables(
				processInstanceId, userId + "");
		OrgainModel deptOrg = orgainService.selectByKey(deptId);
		String deptName = "";
		if (null != deptOrg)
			deptName = deptOrg.getOrgainName();
		int isBack = 0;
		if (resultId.equals("退回到发起人")) {
			// 判断此节点的上一个节点是不是发起节点
			isBack = 1;
			List<String> list = new ArrayList<>();
			list.add(map.get("userId").toString());
			formProperties.put("direction", "end");
			formProperties.put("approvalStatus", "已退回，等待下次审核");
			workFlowService.submitTaskFormData(taskId, list, formProperties,
					userId);

		} else if (resultId.equals("退回")) {
			// 如果是退回
			WorkFlowUtilService workFlowUtilService = SpringContextHolder
					.getBean(WorkFlowUtilService.class);
			for (String key : map.keySet())
				formProperties.put(key, map.get(key).toString());
			formProperties.put("userName", userName);
			formProperties.put("moneyFrom", moneyFrom);
			formProperties.put("moneyNum", moneyNum);
			formProperties.put("applyMsg", applyMsg);
			formProperties.put("approveTime", DateUtils.getTime());
			formProperties.put("approvalStatus", "已退回，等待下次审核");
			try {
				String lastTaskId = workFlowUtilService.findLastTaskId(taskId);
				Map<String, Object> temp = workFlowUtilService
						.findHistoryVariablesById(lastTaskId);
				Object obj = temp.get("taskType");
				String taskType = "";
				if (null != obj) {
					taskType = obj.toString();
				}
				if (taskType.equals("dynamic")) {
					// 判断此节点的上一个节点是不是基层工会节点
					List<String> deptIdList = new ArrayList<>(), approverList = new ArrayList<>();
					// 获取会员信息与基层工会信息
					Member member = new Member();
					member.setId(helpApply.getMemberId());
					Member memberinfo = memberService.selectOne(member);
					// 退回时取基层工会部门人员列表动态指定
					Integer baseUnionId = memberinfo.getBasicUnionId();
					List<Map<String, Object>> varList = orgainService
							.getChilds(baseUnionId.toString());// 查找该节点下的所有数据
					if (CollectionUtils.isNotEmpty(varList)) {
						for (Map<String, Object> var : varList) {
							deptIdList.add(var.get("id").toString());
						}
					}
					if (CollectionUtils.isNotEmpty(deptIdList)) {
						approverList.addAll(userService
								.selectUserByDeptId(deptIdList));
					}
					formProperties.put("direction", "end");
					workFlowService.submitTaskFormData(taskId, approverList,
							formProperties, formProperties.get("userId")
									.toString());
				} else {
					formProperties.put("direction", "end");
					workFlowService.submitTaskFormData(taskId, formProperties,
							userId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			workFlowService.submitTaskFormData(taskId, formProperties, userId);
		}
		if (moneyFrom.equals("中央资金")) {
			moneyFrom = "1";
		} else if (moneyFrom.equals("省财政")) {
			moneyFrom = "2";
		} else if (moneyFrom.equals("市财政")) {
			moneyFrom = "3";
		}
		helpApplyService.updateMoney(helpApplyId, moneyFrom, moneyNum);
		// 审核之后同步
		if (resultId.equals("同意")) {
			this.saveRightsInfoAndSubmitTask(helpApplyId, processInstanceId);
			// 审核通过后 同步资金来源
			helpApplyService.updateMoney(helpApplyId, moneyFrom, moneyNum);
			// 同步会员帮扶表
			if (null != helpApply) {
				MemberHelpModel memberHelp = new MemberHelpModel();
				memberHelp.setMemberId(helpApply.getMemberId());
				memberHelp = memberHelpService.selectOne(memberHelp);
				BeanUtils.copyProperties(helpApply, memberHelp);
				memberHelp.setHelpApplyId(helpApplyId);
				memberHelpService.updateByKey(memberHelp);
			}
		}
		// 同步发送审核推送信息给App提交者
		Map<String, Object> msgMap = new HashMap<>();
		msgMap.put("users", Arrays.asList(map.get("userId").toString()));
		JSONObject obj = new JSONObject();
		obj.put("type", 6);// 0表示系统消息 1表示通讯录 2表示圈子、3通知
		obj.put("subject", "");// type下的具体操作项：新增、修改、删除
		JSONObject content = new JSONObject();
		content.put("id", helpApplyId);
		content.put("status", resultId);
		content.put("createtime", DateUtils.getTime(helpApply.getCreateTime()));
		content.put("taskId", taskId);
		content.put("flowId", processInstanceId);
		content.put("isBack", isBack);
		obj.put("content", content.toString());
		obj.put("data", "您好，你的帮扶申请进度更新为：【" + deptName + "】审批【" + resultId
				+ "】，审批意见为【" + applyMsg + "】，请到个人中心查看详细审批进度！");
		// obj.put("auditStatus", "");
		msgMap.put("content", obj.toString());// 传递的内容
		// msgMap.put("title", "帮扶审批");
		// msgMap.put("text", "审批通过");
		PushUtil.push(3, msgMap);
	}

	@Override
	public void updateApplyFileStatus(Integer id) throws BusinessException {
		helpApplyMapper.updateApplyFileStatus(id);
	}

	@Override
	public List<HelpApplyDto> selectByApplyFileList(List<Integer> id,
			String name, String startTime, String endTime, Integer page,
			Integer rows) throws BusinessException {
		PageHelper.startPage(page, rows);
		return helpApplyMapper.selectByApplyFileList(id, name, startTime,
				endTime);
	}

	@Override
	public void insertProcessInstanceId(Integer id, String processInstanceId) {
		helpApplyMapper.insertProcessInstanceId(id, processInstanceId);
	}

	@Override
	public void updateUserId(Integer helpApplyId, Integer userId)
			throws BusinessException {
		helpApplyMapper.updateUserId(helpApplyId, userId);
	}

	@Override
	public List<HelpApplyDto> selectByApplyFileSuperList(String name,
			String startTime, String endTime, Integer page, Integer rows)
			throws BusinessException {
		PageHelper.startPage(page, rows);
		return helpApplyMapper.selectByApplyFileSuperList(name, startTime,
				endTime);
	}

	@Override
	public List<HelpApplyUserDto> selectHelpApplyId(Integer userId)
			throws BusinessException {
		// TODO Auto-generated method stub
		return helpApplyMapper.selectHelpApplyId(userId);
	}

	@Override
	public void updateApplyFileEndManStatus(Integer id)
			throws BusinessException {
		// TODO Auto-generated method stub
		helpApplyMapper.updateApplyFileEndManStatus(id);
	}

	@Override
	public void updateApplyFileEndStatus(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		helpApplyMapper.updateApplyFileEndStatus(id);
	}

	/**
	 * 提交审批，判断如果流程结束则归档
	 */
	public boolean saveRightsInfoAndSubmitTask(Integer helpApplyId,
			String instanceId) {
		WorkFlowService workFlowService = SpringContextHolder
				.getBean(WorkFlowService.class);
		//workFlowService.submitTaskFormData(taskId, map, userId);
		try {
			//流程是否结束 0结束 1未结束
			if (workFlowService.processIsEnd(instanceId) == 0) {
				//更新归档状态 1为已归档 0为未归档
				helpApplyService.updateApplyFileStatus(helpApplyId);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}

package com.ehome.cloud.member.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Condition;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.help.dto.MemberHelpDto;
import com.ehome.cloud.help.model.MemberHelpModel;
import com.ehome.cloud.help.service.IMemberHelpService;
import com.ehome.cloud.member.dto.MemberDto;
import com.ehome.cloud.member.dto.MemberImportDto;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.service.IEhCacheService;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.ImportExcelUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 会员管理
 * 
 * @Title:MemberController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月21日 下午5:18:09
 * @version:
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	@Resource
	private IMemberService memberService;

	@Resource
	private IDictionaryService dictionaryService;

	@Resource
	private IOrgainService orgainService;

	@Resource
	private IMemberHelpService memberHelpService;

	@Resource
	private IEhCacheService ehCacheService;

	@RequestMapping(value = "/index")
	@RequiresUser
	@RequiresPermissions("mem:member:list")
	public String memberIndex(Model model, NativeWebRequest request) {
		LoginInfoDto loginDto = this.getCurrentUser();
		List<Dictionary> dictSex = dictionaryService.queryByCode("CODE_SEX");
		if (CollectionUtils.isNotEmpty(dictSex))
			model.addAttribute("dictSex", JSON.toJSON(dictSex));
		List<Dictionary> dictMemStatus = dictionaryService
				.queryByCode("CODE_MEMBER_STATUS");
		if (CollectionUtils.isNotEmpty(dictMemStatus))
			model.addAttribute("dictMemStatus", JSON.toJSON(dictMemStatus));
		List<Dictionary> dictEducation = dictionaryService
				.queryByCode("CODE_EDUCATION");
		if (CollectionUtils.isNotEmpty(dictEducation))
			model.addAttribute("dictEducation", JSON.toJSON(dictEducation));
		List<Dictionary> dictAuditStatus = dictionaryService
				.queryByCode("CODE_AUDIT_STATUS");
		if (CollectionUtils.isNotEmpty(dictAuditStatus))
			model.addAttribute("dictAuditStatus", JSON.toJSON(dictAuditStatus));
		List<Dictionary> dictUserResource = dictionaryService
				.queryByCode("CODE_USER_RESOURCE");
		if (CollectionUtils.isNotEmpty(dictUserResource))
			model.addAttribute("dictUserResource",
					JSON.toJSON(dictUserResource));
		List<Dictionary> dictPolitical = dictionaryService
				.queryByCode("CODE_POLITICAL_LANDSCAPE");
		if (CollectionUtils.isNotEmpty(dictPolitical))
			model.addAttribute("dictPolitical", JSON.toJSON(dictPolitical));
		if (null != loginDto) {
			if (!NumberUtils.isNull(loginDto.getBaseUnionId())
					&& !NumberUtils.eqZero(loginDto.getBaseUnionId())) {
				model.addAttribute("isAdd", "1");
				return "/system/member/list.html";
			} else if (!NumberUtils.isNull(loginDto.getDeptId())
					&& !NumberUtils.eqZero(loginDto.getDeptId())) {
				OrgainModel depOrg = orgainService.selectByKey(loginDto
						.getDeptId());
				if (depOrg != null) {
					if (depOrg.getOrgainType().intValue() == 1) {
						OrgainModel deptParentOrg = orgainService
								.selectByKey(depOrg.getParentId());
						if (null != deptParentOrg) {
							if (deptParentOrg.getOrgainType().intValue() == 2) {
								model.addAttribute("isAdd", "2");
								return "/system/member/list.html";
							} else {
								Map<String, Object> map = new HashMap<>();
								map.put("id", loginDto.getOrgainId());
								if (NumberUtils.eqOne(loginDto.getOrgainId()))
									map.put("queryid", "1");
								//map.put("baseOrgId", loginDto.getOrgainId());
								List<Map<String, Object>> varList = orgainService
										.queryByPId(map);
								model.addAttribute("resultStr",
										JSON.toJSONString(varList));
								model.addAttribute("isAdd", "2");
							}
						}
					}
				}
			} else {
				Map<String, Object> map = new HashMap<>();
				map.put("id", loginDto.getOrgainId());
				if (NumberUtils.eqOne(loginDto.getOrgainId()))
					map.put("queryid", "1");
				//map.put("baseOrgId", loginDto.getOrgainId());
				List<Map<String, Object>> varList = orgainService
						.queryByPId(map);
				model.addAttribute("resultStr", JSON.toJSONString(varList));
				model.addAttribute("isAdd", "2");
			}
		}
		return "/system/member/org_list.html";
	}

	/**
	 * 会员查询列表
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryMember", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:list")
	public AjaxResult queryMemberList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "") Integer sex,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") Integer education,
			@RequestParam(required = false, defaultValue = "") Integer auditStatus,
			@RequestParam(required = false, defaultValue = "") Integer userResource,
			@RequestParam(required = false, defaultValue = "") Integer political,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		Integer userId = this.getCurrentUserId();
		Integer basicUnionId = this.getCurrentUserBaseOrgId();
		if (!NumberUtils.isNull(basicUnionId)
				&& !NumberUtils.eqZero(basicUnionId)) {
			//basicUnionId = this.getCurrentUserBaseOrgId();
		} else {
			Integer deptUnionId = this.getCurrentUserDeptId();
			if (!NumberUtils.isNull(deptUnionId)
					&& !NumberUtils.eqZero(deptUnionId)) {
				OrgainModel depOrg = orgainService.selectByKey(deptUnionId);
				if (depOrg != null) {
					if (depOrg.getOrgainType().intValue() == 1) {
						OrgainModel deptParentOrg = orgainService
								.selectByKey(depOrg.getParentId());
						if (null != deptParentOrg) {
							if (deptParentOrg.getOrgainType().intValue() == 2) {
								basicUnionId = deptParentOrg.getId();
							}
						}
					}
				}
			}
		}
		MemberDto memberDto = new MemberDto();
		memberDto.setKeyword(keyword);
		memberDto.setSex(sex);
		memberDto.setStatus(status);
		memberDto.setEducation(education);
		memberDto.setAuditStatus(auditStatus);
		memberDto.setUserResource(userResource);
		memberDto.setPolitical(political);
		memberDto.setUserId(userId);
		memberDto.setBasicUnionId(basicUnionId);
		List<MemberDto> userList = memberService.queryForList(memberDto, page,
				rows);
		DictoryCodeUtils.renderList(userList);
		PageInfo<MemberDto> pageInfo = new PageInfo<>(userList);
		Pagination<MemberDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 根据基层工会查询会员
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryOrgMember", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:list")
	public AjaxResult queryOrgMemberList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "") Integer sex,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") Integer education,
			@RequestParam(required = false, defaultValue = "") Integer auditStatus,
			@RequestParam(required = false, defaultValue = "") Integer userResource,
			@RequestParam(required = false, defaultValue = "") Integer political,
			@RequestParam(required = false, defaultValue = "-1") Integer orgId,
			@RequestParam(required = false, defaultValue = "0") Integer orgType,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<Integer> baseOrgList = this.getNodeList(orgId, orgType);
		// 当前登录者ID
		MemberDto memberDto = new MemberDto();
		memberDto.setKeyword(keyword);
		memberDto.setSex(sex);
		memberDto.setStatus(auditStatus);
		memberDto.setEducation(education);
		memberDto.setAuditStatus(auditStatus);
		memberDto.setUserResource(userResource);
		memberDto.setPolitical(political);
		List<MemberDto> userList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(baseOrgList)) {
			memberDto.setBaseOrgIdList(baseOrgList);
			userList = memberService.queryForList(memberDto, page, rows);
			DictoryCodeUtils.renderList(userList);
		}
		PageInfo<MemberDto> pageInfo = new PageInfo<>(userList);
		Pagination<MemberDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	public List<Integer> getNodeList(Integer nodeId, Integer type) {
		List<Integer> list = new ArrayList<>();
		Map<String, Object> model = new HashMap<>();
		model.put("id", nodeId);
		model.put("type", type);
		list = getChildrenList(model, list);
		return list;
	}

	public List<Integer> getChildrenList(Map<String, Object> model,
			List<Integer> list) {
		if (model.get("type").toString().equals("2"))
			list.add(NumberUtils.toInt(model.get("id").toString()));
		List<Map<String, Object>> childModel = orgainService.getChilds((model
				.get("id").toString()));
		if (CollectionUtils.isNotEmpty(childModel)) {
			childModel.forEach(o -> {
				getChildrenList(o, list);
			});
		}
		return list;
	}

	/**
	 * 删除会员
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:delete")
	public AjaxResult deleteMember(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"), 0);
		if (pkId != 0) {
			// memberService.deleteByKey(pkId);
			memberService.deleteMember(pkId);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 新增会员
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-member", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:add")
	public String addMemberPage(Model model, NativeWebRequest request) {
		List<Dictionary> dictSex = dictionaryService.queryByCode("CODE_SEX");
		if (CollectionUtils.isNotEmpty(dictSex))
			model.addAttribute("dictSex", JSON.toJSON(dictSex));
		List<Dictionary> dictMemStatus = dictionaryService
				.queryByCode("CODE_MEMBER_STATUS");
		if (CollectionUtils.isNotEmpty(dictMemStatus))
			model.addAttribute("dictMemStatus", JSON.toJSON(dictMemStatus));
		List<Dictionary> dictEducation = dictionaryService
				.queryByCode("CODE_EDUCATION");
		if (CollectionUtils.isNotEmpty(dictEducation))
			model.addAttribute("dictEducation", JSON.toJSON(dictEducation));
		List<Dictionary> dictDegree = dictionaryService
				.queryByCode("CODE_DEGREE");
		if (CollectionUtils.isNotEmpty(dictDegree))
			model.addAttribute("dictDegree", JSON.toJSON(dictDegree));
		List<Dictionary> dictPolitical = dictionaryService
				.queryByCode("CODE_POLITICAL_LANDSCAPE");
		if (CollectionUtils.isNotEmpty(dictPolitical))
			model.addAttribute("dictPolitical", JSON.toJSON(dictPolitical));
		List<Dictionary> dictNation = dictionaryService
				.queryByCode("CODE_NATION");
		if (CollectionUtils.isNotEmpty(dictNation))
			model.addAttribute("dictNation", JSON.toJSON(dictNation));
		List<Dictionary> dictMaritalStatus = dictionaryService
				.queryByCode("CODE_MARITAL_STATUS");
		if (CollectionUtils.isNotEmpty(dictMaritalStatus))
			model.addAttribute("dictMaritalStatus",
					JSON.toJSON(dictMaritalStatus));
		return "/system/member/add.html";
	}

	/**
	 * 修改会员
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-member", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:update")
	public String updateMemberPage(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		Member member = memberService.selectByKey(pkId);
		MemberDto memberDto = new MemberDto();
		BeanUtils.copyProperties(member, memberDto);
		model.addAttribute("pkId", pkId);
		List<Dictionary> dictSex = dictionaryService.queryByCode("CODE_SEX");
		if (CollectionUtils.isNotEmpty(dictSex))
			model.addAttribute("dictSex", JSON.toJSON(dictSex));
		List<Dictionary> dictMemStatus = dictionaryService
				.queryByCode("CODE_MEMBER_STATUS");
		if (CollectionUtils.isNotEmpty(dictMemStatus))
			model.addAttribute("dictMemStatus", JSON.toJSON(dictMemStatus));
		List<Dictionary> dictEducation = dictionaryService
				.queryByCode("CODE_EDUCATION");
		if (CollectionUtils.isNotEmpty(dictEducation))
			model.addAttribute("dictEducation", JSON.toJSON(dictEducation));
		List<Dictionary> dictDegree = dictionaryService
				.queryByCode("CODE_DEGREE");
		if (CollectionUtils.isNotEmpty(dictDegree))
			model.addAttribute("dictDegree", JSON.toJSON(dictDegree));
		List<Dictionary> dictPolitical = dictionaryService
				.queryByCode("CODE_POLITICAL_LANDSCAPE");
		if (CollectionUtils.isNotEmpty(dictPolitical))
			model.addAttribute("dictPolitical", JSON.toJSON(dictPolitical));
		List<Dictionary> dictNation = dictionaryService
				.queryByCode("CODE_NATION");
		if (CollectionUtils.isNotEmpty(dictNation))
			model.addAttribute("dictNation", JSON.toJSON(dictNation));
		List<Dictionary> dictMaritalStatus = dictionaryService
				.queryByCode("CODE_MARITAL_STATUS");
		if (CollectionUtils.isNotEmpty(dictMaritalStatus))
			model.addAttribute("dictMaritalStatus",
					JSON.toJSON(dictMaritalStatus));
		model.addAttribute("memberModel", memberDto);
		// 会员帮扶
		List<Dictionary> dictHelpType = dictionaryService
				.queryByCode("CODE_HELP_TYPE");
		if (CollectionUtils.isNotEmpty(dictHelpType))
			model.addAttribute("dictHelpType", JSON.toJSON(dictHelpType));

		List<Dictionary> dictDifficultType = dictionaryService
				.queryByCode("CODE_DIFFICULT_TYPE");
		if (CollectionUtils.isNotEmpty(dictDifficultType))
			model.addAttribute("dictDifficultType",
					JSON.toJSON(dictDifficultType));

		List<Dictionary> dictHealth = dictionaryService
				.queryByCode("CODE_HEALTH_STATUS");
		if (CollectionUtils.isNotEmpty(dictHealth))
			model.addAttribute("dictHealth", JSON.toJSON(dictHealth));

		List<Dictionary> dictDisabilityType = dictionaryService
				.queryByCode("CODE_DISABILITY_TYPE");
		if (CollectionUtils.isNotEmpty(dictDisabilityType))
			model.addAttribute("dictDisabilityType",
					JSON.toJSON(dictDisabilityType));

		List<Dictionary> dictWorkStatus = dictionaryService
				.queryByCode("CODE_WORK_STATUS");
		if (CollectionUtils.isNotEmpty(dictWorkStatus))
			model.addAttribute("dictWorkStatus", JSON.toJSON(dictWorkStatus));

		List<Dictionary> dictModelType = dictionaryService
				.queryByCode("CODE_WORKER_MODEL_TYPE");
		if (CollectionUtils.isNotEmpty(dictModelType))
			model.addAttribute("dictModelType", JSON.toJSON(dictModelType));

		List<Dictionary> dictHousingType = dictionaryService
				.queryByCode("CODE_HOUSING_TYPE");
		if (CollectionUtils.isNotEmpty(dictHousingType))
			model.addAttribute("dictHousingType", JSON.toJSON(dictHousingType));

		List<Dictionary> dictIndustry = dictionaryService
				.queryByCode("CODE_INDUSTRY_INVOLVED");
		if (CollectionUtils.isNotEmpty(dictIndustry))
			model.addAttribute("dictIndustry", JSON.toJSON(dictIndustry));

		List<Dictionary> dictUnitProperties = dictionaryService
				.queryByCode("CODE_UNIT_NATURE");
		if (CollectionUtils.isNotEmpty(dictUnitProperties))
			model.addAttribute("dictUnitProperties",
					JSON.toJSON(dictUnitProperties));

		List<Dictionary> dictCompanyStatus = dictionaryService
				.queryByCode("CODE_COMPANY_CONDITION");
		if (CollectionUtils.isNotEmpty(dictCompanyStatus))
			model.addAttribute("dictCompanyStatus",
					JSON.toJSON(dictCompanyStatus));

		List<Dictionary> dictIsSingleParent = dictionaryService
				.queryByCode("CODE_SINGLE_PARENT");
		if (CollectionUtils.isNotEmpty(dictIsSingleParent))
			model.addAttribute("dictIsSingleParent",
					JSON.toJSON(dictIsSingleParent));

		List<Dictionary> dictMedicalInsurance = dictionaryService
				.queryByCode("CODE_SURANCE_STATUS");
		if (CollectionUtils.isNotEmpty(dictMedicalInsurance))
			model.addAttribute("dictMedicalInsurance",
					JSON.toJSON(dictMedicalInsurance));

		List<Dictionary> dictIsHasSelfSave = dictionaryService
				.queryByCode("CODE_HELP_ONESELF_ABILITY");
		if (CollectionUtils.isNotEmpty(dictIsHasSelfSave))
			model.addAttribute("dictIsHasSelfSave",
					JSON.toJSON(dictIsHasSelfSave));

		List<Dictionary> dictIsZeroJob = dictionaryService
				.queryByCode("CODE_ZERO_WORK_FAMILY");
		if (CollectionUtils.isNotEmpty(dictIsZeroJob))
			model.addAttribute("dictIsZeroJob", JSON.toJSON(dictIsZeroJob));

		List<Dictionary> dictPovertyCauses = dictionaryService
				.queryByCode("CODE_STUCK_REASON");
		if (CollectionUtils.isNotEmpty(dictPovertyCauses))
			model.addAttribute("dictPovertyCauses",
					JSON.toJSON(dictPovertyCauses));

		List<Dictionary> dictCardType = dictionaryService
				.queryByCode("CODE_CARD_TYPE");
		if (CollectionUtils.isNotEmpty(dictCardType))
			model.addAttribute("dictCardType", JSON.toJSON(dictCardType));

		Condition condition = new Condition(MemberHelpModel.class);
		condition.createCriteria().andEqualTo("memberId", pkId);
		List<MemberHelpModel> helpList = memberHelpService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(helpList)) {
			MemberHelpModel memberHelp = helpList.get(0);
			MemberHelpDto memberHelpDto = new MemberHelpDto();
			BeanUtils.copyProperties(memberHelp, memberHelpDto);
			DictoryCodeUtils.renderCode(memberHelpDto);
			if (memberHelpDto.getBrithday() != null)
				memberHelpDto.setBrithdayName(DateUtils.getDay(memberHelpDto
						.getBrithday()));
			if (memberHelpDto.getWorkYear() != null)
				memberHelpDto.setWorkYearName(DateUtils.getDay(memberHelpDto
						.getWorkYear()));
			model.addAttribute("memberHelpDto", memberHelpDto);
		} else {
			model.addAttribute("memberHelpDto", new MemberHelpDto());
		}
		return "/system/member/edit.html";
	}

	/**
	 * 查看资料
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "find-member", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:info")
	public String findMemberPage(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		// 会员信息
		Member member = memberService.selectByKey(pkId);
		MemberDto memberDto = new MemberDto();
		BeanUtils.copyProperties(member, memberDto);
		model.addAttribute("pkId", pkId);
		List<Dictionary> dictSex = dictionaryService.queryByCode("CODE_SEX");
		if (CollectionUtils.isNotEmpty(dictSex))
			model.addAttribute("dictSex", JSON.toJSON(dictSex));
		List<Dictionary> dictMemStatus = dictionaryService
				.queryByCode("CODE_MEMBER_STATUS");
		if (CollectionUtils.isNotEmpty(dictMemStatus))
			model.addAttribute("dictMemStatus", JSON.toJSON(dictMemStatus));
		List<Dictionary> dictEducation = dictionaryService
				.queryByCode("CODE_EDUCATION");
		if (CollectionUtils.isNotEmpty(dictEducation))
			model.addAttribute("dictEducation", JSON.toJSON(dictEducation));
		List<Dictionary> dictDegree = dictionaryService
				.queryByCode("CODE_DEGREE");
		if (CollectionUtils.isNotEmpty(dictDegree))
			model.addAttribute("dictDegree", JSON.toJSON(dictDegree));
		List<Dictionary> dictPolitical = dictionaryService
				.queryByCode("CODE_POLITICAL_LANDSCAPE");
		if (CollectionUtils.isNotEmpty(dictPolitical))
			model.addAttribute("dictPolitical", JSON.toJSON(dictPolitical));
		List<Dictionary> dictNation = dictionaryService
				.queryByCode("CODE_NATION");
		if (CollectionUtils.isNotEmpty(dictNation))
			model.addAttribute("dictNation", JSON.toJSON(dictNation));
		List<Dictionary> dictPovertyCauses = dictionaryService
				.queryByCode("CODE_STUCK_REASON");
		if (CollectionUtils.isNotEmpty(dictPovertyCauses))
			model.addAttribute("dictPovertyCauses",
					JSON.toJSON(dictPovertyCauses));
		List<Dictionary> dictMaritalStatus = dictionaryService
				.queryByCode("CODE_MARITAL_STATUS");
		if (CollectionUtils.isNotEmpty(dictMaritalStatus))
			model.addAttribute("dictMaritalStatus",
					JSON.toJSON(dictMaritalStatus));
		model.addAttribute("memberModel", memberDto);
		// 会员帮扶
		Condition condition = new Condition(MemberHelpModel.class);
		condition.createCriteria().andEqualTo("memberId", pkId);
		List<MemberHelpModel> helpList = memberHelpService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(helpList)) {
			MemberHelpModel memberHelp = helpList.get(0);
			MemberHelpDto memberHelpDto = new MemberHelpDto();
			BeanUtils.copyProperties(memberHelp, memberHelpDto);
			DictoryCodeUtils.renderCode(memberHelpDto);
			if (memberHelpDto.getBrithday() != null)
				memberHelpDto.setBrithdayName(DateUtils.getDay(memberHelpDto
						.getBrithday()));
			if (memberHelpDto.getWorkYear() != null)
				memberHelpDto.setWorkYearName(DateUtils.getDay(memberHelpDto
						.getWorkYear()));
			model.addAttribute("memberHelpDto", memberHelpDto);
		} else {
			model.addAttribute("memberHelpDto", new MemberHelpDto());
		}
		return "/system/member/info.html";
	}

	/**
	 * 保存会员
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/saveMember", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult addMember(
			Model model,
			NativeWebRequest request,
			MemberDto memberDto,
			@RequestParam(required = false, defaultValue = "0") Integer isUpdate,
			@RequestParam(required = false, defaultValue = "0") Integer isAdd)
			throws BusinessException {
		// 当前用户登录ID
		Integer userId = this.getCurrentUserId();
		// 当前登录用基层工会ID
		Integer baseOrgId = this.getCurrentUserBaseOrgId();
		// 当前登录用户的上层工会ID
		Integer upOrgId = this.getCurrentUserOrgId();
		// 身份证号码已存在 则覆盖原来的会员数据
		if (isUpdate == 1) {
			MemberDto queryMember = memberService.checkIdCard(userId,
					memberDto.getIdCard());
			if (null != queryMember) {
				memberService.updateMember(memberDto, queryMember);
			}
			return new AjaxResult(ResponseCode.success.getCode(), "",
					"已更新该用户的会籍信息");
		} else {// 新增会籍信息
			if (isAdd == 0) {
				MemberDto member = null;
				if (StringUtils.isNoneBlank(memberDto.getIdCard())) {
					member = memberService.checkIdCard(userId,
							memberDto.getIdCard());
				}
				if (null != member) {
					return new AjaxResult(ResponseCode.idcard_exist.getCode(),
							"", "检验到身份证号码已存在,请问是否覆盖更新?");
				} else {
					// 新增
					memberDto.setUpperUnionId(upOrgId);
					int pkId = memberService.insertMember(userId, baseOrgId,
							memberDto);
					return new AjaxResult(ResponseCode.success.getCode(), "",
							pkId);
				}
			} else {// 修改会员资料
				if (!NumberUtils.isNull(memberDto.getId())
						&& !NumberUtils.eqZero(memberDto.getId())) {
					Member member = memberService
							.selectByKey(memberDto.getId());
					MemberDto queryMember = new MemberDto();
					BeanUtils.copyProperties(member, queryMember);
					if (null != queryMember) {
						memberService.updateMember(memberDto, queryMember);
					}
				}
				return new AjaxResult(ResponseCode.success.getCode(), "",
						ResponseCode.success.getMsg());
			}
		}
	}

	/**
	 * 审核不通过跳转
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/unApproval", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:aduit")
	public String unApproval(Model model, NativeWebRequest request) {
		String memberIds = request.getParameter("memberIds");
		model.addAttribute("memberIds", memberIds.toString());
		return "/system/member/un_approvel.html";
	}

	/**
	 * 审核会员
	 * 
	 * @param model
	 * @param request
	 * @param status
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:aduit")
	public AjaxResult updateStatus(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer auditStatus,
			@RequestParam(required = false, defaultValue = "") String auditDesc,
			@RequestParam(name = "memberIds[]", required = false) Integer[] memberIds)
			throws BusinessException {
		List<Integer> memberIdList = new ArrayList<>();
		Integer baseOrgId = this.getCurrentUserBaseOrgId();
		if (!CollectionUtils.isEmpty(memberIds)) {
			memberIdList = CollectionUtils.removeNull(new ArrayList<>(Arrays
					.asList(memberIds)));
			memberService.updateStatus(auditStatus, memberIdList, auditDesc,
					baseOrgId);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 根据pkId查找Member对象
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AjaxResult getMemberById(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id)
			throws BusinessException {
		Member member = memberService.selectByKey(id);
		return new AjaxResult(ResponseCode.success.getCode(), "", member);
	}

	/**
	 * 转出
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:transfer")
	public String transfer(Model model, NativeWebRequest request) {
		String memberIds = request.getParameter("memberIds");
		if (StringUtils.isNotBlank(memberIds))
			model.addAttribute("memberIds", memberIds.toString());
		return "/system/member/transfer.html";
	}

	/**
	 * 转出
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @param userPassword
	 * @param comfirmUserPassword
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:transfer")
	public AjaxResult transfer(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer auditStatus,
			@RequestParam(required = false, defaultValue = "") String auditDesc,
			@RequestParam(name = "memberIds[]", required = false) Integer[] memberIds)
			throws BusinessException {
		List<Integer> memberIdList = new ArrayList<>();
		Integer baseOrgId = this.getCurrentUserBaseOrgId();
		if (!CollectionUtils.isEmpty(memberIds)) {
			memberIdList = CollectionUtils.removeNull(new ArrayList<>(Arrays
					.asList(memberIds)));
			memberService.transfer(auditStatus, memberIdList, auditDesc,
					baseOrgId);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 批量导入
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "import-member", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("mem:member:import")
	public String importMember(Model model, NativeWebRequest request) {
		return "/system/member/import-member.html";
	}

	/**
	 * 异步导入会员
	 * 
	 * @param model
	 * @param request
	 * @param file
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "member-import", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("mem:member:import")
	@ResponseBody
	public WebAsyncTask<AjaxResult> dataImport(Model model,
			final NativeWebRequest request,
			@RequestParam("file") final MultipartFile file)
			throws BusinessException {
		final Integer userId = this.getCurrentUserId();
		final Integer baseOrgId = this.getCurrentUserBaseOrgId();
		final Integer upOrgId = this.getCurrentUserOrgId();
		Callable<AjaxResult> callable = new Callable<AjaxResult>() {
			public AjaxResult call() throws BusinessException, IOException {
				List<MemberImportDto> listMember = new ArrayList<>();
				String ext = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf(".") + 1);
				listMember = (List<MemberImportDto>) ImportExcelUtils
						.importExcel(file.getInputStream(), ext,
								MemberImportDto.class);
				Map<String, Object> result = new HashMap<>();
				if (CollectionUtils.isNotEmpty(listMember)) {
					result = memberService.saveImportMember(listMember, userId,
							baseOrgId, upOrgId);
				}
				return new AjaxResult(ResponseCode.success.getCode(), null,
						result);
			}
		};
		return new WebAsyncTask<>(30 * 1000, callable);
	}

	/**
	 * 更新临时表数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/saveTemp", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:import")
	public AjaxResult saveTemp(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer userId = this.getCurrentUserId();
		memberService.saveTemp(userId);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 清空临时表数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/clearTemp", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("mem:member:import")
	public AjaxResult clearTemp(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer userId = this.getCurrentUserId();
		memberService.deleteTemp(userId);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 获取Cache数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/getImportCache", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getCache(Model model, NativeWebRequest request)
			throws BusinessException {
		DecimalFormat df = new DecimalFormat("##%");
		Object total = ehCacheService.getCache(
				IEhCacheService.CACHE_MEMBER_IMPORT, "MEMBER_TOTAL_COUNT");
		Object count = ehCacheService.getCache(
				IEhCacheService.CACHE_MEMBER_IMPORT, "MEMBER_COUNT");
		double total_import = 0, count_import = 0;
		if (null != total)
			total_import = Double.parseDouble(total.toString());
		if (null != count)
			count_import = Double.parseDouble(count.toString());
		String result = "0%";
		if (total_import != 0)
			result = df.format(count_import / total_import);
		return new AjaxResult(ResponseCode.success.getCode(),
				ResponseCode.success.getMsg(), result);
	}

	/**
	 * 导出
	 * 
	 * @param model
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/export")
	@RequiresUser
	@RequiresPermissions("mem:member:export")
	public void exportExcel(Model model, HttpServletRequest request,
			MemberDto memberDto, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		Integer userId = this.getCurrentUserId();
		Integer basicUnionId = this.getCurrentUserBaseOrgId();
		if (!NumberUtils.isNull(basicUnionId)
				&& !NumberUtils.eqZero(basicUnionId)) {
			//basicUnionId = this.getCurrentUserBaseOrgId();
		} else {
			Integer deptUnionId = this.getCurrentUserDeptId();
			if (!NumberUtils.isNull(deptUnionId)
					&& !NumberUtils.eqZero(deptUnionId)) {
				OrgainModel depOrg = orgainService.selectByKey(deptUnionId);
				if (depOrg != null) {
					if (depOrg.getOrgainType().intValue() == 1) {
						OrgainModel deptParentOrg = orgainService
								.selectByKey(depOrg.getParentId());
						if (null != deptParentOrg) {
							if (deptParentOrg.getOrgainType().intValue() == 2) {
								basicUnionId = deptParentOrg.getId();
							}
						}
					}
				}
			}
		}
		memberDto.setUserId(userId);
		memberDto.setBasicUnionId(basicUnionId);
		List<MemberDto> memberList = memberService.queryExport(memberDto);
		DictoryCodeUtils.renderList(memberList);
		memberService.exportMember(memberList, response);
	}

	/**
	 * 导出
	 * 
	 * @param model
	 * @param request
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "/exportOrg")
	@RequiresUser
	@RequiresPermissions("mem:member:export")
	public void exportExcel(Model model, HttpServletRequest request,
			MemberDto memberDto,
			@RequestParam(name = "orgId", required = false) String orgId,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		Integer userId = this.getCurrentUserId();
		Integer basicUnionId = this.getCurrentUserBaseOrgId();
		memberDto.setUserId(userId);
		memberDto.setBasicUnionId(basicUnionId);
		String[] orgIdList = new String[] {};
		if (StringUtils.isNotBlank(orgId)) {
			orgIdList = (orgId.split(","));
		}
		List<MemberDto> memberList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(orgIdList)) {
			List<Integer> baseOrgList = new ArrayList<>();
			for (String str : orgIdList) {
				baseOrgList.add(NumberUtils.toInt(str));
			}
			memberDto.setBaseOrgIdList(baseOrgList);
			memberList = memberService.queryExportOrg(memberDto);
			DictoryCodeUtils.renderList(memberList);
		}
		memberService.exportMember(memberList, response);
	}
}

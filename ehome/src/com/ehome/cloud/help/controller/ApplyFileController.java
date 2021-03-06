package com.ehome.cloud.help.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Condition;

import com.alibaba.fastjson.JSON;
import com.ehome.activiti.services.WorkFlowService;
import com.ehome.cloud.help.dto.HelpApplyDto;
import com.ehome.cloud.help.dto.HelpApplyFamilyDto;
import com.ehome.cloud.help.dto.HelpApplyUserDto;
import com.ehome.cloud.help.model.HelpApplyFamilyModel;
import com.ehome.cloud.help.model.HelpApplyModel;
import com.ehome.cloud.help.service.IHelpApplyFamilyService;
import com.ehome.cloud.help.service.IHelpApplyService;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.ExcelUtil;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.StringUtils;
import com.github.pagehelper.PageInfo;

/**
 * @Title:ApplyFileController
 * @Description:TODO
 * @author:tcr
 * @date:2017年4月25日
 * @version:
 */
@Controller
@RequestMapping(value = "/applyFile")
public class ApplyFileController extends BaseController {

	@Resource
	private IHelpApplyService helpApplyService;

	@Resource
	private IHelpApplyFamilyService helpApplyFamilyService;

	@Resource
	private IUploadFileService uploadFileService;

	@Resource
	private IDictionaryService dictionaryService;

	@Autowired(required = false)
	private WorkFlowService workFlowService;

	@Resource
	private IOrgainService orgainService;

	@Resource
	private IUserService userService;

	/**
	 * 归档页面
	 * 
	 * @author tcr
	 * @version 1.0
	 * @date 2017-4-26
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/goApplyFileList")
	@RequiresUser
	@RequiresPermissions("help:archive:help")
	public ModelAndView goApplyFileList() {
		ModelAndView mv = new ModelAndView("help/helpApply/applyFileList.html");
		return mv;
	}

	/**
	 * 归档列表
	 * 
	 * @author tcr
	 * @version 1.0
	 * @date 2017-4-26
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getApplyFileList", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("help:archive:help")
	@ResponseBody
	public AjaxResult getRuntimeTask(
			HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String startTime,
			@RequestParam(required = false, defaultValue = "") String endTime,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "10") Integer rows,
			@RequestParam(required = false, defaultValue = "1") Integer page)
			throws BusinessException {
		List<HelpApplyDto> applyFileList = new ArrayList<>();
		try {
			Integer userId = this.getCurrentUserId();
			Integer deptId = userService.selectByDeptId(userId);
			//根据t_user表中的部门id查询出组织表t_orgain表的id和parent_id
			List<OrgainModel> orgainlist = orgainService
					.selectOrgainType(deptId);
			List<HelpApplyUserDto> helpApplyIdList = helpApplyService
					.selectHelpApplyId(userId);
			Integer orgainParentId = null;
			//Integer orgainType = null;
			for (int i = 0; i < orgainlist.size(); i++) {
				orgainParentId = orgainlist.get(i).getParentId();
				//	orgainType = orgainlist.get(i).getOrgainType();
			}
			Integer orgainId = orgainService.selectOrgainId(orgainParentId);
			//用归属工会id查询用户id
			Integer superUserId = userService.selectSuperUserId(orgainParentId);
			//当前用户和归属工会做对比，相等的话就可以看到全部的数据
			if (superUserId != null && userId != null) {
				if (userId.intValue() == superUserId.intValue()) {
					applyFileList = helpApplyService
							.selectByApplyFileSuperList(name, startTime,
									endTime, page, rows);
					DictoryCodeUtils.renderList(applyFileList);
				}
			}
			//判断类型是否是等于2，如果等于2的话就是基层工会用户
			if (orgainParentId != null && deptId != null) {
				if (orgainParentId.intValue() == orgainId.intValue()) {
					List<Integer> helpApplyId = new ArrayList<>();
					Integer ids = 0;
					for (int i = 0; i < helpApplyIdList.size(); i++) {
						ids = helpApplyIdList.get(i).getHelpApplyId();
						helpApplyId.add(ids);
					}
					applyFileList = helpApplyService.selectByApplyFileList(
							helpApplyId, name, startTime, endTime, page, rows);
					DictoryCodeUtils.renderList(applyFileList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<HelpApplyDto> pageInfo = new PageInfo<>();
		Pagination<HelpApplyDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 查看详情
	 * 
	 * @param processInstanceId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get-form/historytask/{processInstanceId}")
	@ResponseBody
	public ModelAndView findHistoryTaskForm(
			@PathVariable("processInstanceId") String processInstanceId,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		try {
			//Integer userId = this.getCurrentUserId();
			String fromKey = "helpApply";
			HelpApplyDto helpApplyDto = new HelpApplyDto();
			List<Map<String, Object>> variableMapList = workFlowService
					.getApproveListByProcessInstanceId(processInstanceId);
			String helpApplyId = null;
			String brithday = "", workYear = "";
			List<UploadFile> idCardList = new ArrayList<>(), famIdCardList = new ArrayList<>(), povertyImgIdList = new ArrayList<>();
			// 取帮扶id
			for (int i = 0; i < variableMapList.size(); i++) {
				helpApplyId = variableMapList.get(0).get("helpApplyId")
						.toString();
			}
			if (StringUtils.isNotBlank(helpApplyId)) {
				HelpApplyModel helpApply = helpApplyService
						.selectByKey(NumberUtils.toInt(helpApplyId));
				brithday = DateUtils.getDay(helpApply.getBrithday());
				workYear = DateUtils.getDay(helpApply.getWorkYear());
				if (null != helpApply) {
					BeanUtils.copyProperties(helpApply, helpApplyDto);
					DictoryCodeUtils.renderCode(helpApplyDto);
					if (StringUtils.isNotBlank(helpApply.getPovertyCauses())) {
						String[] povertyId = helpApply.getPovertyCauses()
								.split(",");
						if (!CollectionUtils.isEmpty(povertyId)) {
							for (int i = 0; i < povertyId.length; i++) {
								povertyId[i] = dictionaryService
										.getRenderFieldValue(
												"CODE_STUCK_REASON",
												povertyId[i]);
							}
						}
						helpApplyDto.setPovertyCausesName(StringUtils.join(
								povertyId, ","));
					} else {
						helpApplyDto.setPovertyCausesName("");
					}
				}
				if (null != helpApply) {
					BeanUtils.copyProperties(helpApply, helpApplyDto);
					DictoryCodeUtils.renderCode(helpApplyDto);
					Condition condition = new Condition(
							HelpApplyFamilyModel.class);
					condition.createCriteria().andEqualTo("helpApplyId",
							helpApply.getId());
					List<HelpApplyFamilyModel> familyList = helpApplyFamilyService
							.selectByCondition(condition);
					List<HelpApplyFamilyDto> familyDtoList = new ArrayList<>();
					if (CollectionUtils.isNotEmpty(familyList)) {
						for (HelpApplyFamilyModel family : familyList) {
							HelpApplyFamilyDto familyDto = new HelpApplyFamilyDto();
							BeanUtils.copyProperties(family, familyDto);
							familyDtoList.add(familyDto);
						}
						DictoryCodeUtils.renderList(familyDtoList);
						helpApplyDto.setHelpApplyFamilyList(familyDtoList);
					}
					if (StringUtils.isNotBlank(helpApply.getIdCardImgId())) {
						List<String> idCardImgId = Arrays.asList(helpApply
								.getIdCardImgId().split(","));
						if (!CollectionUtils.isEmpty(idCardImgId)) {
							Integer[] foo = new Integer[idCardImgId.size()];
							for (int i = 0; i < idCardImgId.size(); i++) {
								foo[i] = NumberUtils.toInt(idCardImgId.get(i),
										0);
							}
							Iterable<Integer> iterable = Arrays.asList(foo);
							Condition con = new Condition(UploadFile.class);
							con.createCriteria().andIn("id", iterable);
							List<UploadFile> uploadFiled = uploadFileService
									.selectByCondition(con);
							if (CollectionUtils.isNotEmpty(uploadFiled)) {
								for (UploadFile fild : uploadFiled) {
									UploadFile filePath = new UploadFile();
									BeanUtils.copyProperties(fild, filePath);
									idCardList.add(filePath);
								}
							}
						}
					}
					if (StringUtils.isNotBlank(helpApply.getFamIdCardImgId())) {
						List<String> famIdCardImgId = Arrays.asList(helpApply
								.getFamIdCardImgId().split(","));
						if (!CollectionUtils.isEmpty(famIdCardImgId)) {
							Integer[] foo = new Integer[famIdCardImgId.size()];
							for (int i = 0; i < famIdCardImgId.size(); i++) {
								foo[i] = NumberUtils.toInt(
										famIdCardImgId.get(i), 0);
							}
							Iterable<Integer> iterable = Arrays.asList(foo);
							Condition con = new Condition(UploadFile.class);
							con.createCriteria().andIn("id", iterable);
							List<UploadFile> uploadFiled = uploadFileService
									.selectByCondition(con);
							if (CollectionUtils.isNotEmpty(uploadFiled)) {
								for (UploadFile fild : uploadFiled) {
									UploadFile filePath = new UploadFile();
									BeanUtils.copyProperties(fild, filePath);
									famIdCardList.add(filePath);
								}
							}
						}
					}
					if (StringUtils.isNotBlank(helpApply.getPovertyImgId())) {
						List<String> povertyImgId = Arrays.asList(helpApply
								.getPovertyImgId().split(","));
						if (!CollectionUtils.isEmpty(povertyImgId)) {
							Integer[] foo = new Integer[povertyImgId.size()];
							for (int i = 0; i < povertyImgId.size(); i++) {
								foo[i] = NumberUtils.toInt(povertyImgId.get(i),
										0);
							}
							Iterable<Integer> iterable = Arrays.asList(foo);
							Condition con = new Condition(UploadFile.class);
							con.createCriteria().andIn("id", iterable);
							List<UploadFile> uploadFiled = uploadFileService
									.selectByCondition(con);
							if (CollectionUtils.isNotEmpty(uploadFiled)) {
								for (UploadFile fild : uploadFiled) {
									UploadFile filePath = new UploadFile();
									BeanUtils.copyProperties(fild, filePath);
									povertyImgIdList.add(filePath);
								}
							}
						}
					}
				}
			}
			// 整个页面，参数已经赋值（外置表单，普通表单不适用）
			List<Dictionary> dicList = dictionaryService
					.queryByCode("CODE_AMOUNT_SOURCE");
			mav.addObject("dicList", JSON.toJSON(dicList));
			mav.addObject("taskId", "");
			mav.addObject("processInstanceId", processInstanceId);
			mav.addObject("helpApplyId", helpApplyId);
			mav.addObject("applyMsg", "");
			mav.addObject("helpApplyDto", helpApplyDto);
			mav.addObject("idCardImg", idCardList);
			mav.addObject("famIdCardImg", famIdCardList);
			mav.addObject("povertyImgId", povertyImgIdList);
			mav.addObject("brithday", brithday);
			mav.addObject("workYear", workYear);
			mav.addObject("variableMapList", variableMapList);
			mav.addObject("orginType", "1");
			mav.setViewName("/help/helpApply/" + fromKey + ".html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * 导出excel 未审批维权申请表
	 * 
	 * @param requsest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkExport/{processInstanceId}")
	public void checkexport(HttpServletRequest requsest,
			HttpServletResponse response,
			@PathVariable("processInstanceId") String processInstanceId)
			throws Exception {
		HelpApplyDto helpApplyDto = new HelpApplyDto();
		List<Map<String, Object>> variableMapList = workFlowService
				.getApproveListByProcessInstanceId(processInstanceId);
		// 取帮扶id
		String helpApplyId = "";
		for (int i = 0; i < variableMapList.size(); i++) {
			helpApplyId = variableMapList.get(0).get("helpApplyId").toString();
		}
		HelpApplyModel helpApply = helpApplyService.selectByKey(NumberUtils
				.toInt(helpApplyId));
		if (null != helpApply) {
			BeanUtils.copyProperties(helpApply, helpApplyDto);
			DictoryCodeUtils.renderCode(helpApplyDto);
			if (StringUtils.isNotBlank(helpApply.getPovertyCauses())) {
				String[] povertyId = helpApply.getPovertyCauses().split(",");
				if (!CollectionUtils.isEmpty(povertyId)) {
					for (int i = 0; i < povertyId.length; i++) {
						povertyId[i] = dictionaryService.getRenderFieldValue(
								"CODE_STUCK_REASON", povertyId[i]);
					}
				}
				helpApplyDto.setPovertyCausesName(StringUtils.join(povertyId,
						","));
			} else {
				helpApplyDto.setPovertyCausesName("");
			}
		}
		BeanUtils.copyProperties(helpApply, helpApplyDto);
		DictoryCodeUtils.renderCode(helpApplyDto);
		Condition condition = new Condition(HelpApplyFamilyModel.class);
		condition.createCriteria().andEqualTo("helpApplyId", helpApply.getId());
		List<HelpApplyFamilyModel> familyList = helpApplyFamilyService
				.selectByCondition(condition);
		List<HelpApplyFamilyDto> familyDtoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(familyList)) {
			for (HelpApplyFamilyModel family : familyList) {
				HelpApplyFamilyDto familyDto = new HelpApplyFamilyDto();
				BeanUtils.copyProperties(family, familyDto);
				familyDtoList.add(familyDto);
			}
			DictoryCodeUtils.renderList(familyDtoList);
		}
		String brithday = "";
		String phone = helpApplyDto.getPhone();
		String helpType = helpApplyDto.getHelpType().toString();
		String reasons = helpApplyDto.getReasons();
		brithday = DateUtils.getDay(helpApplyDto.getBrithday());
		String workYear = DateUtils.getDay(helpApplyDto.getWorkYear());
		//		String sexName = dictionaryService.getRenderFieldValue("CODE_SEX",
		//				helpApplyDto.getSex().toString());
		//		String cardTypeName = dictionaryService.getRenderFieldValue(
		//				"CODE_CARD_TYPE", helpApplyDto.getCardType().toString());
		//		String cardType = dictionaryService.getRenderFieldValue(
		//				"CODE_CARD_TYPE", helpApplyDto.getCardType().toString());
		String povertyCausesName = helpApplyDto.getPovertyCausesName();
		String openBank = helpApplyDto.getOpenBank();
		String branchBank = helpApplyDto.getBranchBank();
		String cardNum = helpApplyDto.getCardNum();
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			String filedisplay = "帮扶申请表.xls";
			ExcelUtil excel = new ExcelUtil();
			excel.setSrcPath(getClassResources()
					+ "exceltemplate/helpApplyAndRightApplyTemplate.xls");
			excel.setSheetName("Sheet1");
			excel.getSheet();
			excel.setCellStrValue(2, 3, helpApplyDto.getName() == null ? ""
					: helpApplyDto.getName());// 姓名
			excel.setCellStrValue(2, 8, helpApplyDto.getSexName() == null ? ""
					: helpApplyDto.getSexName());// 性别
			excel.setCellStrValue(2, 11,
					helpApplyDto.getBrithday() == null ? "" : brithday);// 生日
			excel.setCellStrValue(
					2,
					16,
					helpApplyDto.getCardTypeName() == null ? "" : helpApplyDto
							.getCardTypeName());// 户口类型
			excel.setCellStrValue(3, 5, helpApplyDto.getIdCard() == null ? ""
					: helpApplyDto.getIdCard());// 身份证号
			excel.setCellStrValue(3, 11, helpApplyDto.getCompany() == null ? ""
					: helpApplyDto.getCompany());// 工作单位
			excel.setCellStrValue(4, 5, helpApplyDto.getAddress() == null ? ""
					: helpApplyDto.getAddress());// 地址
			excel.setCellStrValue(4, 11,
					helpApplyDto.getFamilyNum() == null ? "" : helpApplyDto
							.getFamilyNum().toString());// 家庭人数
			excel.setCellStrValue(4, 15, helpApplyDto.getPhone() == null ? ""
					: helpApplyDto.getPhone());// 电话号码
			excel.setCellStrValue(5, 6,
					helpApplyDto.getIncomeTotal() == null ? "" : helpApplyDto
							.getIncomeTotal().toString());// 家庭经济总收入
			excel.setCellStrValue(5, 14,
					helpApplyDto.getIncomeAvg() == null ? "" : helpApplyDto
							.getIncomeAvg().toString());// 家庭人均年收入
			if (helpType.equals("100071")) {// 帮扶类型
				excel.setCellStrValue(8, 3, "√");
			} else if (helpType.equals("100399")) {
				excel.setCellStrValue(8, 9, "√");
			} else if (helpType.equals("100072")) {
				excel.setCellStrValue(8, 15, "√");
			}
			excel.setCellStrValue(9, 4, helpApplyDto.getReasons() == null ? ""
					: reasons);// 申请原因
			//湛江市困难职工档案表
			excel.setCellStrValue(37, 18,
					helpApplyDto.getDifficultTypeName() == null ? ""
							: helpApplyDto.getDifficultTypeName());// 困难类别
			excel.setCellStrValue(39, 2, helpApplyDto.getName() == null ? ""
					: helpApplyDto.getName());// 姓名
			excel.setCellStrValue(
					39,
					4,
					helpApplyDto.getNationName() == null ? "" : helpApplyDto
							.getNationName());// 民族
			excel.setCellStrValue(39, 5, helpApplyDto.getSexName() == null ? ""
					: helpApplyDto.getSexName());// 性别
			excel.setCellStrValue(
					39,
					6,
					helpApplyDto.getPoliticalName() == null ? "" : helpApplyDto
							.getPoliticalName());// 政治面貌
			excel.setCellStrValue(39, 9, helpApplyDto.getIdCard() == null ? ""
					: helpApplyDto.getIdCard());// 身份证号
			excel.setCellStrValue(39, 12,
					helpApplyDto.getBrithday() == null ? "" : brithday);// 出生日期
			excel.setCellStrValue(
					39,
					14,
					helpApplyDto.getHealthName() == null ? "" : helpApplyDto
							.getHealthName());// 健康状况
			excel.setCellStrValue(39, 18,
					helpApplyDto.getDisabilityTypeName() == null ? ""
							: helpApplyDto.getDisabilityTypeName());// 残疾类别
			excel.setCellStrValue(39, 21,
					helpApplyDto.getWorkStatusName() == null ? ""
							: helpApplyDto.getWorkStatusName());// 工作状态
			excel.setCellStrValue(
					39,
					23,
					helpApplyDto.getModelTypeName() == null ? "" : helpApplyDto
							.getModelTypeName());// 劳模类型
			excel.setCellStrValue(41, 2,
					helpApplyDto.getHousingTypeName() == null ? ""
							: helpApplyDto.getHousingTypeName());// 住房类型
			excel.setCellStrValue(
					41,
					4,
					helpApplyDto.getHousingArea() == null ? "" : helpApplyDto
							.getHousingArea());// 建筑面积
			excel.setCellStrValue(41, 6, helpApplyDto.getPhone() == null ? ""
					: phone);// 手机号码
			excel.setCellStrValue(41, 8, "");// 其他联系方式
			excel.setCellStrValue(
					41,
					12,
					helpApplyDto.getPostcode() == null ? "" : helpApplyDto
							.getPostcode());// 邮政编码
			excel.setCellStrValue(41, 14,
					helpApplyDto.getWorkYear() == null ? "" : workYear);// 工作时间
			excel.setCellStrValue(
					41,
					18,
					helpApplyDto.getIndustryName() == null ? "" : helpApplyDto
							.getIndustryName());// 所属行业
			excel.setCellStrValue(41, 21,
					helpApplyDto.getMaritalStatusName() == null ? ""
							: helpApplyDto.getMaritalStatusName());// 婚姻状况
			excel.setCellStrValue(
					41,
					23,
					helpApplyDto.getCardTypeName() == null ? "" : helpApplyDto
							.getCardTypeName());//户口类型
			excel.setCellStrValue(43, 2, helpApplyDto.getAddress() == null ? ""
					: helpApplyDto.getAddress());// 家庭住址
			excel.setCellStrValue(43, 9, helpApplyDto.getCompany() == null ? ""
					: helpApplyDto.getCompany());// 工作单位
			excel.setCellStrValue(43, 14,
					helpApplyDto.getUnitPropertiesName() == null ? ""
							: helpApplyDto.getUnitPropertiesName());// 单位性质
			excel.setCellStrValue(43, 21,
					helpApplyDto.getCompanyStatusName() == null ? ""
							: helpApplyDto.getCompanyStatusName());// 企业状况
			excel.setCellStrValue(43, 23,
					helpApplyDto.getIsSingleParentName() == null ? ""
							: helpApplyDto.getIsSingleParentName());// 是否单亲
			excel.setCellStrValue(45, 2,
					helpApplyDto.getMonthlyIncome() == null ? "" : helpApplyDto
							.getMonthlyIncome().toString());// 本人月平均收入
			excel.setCellStrValue(45, 7,
					helpApplyDto.getFamilySalaryIncome() == null ? ""
							: helpApplyDto.getFamilySalaryIncome().toString());// 家庭其他非薪资年收入
			excel.setCellStrValue(45, 9,
					helpApplyDto.getIncomeTotal() == null ? "" : helpApplyDto
							.getIncomeTotal().toString());// 家庭年度总收入
			excel.setCellStrValue(45, 11,
					helpApplyDto.getFamilyNum() == null ? "" : helpApplyDto
							.getFamilyNum().toString());// 家庭人口
			excel.setCellStrValue(45, 14,
					helpApplyDto.getAvgMonthlyIncome() == null ? ""
							: helpApplyDto.getAvgMonthlyIncome().toString());// 家庭月人均收入
			excel.setCellStrValue(45, 20,
					helpApplyDto.getSeatRegistered() == null ? ""
							: helpApplyDto.getSeatRegistered());// 户口所在地行政区划
			excel.setCellStrValue(45, 23,
					helpApplyDto.getMedicalInsuranceName() == null ? ""
							: helpApplyDto.getMedicalInsuranceName());// 医保状况
			excel.setCellStrValue(46, 7,
					helpApplyDto.getIsHasSelfSaveName() == null ? ""
							: helpApplyDto.getIsHasSelfSaveName());// 是否有一定自救能力
			excel.setCellStrValue(
					46,
					22,
					helpApplyDto.getIsZeroJobName() == null ? "" : helpApplyDto
							.getIsZeroJobName());// 是否为零就业家庭
			//家庭成员
			for (int i = 0; i < familyDtoList.size(); i++) {
				excel.setCellStrValue(48 + i, 4, familyDtoList.get(i).getName());// 姓名
				excel.setCellStrValue(48 + i, 5, familyDtoList.get(i)
						.getFamilyRelationshipName());//关系
				excel.setCellStrValue(48 + i, 6, familyDtoList.get(i)
						.getSexName());// 性别
				excel.setCellStrValue(48 + i, 8, familyDtoList.get(i)
						.getPoliticalName());// 政治面貌 
				excel.setCellStrValue(48 + i, 10, familyDtoList.get(i)
						.getIdCard());// 身份证号
				excel.setCellStrValue(48 + i, 13, familyDtoList.get(i)
						.getHealthName());// 健康状况
				excel.setCellStrValue(48 + i, 15, familyDtoList.get(i)
						.getMonthlyIncome().toString());// 月收入
				excel.setCellStrValue(48 + i, 18, familyDtoList.get(i)
						.getIdentity());// 身份
				excel.setCellStrValue(48 + i, 20, familyDtoList.get(i)
						.getMedicalInsuranceName());// 医保状况
				excel.setCellStrValue(48 + i, 23, familyDtoList.get(i)
						.getUnitSchool());//单位或学校
			}
			excel.setCellStrValue(54, 15,
					helpApplyDto.getPovertyCausesName() == null ? ""
							: povertyCausesName);// 致困原因
			excel.setCellStrValue(55, 4,
					helpApplyDto.getOpenBank() == null ? "" : openBank);//开户银行
			excel.setCellStrValue(55, 9,
					helpApplyDto.getBranchBank() == null ? "" : branchBank);// 支行名称
			excel.setCellStrValue(55, 16,
					helpApplyDto.getCardNum() == null ? "" : cardNum);// 银行卡号
			for (int i = 1; i < variableMapList.size(); i++) {
				excel.setCellStrValue(63 + i, 2,
						variableMapList.get(i).get("taskName").toString());// 审批机构
				excel.setCellStrValue(63 + i, 4,
						variableMapList.get(i).get("userName").toString());// 审批人
				excel.setCellStrValue(63 + i, 7,
						variableMapList.get(i).get("applyMsg").toString());// 审批意见
				excel.setCellStrValue(63 + i, 10,
						variableMapList.get(i).get("approvalStatus").toString());// 审批结果
				excel.setCellStrValue(63 + i, 12,
						variableMapList.get(i).get("approveTime").toString());// 审批时间

				excel.setCellStrValue(63 + i, 18,
						variableMapList.get(i).get("moneyNum").toString());// 建议救助资金
				excel.setCellStrValue(63 + i, 20,
						variableMapList.get(i).get("moneyFrom").toString());// 资金来源
			}
			// excel.setCellStrValue(8, 0, "");
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filedisplay);
			OutputStream output = response.getOutputStream();
			excel.exportToNewFile(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件路径
	 * 
	 * @return
	 */
	public static String getClassResources() {
		String path = (String.valueOf(Thread.currentThread()
				.getContextClassLoader().getResource("")))
				.replaceAll("file:/", "").replaceAll("%20", " ").trim();
		if (path.indexOf(":") != 1) {
			path = File.separator + path;
		}
		return path;
	}
}

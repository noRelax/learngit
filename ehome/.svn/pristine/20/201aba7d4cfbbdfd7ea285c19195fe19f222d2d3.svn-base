package com.ehome.cloud.help.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.help.dto.HelpApplyDto;
import com.ehome.cloud.help.dto.HelpApplyFamilyDto;
import com.ehome.cloud.help.dto.SignTableDetailDto;
import com.ehome.cloud.help.dto.SignTableDto;
import com.ehome.cloud.help.model.HelpApplyFamilyModel;
import com.ehome.cloud.help.model.HelpApplyModel;
import com.ehome.cloud.help.model.SignTableDetailModel;
import com.ehome.cloud.help.model.SignTableModel;
import com.ehome.cloud.help.service.IHelpApplyFamilyService;
import com.ehome.cloud.help.service.IHelpApplyService;
import com.ehome.cloud.help.service.ISignTableDetailService;
import com.ehome.cloud.help.service.ISignTableService;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.ConvertNumUtil;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.ExcelUtil;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.PathUtil;
import com.ehome.core.util.UuidUtil;
import com.ehome.core.util.ZipUtil;
import com.github.pagehelper.PageInfo;

/**
 * @Title:SignLedTableController
 * @Description: TODO 帮扶-签领表
 * @author hl@diandianwifi.com
 * @date 2017年3月21日 下午3:08:12
 * @version
 */
@Controller
@RequestMapping(value = "/signTable")
public class SignTableController extends BaseController {

	@Resource
	private IDictionaryService dictionaryService;

	@Resource
	private ISignTableService signTableService;

	@Resource
	private IHelpApplyFamilyService helpApplyFamilyService;

	@Resource
	private ISignTableDetailService signTableDetailService;

	@Resource
	private IHelpApplyService helpApplyService;

	@Resource
	private IUploadFileService uploadFileService;
	
	@Resource
	private IMemberService memberService; 

	@RequestMapping(value = "/unsignList")
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	public String getApplyPageList(Model model, NativeWebRequest request) {
		// 回显资金来源下拉框
		List<Dictionary> distAmountSource = dictionaryService
				.queryByCode("CODE_AMOUNT_SOURCE");
		if (CollectionUtils.isNotEmpty(distAmountSource)) {
			model.addAttribute("distAmountSource",
					JSON.toJSON(distAmountSource));
		}
		// 回显帮扶项目下拉框
		List<Dictionary> helpType = dictionaryService
				.queryByCode("CODE_HELP_TYPE");
		if (CollectionUtils.isNotEmpty(helpType)) {
			model.addAttribute("helpType", JSON.toJSON(helpType));
		}
		return "/caiwushenpi/unsign/list.html";
	}

	/**
	 * @param model
	 * @param request
	 * @param helpProject
	 *            帮扶项目
	 * @param amountSource
	 *            资金来源
	 * @param sEcho
	 * @param page
	 *            当前页面
	 * @param rows
	 *            页数
	 * @return
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	public AjaxResult queryUnsignList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "1") Integer helpProject,
			@RequestParam(required = false, defaultValue = "1") Integer amountSource,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		Condition memberCondition = new Condition(Member.class);
		memberCondition.createCriteria().andEqualTo("upperUnionId", this.getCurrentUserOrgId());
		List<Member> memberList = memberService.selectByCondition(memberCondition);
		Integer[] foo = new Integer[memberList.size()];
		if(CollectionUtils.isNotEmpty(memberList)){
			for (int i = 0; i < memberList.size(); i++) {
				foo[i] = memberList.get(i).getId();
			}
		}
		Iterable<Integer> iterable = null;
		if(foo.length>0){
			iterable = Arrays.asList(foo);
		}else{
			List<SignTableDetailDto> list  = new ArrayList<SignTableDetailDto>();
			PageInfo<SignTableDetailDto> pageInfo = new PageInfo<>(
					list);
			Pagination<SignTableDetailDto> pagination = new Pagination<>();
			pagination.setData(pageInfo.getList());
			pagination.setsEcho(sEcho);
			pagination.setiTotalDisplayRecords((int) list.size());
			pagination.setiTotalRecords((int) list.size());
			return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
		}
		List<SignTableDetailDto> signTableDetailDtoList = new ArrayList<SignTableDetailDto>();
		Condition helpApplyCondition = new Condition(HelpApplyModel.class);
		Criteria helpApplyCriteria = helpApplyCondition.createCriteria();
		helpApplyCriteria.andEqualTo("isSign", 0);
		helpApplyCriteria.andEqualTo("helpType", helpProject);
		helpApplyCriteria.andEqualTo("applyFileStatus", 1);
		helpApplyCriteria.andEqualTo("amountSource", amountSource);
		helpApplyCriteria.andIn("memberId", iterable);
		List<HelpApplyModel> helpApplyModelList = helpApplyService
				.selectByCondition(helpApplyCondition);
		if (!CollectionUtils.isEmpty(helpApplyModelList)) {
			for (HelpApplyModel helpApplyModel : helpApplyModelList) {
				if (helpApplyModel != null) {
					SignTableDetailDto signTableDetailDto = new SignTableDetailDto();
					SignTableDetailModel signTableDetailModel = new SignTableDetailModel();
					Condition condtion = new Condition(
							SignTableDetailModel.class);
					Criteria criteria = condtion.createCriteria();
					criteria.andEqualTo("helpApplyId", helpApplyModel.getId());
					List<SignTableDetailModel> signTableDetailModelList = signTableDetailService
							.selectByCondition(condtion);
					if (CollectionUtils.isEmpty(signTableDetailModelList)) {
						// 没有则先插入个签表
						signTableDetailModel.setHelpApplyId(helpApplyModel
								.getId());
						signTableDetailService.save(signTableDetailModel);
						Condition condtion1 = new Condition(
								SignTableDetailModel.class);
						Criteria criteria1 = condtion1.createCriteria();
						criteria1.andEqualTo("helpApplyId",
								helpApplyModel.getId());
						List<SignTableDetailModel> sList = signTableDetailService
								.selectByCondition(condtion);
						signTableDetailDto.setId(sList.get(0).getId());
					} else {
						signTableDetailDto.setId(signTableDetailModelList
								.get(0).getId());
					}
					signTableDetailDto.setName(helpApplyModel.getName());
					signTableDetailDto.setSex(helpApplyModel.getSex());
					signTableDetailDto.setAge(Integer.parseInt(DateUtils
							.getPersonAgeByBirthDate(helpApplyModel
									.getBrithday())));
					signTableDetailDto.setIdCard(helpApplyModel.getIdCard());
					signTableDetailDto.setCardNum(helpApplyModel.getCardNum());
					signTableDetailDto.setCompanyOrAddress(helpApplyModel
							.getCompany()+"/" + helpApplyModel.getAddress());
					signTableDetailDto.setAmountNum(helpApplyModel
							.getAmountNum());
					signTableDetailDto.setAmountSource(helpApplyModel
							.getAmountSource());
					signTableDetailDto.setCellphone(helpApplyModel.getPhone());
					signTableDetailDto.setHelpProject(helpApplyModel
							.getHelpType());
					signTableDetailDto.setEmployerNum(helpApplyModel
							.getEmployerNo());
					signTableDetailDtoList.add(signTableDetailDto);
				}
			}
		}
		int j = 0;
		List<SignTableDetailDto> signTableDetailDtoPageList = new ArrayList<SignTableDetailDto>();
		for (int i = (page - 1) * rows; j < rows; i++) {
			if (i < signTableDetailDtoList.size()) {
				signTableDetailDtoPageList.add(signTableDetailDtoList.get(i));
			} else {
				break;
			}
			j++;
		}
		DictoryCodeUtils.renderList(signTableDetailDtoPageList);
		PageInfo<SignTableDetailDto> pageInfo = new PageInfo<>(
				signTableDetailDtoPageList);
		Pagination<SignTableDetailDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) signTableDetailDtoList.size());
		pagination.setiTotalRecords((int) signTableDetailDtoList.size());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/helpApply")
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	public ModelAndView queryApplyDetail(NativeWebRequest request) {
		ModelAndView model = new ModelAndView();
		SimpleDateFormat sf = new SimpleDateFormat("yyy-MM-dd");
		String idStr = request.getParameter("id");
		List<UploadFile> idCardList = new ArrayList<UploadFile>();
		List<UploadFile> famIdCardList = new ArrayList<UploadFile>();
		List<UploadFile> povertyImgIdList = new ArrayList<UploadFile>();
		HelpApplyDto helpApplyDto = new HelpApplyDto();
		if (StringUtils.isNotBlank(idStr)) {
			SignTableDetailModel signTableDetailModel = signTableDetailService
					.selectByKey(NumberUtils.toInteger(idStr));
			HelpApplyModel helpApplyModel = helpApplyService
					.selectByKey(signTableDetailModel.getHelpApplyId());
			if (null != helpApplyModel) {
				BeanUtils.copyProperties(helpApplyModel, helpApplyDto);
				DictoryCodeUtils.renderCode(helpApplyDto);
				Condition condition = new Condition(HelpApplyFamilyModel.class);
				condition.createCriteria().andEqualTo("helpApplyId",
						helpApplyModel.getId());
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
				if (StringUtils.isNotBlank(helpApplyModel.getIdCardImgId())) {
					List<String> idCardImgId = Arrays.asList(helpApplyModel
							.getIdCardImgId().split(","));
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
						if (CollectionUtils.isNotEmpty(uploadFiled)) {
							for (UploadFile fild : uploadFiled) {
								UploadFile filePath = new UploadFile();
								BeanUtils.copyProperties(fild, filePath);
								idCardList.add(filePath);
							}
						}
					}
				}
				if (StringUtils.isNotBlank(helpApplyModel.getFamIdCardImgId())) {
					List<String> famIdCardImgId = Arrays.asList(helpApplyModel
							.getFamIdCardImgId().split(","));
					if (!CollectionUtils.isEmpty(famIdCardImgId)) {
						Integer[] foo = new Integer[famIdCardImgId.size()];
						for (int i = 0; i < famIdCardImgId.size(); i++) {
							foo[i] = NumberUtils
									.toInt(famIdCardImgId.get(i), 0);
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
				if (StringUtils.isNotBlank(helpApplyModel.getPovertyImgId())) {
					List<String> povertyImgId = Arrays.asList(helpApplyModel
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
		// mav.addObject("renderedTaskForm", renderedTaskForm.toString());//
		// 整个页面，参数已经赋值（外置表单，普通表单不适用）
		model.addObject("helpApplyDto", helpApplyDto);
		model.addObject("workYear", sf.format(helpApplyDto.getWorkYear()));
		model.addObject("idCardImg", idCardList);
		model.addObject("famIdCardImg", famIdCardList);
		model.addObject("povertyImgId", povertyImgIdList);
		model.setViewName("/caiwushenpi/unsign/helpApply.html");
		model.addObject("birthday",sf.format(helpApplyDto.getBrithday()));
		return model;
	}

	/**
	 * 生成签领表
	 * 
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/saveSignTable", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	@ResponseBody
	public AjaxResult saveSignTable(
			HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String helpType,
			@RequestParam(required = false, defaultValue = "") String amountSource,
			@RequestParam(required = false, defaultValue = "") String number,
			@RequestParam(required = false, defaultValue = "") String id) {
		id = id.replace("[", "");
		id = id.replace("]", "");
		String[] sId = id.split(",");
		final int POOL_SIZE = 10;
		Integer RECORD_COUNT = 0;
		List<SignTableDetailModel> signTableDetailModelList = new ArrayList<SignTableDetailModel>(
				10);
		List<SignTableDetailModel> subList = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyMMddHHmmssSSS");
		for (int i = 0; i < sId.length; i++) {
			SignTableDetailModel signTableDetailModel = signTableDetailService
					.selectByKey(Integer.parseInt(sId[i].trim()));
			signTableDetailModelList.add(signTableDetailModel);
		}
		while (CollectionUtils.isNotEmpty(signTableDetailModelList)) {
			if (signTableDetailModelList.size() > POOL_SIZE) {
				subList = signTableDetailModelList.subList(0, POOL_SIZE);
				signTableDetailModelList = signTableDetailModelList.subList(
						POOL_SIZE, signTableDetailModelList.size());
			} else {
				subList = new ArrayList<>();
				subList.addAll(signTableDetailModelList);
				signTableDetailModelList.clear();
			}
			if (CollectionUtils.isNotEmpty(subList)) {
				BigDecimal totalAmount = BigDecimal.ZERO;
				for (SignTableDetailModel signDetail : subList) {
					HelpApplyModel applyModel = helpApplyService
							.selectByKey(signDetail.getHelpApplyId());
					totalAmount = totalAmount
							.add(applyModel.getAmountNum() == null ? BigDecimal.ZERO
									: applyModel.getAmountNum());
				}
				String time = sf.format(new Date(System.currentTimeMillis()));
				SignTableModel sign = new SignTableModel();
				sign.setSignTableName(time + amountSource + helpType + "帮扶签领表");
				List<Dictionary> helpTypeList = dictionaryService
						.queryByCode("CODE_HELP_TYPE");
				for (Dictionary dictionary : helpTypeList) {
					if (dictionary.getDictValue().equals(helpType)) {
						sign.setHelpProject(Integer.parseInt(dictionary
								.getDictKey()));
					}
				}
				List<Dictionary> amountSourceList = dictionaryService
						.queryByCode("CODE_AMOUNT_SOURCE");
				for (Dictionary dictionary : amountSourceList) {
					if (dictionary.getDictValue().equals(amountSource)) {
						sign.setAmountSource((Integer.parseInt(dictionary
								.getDictKey())));
					}
				}
				sign.setTotalAmount(totalAmount);
				sign.setTotalEmployer(subList.size());
				sign.setCreateDate(new Date(System.currentTimeMillis()));
				sign.setCityId(this.getCurrentUserCityId());
				int key = signTableService.save(sign);
				RECORD_COUNT++;
				if (key > 0) {
					for (SignTableDetailModel signDetail : subList) {
						signDetail.setSignId(sign.getId());
						signTableDetailService.updateByKey(signDetail);
						HelpApplyModel applyModel = helpApplyService
								.selectByKey(signDetail.getHelpApplyId());
						applyModel.setIsSign(1);
						helpApplyService.updateByKey(applyModel);
					}
				}
			}
		}
		return new AjaxResult(ResponseCode.success.getCode(),
				String.valueOf(RECORD_COUNT), ResponseCode.success.getMsg());
	}

	/**
	 * 添加签领个案
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findSignTemp")
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	public ModelAndView joinTemp(Model model,
			@RequestParam(name = "id", required = false) String[] id)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < id.length; i++) {
			list.add(Integer.parseInt(id[i]));
		}
		model.addAttribute("id", list);
		modelAndView.setViewName("/caiwushenpi/unsign/list_temp.html");
		return modelAndView;
	}

	/**
	 * 临时表列表
	 * 
	 * @param model6
	 * @param request
	 * @param id
	 * @param sEcho
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/queryTempList", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("help:cwcp:unsign")
	public AjaxResult queryTempList(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "0") String id,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		id = id.replace("[", "");
		id = id.replace("]", "");
		String[] sId = id.split(",");
		List<SignTableDetailDto> signTableDetailDtoList = new ArrayList<SignTableDetailDto>(
				sId.length);
		for (int i = 0; i < sId.length; i++) {
			SignTableDetailDto signTableDetailDto = new SignTableDetailDto();
			SignTableDetailModel signTableDetailModel = signTableDetailService
					.selectByKey(Integer.parseInt(sId[i].trim()));
			HelpApplyModel helpApplyModel = helpApplyService
					.selectByKey(signTableDetailModel.getHelpApplyId());
			signTableDetailDto.setSex(helpApplyModel.getSex());
			signTableDetailDto.setId(signTableDetailModel.getId());
			signTableDetailDto
					.setAmountSource(helpApplyModel.getAmountSource());
			signTableDetailDto.setHelpProject(helpApplyModel.getHelpType());
			signTableDetailDto.setCardNum(helpApplyModel.getCardNum());
			signTableDetailDto.setAmountNum(helpApplyModel.getAmountNum());
			signTableDetailDto.setCellphone(helpApplyModel.getPhone());
			signTableDetailDto.setEmployerNum(helpApplyModel.getEmployerNo());
			signTableDetailDto.setDocumentNum(signTableDetailModel
					.getDocumentNum());
			signTableDetailDto.setIdCard(helpApplyModel.getIdCard());
			signTableDetailDto.setName(helpApplyModel.getName());
			Date brithday = helpApplyModel.getBrithday();
			signTableDetailDto.setAge(Integer.parseInt(DateUtils
					.getPersonAgeByBirthDate(brithday)));
			signTableDetailDto.setCompanyOrAddress(helpApplyModel
					.getCompany()+"/" + helpApplyModel.getAddress());
			signTableDetailDtoList.add(signTableDetailDto);
		}
		int j = 0;
		List<SignTableDetailDto> signTableDetailDtoPageList = new ArrayList<SignTableDetailDto>();
		for (int i = (page - 1) * rows; j < rows; i++) {
			if (i < signTableDetailDtoList.size()) {
				signTableDetailDtoPageList.add(signTableDetailDtoList.get(i));
			} else {
				break;
			}
			j++;
		}
		DictoryCodeUtils.renderList(signTableDetailDtoPageList);
		PageInfo<SignTableDetailDto> pageInfo = new PageInfo<>(
				signTableDetailDtoPageList);
		Pagination<SignTableDetailDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		// pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) signTableDetailDtoList.size());
		pagination.setiTotalRecords((int) signTableDetailDtoList.size());
		model.addAttribute("helpProject", signTableDetailDtoList.get(0)
				.getHelpProject());
		model.addAttribute("amountSource", signTableDetailDtoList.get(0)
				.getAmountSource());
		model.addAttribute("number", sId.length);
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 跳转到签领表列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/signList")
	@RequiresUser
	@RequiresPermissions("help:cwcp:sign")
	public String showApplyInfo(Model model, NativeWebRequest request) {
		return "/caiwushenpi/sign/list.html";
	}

	/**
	 * 显示签领表列表数据
	 * 
	 * @param model
	 * @param request
	 * @param sEcho
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/showSignTalbeList", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("help:cwcp:sign")
	@ResponseBody
	public AjaxResult showApplyInfoList(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		List<SignTableDto> signTableDtoList = signTableService.queryPage(page,
				rows);
		DictoryCodeUtils.renderList(signTableDtoList);
		PageInfo<SignTableDto> pageInfo = new PageInfo<>(signTableDtoList);
		Pagination<SignTableDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 查看签领表详情
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showSignTalbeInfo")
	@RequiresUser
	@RequiresPermissions("help:cwcp:sign")
	public ModelAndView querySignTableInfo(Model model,
			@RequestParam(required = false, defaultValue = "") Integer id)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		BigDecimal totalAmount = BigDecimal.ZERO;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		List<SignTableDetailDto> listDto = new ArrayList<SignTableDetailDto>();
		SignTableModel signTableModel = signTableService.selectByKey(id);
		totalAmount = signTableModel.getTotalAmount();
		listDto = signTableDetailService.selectDtoBySignId(id);
		if(CollectionUtils.isNotEmpty(listDto)){
			for (SignTableDetailDto signTableDetailDto : listDto) {
				signTableDetailDto.setAge(Integer.parseInt(DateUtils.getPersonAgeByBirthDate(signTableDetailDto.getBirthday())));
			}
		}
		DictoryCodeUtils.renderList(listDto);
		String amountStr = ConvertNumUtil.NumToChinese(totalAmount
				.doubleValue());
		mv.addObject("listDto",JSON.toJSON(listDto));
		mv.addObject("totalAmount", totalAmount);
		mv.addObject("amountStr",amountStr);
		mv.addObject("amoutSourceName", listDto.get(0).getAmountSourceName());
		mv.addObject("helpTypeName", listDto.get(0).getHelpProjectName());
		mv.addObject("createDate",sdf.format(signTableModel.getCreateDate()));
		mv.addObject("signId", id);
		mv.setViewName("/caiwushenpi/sign/signTableInfo.html");
		return mv;
	}

	@RequestMapping(value = "/exportSignTable")
	@RequiresUser
	@RequiresPermissions("help:cwcp:sign")
	public void exportSignTable(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "") Integer signId)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		this.export(request, response, signId);
	}

	@RequestMapping(value = "/batchExportSign")
	@RequiresUser
	@RequiresPermissions("help:cwcp:sign")
	public void batchExportSignTable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String arg = request.getParameter("idList");
		if (StringUtils.isNotBlank(arg)) {
			String[] idList = arg.split(",");
			if (idList.length > 0) {
				this.export(request, response, idList);
			}
		}
	}

	public void export(HttpServletRequest request,
			HttpServletResponse response, Integer signId) throws Exception {
		ExcelUtil excel = null;
		String filedisplay = "", projectName = "", sourceName = "";
		SignTableModel signTableModel = signTableService.selectByKey(signId);
		// Condition condition = new Condition(SignTableDetailModel.class);
		// Criteria criteria = condition.createCriteria();
		// criteria.andEqualTo("signId", signId);
		// List<SignTableDetailModel> list =
		// signTableDetailService.selectByCondition(condition);
		List<SignTableDetailDto> listDto = signTableDetailService
				.selectDtoBySignId(signId);
		BigDecimal totalAmount = BigDecimal.ZERO;
		totalAmount = signTableModel.getTotalAmount();
		String amountStr = ConvertNumUtil.NumToChinese(totalAmount
				.doubleValue());
		List<Dictionary> distAmountSource = dictionaryService
				.queryByCode("CODE_AMOUNT_SOURCE");
		for (Dictionary dictionary : distAmountSource) {
			if (Integer.parseInt(dictionary.getDictKey()) == signTableModel
					.getAmountSource().intValue()) {
				sourceName = dictionary.getDictValue();
			}
		}
		// 回显帮扶项目下拉框
		List<Dictionary> helpType = dictionaryService
				.queryByCode("CODE_HELP_TYPE");
		for (Dictionary dictionary : helpType) {
			if (Integer.parseInt(dictionary.getDictKey()) == signTableModel
					.getHelpProject().intValue()) {
				projectName = dictionary.getDictValue();
			}
		}
		filedisplay = "专项资金帮扶情况实名制签领表(" + sourceName + ")" + ".xls";
		excel = this.commonExport(excel, signTableModel, listDto, sourceName,
				projectName, amountStr, totalAmount);
		filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filedisplay);
		OutputStream output = response.getOutputStream();
		excel.exportToNewFile(output);
	}

	public void export(HttpServletRequest request,
			HttpServletResponse response, String[] fkSignIdList)
			throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		File file = new File(path + "\\downloadFiles");
		if (!file.exists()) {
			file.mkdir();
		}
		List<File> listFile = new ArrayList<>();
		try {
			if (fkSignIdList.length > 0) {
				ExcelUtil excel = null;
				for (String fkSignId : fkSignIdList) {
					String filedisplay = "", projectName = "", sourceName = "";
					SignTableModel signTableModel = signTableService
							.selectByKey(Integer.parseInt(fkSignId));
					List<SignTableDetailDto> listDto = signTableDetailService
							.selectDtoBySignId(Integer.parseInt(fkSignId));
					BigDecimal totalAmount = BigDecimal.ZERO;
					totalAmount = signTableModel.getTotalAmount();
					String amountStr = ConvertNumUtil.NumToChinese(totalAmount
							.doubleValue());
					List<Dictionary> distAmountSource = dictionaryService
							.queryByCode("CODE_AMOUNT_SOURCE");
					for (Dictionary dictionary : distAmountSource) {
						if (Integer.parseInt(dictionary.getDictKey()) == signTableModel
								.getAmountSource().intValue()) {
							sourceName = dictionary.getDictValue();
						}
					}
					// 回显帮扶项目下拉框
					List<Dictionary> helpType = dictionaryService
							.queryByCode("CODE_HELP_TYPE");
					for (Dictionary dictionary : helpType) {
						if (Integer.parseInt(dictionary.getDictKey()) == signTableModel
								.getHelpProject().intValue()) {
							projectName = dictionary.getDictValue();
						}
					}
					filedisplay = "专项资金帮扶情况实名制签领表(" + sourceName + ")_"
							+ UuidUtil.get32UUID() + ".xls";
					excel = this.commonExport(excel, signTableModel, listDto,
							sourceName, projectName, amountStr, totalAmount);
					FileOutputStream output = new FileOutputStream(
							file.getPath() + "\\" + filedisplay);
					excel.exportToNewFile(output);
					listFile.add(new File(file.getPath() + "\\" + filedisplay));
				}
				String zipName = "专项资金帮扶情况实名制签领表" + UuidUtil.get32UUID()
						+ ".zip";
				File zipfile = new File(file.getPath() + "\\" + zipName);
				ZipUtil.zipFiles(listFile, zipfile);
				ZipUtil.downFile(response, file.getPath() + "", zipName);
				if (CollectionUtils.isNotEmpty(listFile)) {
					for (File excelfile : listFile) {
						if (excelfile.exists()) {
							excelfile.delete();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExcelUtil commonExport(ExcelUtil excel, SignTableModel entity,
			List<SignTableDetailDto> list, String sourceName,
			String projectName, String amountStr, BigDecimal totalAmount)
			throws Exception {
		DictoryCodeUtils.renderList(list);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		excel = new ExcelUtil();
		if (sourceName.equals("中央资金")) {
			excel.setSrcPath(PathUtil.getClassResources()
					+ "exceltemplate/signLedTableTemplate1.xls");
			excel.setSheetName("中央资金");
		} else {
			excel.setSrcPath(PathUtil.getClassResources()
					+ "exceltemplate/signLedTableTemplate2.xls");
			excel.setSheetName("省市资金");
		}
		excel.getSheet();
		excel.setCellStrValue(2, 7, "帮扶项目:" + projectName);
		excel.setCellStrValue(2, 13, sdf.format(entity.getCreateDate()));
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				SignTableDetailDto data = list.get(i);
				excel.setCellStrValue(6 + 2 * i, 0, String.valueOf(i + 1));
				excel.setCellStrValue(6 + 2 * i, 1,
						data.getName() != null ? data.getName().toString() : "");
				excel.setCellStrValue(6 + 2 * i, 2, data.getSexName());
				excel.setCellStrValue(6 + 2 * i, 3,
						DateUtils.getPersonAgeByBirthDate(data.getBirthday()));
				if (data.getIdCard() != null) {
					excel.setCellTextStrValue2(6 + 2 * i, 4, data.getIdCard()
							.toString(), (short) 2, (short) 1);
				} else {
					excel.setCellStrValue(6 + 2 * i, 4, "");
				}
				if (data.getCardNum() != null) {
					excel.setCellTextStrValue2(6 + 2 * i + 1, 4, data
							.getCardNum() != null ? data.getCardNum()
							.toString() : "", (short) 2, (short) 1);
				} else {
					excel.setCellStrValue(6 + 2 * i + 1, 4, "");
				}
				excel.setCellTextStrValue2(
						6 + 2 * i,
						7,
						(data.getCompanyOrAddress() != null ? data
								.getCompanyOrAddress().toString() : "")
								+ "/"
								+ (data.getCompanyOrAddress() != null ? data
										.getCompanyOrAddress().toString() : ""),
						(short) 2, (short) 1);
				excel.setCellStrValue(6 + 2 * i, 10,
						data.getAmountNum() != null ? data.getAmountNum()
								.toString() : "");
				excel.setCellStrValue(6 + 2 * i, 11,
						data.getCellphone() != null ? data.getCellphone()
								.toString() : "");
				excel.setCellStrValue(6 + 2 * i, 13,
						data.getEmployerNum() != null ? data.getEmployerNum()
								.toString() : "");
				excel.setCellStrValue(6 + 2 * i, 14,
						data.getDocumentNum() != null ? data.getDocumentNum()
								.toString() : "");
			}
		}
		excel.setCellStrValue(26, 2, amountStr);
		excel.setCellStrValue(26, 10, totalAmount.toString());
		return excel;
	}

	private Integer getCurrentUserCityId() {
		LoginInfoDto loginDto = this.getCurrentUser();
		return loginDto.getCity();
	}
	
}

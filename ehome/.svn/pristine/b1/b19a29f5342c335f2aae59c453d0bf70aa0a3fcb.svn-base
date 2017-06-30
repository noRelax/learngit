package com.ehome.cloud.member.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.member.dto.MemberHomeDto;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.cloud.member.service.IMemberHomeService;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title:MemberHomeController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月22日 上午11:53:37
 * @version:
 */
@Controller
@RequestMapping(value = "/member/home")
public class MemberHomeController extends BaseController {

	@Resource
	private IMemberHomeService memberHomeService;
	
	@Resource
	private IDictionaryService dictionaryService;

	/**
	 * 查询列表
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryMemberHome", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult queryMemberHomeList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer memberId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		List<MemberHomeDto> homeList = memberHomeService.queryForList(memberId,
				page, rows);
		DictoryCodeUtils.renderList(homeList);
		PageInfo<MemberHomeDto> pageInfo = new PageInfo<>(homeList);
		Pagination<MemberHomeDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteMemberHome", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult deleteMemberHome(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"), 0);
		if (pkId != 0) {
			memberHomeService.deleteByKey(pkId);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 新增
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-member-home", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	public String addHome(Model model, NativeWebRequest request) {
		Integer memberId = NumberUtils.toInt(request.getParameter("memberId"));
		List<Dictionary> familyRelationship = dictionaryService
				.queryByCode("CODE_FAMILY_RELATIONSHIP");
		if (CollectionUtils.isNotEmpty(familyRelationship))
			model.addAttribute("familyRelationship", JSON.toJSON(familyRelationship));
		model.addAttribute("memberId", memberId);
		return "/system/member/add_home.html";
	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-member-home", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	public String updateHome(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		List<Dictionary> familyRelationship = dictionaryService
				.queryByCode("CODE_FAMILY_RELATIONSHIP");
		if (CollectionUtils.isNotEmpty(familyRelationship))
			model.addAttribute("familyRelationship", JSON.toJSON(familyRelationship));
		MemberHome mh = memberHomeService.selectByKey(pkId);
		model.addAttribute("mhModel", mh);
		return "/system/member/edit_home.html";
	}

	/**
	 * 增加
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addMemberHome", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult addHome(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer memberId,
			@RequestParam(required = false, defaultValue = "") String members,
			@RequestParam(required = false, defaultValue = "") String contact,
			@RequestParam(required = false, defaultValue = "") Integer familyRelationship)
			throws BusinessException {
		// 修改
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			MemberHome mh = memberHomeService.selectByKey(id);
			if (null != mh) {
				mh.setContact(contact);
				mh.setMemberId(memberId);
				mh.setMembers(members);
				mh.setFamilyRelationship(familyRelationship);
				memberHomeService.updateByKey(mh);
			}
		} else {
			// 新增
			MemberHome mh = new MemberHome();
			mh.setContact(contact);
			mh.setMemberId(memberId);
			mh.setMembers(members);
			mh.setFamilyRelationship(familyRelationship);
		    memberHomeService.save(mh);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

}

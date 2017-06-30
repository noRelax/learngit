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

import com.ehome.cloud.member.model.MemberResume;
import com.ehome.cloud.member.service.IMemberResumeService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title:MemberResumeController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月1日 上午10:33:41
 * @version:
 */
@Controller
@RequestMapping(value = "/member/resume")
public class MemberResumeController extends BaseController {

	@Resource
	private IMemberResumeService memberResumeService;

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
	@RequestMapping(value = "/queryMemberResume", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult queryMemberResumeList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer memberId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		List<MemberResume> resumeList = memberResumeService.queryForList(
				memberId, page, rows);
		PageInfo<MemberResume> pageInfo = new PageInfo<>(resumeList);
		Pagination<MemberResume> pagination = new Pagination<>();
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
	@RequestMapping(value = "/deleteMemberResume", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult deleteMemberResume(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"), 0);
		if (pkId != 0) {
			memberResumeService.deleteByKey(pkId);
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
	@RequestMapping(value = "add-member-resume", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	public String addResume(Model model, NativeWebRequest request) {
		Integer memberId = NumberUtils.toInt(request.getParameter("memberId"));
		model.addAttribute("memberId", memberId);
		return "/system/member/add_resume.html";
	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-member-resume", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	public String updateResume(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		MemberResume mr = memberResumeService.selectByKey(pkId);
		model.addAttribute("mrModel", mr);
		return "/system/member/edit_resume.html";
	}

	/**
	 * 增加
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addMemberResume", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "mem:member:add", "mem:member:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult addResume(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer memberId,
			@RequestParam(required = false, defaultValue = "") String companyName,
			@RequestParam(required = false, defaultValue = "") String job,
			@RequestParam(required = false, defaultValue = "") String joinedDate,
			@RequestParam(required = false, defaultValue = "") String releaseDate,
			@RequestParam(required = false, defaultValue = "") String jobContent)
			throws BusinessException {
		// 修改
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			MemberResume mr = memberResumeService.selectByKey(id);
			if (null != mr) {
				mr.setCompanyName(companyName);
				mr.setMemberId(memberId);
				mr.setJob(job);
				mr.setJoinedDate(DateUtils.getDate(joinedDate));
				mr.setReleaseDate(DateUtils.getDate(releaseDate));
				mr.setJobContent(jobContent);
				memberResumeService.updateByKey(mr);
			}
		} else {
			// 新增
			MemberResume mr = new MemberResume();
			mr.setCompanyName(companyName);
			mr.setMemberId(memberId);
			mr.setJob(job);
			mr.setJoinedDate(DateUtils.getDate(joinedDate));
			mr.setReleaseDate(DateUtils.getDate(releaseDate));
			mr.setJobContent(jobContent);
			memberResumeService.save(mr);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

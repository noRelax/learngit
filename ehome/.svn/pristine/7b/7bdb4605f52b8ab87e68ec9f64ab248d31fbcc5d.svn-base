/**
 * @Project:ZGHome
 * @FileName:AppComment.java
 */
package com.ehome.cloud.app.guangchang;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.guangchang.model.CommentModel;
import com.ehome.cloud.guangchang.service.ICommentService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.PageData;
import com.ehome.core.util.SerializeUtils;
import com.ehome.core.util.StringUtils;
import com.ehome.core.util.TokenUtil;
import com.ehome.core.util.redis.JedisUtils;

/**
 * 广场评论（匿名聊）客户端处理
 * 
 * @Title:AppComment
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/guangchang/comment")
public class AppComment extends BaseController {

	@Resource
	private ICommentService iCommentService;
	@Resource
	private IAppUserService iAppUserService;

	/**
	 * 添加评论
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add() {
		logger.info("添加评论");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("",
				Long.parseLong(pd.getString("time").trim()),
				pd.getString("apptype").trim(), pd.getString("token").trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		CommentModel commentModel = new CommentModel();
		commentModel.setUserId(Integer.parseInt(pd.getString("userId").trim()));
		commentModel.setDiscussionId(Integer.parseInt(pd.getString(
				"discussionId").trim()));
		commentModel.setIsanonymous(Integer.parseInt(pd
				.getString("isanonymous").trim()));
		commentModel.setContent(pd.getString("content"));
		commentModel.setCommenttime(new Date());
		try {
			iCommentService.save(commentModel);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("id", commentModel.getId());
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 添加回复
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public Map<String, Object> getList() {
		logger.info("获取评论列表");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("",
				Long.parseLong(pd.getString("time").trim()),
				pd.getString("apptype").trim(), pd.getString("token").trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		PageData conditionPd = new PageData();
		int start = StringUtils.String2Int(pd.getString("start"), 1);
		int limit = StringUtils.String2Int(pd.getString("limit"), 5);
		if (!pd.getString("discussionId").trim().equals("")) {
			int discussionId = Integer.parseInt(pd.getString("discussionId")
					.trim());
			conditionPd.put("discussionId", discussionId);
		}
		conditionPd.put("start", start);
		conditionPd.put("limit", limit);
		List<PageData> commentList = null;
		try {
			commentList = iCommentService.selectListByDiscussion(conditionPd);
			int userId = 0, replyUserId = 0;
			AppUserModel user = null;
			for (PageData commentPd : commentList) {
				userId = commentPd.getInteger("userId");
				if (JedisUtils
						.get(("login:user:login:id:" + userId).getBytes()) == null) {
					user = iAppUserService.selectByKey(userId);
					if (user == null) {
						commentPd.put("userName", null);
						commentPd.put("userAvatar", null);
					} else {
						commentPd.put("userName", user.getNickName());
						commentPd.put("userAvatar", user.getPortrait());
						JedisUtils.set(
								("login:user:login:id:" + userId).getBytes(),
								SerializeUtils.serialize(user), 604800);
					}
				} else {
					user = (AppUserModel) SerializeUtils.deserialize(JedisUtils
							.get(("login:user:login:id:" + userId).getBytes()));
					if (user == null) {
						commentPd.put("userName", null);
						commentPd.put("userAvatar", null);
						JedisUtils.unLock("login:user:login:id:" + userId);
					} else {
						commentPd.put("userName", user.getNickName());
						commentPd.put("userAvatar", user.getPortrait());
					}
				}
				if (commentPd.get("replyUserId") != null) {
					replyUserId = commentPd.getInteger("replyUserId");
					if (JedisUtils.get(("login:user:login:id:" + replyUserId)
							.getBytes()) == null) {
						user = iAppUserService.selectByKey(replyUserId);
						if (user == null) {
							commentPd.put("replyUserName", null);
							commentPd.put("replyAvatar", null);
						} else {
							commentPd.put("replyUserName", user.getNickName());
							commentPd.put("replyAvatar", user.getPortrait());
							JedisUtils.set(
									("login:user:login:id:" + replyUserId)
											.getBytes(), SerializeUtils
											.serialize(user), 604800);
						}
					} else {
						user = (AppUserModel) SerializeUtils
								.deserialize(JedisUtils
										.get(("login:user:login:id:" + replyUserId)
												.getBytes()));
						if (user == null) {
							commentPd.put("replyUserName", null);
							commentPd.put("replyAvatar", null);
							JedisUtils.unLock("login:user:login:id:"
									+ replyUserId);
						} else {
							commentPd.put("replyUserName", user.getNickName());
							commentPd.put("replyAvatar", user.getPortrait());
						}
					}
				} else {
					commentPd.put("replyUserName", null);
					commentPd.put("replyAvatar", null);
				}
			}
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("datas", commentList);
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除评论，评论数-1
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> delete() {
		logger.info("删除评论");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("",
				Long.parseLong(pd.getString("time").trim()),
				pd.getString("apptype").trim(), pd.getString("token").trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		int commentId = Integer.parseInt(pd.getString("commentId").trim());
		try {
			CommentModel commentModel = iCommentService.selectByKey(commentId);
			iCommentService.deleteByEntity(commentModel);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}
}

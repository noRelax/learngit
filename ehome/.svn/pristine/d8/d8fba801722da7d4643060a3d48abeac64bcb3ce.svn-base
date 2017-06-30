/**
 * @Project:ZGHome
 * @FileName:AppDiscussion.java
 */
package com.ehome.cloud.app.guangchang;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Condition;

import com.ehome.cloud.guangchang.model.DiscussionModel;
import com.ehome.cloud.guangchang.model.UpvoteModel;
import com.ehome.cloud.guangchang.service.IDiscussionService;
import com.ehome.cloud.guangchang.service.IUpvoteService;
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
 * 广场讨论客户端处理
 * 
 * @Title:AppDiscussion
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/guangchang/discussion")
public class AppDiscussion extends BaseController {

	@Resource
	private IDiscussionService iDiscussionService;
	@Resource
	private IUpvoteService iUpvoteService;
	@Resource
	private IAppUserService iAppUserService;

	/**
	 * 添加讨论（匿名聊）
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add() {
		logger.info("添加讨论");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("",
				Long.parseLong(pd.getString("time").trim()),
				pd.getString("apptype").trim(), pd.getString("token").trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		DiscussionModel discussionModel = new DiscussionModel();
		discussionModel.setUserId(Integer.parseInt(pd.getString("userId")
				.trim()));
		discussionModel.setLat(pd.getString("lat").trim());
		discussionModel.setLng(pd.getString("lng").trim());
		discussionModel.setPicIds(pd.getString("picIds").trim());
		discussionModel.setPicUrls(pd.getString("picUrls").trim());
		discussionModel.setIsanonymous(Integer.parseInt(pd.getString(
				"isanonymous").trim()));
		if (!pd.getString("topicId").trim().equals("")) {
			discussionModel.setTopicId(Integer.parseInt(pd.getString("topicId")
					.trim()));
		}
		discussionModel.setContent(pd.getString("content"));
		discussionModel.setAddress(pd.getString("address"));
		discussionModel.setIsshowposition(Integer.parseInt(pd.getString(
				"isShowPosition").trim()));
		discussionModel.setCommenttimes(0);
		discussionModel.setUpvotetimes(0);
		discussionModel.setCreatetime(new Date());
		try {
			iDiscussionService.save(discussionModel);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("id", discussionModel.getId());
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取讨论（匿名聊）列表
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public Map<String, Object> getList() {
		logger.info("获取讨论列表");
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
		if (!pd.getString("topicId").trim().equals("")) {
			int topicId = Integer.parseInt(pd.getString("topicId").trim());
			conditionPd.put("topicId", topicId);
		}
		if (!pd.getString("userId").trim().equals("")) {
			int userId = Integer.parseInt(pd.getString("userId").trim());
			conditionPd.put("userId", userId);
		}
		if (!pd.getString("isanonymous").trim().equals("")) {
			int isanonymous = Integer.parseInt(pd.getString("isanonymous")
					.trim());
			conditionPd.put("isanonymous", isanonymous);
		}
		conditionPd.put("start", start);
		conditionPd.put("limit", limit);
		List<PageData> list = null;
		try {
			list = iDiscussionService.selectForList(conditionPd);
			Jedis jedis = JedisUtils.getJedis();
			int id = 0;
			AppUserModel user = null;
			for (PageData discussionPd : list) {
				id = discussionPd.getInteger("userId");
				if (jedis.get(("login:user:login:id:" + id).getBytes()) == null) {
					user = iAppUserService.selectByKey(id);
					if (user == null) {
						discussionPd.put("userName", null);
						discussionPd.put("userAvatar", null);
					} else {
						discussionPd.put("userName", user.getNickName());
						discussionPd.put("userAvatar", user.getPortrait());
						jedis.set(("login:user:login:id:" + id).getBytes(),
								SerializeUtils.serialize(user));
						jedis.expire(("login:user:login:id:" + id).getBytes(),
								604800);//过期时间一周
					}
				} else {
					user = (AppUserModel) SerializeUtils.deserialize(jedis
							.get(("login:user:login:id:" + id).getBytes()));
					if (user == null) {
						discussionPd.put("userName", null);
						discussionPd.put("userAvatar", null);
						jedis.del(("login:user:login:id:" + id).getBytes());
					} else {
						discussionPd.put("userName", user.getNickName());
						discussionPd.put("userAvatar", user.getPortrait());
					}
				}
			}
			JedisUtils.closeJedis(jedis);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("datas", list);
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取讨论（匿名聊）内容
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public Map<String, Object> get() {
		logger.info("获取讨论");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if (!TokenUtil.validateToken("",
				Long.parseLong(pd.getString("time").trim()),
				pd.getString("apptype").trim(), pd.getString("token").trim())) {
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		int id = StringUtils.String2Int((pd.getString("discussionId").trim()),
				0);
		int userId = StringUtils.String2Int((pd.getString("userId").trim()), 0);
		PageData discussionPd = null;
		Condition condition = new Condition(UpvoteModel.class);
		Condition.Criteria criteria = condition.createCriteria();
		if (id != 0) {
			criteria.andEqualTo("discussionId", id);
		}
		if (userId != 0) {
			criteria.andEqualTo("userId", userId);
		}
		List<PageData> returnList = new ArrayList<PageData>();
		try {
			discussionPd = iDiscussionService.selectByDiscussion(id);
			int appUserId = 0;
			AppUserModel user = null;
			appUserId = discussionPd.getInteger("userId");
			if (JedisUtils.get(("login:user:login:id:" + appUserId).getBytes()) == null) {
				user = iAppUserService.selectByKey(appUserId);
				if (user == null) {
					discussionPd.put("userName", null);
					discussionPd.put("userAvatar", null);
				} else {
					discussionPd.put("userName", user.getNickName());
					discussionPd.put("userAvatar", user.getPortrait());
					JedisUtils.set(
							("login:user:login:id:" + appUserId).getBytes(),
							SerializeUtils.serialize(user), 604800);
				}
			} else {
				user = (AppUserModel) SerializeUtils.deserialize(JedisUtils
						.get(("login:user:login:id:" + appUserId).getBytes()));
				if (user == null) {
					discussionPd.put("userName", null);
					discussionPd.put("userAvatar", null);
					JedisUtils.unLock("login:user:login:id:" + appUserId);
				} else {
					discussionPd.put("userName", user.getNickName());
					discussionPd.put("userAvatar", user.getPortrait());
				}
			}
			List<UpvoteModel> upvoteList = iUpvoteService
					.selectByCondition(condition);
			if (upvoteList.size() > 0) {
				discussionPd.put("isupvote", 1);
			} else {
				discussionPd.put("isupvote", 0);
			}
			returnList.add(discussionPd);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("datas", returnList);
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取个人讨论（匿名聊）列表
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/getDiscussionList", method = RequestMethod.POST)
	public Map<String, Object> getDiscussionList() {
		logger.info("获取讨论列表");
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
		int userId = Integer.parseInt(pd.getString("userId").trim());
		int isanonymous = Integer.parseInt(pd.getString("isanonymous").trim());
		conditionPd.put("isanonymous", isanonymous);
		conditionPd.put("userId", userId);
		conditionPd.put("start", start);
		conditionPd.put("limit", limit);
		List<DiscussionModel> list = null;
		try {
			list = iDiscussionService.selectDiscussionList(conditionPd);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("datas", list);
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}
		return map;
	}
}

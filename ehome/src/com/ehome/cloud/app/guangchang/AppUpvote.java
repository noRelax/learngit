/**
 * @Project:ZGHome
 * @FileName:AppUpvote.java
 */
package com.ehome.cloud.app.guangchang;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.guangchang.model.UpvoteModel;
import com.ehome.cloud.guangchang.service.IUpvoteService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.PageData;
import com.ehome.core.util.TokenUtil;

/**
 * 广场讨论点赞客户端处理
 * 
 * @Title:AppUpvote
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/guangchang/upvote")
public class AppUpvote extends BaseController {

	@Resource
	private IUpvoteService iUpvoteService;

	/**
	 * 点赞
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add() {
		logger.info("点赞");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(pd.getString("time").trim()), pd.getString("apptype").trim(), pd.getString("token").trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

		UpvoteModel upvoteModel = new UpvoteModel();
		upvoteModel.setDiscussionId(Integer.parseInt(pd.getString("discussionId").trim()));
		upvoteModel.setUserId(Integer.parseInt(pd.getString("userId").trim()));
		upvoteModel.setUpvotetime(new Date());

		try {
			iUpvoteService.save(upvoteModel);
			map.put("status", ResponseCode.success.getCode());
			map.put("message", "success");
			map.put("id", upvoteModel.getId());
		} catch (Exception e) {
			map.put("status", ResponseCode.error.getCode());
			map.put("message", "error");
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 取消赞
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> delete() {
		logger.info("取消赞");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(pd.getString("time").trim()), pd.getString("apptype").trim(), pd.getString("token").trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

		UpvoteModel upvoteModel = new UpvoteModel();
		upvoteModel.setDiscussionId(Integer.parseInt(pd.getString("discussionId").trim()));
		upvoteModel.setUserId(Integer.parseInt(pd.getString("userId").trim()));

		try {
			iUpvoteService.deleteByUserAndDiscussion(upvoteModel);
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

/**
 * @Project:ZGHome
 * @FileName:AppReply.java
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

import com.ehome.cloud.guangchang.model.CommentModel;
import com.ehome.cloud.guangchang.service.ICommentService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.PageData;
import com.ehome.core.util.TokenUtil;

/**
 * 广场讨论（匿名聊）回复客户端处理
 * 
 * @Title:AppReply
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/guangchang/reply")
public class AppReply extends BaseController {

	// 回复和评论使用一个表，若回复则需存回复的用户

	@Resource
	private ICommentService iCommentService;

	/**
	 * 添加回复
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, Object> add() {
		logger.info("添加回复");
		PageData pd = this.getPageData();
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(pd.getString("time").trim()), pd.getString("apptype").trim(), pd.getString("token").trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

		CommentModel commentModel = new CommentModel();
		commentModel.setUserId(Integer.parseInt(pd.getString("userId").trim()));
		commentModel.setDiscussionId(Integer.parseInt(pd.getString("discussionId").trim()));
		commentModel.setIsanonymous(Integer.parseInt(pd.getString("isanonymous").trim()));
		commentModel.setReplyuserId(Integer.parseInt(pd.getString("replyuserId").trim()));
		commentModel.setContent(pd.getString("content"));
		commentModel.setCommenttime(new Date());
		// commentModel.setCommentId(Integer.parseInt(pd.getString("commentId").trim()));

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

}

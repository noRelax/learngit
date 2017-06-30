package com.ehome.cloud.app.sys;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.app.sys.sdk.GeetestConfig;
import com.ehome.cloud.app.sys.sdk.GeetestLib;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.RonglianyunSMSUtil;
import com.ehome.core.util.redis.JedisUtils;

/**
 * 
 * @Title:GeetestController
 * @Description:极验
 * @author:zsh
 * @date:2017年3月30日
 * @version 1.0,2017年3月30日
 * @{tags}
 */
@Controller
@RequestMapping(value = "/app/Geetest")
public class GeetestController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(GeetestController.class);

	/**
	 * 使用Get的方式返回challenge和capthca_id 启动极验 验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/startCaptcha", method = RequestMethod.GET)
	public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
				GeetestConfig.isnewfailback());
		String resStr = "{}";
		String userid = request.getParameter("jiYanUid") == null ? "" : request.getParameter("jiYanUid").toString();
		logger.info("userid==>:" + userid);
		// 进行验证预处理
		int gtServerStatus = gtSdk.preProcess(userid);
		String useridStatus = "status" + userid;
		JedisUtils.setEx(userid, 1000 * 60 * 2, userid);
		JedisUtils.setEx(useridStatus, 1000 * 60 * 2, Integer.toString(gtServerStatus));
		resStr = gtSdk.getResponseStr();
		PrintWriter out = response.getWriter();
		out.println(resStr);
	}

	/**
	 * 提交时进行验证 (只有当第三方校验通过时，点击确认才会进入此方法)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public void verify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
				GeetestConfig.isnewfailback());

		String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
		String validate = request.getParameter(GeetestLib.fn_geetest_validate);
		String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
		String jiYanUid = request.getParameter("jiYanUid") == null ? "" : request.getParameter("jiYanUid").toString();
		String user_account = request.getParameter("user_account") == null ? ""
				: request.getParameter("user_account").toString();

		String userid = JedisUtils.get(jiYanUid);
		String useridStatus = "status" + userid;
		int gt_server_status_code = Integer.parseInt(JedisUtils.get(useridStatus));

		int gtResult = 0;
		if (gt_server_status_code == 1) {
			// gt-server正常，向gt-server进行二次验证
			gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
			logger.info("gtResult==>:" + gtResult);
		} else {
			// gt-server非正常情况下，进行failback模式验证
			logger.info("failback:use your own server captcha validate");
			gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
			logger.info("gtResult==>:" + gtResult);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("version", gtSdk.getVersionInfo());
		if (gtResult == 1) {
			// 验证成功
			// PrintWriter out = response.getWriter();
			JSONObject data = new JSONObject();
			try {
				data.put("status", "success");
				data.put("version", gtSdk.getVersionInfo());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// out.println(data.toString());
			logger.info(data.toString());
			int code = NumberUtils.getRandomNum();// 生成随机验证码
			String uuid = UUID.randomUUID().toString();
			if (RonglianyunSMSUtil.send(user_account, new String[] { String.valueOf(code), "10" })) {
				JedisUtils.setEx(uuid, 1000 * 60 * 2, Integer.toString(code));
				map.put("codeKey", uuid);
			}
			logger.info("进入验证通过");
		} else {
			logger.info("进入不验证通过");
			// 验证失败
			JSONObject data = new JSONObject();
			try {
				data.put("status", "fail");
				data.put("version", gtSdk.getVersionInfo());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().print(JsonUtil.appResposeSingeMapJson(ResponseCode.code_error.getCode(),
					ResponseCode.code_error.getMsg(), map));
			return;
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), map));
	}

}
/*
 * 广州陨石互联网科技有限公司
 * 
 * 项目名称 : ZGHome-Common
 * 创建日期 : 2017年5月22日
 * 修改历史 : 
 *     1. [2017年5月22日]创建文件 by admin
 */
package com.ehome.cloud.app.sys.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.HttpUtils;
import com.ehome.core.util.StringUtils;

/**
 * //TODO 添加类/接口功能描述
 * @author 张钟武
 */
public class ThirdPathLoginHelper {

	private final static String QQ_AUTH_OPENID_URL = "https://graph.qq.com/oauth2.0/me";

	private final static String WEIXIN_ACCESS_TOKEN_VALID_URL = "https://api.weixin.qq.com/sns/auth";

	private final static String SINA_AUTH_OPENID_URL = "https://api.weibo.com/oauth2/get_token_info";

	public static String getAuthOpenId(String accessToken, String openId,
			Integer fromType) {
		Map<String, String> params = new HashMap<>();
		if (fromType == 2) {
			//			params.put("appid", "APPID");
			//			params.put("secret", "SECRET");
			//			params.put("code", "code");
			//			params.put("grant_type", "grant_type");
			//			String result = HttpUtils.URLGet(WEIXIN_AUTH_ACCESS_TOKEN_URL,
			//					params, "UTF-8");
			//			if (StringUtils.isNotBlank(result)) {
			//				JSONObject token = JSON.parseObject(result);
			//				String access_token = token.getString("access_token");
			//				String openid = token.getString("openid");
			//				String refresh_token = token.getString("refresh_token");
			//				return openid;
			//			}
			//微信授权
			params.put("access_token", accessToken);
			params.put("openid", openId);
			String result = HttpUtils.URLGet(WEIXIN_ACCESS_TOKEN_VALID_URL,
					params, "UTF-8");
			//System.out.println("weixin:"+result);
			if (StringUtils.isNotBlank(result)) {
				JSONObject json = JSON.parseObject(result);
				if (json.getString("errcode").equals("0")
						&& json.getString("errmsg").equals("ok")) {
					return openId;
				} else {
					throw new BusinessException("授权凭证access_token无效");
				}
			} else {
				throw new BusinessException("授权凭证access_token无效");
			}
		} else if (fromType == 3) {
			//QQ授权
			params.put("access_token", accessToken);
			String result = HttpUtils.URLGet(QQ_AUTH_OPENID_URL, params,
					"UTF-8");
			if (StringUtils.isNotBlank(result)) {
				String jsonResult = StringUtils.substringBefore(
						StringUtils.substringAfter(result, "("), ")");
				JSONObject json = JSON.parseObject(jsonResult);
				if (openId.equals(json.getString("openid"))) {
					return json.getString("openid");
				} else {
					throw new BusinessException("授权凭证access_token无效");
				}
			} else {
				throw new BusinessException("授权凭证access_token无效");
			}
		} else if (fromType == 4) {
			//新浪微博授权
			params.put("access_token", accessToken);
			String result = HttpUtils.URLPost(SINA_AUTH_OPENID_URL, params,
					"UTF-8");
			//System.out.println("新浪:"+result);
			if (StringUtils.isNotBlank(result)) {
				JSONObject json = JSON.parseObject(result);
				if (openId.equals(json.getString("uid"))) {
					return json.getString("uid");
				} else {
					throw new BusinessException("授权凭证access_token无效");
				}
			} else {
				throw new BusinessException("授权凭证access_token无效");
			}
		}
		return StringUtils.EMPTY;
	}
}

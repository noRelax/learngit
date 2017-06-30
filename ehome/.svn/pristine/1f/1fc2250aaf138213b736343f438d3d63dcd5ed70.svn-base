package com.ehome.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehome.core.dict.ResponseCode;
import com.ehome.core.util.MapUtils;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
/*
 * @Title:GetuiUtil
 * @Description:TODO
 * @author:zsh
 * @date:2017年4月10日
 * @version 1.0,2017年4月10日
 * 个推管理类
  */
public class GetuiUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(GetuiUtil.class);
	public static final String defaultValue = "";
	//  透传
	@Deprecated
	public static TransmissionTemplate transmissionTemplateDemo() {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(Constants.APPID);
	    template.setAppkey(Constants.APPKEY);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent("透传测试001");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	//通知栏
	@Deprecated
	public static NotificationTemplate notificationTemplateDemo() {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(Constants.APPID);
		template.setAppkey(Constants.APPKEY);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent("测试通知内容");
		return template;
	}
	
	//测试通知
	@Test
	public void testNotify(){
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IGtPush push = new IGtPush(Constants.HOST, Constants.APPKEY, Constants.MASTERSECRET);
		// 通知透传模板
		NotificationTemplate template = notificationTemplateDemo();
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 配置推送目标
		List targets = new ArrayList();
		Target target1 = new Target();
		Target target2 = new Target();
		target1.setAppId(Constants.APPID);
//		target1.setClientId(CID1);
		 target1.setAlias("1234");
		target2.setAppId(Constants.APPID);
//		target2.setClientId(CID2);
		 target2.setAlias("456");
		targets.add(target1);
		targets.add(target2);
		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println(ret.getResponse().toString());
	}
	 //测试透传
	@Test
	public void testTransmission(){
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		// IGtPush push = new IGtPush(Const.HOST, Const.APPKEY,
		// Const.MASTERSECRET);

		IGtPush push = new IGtPush(Constants.APPKEY, Constants.MASTERSECRET, true);
		// 通知透传模板
		TransmissionTemplate template = transmissionTemplateDemo();
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);

		String taskId = push.getContentId(message);
		List<Target> users = new ArrayList<>();
		Target target = new Target();
		target.setAppId(Constants.APPID);
		target.setAlias("1234");
		users.add(target);
		users.add(target);
		IPushResult ret = push.pushMessageToList(taskId, users);
		System.out.println(ret.getResponse().toString());
	}
	
	/**
	 *  创建安卓透传
	  * @param map  
	  * @return 
	  *
	 */
	public static TransmissionTemplate createTranstransparent(Map<String, Object> map) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(Constants.APPID);
	    template.setAppkey(Constants.APPKEY);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent(MapUtils.getString(map, "content", defaultValue));
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	/**
	 *  创建通知
	  * @param map 
	  * @return 
	  *
	 */
	public static NotificationTemplate createNotification(Map<String, Object> map) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(Constants.APPID);
		template.setAppkey(Constants.APPKEY);

		Style0 style = new Style0();
		
		// 设置通知栏标题与内容
		style.setTitle(MapUtils.getString(map, "title", defaultValue));//通知栏标题
		style.setText(MapUtils.getString(map, "text", defaultValue));//通知栏 内容
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(MapUtils.getString(map, "content", defaultValue));
		return template;
	}
	
	/**
	 * 推送通知
	  * @param map
	  * @return 
	  *
	 */
	public static String pushNotification(Map<String, Object> map){
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		IGtPush push = new IGtPush(Constants.HOST, Constants.APPKEY, Constants.MASTERSECRET);
		// 通知透传模板
		NotificationTemplate template = createNotification(map);
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 配置推送目标
		List targets = new ArrayList();
		//推送的用户
		List<String> users = (List<String>) map.get("users");
		if(users!=null && users.size()>0){
			for(String alias : users){
				Target target = new Target();
				target.setAppId(Constants.APPID);
				target.setAlias(alias);
				targets.add(target);
			}
		}
		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		if("ok".equals(ret.getResponse().get("result"))){
			ret.getResponse().put("status", ResponseCode.success.getCode());
		}else{
			ret.getResponse().put("status", ResponseCode.error.getCode());

		}
		System.out.println(ret.getResponse().toString());
		logger.info(ret.getResponse().toString());
		return ret.getResponse().toString();
	}
	
	/**
	 *  创建苹果透传
	  * @return 
	  *
	 */
	public static TransmissionTemplate getTemplate(Map<String, Object> map) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(Constants.APPID);
	    template.setAppkey(Constants.APPKEY);
	    template.setTransmissionContent(MapUtils.getString(map, "content", defaultValue));
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    payload.setBadge(1);
//	    payload.setContentAvailable(1);
	    payload.setSound("default");
//	    payload.setCategory("$由客户端定义");
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("您有新的通知"));
		payload.addCustomMsg("data", MapUtils.getString(map, "content", defaultValue));
	    //字典模式使用下者
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
	    return template;
	}
	
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody("body");
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // IOS8.2以上版本支持
	    alertMsg.setTitle("Title");
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}
	/**
	 * 推送安卓透传
	  * @param map
	  * @return 
	  *
	 */
	@Deprecated
	public static String pushTranstransparent(Map<String, Object> map){
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		// IGtPush push = new IGtPush(Const.HOST, Const.APPKEY,
		// Const.MASTERSECRET);

		IGtPush push = new IGtPush(Constants.APPKEY, Constants.MASTERSECRET, true);
		// 通知透传模板
		TransmissionTemplate template = createTranstransparent(map);
//		TransmissionTemplate template = getTemplate();
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);

		String taskId = push.getContentId(message);
		List<Target> targets = new ArrayList<>();
		List<String> users = (List<String>) map.get("users");
		if(users!=null && users.size()>0){
			for(String alias : users){
				Target target = new Target();
				target.setAppId(Constants.APPID);
				target.setAlias(alias);
				targets.add(target);
			}
		}
		IPushResult ret = push.pushMessageToList(taskId, targets);
		
		if("ok".equals(ret.getResponse().get("result"))){
			ret.getResponse().put("status", ResponseCode.success.getCode());
		}else{
			ret.getResponse().put("status", ResponseCode.error.getCode());

		}
		System.out.println(ret.getResponse().toString());
		logger.info(ret.getResponse().toString());
		return ret.getResponse().toString();
	}
	/**
	 * 推送苹果透传(安卓也可以收到)
	 * @param map
	 * @return 
	 *
	 */
	public static String pushTranstransparentToIOS(Map<String, Object> map){
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
		// IGtPush push = new IGtPush(Const.HOST, Const.APPKEY,
		// Const.MASTERSECRET);
		
		IGtPush push = new IGtPush(Constants.APPKEY, Constants.MASTERSECRET, true);
		// 通知透传模板
//		TransmissionTemplate template = createTranstransparent(map);
		TransmissionTemplate template = getTemplate(map);
//		getDictionaryAlertMsg();
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		
		String taskId = push.getContentId(message);
		List<Target> targets = new ArrayList<>();
		List<String> users = (List<String>) map.get("users");
		if(users!=null && users.size()>0){
			for(String alias : users){
				Target target = new Target();
				target.setAppId(Constants.APPID);
				target.setAlias(alias);
				targets.add(target);
			}
		}
		IPushResult ret = push.pushMessageToList(taskId, targets);
		
		if("ok".equals(ret.getResponse().get("result"))){
			ret.getResponse().put("status", ResponseCode.success.getCode());
		}else{
			ret.getResponse().put("status", ResponseCode.error.getCode());
			
		}
		System.out.println(ret.getResponse().toString());
		logger.info(ret.getResponse().toString());
		return ret.getResponse().toString();
	}
	
	
	@Test
	public void testPushNotification(){
		Map<String, Object> map = new HashMap();
		List<String> users = new ArrayList<>();
		users.add("12");
		map.put("users", users);
		map.put("content", "通知栏你好");//传递的内容
		map.put("title",  "biaoti");
		map.put("text","通知栏内容");
		pushNotification(map);
		
		
	}
	
	@Test
	public void testPushTranstransparent(){
		Map<String, Object> map = new HashMap();
		List<String> users = new ArrayList<>();
		users.add("1234");
		map.put("users", users);
		map.put("content", "透传你好");//传递的内容
		map.put("title",  "biaoti");
		map.put("text","通知栏内容");
		pushTranstransparent(map);
	}
	
}

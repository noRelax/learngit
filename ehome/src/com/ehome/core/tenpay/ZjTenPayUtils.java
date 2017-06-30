package com.ehome.core.tenpay;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpRequest;

public class ZjTenPayUtils {

	/** APP_ID 应用从官方网站申请到的合法appId */
	public static final String WX_APP_ID = "wxc0b81b005229dd91";

	public static final String NOTIFY_URL = "/zjtenpay/recveive.html";
	/** 商户号 */
	public static final String WX_PARTNER_ID = "1481079462";
	/** 接口链接 */
	public static final String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String orderquery_url = "https://api.mch.weixin.qq.com/pay/orderquery";
	/** 商户平台和开发平台约定的API密钥，在商户平台设置 */
	public static final String key = "1f98bb0762527b2e9863406e9b773ef1";

	public StringBuffer wechatPaySb = new StringBuffer();

	public String GetProduct(Map<String, String> params,HttpServletRequest request) {
		String nonceStr = genNonceStr();
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", WX_APP_ID));
		 packageParams.add(new BasicNameValuePair("attach",params.get("attachdata")));
		// body为汉字是要转成UTF-8
		packageParams.add(new BasicNameValuePair("body", params.get("body")));
		packageParams.add(new BasicNameValuePair("mch_id", WX_PARTNER_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		String strBackUrl = "http://" + request.getServerName() + request.getContextPath()+NOTIFY_URL; 
		System.out.println("回调地址:"+strBackUrl);
		packageParams.add(new BasicNameValuePair("notify_url", strBackUrl));
		packageParams.add(new BasicNameValuePair("out_trade_no", params.get("TradeNo").toString()));
		packageParams.add(new BasicNameValuePair("spbill_create_ip", params.get("uip").toString()));
		packageParams.add(new BasicNameValuePair("total_fee",
				"" + (int)(Double.valueOf(params.get("pay_money").toString()) * 100)));
		packageParams.add(new BasicNameValuePair("trade_type", "APP"));
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = toXml(packageParams);
		return xmlstring;
	}
	
	public String GetTrade(String TradeNo) {
		String nonceStr = genNonceStr();
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", WX_APP_ID));
		packageParams.add(new BasicNameValuePair("mch_id", WX_PARTNER_ID));
		packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
		packageParams.add(new BasicNameValuePair("out_trade_no", TradeNo));
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = toXml(packageParams);
		return xmlstring;
	}
	
	public String GetReq(String pid) {
		String nonceStr = genNonceStr();
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", WX_APP_ID));
		packageParams.add(new BasicNameValuePair("noncestr", nonceStr));
		packageParams.add(new BasicNameValuePair("package", "Sign=WXPay"));
		packageParams.add(new BasicNameValuePair("partnerid", WX_PARTNER_ID));
		packageParams.add(new BasicNameValuePair("prepayid", pid));
		packageParams.add(new BasicNameValuePair("timestamp", String.valueOf((long)System.currentTimeMillis() / 1000)));
		String sign = genPackageSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign", sign));
		String xmlstring = toXml(packageParams);
		return xmlstring;
	}

	// 获取随机验证参数
	private String genNonceStr() {
		Random random = new Random();
		return MD5Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}

	// 获取包签名
	public String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(key);
		String packageSign = "";
		packageSign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();

//		System.out.println("包签名：=" + packageSign);
		return packageSign;
	}

	/**
	 * 获得app前面
	 * 
	 * @param params
	 *            参数
	 * @return 签名以后的字符串
	 */
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(key);
		this.wechatPaySb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		System.out.println("APP签名=" + appSign);
		return appSign;
	}

	/**
	 * 将请求参数转换为xml格式
	 * 
	 * @param params
	 *            参数list
	 * @return xml字符串
	 */
	public String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<" + params.get(i).getName() + ">");
			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 解析反馈xml数据
	 * 
	 * @param content
	 *            反馈内容
	 * @return 解析结果map
	 */
	// public Map<String,String> decodeXml(String content) {
	// try {
	// Map<String, String> xml = new HashMap<String, String>();
	// XmlPullParser parser = Xml.newPullParser();
	// parser.setInput(new StringReader(content));
	// int event = parser.getEventType();
	// while (event != XmlPullParser.END_DOCUMENT) {
	// String nodeName=parser.getName();
	// switch (event) {
	// case XmlPullParser.START_DOCUMENT:
	//
	// break;
	// case XmlPullParser.START_TAG:
	// if("xml".equals(nodeName)==false){
	// //实例化student对象
	// xml.put(nodeName,parser.nextText());
	// }
	// break;
	// case XmlPullParser.END_TAG:
	// break;
	// }
	// event = parser.next();
	// }
	// return xml;
	// } catch (Exception e) {
	//// e.printStackTrace();
	// }
	// return null;
	// }

	public String genTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

}

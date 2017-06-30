package com.ehome.core.onlinepay;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.cloud.puhui.service.IMerchantService;
import com.ehome.cloud.puhui.service.IPhOrderService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.tenpay.HttpUtil;
import com.ehome.core.tenpay.XMLUtil;
import com.ehome.core.tenpay.ZjTenPayUtils;
import com.ehome.core.util.StringUtils;

@Controller
@RequestMapping("/zjtenpay")
public class ZjTenPay {

	@Resource(name = "phOrderService")
	private IPhOrderService phOrderService;

	@Autowired
	private IAppUserService iAppUserService;

	@Autowired
	private IMerchantService merchantService;

	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	@ResponseBody
	@RequestMapping(value = "/trade")
	public Object TenPayTrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map<Object, Object> resInfo = new HashMap<Object, Object>();
		int retcode = 20000;
		String retmsg = "未知错误";
		ZjTenPayUtils tenpay = new ZjTenPayUtils();
		String trade_no = "";
		if (null == request.getParameter("trade_no")||request.getParameter("trade_no").isEmpty()) {
			retcode = 20003;
			retmsg = "订单号为空";
		} else {
			trade_no = request.getParameter("trade_no");
			String entity = tenpay.GetTrade(trade_no);
			String buf = HttpUtil.sendPostUrl(ZjTenPayUtils.orderquery_url, entity, "UTF-8");
			String content = new String(buf.getBytes("GBK"), "UTF-8");
			Map<String, String> xml = null;
			try {
				xml = XMLUtil.doXMLParse(content);
				if (xml.get("return_code").equals("SUCCESS") && xml.get("result_code").equals("SUCCESS")) {
					retcode = 10000;
					retmsg = "操作成功!";
				} else {
					retmsg = xml.get("return_msg").toString();
				}
			} catch (JDOMException e) {
				retcode = 20001;
				retmsg = "操作失败!";
				e.printStackTrace();
			} catch (IOException e) {
				retcode = 20002;
				retmsg = "操作失败!";
				e.printStackTrace();
			}
			if (retcode == 10000) {
				resInfo.put("data", xml);
			}
		}
		resInfo.put("status", retcode);
		resInfo.put("message", retmsg);

		String strJson = JSON.toJSONString(resInfo);
		return strJson;
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public Object TenPayCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map<Object, Object> resInfo = new HashMap<Object, Object>();
		int retcode = 20000;
		String retmsg = "未知错误";
		ZjTenPayUtils tenpay = new ZjTenPayUtils();
		String stitle = request.getParameter("stitle");
		if (stitle.equals(new String(stitle.getBytes("ISO8859-1"), "ISO8859-1"))) {
			stitle = new String(stitle.getBytes("ISO8859-1"), "UTF-8");
		}
		String remarks = "";
		if (null != request.getParameter("remarks")) {
			remarks = request.getParameter("remarks");
			if (remarks.equals(new String(remarks.getBytes("ISO8859-1"), "ISO8859-1"))) {
				remarks = new String(remarks.getBytes("ISO8859-1"), "UTF-8");
			}
		}
		Map<String, String> sendparams = new HashMap<String, String>();
		ArrayList<String> attachdata = new ArrayList<String>();
		attachdata.add(request.getParameter("original_price"));
		attachdata.add(request.getParameter("discount"));
		attachdata.add(request.getParameter("handling_charge"));
		attachdata.add(request.getParameter("discount_money"));
		attachdata.add(request.getParameter("discount_type"));
		attachdata.add(request.getParameter("customer_id"));
		attachdata.add(request.getParameter("merchant_id"));
		attachdata.add(remarks);
		sendparams.put("body", stitle);
		sendparams.put("pay_money", request.getParameter("pay_money").toString());
		sendparams.put("TradeNo", getOutTradeNo());
		sendparams.put("uip", request.getRemoteAddr());
		sendparams.put("attachdata", StringUtils.join(attachdata, "|"));
		String entity = tenpay.GetProduct(sendparams,request);
		// System.out.println("[Vachon] entity:" + entity);
		String buf = HttpUtil.sendPostUrl(ZjTenPayUtils.unifiedorder_url, entity, "UTF-8");
		String content = new String(buf.getBytes("GBK"), "UTF-8");
		// System.out.println("[Vachon] orion:" + content);
		Map<String, String> xml = null;
		try {
			xml = XMLUtil.doXMLParse(content);
			if (xml.get("return_code").equals("SUCCESS") && xml.get("result_code").equals("SUCCESS")) {
				retcode = 10000;
				retmsg = "操作成功!";
			} else {
				retmsg = xml.get("return_msg").toString();
			}
		} catch (JDOMException e) {
			retcode = 20001;
			retmsg = "操作失败!";
			e.printStackTrace();
		} catch (IOException e) {
			retcode = 20002;
			retmsg = "操作失败!";
			e.printStackTrace();
		}
		if (retcode == 10000) {
			xml.remove("nonce_str");
			xml.remove("sign");
			try {
				xml = XMLUtil.doXMLParse(tenpay.GetReq(xml.get("prepay_id").toString()));
				xml.put("trade_no", sendparams.get("TradeNo"));
				resInfo.put("data", xml);
			} catch (JDOMException e) {
				retcode = 20001;
				retmsg = "操作失败!";
				e.printStackTrace();
			} catch (IOException e) {
				retcode = 20002;
				retmsg = "操作失败!";
				e.printStackTrace();
			}
		}
		resInfo.put("status", retcode);
		resInfo.put("message", retmsg);

		String strJson = JSON.toJSONString(resInfo);
		return strJson;
	}

	@ResponseBody
	@RequestMapping(value = "/recveive")
	public Object TenPayRecv(HttpServletRequest request, HttpServletResponse response) {
		String inputLine;
		String notityXml = "";
		try {

			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> xml = null;
		List<NameValuePair> parameters = null;
		try {
			xml = XMLUtil.doXMLParse(notityXml);
			parameters = XMLUtil.doXMLParseList(notityXml);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		ZjTenPayUtils tenpay = new ZjTenPayUtils();
		List<NameValuePair> recvParams = new LinkedList<NameValuePair>();
		String sign = xml.get("sign").toString();
		parameters.remove(new BasicNameValuePair("sign", sign));
		String md5sum = tenpay.genPackageSign(parameters);
		if (sign.equals(md5sum) && "SUCCESS".equals(xml.get("return_code"))
				&& "SUCCESS".equals(xml.get("result_code"))) {
			recvParams.add(new BasicNameValuePair("return_code", "SUCCESS"));
			recvParams.add(new BasicNameValuePair("return_msg", "OK"));
			String[] attachdata = StringUtils.split(xml.get("attach").toString(), "|");
			PhOrder row = new PhOrder();
			row.setOrderNum(xml.get("out_trade_no").toString());
			row.setPayTransactionNo(xml.get("transaction_id").toString());
			SimpleDateFormat dateconv = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				row.setPayTime(dateconv.parse(xml.get("time_end").toString()));
			} catch (ParseException e) {
				row.setPayTime(new Date());
				e.printStackTrace();
			}
			row.setPayWay(0);
			row.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(attachdata[0])));
			row.setDiscount(Float.valueOf(attachdata[1]));
			row.setPayMoney(BigDecimal.valueOf((double) Double.valueOf(xml.get("total_fee").toString()) / (double) 100));
			row.setHandlingCharge(BigDecimal.valueOf(Double.valueOf(attachdata[2])));
			row.setDiscountMoney(BigDecimal.valueOf(Double.valueOf(attachdata[3])));
			row.setDiscountType(Integer.valueOf(attachdata[4]));
			row.setCustomerId(Integer.valueOf(attachdata[5]));
			row.setMerchantId(Integer.valueOf(attachdata[6]));
			if(attachdata.length>7){
				row.setRemarks(attachdata[7]);
			}
			row.setIssettle(0);
			AppUserModel userinfo = iAppUserService.selectByKey(row.getCustomerId());
			row.setCustomerName(userinfo.getNickName());
			MerchantModel minfo = merchantService.selectByKey(row.getMerchantId());
			row.setMerchantAddr(minfo.getAddress());
			row.setMerchantName(minfo.getName());
			row.setCity(minfo.getCity());
			row.setCounty(minfo.getCounty());
			row.setProvince(minfo.getProvince());
			phOrderService.insertOrder(row);
		} else {
			recvParams.add(new BasicNameValuePair("return_code", "FAIL"));
			recvParams.add(new BasicNameValuePair("return_msg", "签名错误"));
		}
		if (recvParams.size() == 0) {
			recvParams.add(new BasicNameValuePair("return_code", "FAIL"));
			recvParams.add(new BasicNameValuePair("return_msg", ""));
		}
		return tenpay.toXml(recvParams);
	}
}

package com.ehome.core.onlinepay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.cloud.puhui.service.IMerchantService;
import com.ehome.cloud.puhui.service.IPhOrderService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.model.UserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.cloud.sys.service.impl.UserServiceImpl;
import com.ehome.core.tenpay.TenPayUtils;
import com.ehome.core.tenpay.XMLUtil;
import com.ehome.core.util.StringUtils;

@Controller
@RequestMapping("/zjalipay")
public class ZjAliPay {
//	public static final String APPID = "2017022305839284";
	public static final String APPID = "2017050907177528";
	public static final String CHARSET = "utf-8";
	public static final String APIURL = "https://openapi.alipay.com/gateway.do";
//	public static final String APIURL = "https://openapi.alipaydev.com/gateway.do";
	public static final String NOTIFY_URL = "/zjalipay/recveive.html";
	
//	public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDiyItPLuRcwdGyOV3FE6eVCuXQ9Zl/5gJuZH8XpA3tKS5F746nSf6x0AsLvXEftP/X/1sDiGe0jFTDNJK6GdVs54G1WZB4Fs8UmDDkdlx9P4WOo/4rOvRr1/k9v6Jm8nVSraHXdRXC4xTYbaCHahmSdpEc4vCGgrjQa6Yw8EeALMDjaS3WampNy9FDwum6606sh31BLfI5x6GBrc/eTKFkblJZ7z9EHWmXN+pJ5mXbM/eHedhLN3eYJWh/l2LUxzKpIANUnL89M+s8haBDEtWxmQiM+aASW7G9PJDkuj7QGNQC84MaxiYBbYjqyBxrY2Nr/SmGaXaIW+G51i5dfR2PAgMBAAECggEAQ/qzb6fxwjBDKHZzukia+l1K6GlGewU6g1bSlgaSOh4XBdW6YEEY/LoNJZbC2ytJabjc0mFIHOHX4q/Wr7xgsoVu5yqS2LyVlGnOdh1Yuh6ZbVmRLeo9LDqQNsdD6jIRoxfzJH+vZzzkmb+61/yg77tk4lDQorF6rtcl+RN9i1jOYtbCdHksr16qngsonoLIUzSC/ZybXyYXD/jja7fexKGGt5FWvbu5EbhSMI+V1qiUQBQD9oH3iouy2pmvKANait3B9fAhrrmqmUB68VQUrKRnFLzkA4BYb92BTQg5+bXEEPSN2tcOErs52xm5Ht05AVT9uTAhVChqv1IXUjKkSQKBgQD64y7aj4YFVJ7PoZfjKNB9mlnwOaeNiPQ8VFhpFaEbhms/uWUDvf0i+9sGZm1cPYj84NzXFk3tWdu8dRnGOOJSmQ2V5KXT/sGU/E3yzKA1sWI0BdFyV115gpPYxe/b8/SF0r4YtBiEc3d/mf+yTdnf/eqyZkAl197HrJTnJsSy0wKBgQDnZ53Gwd+YyrBFWRC7nnQEotPalxHuyWI93F7ow2N3bAlParukqNk0pNuK6Y+08sUYU9xtc2/4Zz3295NCil+SHqMXG+Klq/GzX6XhfHRzCI2fTmfuV0/ZOmmHO5vj75MqaHGVaYva5SL5RQ9n+87Dcg9MShXC8bNdiAMSRt3c1QKBgQCcxYlpmXWtrwrtw5QdpkthsSmyKtTAZGv5ptr4D0P05zHtR/QPOpELY0Wh4i9EDE+ldP2Kia14O0ZLpQDOFI2X/3u1xoK4zeA5MM+kgKk74/PxfhE62czXOPkZ49XWI8MQPHf9B8yqN1zcp2ZYDL2wcgJOKAa9nv7e1sDVECypcQKBgQDX+J8K1/tF8TZfIuc5FIQXp5iulPyJrq7gKYLzHxkAZnayBl2nKme+eHbmyWH0cfh94hcCmBDS+BifHSX1novgJnP0p3P7R+VHkmeRccSFcGAtZNIfYqIFjjzbmEjAjeL+W4hW+LEs1Vq05z2fwSaSxyt7+lEhJtBO5RhowHdlGQKBgFXOqyfJnOSSlLVgY/K0oh2q2nEF36Ji8CZPLMYup1C3Ed0oeSgnlE1P4A8weSKkMQGEnf2h0+JO2xU0ogU673h3BHTeAENtKzHV9fIiq+0ySip9ExZng9BAkJsxcfC7Fdrmgkga9O8TbagdAxGSg9EVsz9pn39tzYZfX6/N2hnE";
//	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxbJR6AOz86Bgz9T+2QXQ5bkQMfjudvIPmeccPLq9dNl4KK6GImmixkvuYLXz6I0uAj0sYkHqwdZJfcqW7/aYdQkWH5RheFdkCFvJ/HJysIGjo++O1bgH3+6TL/WBCitVd76uFCkY3274eZpYUcd8Smk1wdhNLAe0VLfzY5SLkK086IY/Mz7ZKjl5SxpZVEgiMFQPmrl65d/NCeG7gLOJN+sMDyqjgd26PT8RmQpTLD84EPQY1eYwz+wRTagxPXmvJZpZuFhzhCYZxjvROEcHMIFC09mGOMQrN2lwc/oE81lVzm3Ufx9y2PWNS1GQWcpz3eZ2UxuEEKLyuO0H+8XSUwIDAQAB";

	public static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCYb66LOVTG84vwXU3ceysjDsfTV7OUjjA/oOpJeFJgfG5TfnmZ0+PZSXgZ1CtC6EXQ90j+ItIv+ikW0/Fd8Jna2dxVHYgu68gHPwPbQ1oionCgELKEeMu9KwGpBMelKoC2XxQuzNDKI0uoRe7j3vdm73JIhpaAXUoSr5+PCdp6rY/g1ocmiWBDg7QdOBG0ke+dMGKMK7bocBNmVhceGJMcCvp6cUG1k7cab/WNYDldaZzIeiuKKY/+ZXJbFbwxb9QuTnSNWfqJIOnM9doC0Uu4UMlTQMQPeeoYxpcA7FgUlxUPGO1bVD4PuCHNB+tFM/UdbvXml+kyuipUiJ7+y65zAgMBAAECggEAAIyGaGHweDbwThFH8n9PDHVFKvGp5+MLMW926ITSZx6LlylhbrhgnauyDwXAkiEb/0CktqWRdDLGaxqgR+JR64PVU0kj8GdQ9RW5AdUgQferyCfHPMrikO/StbUasvGQPRfijriWF+RApX56+b8XzPsXwkMZz6SGW8LeJDm427A63GngHpyTtG93XhOHD7u85htTU9i51MZV+9iWg/X7ITKSqNjw6DpzLaLmNriLQULEGyD2aEEPGyvZdFvbujh4TVisoKscNJ+F5HZl16gPhBUAvKbDXR11RgI6O36wkekxzKccp2w9UF3V2W71F5BFe9JbGPR9WL2zR3j4aW+twQKBgQDyyYJKdUUd8gcZStrdLavB62tOLrl3pUP42YVy3wcXyWkWq6M8WWyIylneCVYRQH3Mkb1zlxewEQoDln2iiebh6rT3nP6CZ5H9zzstKYofCAAYwaxOgko49SsMBZVFT0ngmMmfQHt8tgb7xJmFiSq5JpDR4ZD2Jg0NZknNVNl/SQKBgQCgu2laiXXQaBSu1+bRiDOYKwSbxYYfQv24ucEQICgpxfAlhXmtT9ViSX5j3Cq5rkEcQphXLA46y8c6eWhHIM/4+McTWMb+7cjwWIhyNqvebqrrhRrzg53qH89ZYnm3ACIhGjPairDnvmlSN0jyL7WoVn/bASLTV3kF9qcUeh5z2wKBgQDEe8wT0iAjgfG4yFmj0ghEoe9vkGTMrVT7GZ7kPhaRa5C+ph0vA/fQJGLiTI/42kZ0viWiXE7xUHjYOnxmhE/z2WynT8ixZHxD+egcPpfnUSQy5No7lznWKqOx0Pri4q4ZM81IMnk9jlrMemj8JuwKd2mQUYptipHZnERyBg8W8QKBgQCcZgAbwsnK4pvNjfPhSuC5XpJ7aHYbkToFN2pu4Ht0OlB0ki5Vv+roWo8LaD6CstZUQ1Cs8gdems7fTSd0nq7/mkNLxgTMJmBxzhPm3t/Z7mGODtSswxMzr4Q5e6LnuM982lw+eNmuhg9Bmt32GwpuJM0AM1jJwMHYVas6N6HOJQKBgQDhd7PTozuoilCg6BoBtokrmHjh3P++3mfn6j5tz+rAC6ESCydegbBvXDdF5sxAz0U8+L2qHWXwoi+xVCG4XuPMw3jVGNRsqxgcEgRz2ncTJQR5U+wC/SYuE8JCyp7y57u0JHg/w5FC6hcwpi5xnHjQfgfx4IpP6NWMlvey8RVbAA==";
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyzhjHZc6fqs6KbqimlrCCWrpEtZdQNM/jHKlifmFJFfS9F11cQ814qPsT3jf8CkHUQRckjZ8yzM/M6qFw5L4rZ4frRv16ybIbbefGNgUzw+jYkvsG3B/l61tzR2yE8FJPpT7c8xcSmUb/iGSzSzLriZYSe8rkGwMHOrWw26NW9u7NdK8UnXhW7CQUB+T/yMtdA7FZjPS3TTyUIbh1GNOKQvYHURG2x2w9WdY3ATrdO4c6eBuPnVqr1Ks6VZWMAs6XnljKsxHwEp7MQ7asuGHuM4W2GUd2x9+dKht3i/6J6NimvvqAUtxoc3KkRDZLZf2AIBXbiLpkQXlGVdfrl/2kQIDAQAB";
	
	@Resource(name = "phOrderService")
	private IPhOrderService phOrderService;

	@Autowired
	private IAppUserService iAppUserService;

	@Autowired
	private IMerchantService merchantService;
	
//	public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCB0rxaOCudHNuWm+/1OBRZ68bxIMY28/9OQDFCGbr/3QAy4yIfF78+/tTqPO+ZMvlLo+pAWZieYlxrgUj8cnoW4eS4CzWM3/97A24P0j1Yl5oxDKxZggSoKpdPT9AzadqU7q0Is5EjXQPzgjHmQ10uVmpCWl+PndNqIRCWIUXmguD2rq8oLK0rmIRW/cO96I2RfLgqHH4vs6P7TeKd2uzbh4jaMyYh42AAUzcBtdgPOvKxVPaP6ES7rNeZ+svF/SJA3xCU3aMrxDmD7DKQC4Chouv8QitOGKk+JhheGZ4PezQ3e5f4GCykTHAkAJd0waVUtFD/1d/dLyLNa7SzQq0XAgMBAAECggEAHkClc8CzKSGYajhcaqBu3aV77qTci+HAFNQQpn6ofQdbDICxat11y5BGvW8FxoWCYoYICIbYcdAqCUNPZsSmfr6VNe4mxHVOa7QY6cbsVMC/Rb7WSuVpgGasa+cFdXWtYhdcazKtF7iNOymTxzzbcfjhOiUlL6pvocOiwdlvGJACNVSjfiyaQgALN1fRzHpt07YRSb9VH58O8vpzjeh/PsMIQWcLV5JlJwpGubSNlQjA0Ptu2UnEppz+vNoP04OBxU8T7XFIsU426qJ9uwsH8+kDOvsmlq07g5I3WirEZc0k3hIPmuU7daaqw6RekHweVFvG9mAT5Pi/G29n/Kw8wQKBgQC2aYwkOhYkpUIvgqshdFRfFDG5joXhkTtGmgI7dbdl64h1I0RcsSkWqB57WpJWMXaVmzzbOMIY04qZ7Dz7WbDOZ53XleebgMHXVk/FEc8FrhaOH0NH0hujGn44/TuJotTCTOMraK/aXWPNTqG+/frsRn354yrcc4RO/yJfiTn5QwKBgQC2Mhlo9IX26e+n+5BNMef02130BE+w4sq6JO0iyt7H2ud92UZGlJIcZX3bGZtFoMdZkhEi57NH7Xhg6pw12DGv1CUKYwwwH4/bZE0IjlDQQftIeWKiR8JAMRqdU7CktU6yojLQYgAElXTmP3WbGSDb6fgyEFjFX4gpKI/QESeFnQKBgCE2tLK/9No1UQjPlRNhWB970NYFknEQ/acMUc270Gn5af5SLpdmFqIueGZjhnO1S+9PtdQA0K6ujfUa47usiBn9GetwOxzAZAsuhxXN+VECcs7r+FcydZ/MKiF8UoPsJys9zrtPs31iNdLjN2ZMXoz5BOUkLOC5E+VCZWLZ3Ll7AoGAeOu3QZlIcB/DF1I264T71d7h9FoIKfXbZr+rQzQxt6LpkPTrV1yzvHT/mrLcAtI6fJAdH3V3330UL/6FvpbGI1RBYStro2TPLWTcW5lYuV+RTwCBHgNSQRhV+aTZz/clTBmqb/Ma3+/xrf7gJxf+XvZh65g90oEc9XQePpGkEWECgYAaMYdP9CrKXH0Kc0X0dcld6XLi8Sn11OGbd35wNX5xPLEmkawlnwjEXvzKh4x0hnbmaTZsQxxR277k0IOu9Jgpupx+wOD7M8Bz8C9cvOEUqZ1nTcr8mGyZkYBueeT48FEZpHu/EKl9w8eBFG7GW3YrRh3ulo2SqZtx0WsxIJBYcA==";
//	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoypUn5qoT4jpfXuU20GAlozkLm9b3rZf45nImvCgz9+o0tIQPA6dCRACZDPejh3Fo8Dx8W4yqgbwRe9jsJE4jqrC09NWXT5g8uzX2IeBrRVfCTMSria55DmRaylitCNnUVWW2PCmbsF3pGBff9SVj1Lqr7mtif8XGWSmv09V6SflRxrxVLsNvzuSXoKa0m6TB+CH29d8QxjerBbpRBV23PsR7O5bLQ8/hh0/7q9aQdiVq9U5/Xm+vMwY0rmrTjPF/ewWvJM2J36Chp066SL+Ctc+MnqOKXA6Tqjjqwq/KFqXRJI+L7WJj/185GwcKp00cu9BF0NGxr2xiOmJ9830tQIDAQAB";

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
	@RequestMapping(value = "/create")
	public Object AliPayCreate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//实例化客户端
		Map<Object, Object> resInfo = new HashMap<Object, Object>();
		int retcode = 20000;
		String retmsg = "未知错误";
		String stitle = request.getParameter("stitle");
		
		if (stitle.equals(new String(stitle.getBytes("ISO8859-1"), "ISO8859-1"))) {
			stitle = new String(stitle.getBytes("ISO8859-1"), "UTF-8");
		}
		String remarks = "";
		if(request.getParameter("ramarks")!=null){
			remarks = request.getParameter("remarks");
			if (remarks.equals(new String(remarks.getBytes("ISO8859-1"), "ISO8859-1"))) {
				remarks = new String(remarks.getBytes("ISO8859-1"), "UTF-8");
			}
		}
		ArrayList<String> attachdata = new ArrayList<String>();
		attachdata.add(request.getParameter("original_price"));
		attachdata.add(request.getParameter("discount"));
		attachdata.add(request.getParameter("handling_charge"));
		attachdata.add(request.getParameter("discount_money"));
		attachdata.add(request.getParameter("discount_type"));
		attachdata.add(request.getParameter("customer_id"));
		attachdata.add(request.getParameter("merchant_id"));
		attachdata.add(remarks);
		AlipayClient alipayClient = new DefaultAlipayClient(APIURL, APPID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest alipayrequest = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		Map<String,String> model = new HashMap<String, String>();
		model.put("subject", stitle);
		model.put("out_trade_no",getOutTradeNo());
//		model.put("timeout_express","30m");
		model.put("total_amount",request.getParameter("pay_money").toString());
		model.put("product_code","QUICK_MSECURITY_PAY");
		model.put("passback_params", URLEncoder.encode(StringUtils.join(attachdata,"|"),"UTF-8"));
//		alipayrequest.setBizModel(model);
		alipayrequest.setBizContent(JSON.toJSONString(model));
		String strBackUrl = "http://" + request.getServerName() + request.getContextPath()+NOTIFY_URL; 
		alipayrequest.setNotifyUrl(strBackUrl);
		System.out.println("回调地址:"+strBackUrl);
		String retval = "";
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse alipayresponse = alipayClient.sdkExecute(alipayrequest);
			retval = alipayresponse.getBody();
			retcode = 10000;
			retmsg = "操作成功";
			Map<String, String> rdata = new HashMap<String, String>();
			rdata.put("orderstr", retval);
			resInfo.put("data", rdata);
		} catch (AlipayApiException e) {
			retcode = 20001;
			retmsg = "操作失败!";
			e.printStackTrace();
		}
		resInfo.put("status", retcode);
		resInfo.put("message", retmsg);

		String strJson = JSON.toJSONString(resInfo);
		return strJson;
	}
	
//	@RequestMapping(value = "/recveive")
//	public void AliPayRecv(HttpServletRequest request, HttpServletResponse response) {
//		//获取支付宝POST过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//		Map requestParams = request.getParameterMap();
//		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//		    String name = (String) iter.next();
//		    String[] values = (String[]) requestParams.get(name);
//		    String valueStr = "";
//		    for (int i = 0; i < values.length; i++) {
//		        valueStr = (i == values.length - 1) ? valueStr + values[i]
//		                    : valueStr + values[i] + ",";
//		  }
//		  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//		  params.put(name, valueStr);
//		 }
//		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//		try {
//			boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
//		} catch (AlipayApiException e) {
//			e.printStackTrace();
//		}
//	}
	
	@ResponseBody
	@RequestMapping(value = "/recveive")
	public Object AliPayRecv(HttpServletRequest request, HttpServletResponse response) {
		String ret_msg = "fail";
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  }
		  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		  params.put(name, valueStr);
		 }
		System.out.println(JSON.toJSONString(requestParams));
		boolean flag = false;
		try {
			flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if (flag && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
			ret_msg = "success";
			String[] attachdata=null;
			try {
				attachdata = StringUtils.split(URLDecoder.decode(params.get("passback_params").toString(),"UTF-8"), "|");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			PhOrder row = new PhOrder();
			row.setOrderNum(params.get("out_trade_no").toString());
			row.setPayTransactionNo(params.get("trade_no").toString());
			SimpleDateFormat dateconv = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				row.setPayTime(dateconv.parse(params.get("gmt_payment").toString()));
			} catch (ParseException e) {
				row.setPayTime(new Date());
				e.printStackTrace();
			}
			row.setPayWay(1);
			row.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(attachdata[0])));
			row.setDiscount(Float.valueOf(attachdata[1]));
			row.setPayMoney(BigDecimal.valueOf(Double.valueOf(params.get("receipt_amount").toString())));
			row.setHandlingCharge(BigDecimal.valueOf(Double.valueOf( attachdata[2])));
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
		}
		return ret_msg;
	}
}

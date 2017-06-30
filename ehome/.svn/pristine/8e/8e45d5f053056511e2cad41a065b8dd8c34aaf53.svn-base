package com.ehome.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

/**
 * token解密  类型转换工具类
 * 
 * @author xianwejia
 *
 */
public class OldSystemChangeUtil {

	public static String o2s(Object o){
		if(o!= null){
			return o.toString();
		}else{
			return "";
		}
	}
	
	public static String authObj2s(Object o){
		if(o!= null){
			if (o.toString().startsWith("theToken:")) {
				try {
					return OldSystemTokenUtil.authToken(o.toString());
				} catch (Exception e) {
					return "";
				}
			}else{
				return o.toString();
			}
		}else{
			return "";
		}
	}
	
	public static Integer o2i(Object o){
		if(o!= null){
			return Integer.parseInt(o.toString());
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(TokenUtil.authToken("theToken:W+il5pvjsyM="));
		
//				System.out.println(TokenUtil.authToken("theToken:r/NUjzcEijs="));
//				System.out.println(TokenUtil.authToken("theToken:W+il5pvjsyM="));
//				System.out.println(TokenUtil.createToken("366"));
//				System.out.println(TokenUtil.authToken("theToken:W+il5pvjsyM="));
				
//				System.out.println(EncryptUtil.dencryptPwd("f9333566c8c301303cbfd786c5283e45fe30604fab4bf60dbf77be76d2b46d02"));
//				System.out.println(TokenUtil.createToken("2"));
//				System.out.println(TokenUtil.authToken("theToken:7G0bpQfcmbY="));
//				System.out.println(ChangeUtil.authObj2s("theToken:7G0bpQfcmbY="));
//				System.out.println(ChangeUtil.authObj2s("theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k=theToken:gj37U+EU29k="));
				
				
//				System.out.println(TokenUtil.createToken("{\"username\":\"郑水洪\",\"age\":18,\"time\":\"2016-10-10 09:17:20:200\",\"token\":\"2cff315bade6401d38de383f6c57be\",\"appId\":\"android\"}"));
//				String data = ChangeUtil.authObj2s(TokenUtil.createToken("{\"username\":\"郑水洪\",\"age\":18,\"time\":\"2016-10-10 09:17:20:200\",\"token\":\"2cff315bade6401d38de383f6c57be\",\"appId\":\"android\"}"));
//				
//				Map map= JSON.parseObject(data, Map.class);
//				Iterator it = map.entrySet().iterator() ;
//				while (it.hasNext()){
//					Map.Entry entry = (Map.Entry) it.next() ;
//					Object key = entry.getKey();
//					System.out.println(key.toString());
//					Object value = entry.getValue();
//				}
				
		
		
//				

				
//				String ssString =URLDecoder.decode(ss,"utf-8");
//				
//				System.out.println(ssString);
//				
//				System.err.println(ChangeUtil.authObj2s(ssString));
//				System.out.println(">>>"+ChangeUtil.authObj2s("theToken:rSaK6cggu6nIDdI/7C/2lGcBnapJV2p2ujs42YRwoIKVDDrg68RRTkQRVbf1A7d2n2DO6PlWhdv/AHvFFzUrslXGD0ZApKRYS81WIXo rsdvQfkjvBFh9bdNYkigAXkWcTRTM8AqlPr5ieOVHkcT7A1Ms9P2WjiklXNUNXczl7uZ1 tQqjwKttg3OQGigo3mDWrgxDKauGTsnLMx80g7V73E2eRYykE7KluhkYbXzS RNgFuvZwdWyRlDIjw/2xwE76CvM/5iB3cpUUil/GeBcb5DzUJFsAuxcjJMl45ySXYwUTGlTDqfAvs7uGez6jAD3juWEUzWa/62ek/TjT0KuXxzIuEo4lPYCNJB7l7WSc="));
				     // System.out.println(ChangeUtil.authObj2s("theToken:rSaK6cggu6nIDdI/7C/2lGcBnapJV2p2ujs42YRwoIKVDDrg68RRTkQRVbf1A7d2n2DO6PlWhdv/AHvFFzUrslXGD0ZApKRYS81WIXo+rsdvQfkjvBFh9bdNYkigAXkWcTRTM8AqlPr5ieOVHkcT7A1Ms9P2WjiklXNUNXczl7uZ1+tQqjwKttg3OQGigo3mDWrgxDKauGTsnLMx80g7V73E2eRYykE7KluhkYbXzS+RNgFuvZwdWyRlDIjw/2xwE76CvM/5iB3cpUUil/GeBcb5DzUJFsAuxcjJMl45ySXYwUTGlTDqfAvs7uGez6jAD3juWEUzWa/62ek/TjT0KuXxzIuEo4lPYCNJB7l7WSc="));
				
////				
//				String param = "userId=123&user_name=郑水洪&phone=123123&idcard=123123123&enterprise_id=12&start_time=2015-09-09 12:10:00&start_station_id=49&end_station_id=60";
//				String arr [] = param.split("&");
//				Map map2 = new HashMap();
//				for (int i = 0; i < 
//						arr.length; i++) {
//					String temp = arr[i];
//					String arrTemnp []  = temp.split("=");
//					map2.put(arrTemnp[0], arrTemnp[1]);
//				}
//				
//				String time = "2016-10-10 09:17:20:200";
//				String android="afe8d937dd8692737a9f9349aandroid";
////				String ios="afe8d937dd8692737a9f9349aa4bcios";
//				map2.put("time",time);
//				String token = DigestUtils.md5Hex(time+android);
////				String token = "123";
//				
////				map2.put("token", token);
//				map2.put("token", "6711d30830d55da54ccc53ae3dfc842c");
//				
//				map2.put("appId", "android");
////				System.out.println(map2);
////				System.out.println(JSON.toJSONString(map2));
//				String  ss =URLEncoder.encode(TokenUtil.createToken(JSON.toJSONString(map2)),"utf-8");
//				System.out.println(ss);
////				String ssString =URLDecoder.decode(ss,"utf-8");
////				
////				System.out.println(ssString);
////				
////				System.err.println(ChangeUtil.authObj2s(ssString));
////				System.out.println(">>>"+ChangeUtil.authObj2s("theToken:rSaK6cggu6nIDdI/7C/2lGcBnapJV2p2ujs42YRwoIKVDDrg68RRTkQRVbf1A7d2n2DO6PlWhdv/AHvFFzUrslXGD0ZApKRYS81WIXo rsdvQfkjvBFh9bdNYkigAXkWcTRTM8AqlPr5ieOVHkcT7A1Ms9P2WjiklXNUNXczl7uZ1 tQqjwKttg3OQGigo3mDWrgxDKauGTsnLMx80g7V73E2eRYykE7KluhkYbXzS RNgFuvZwdWyRlDIjw/2xwE76CvM/5iB3cpUUil/GeBcb5DzUJFsAuxcjJMl45ySXYwUTGlTDqfAvs7uGez6jAD3juWEUzWa/62ek/TjT0KuXxzIuEo4lPYCNJB7l7WSc="));
//				// System.out.println(ChangeUtil.authObj2s("theToken:rSaK6cggu6nIDdI/7C/2lGcBnapJV2p2ujs42YRwoIKVDDrg68RRTkQRVbf1A7d2n2DO6PlWhdv/AHvFFzUrslXGD0ZApKRYS81WIXo+rsdvQfkjvBFh9bdNYkigAXkWcTRTM8AqlPr5ieOVHkcT7A1Ms9P2WjiklXNUNXczl7uZ1+tQqjwKttg3OQGigo3mDWrgxDKauGTsnLMx80g7V73E2eRYykE7KluhkYbXzS+RNgFuvZwdWyRlDIjw/2xwE76CvM/5iB3cpUUil/GeBcb5DzUJFsAuxcjJMl45ySXYwUTGlTDqfAvs7uGez6jAD3juWEUzWa/62ek/TjT0KuXxzIuEo4lPYCNJB7l7WSc="));
//				
		
		
		
		
		String param = "pageNum=1&pageSize=10&enterprise_name=甘";
		String arr [] = param.split("&");
		Map map2 = new HashMap();
		for (int i = 0; i < 
				arr.length; i++) {
			String temp = arr[i];
			String arrTemnp []  = temp.split("=");
			map2.put(arrTemnp[0], arrTemnp[1]);
		}
		
		
		String time = "2016-10-10 09:17:20:200";
//		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//		Date date = new Date(System.currentTimeMillis());
//		time = f.format(date);
		
		System.out.println("当前时间:"+time);
		
		String android="afe8d937dd8692737a9f9349aandroid";
//		String ios="afe8d937dd8692737a9f9349aa4bcios";
		String tokenValue = android;
		System.out.println("密钥值:"+tokenValue);
		
		System.out.println("当前时间与密钥值"+time+tokenValue);
		
		String token = DigestUtils.md5Hex(time+tokenValue);
		
		System.out.println("当前时间与密钥值进行md5加密后的token值:"+token);
		map2.put("time",time);
		map2.put("token",token);
		map2.put("appId", "android");
		
		System.out.println("map字符串:"+map2);
		System.out.println("map转json字符串:"+JSON.toJSONString(map2));
		
		String createToken = OldSystemTokenUtil.createToken(JSON.toJSONString(map2));
		
		System.out.println("createToken长度:"+createToken.length());
		System.out.println("createToken:"+createToken);
		
		String  data =java.net.URLEncoder.encode(createToken,"utf-8");
		System.out.println("data:"+data);
		System.out.println("data长度:"+data.length());
		
		
//		System.out.println(authObj2s(java.net.URLDecoder.decode("theToken%3ArSaK6cggu6nIDdI%2F7C%2F2lN5nNEbDoI42PAJVhZWRi5zCCtZW9S8UBW46%2BhVHQWfkzf%2BCAQEjoOVu%0A82hAxLi95cn3RijOrDOcg1Lg95pCayJtPPjMzSgtem4daA1bZ1GPsnMKQiGk7PJTvUYvryqGDYMY%0AlazCfAXZ5I33c97s1RpJIqhY%2Fsds7VB%2BF4BUC670j3%2FBM8mPMkY%3D", "utf-8")));
		
		
	}
	
	/**
	 * 
	  * @param map appId 和业务参数
	  * @return
	  * @throws Exception 
	  *
	 */
	public static String createRquestData(Map<String ,Object> map) throws Exception{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date date = new Date(System.currentTimeMillis());
		String  time = f.format(date);
		String tokenValue = SpringPropertiesUtil.get(MapUtils.getString(map,"appId", null));
		String token = DigestUtils.md5Hex(time+tokenValue);
		map.put("time", time);
		map.put("token", token);
		String  createToken = OldSystemTokenUtil.createToken(JSON.toJSONString(map));
		return java.net.URLEncoder.encode(createToken,"utf-8");
	}

}

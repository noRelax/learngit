package com.ehome.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JsonUtil {

	/**
	 * 简单类型：Json转化为bean
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object jsonString2Object(String jsonString, Class<?> pojoCalss) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Object pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;

	}

	/**
	 * 复杂类型：Json转化为bean<br>
	 * 用法示例：<br>
	 * Map<String, Class> classMap = new HashMap<String, Class>();
	 * classMap.put("list", ChildBean.class); //指定复杂类型属性的映射关系，可以使多个放到map中<br>
	 * Person person=(Person)JsonUtil.jsonString2Object(str2, Person.class,
	 * classMap);<br>
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object jsonString2Object(String jsonString,
			Class<?> pojoCalss, Map<String, Class<?>> classMap) {
		JSONObject jobj = JSONObject.fromObject(jsonString);
		return JSONObject.toBean(jobj, pojoCalss, classMap);
	}

	/**
	 * 简单|复杂类型：将java对象转换成json字符串<br>
	 * 支持复杂类型：Java bean 中既有属性又有list
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String object2JsonString(Object javaObj) {
		JSONObject json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List<Object> jsonList2JavaList(String jsonString,
			Class<?> pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List<Object> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;
	}

	/**
	 * 从java对象集合表达式中得到一个json列表
	 * 
	 * @param list
	 * @return
	 */
	public static String javaList2JsonList(List<?> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/**
	 * 数组转换为JSON
	 * 
	 * @param array
	 * @return
	 */
	public static String javaArray2Json(Object[] array) {
		JSONArray jsonarray = JSONArray.fromObject(array);
		return jsonarray.toString();
	}

	/**
	 * Json数组转化为Java数组
	 * 
	 * @param jsonArray
	 * @param clas
	 * @return
	 */
	public static Object jsonArray2JavaArrray(String jsonArray, Class<?> clas) {
		JsonConfig jconfig = new JsonConfig();
		jconfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		jconfig.setRootClass(clas);
		JSONArray jarr = JSONArray.fromObject(jsonArray);
		return JSONSerializer.toJava(jarr, jconfig);
	}

	/**
	 * Map转换成json
	 * 
	 * @param map
	 * @return
	 */
	public static String javaMap2Json(Map<String, Object> map) {
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * json转化为map
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object javaMap2Json(String jsonString, Class<?> pojoCalss) {
		return jsonString2Object(jsonString, pojoCalss);// 调用方法jsonString2Object
	}

	/**
	 * 
	 * 转义
	 */
	public static String htmlToJsonStr(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
		// return content.replaceAll("\"","\\\\\"");
	}

	/**
	 * 
	 * @param varList
	 * @param page
	 * @return 将数据拼结成json串 格式：[]
	 * 
	 */
	public static String returnJsonBylist(List<Map<String, Object>> varList) {
		String resultStr = "";
		if (!CollectionUtils.isNotEmpty(varList)) {
			resultStr = "[]";
			return resultStr;
		}
		try {
			Map<String, Object> map = null;
			StringBuffer buff = new StringBuffer();
			int column = 0;
			int k = 0;
			buff.append("[");
			for (int i = 0; i < varList.size(); i++) {
				map = varList.get(i);
				column = map.size();
				k = 0;
				buff.append("{");
				for (Iterator<String> it = map.keySet().iterator(); it
						.hasNext();) {
					k++;
					String key = (String) it.next();
					Object value = (map.get(key) == null ? "" : map.get(key));
					if (k == column) {
						buff.append("\"" + key + "\":\""
								+ htmlToJsonStr(value.toString()) + "\"");
					} else {
						buff.append("\"" + key + "\":\""
								+ htmlToJsonStr(value.toString()) + "\",");
					}
				}
				buff.append("},");
			}
			if (k != 0)
				resultStr = buff.toString().substring(0,
						buff.toString().length() - 1);
			else
				resultStr = buff.toString();
			resultStr = resultStr + "]";
		} catch (Exception e) {
		}
		return resultStr;

	}

	/**
	 * 
	 * @param varList
	 * @param page
	 * @return PageData 将单个的PageData数据拼结成json串
	 */
	public static String returnJsonSign(Map<String, Object> map) {
		if (map == null) {
			String resultStr = "{\"msg\":0}";
			return resultStr;
		}
		String resultStr = "";
		try {
			StringBuffer buff = new StringBuffer();
			int k = 0;
			buff.append("{");
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				k++;
				String key = (String) it.next();
				Object value = (map.get(key) == null ? "" : map.get(key));
				buff.append("\"" + key + "\":\""
						+ htmlToJsonStr(value.toString()) + "\",");
			}
			String str = "";
			if (k != 0)
				str = buff.toString()
						.substring(0, buff.toString().length() - 1);
			else
				str = buff.toString();
			resultStr = str + "}";
			// System.out.println("buff--->>"+resultStr);
		} catch (Exception e) {
		}
		return resultStr;

	}
	
	/**
	 * 
	 * @param varList
	 * @param page
	 * @return PageData 将单个的PageData数据拼结成json串 不转义
	 */
	public static String returnJsonSignNoTransfer(Map<String, Object> map) {
		if (map == null) {
			String resultStr = "{\"msg\":0}";
			return resultStr;
		}
		String resultStr = "";
		try {
			StringBuffer buff = new StringBuffer();
			int k = 0;
			buff.append("{");
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				k++;
				String key = (String) it.next();
				Object value = (map.get(key) == null ? "" : map.get(key));
				buff.append("\"" + key + "\":\""
						+ value.toString() + "\",");
			}
			String str = "";
			if (k != 0)
				str = buff.toString()
						.substring(0, buff.toString().length() - 1);
			else
				str = buff.toString();
			resultStr = str + "}";
			// System.out.println("buff--->>"+resultStr);
		} catch (Exception e) {
		}
		return resultStr;

	}

	/**
	 * 接口返回简单json对象
	 * 
	 * @param status
	 * @param message
	 * @param map
	 * @return
	 *
	 */
	public static String appResposeSingeMapJson(int status, String message,
			Map<String, Object> map) {
		String returnStr = "";
		String resultStr = "{\"status\":\"" + status + "\",\"message\":\""
				+ message + "\"}";
		if (null == map || map.isEmpty()) {
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\",\"data\":{}}";
		} else {
			returnStr += returnJsonSign(map);
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\"" + ",\"data\":" + returnStr + "}";
		}

		return resultStr;
	}

	/**
	 * 接口返回简单json对象不转义
	 * 
	 * @param status
	 * @param message
	 * @param map
	 * @return
	 *
	 */
	public static String appResposeSingeMapJsonNoransfer(int status, String message,
			Map<String, Object> map) {
		String returnStr = "";
		String resultStr = "{\"status\":\"" + status + "\",\"message\":\""
				+ message + "\"}";
		if (null == map || map.isEmpty()) {
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\",\"data\":{}}";
		} else {
			returnStr += returnJsonSignNoTransfer(map);
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\"" + ",\"data\":" + returnStr + "}";
		}

		return resultStr;
	}
	
	/**
	 * 接口返回json 已弃用
	 * 
	 * @param status
	 * @param message
	 * @param list
	 * @return
	 *
	 */
	@Deprecated
	public static String appResposeListJson(int status, String message,
			List<Map<String, Object>> list) {
		String returnStr = "";
		String resultStr = "{\"status\":\"" + status + "\",\"message\":\""
				+ message + "\"}";
		if (!CollectionUtils.isNotEmpty(list)) {
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\",\"data\":[]}";
		} else {
			returnStr += returnJsonBylist(list);
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\"" + ",\"data\":" + returnStr + "}";
		}
		return resultStr;
	}

	/**
	 * 接口返回复杂json对象
	 * 
	 * @param status
	 * @param message
	 * @param dataList
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public static String appResposeMoreListJson(int status, String message,
			List<Map<String, Object>> dataList) {
		String returnStr = "";
		String resultStr = "{\"status\":\"" + status + "\",\"message\":\""
				+ message + "\"}";
		if (dataList == null || dataList.isEmpty()) {
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\",\"data\":{}}";
		} else {
			String eachData = "";
			for (Map<String, Object> dataMap : dataList) {
				String name = dataMap.get("name").toString();
				List<Map<String, Object>> listMap = (List<Map<String, Object>>) dataMap
						.get("list");
				eachData += "{\"" + name + "\":" + returnJsonBylist(listMap)
						+ "},";
				returnStr += eachData;
			}
			if (returnStr.endsWith(",")) {
				returnStr = returnStr.substring(0, returnStr.length() - 1);
			}
			resultStr = "{\"status\":\"" + status + "\",\"message\":\""
					+ message + "\"" + ",\"data\":" + returnStr + "}";
		}
		return resultStr;
	}
	
	/**
	 * 转化map
	  * @param map
	  * @return 
	  *
	 */
	public static Map<String, Object> exchangeMap(Map<String, Object> map){
		for (Iterator<String> it = map.keySet().iterator(); it
				.hasNext();) {
			String key = (String) it.next();
			Object value = (map.get(key) == null ? "" : map.get(key));
			map.put(key, value.toString());
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
}
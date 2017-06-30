package com.ehome.core.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;

/**
 * 字符串操作工具类
 * 
 * @Title:StringUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:09:51
 * @version:
 */
public final class StringUtils extends org.apache.commons.lang3.StringUtils {
	// Suppresses default constructor, ensuring non-instantiability.

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	private StringUtils() {
	}

	/**
	 * @Title: isPhone
	 * @Description: 判断是否固话或手机号码
	 * @param num
	 * @return
	 */
	public static boolean isPhone(String num) {
		return isMobi(num) || isTel(num);
	}

	/**
	 * @Description: 判断是否为3 ~ 11位长度的手机号码
	 * @param num
	 * @return
	 */
	public static boolean isMobi(String num) {
		return isNotBlank(num) && num.matches("^[0-9]{3,11}$");
	}

	/**
	 * 字符串序列是否为手机号码格式
	 * 
	 * @param cs
	 * @return
	 */
	public static final Boolean isMobiNumber(final CharSequence cs) {
		return StringUtils.isBlank(cs) ? Boolean.FALSE : Boolean.valueOf(cs
				.length() == 11
				&& cs.toString().matches("^1[34578]{1}[0-9]{9}$"));
	}

	/**
	 * @Title: isTel
	 * @Description: 判断是否电话号码
	 * @param num
	 * @return
	 */
	public static boolean isTel(String num) {
		return isNotBlank(num) && num.matches("^[0-9]{3,4}[\\-]{1}[0-9]{7,8}$");
	}

	/**
	 * @Title: isLoginName
	 * @Description: 判断是否为有效账户格式
	 * @param loginName
	 * @return
	 */
	public static boolean isLoginName(String loginName) {
		// return isNotBlank(loginName) &&
		// loginName.matches("^[0-9a-zA-Z@\\.\\-]{3,18}$");
		return isNotBlank(loginName) && loginName.matches("^[^\\s]+$");
	}

	/**
	 * @Title: isLoginPwd
	 * @Description: 判断是否为有效密码格式
	 * @param loginPwd
	 * @return
	 */
	public static boolean isLoginPwd(String loginPwd) {
		return isNotBlank(loginPwd)
				&& loginPwd.matches("^[\u0021-\u007E]{6,18}$");
	}

	/**
	 * @Title: lenBetween
	 * @Description: 字符串长度区间
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean lenBetween(String str, Integer min, Integer max) {
		return isNotBlank(str) && str.length() >= min && str.length() <= max;
	}

	/**
	 * 从 0 ~ 9 中获取验证码
	 * 
	 * @param len
	 *            验证码长度
	 * @return
	 */
	public static String getVerifyCode(int len) {
		return getVerifyCode("012356789", len);
	}

	/**
	 * 获取验证码
	 * 
	 * @param scope
	 *            验证码范围
	 * @param len
	 *            验证码长度
	 * @return
	 */
	public static String getVerifyCode(String scope, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(scope.charAt(RandomUtils.nextInt(0, scope.length())));
		}
		return sb.toString();
	}

	/**
	 * 获取非空白的字符串，如果 str 为 null 或 空白，返回 null
	 * 
	 * @param str
	 * @return isBlank(str) ? null : str;
	 */
	public static String getNotBlank(String str) {
		return defaultIfBlank(str, null);
	}

	/**
	 * 获取字符串的取值，如果 str 为 null 或空白，返回 blankRepVal
	 * 
	 * @param str
	 * @param blankRepVal
	 * @return
	 */
	public static String getNotBlank(String str, String blankRepVal) {
		return defaultIfBlank(str, blankRepVal);
	}

	/**
	 * 获取字符串的取值，如果为 null 或者空白，返回 ""
	 * 
	 * @param str
	 * @return
	 */
	public static String getNotNull(String str) {
		return defaultIfBlank(str, EMPTY);
	}

	/** 把一个可以迭代的字符串集合中的元素用指定的字符连接起来
	 * @param delimiter
	 * @param elements
	 * @return
	 */
	public static String join(CharSequence delimiter,
			Iterable<? extends CharSequence> elements) {
		return StringUtils.join(elements, delimiter);
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		//if (null == valStr)
		if (isBlank(valStr)) {
			return new String[] {};
		}
		return valStr.split(",");
	}

	/**
	 * 字符串转化为数字
	 * 
	 * @param valStr
	 * @return
	 */
	public static int String2Int(String valStr, int def) {
		//if (valStr == null) {
		if (isBlank(valStr)) {
			return def;
		}
		try {
			return Integer.parseInt(valStr);
		} catch (final NumberFormatException nfe) {
			return def;
		}
	}

	/**
	 * 将对象转化为字符串
	 * 
	 * @param o
	 * @return
	 */
	public static String obj2String(Object o, String def) {
		if (o == null) {
			return def;
		} else {
			return String.valueOf(o);
		}
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getString(Map<String, Object> map, String key,
			String def) {
		return obj2String(map.get(key), def);
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getString(Map<String, Object> map, String key) {
		return obj2String(map.get(key), "");
	}

	/**
	 * 获取map的value为int
	 * 
	 * @param map
	 * @param key
	 * @param def
	 * @return
	 */
	public static int getInt(Map<String, Object> map, String key, int def) {
		return String2Int(obj2String(map.get(key), ""), def);
	}

	/**
	 * 将驼峰命名转化为下划线命名 HelloWorldMyLove -> hello_world_my_love
	 * 
	 * @param s
	 * @return
	 */
	public static String toUnderlineName2(String s) {
		return s.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	public static String toUnderlineName(String s) {
		String SEPARATOR = "_";
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将下划线命名转化为驼峰命名 hello_world_my_love -> helloWorldMyLove
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		s = s.toLowerCase();
		Matcher matcher = linePattern.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String toCapitalizeCamelCase(String s) {
		//if (s == null) {
		if (isBlank(s)) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}

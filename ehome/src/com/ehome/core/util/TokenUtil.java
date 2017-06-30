package com.ehome.core.util;

import java.security.MessageDigest;

/**
 * token加密解密工具类
 * 
 * @author xianweijia
 *
 */
public class TokenUtil {

	private static String salt;

	private final static String default_salt = "afe8d937dd8692737a9f9349aa4bcfdc";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(generateToken("", 1l, "3"));
		Long l = System.currentTimeMillis();
		String token = generateToken("", 1l, "3");
		System.out.println(token);
		System.out.println(validateToken("", 1l, "3", token));
	}

	/**
	 * @return the salt
	 */
	public static String getSalt(String appid) {
		if (appid.equals(""))
			appid = "ehome";
		salt = SpringPropertiesUtil.get("token_key_" + appid);
		return salt;
	}

	/**
	 * 验证token
	 * 
	 * @param content
	 * @param timestamp
	 * @param appid
	 * @param validatetoken
	 *            待验证token
	 * @return
	 */
	public static boolean validateToken(String content, Long timestamp,
			String appid, String validatetoken) {
		if (timestamp == 0 || appid.equals("") || validatetoken.equals("")) {
			return false;
		}
		String salt = getSalt(appid);
		String token = "";
		//System.out.println(timestamp + salt + content);
		if (!salt.equals("")) {
			token = MD5(timestamp + salt + content);
		}
		if (validatetoken.equals(token)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证token
	 * 
	 * @param content
	 * @param timestamp
	 * @param appid
	 * @param validatetoken
	 *            待验证token
	 * @return
	 */
	public static boolean validateToken(String content, Long timestamp,
			String validatetoken) {
		if (timestamp == 0 || validatetoken.equals("")) {
			return false;
		}

		String salt = getSalt("");
		String token = "";
		if (!salt.equals("")) {
			//System.out.println(timestamp + salt + content);
			token = MD5(timestamp + salt + content);
		}

		if (validatetoken.equals(token)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取token
	 * 
	 * @param uniq
	 * @param timastamp
	 * @param appid
	 * @return
	 */
	public static String generateToken(String content, Long timastamp,
			String appid) {
		String salt = getSalt(appid);
		String token = "";
		if (!salt.equals("")) {
			token = MD5(timastamp + salt + content);
		}
		return token;
	}

	/**
	 * 获取token
	 * 
	 * @param uniq
	 * @param timastamp
	 * @param appid
	 * @return
	 */
	public static String generateToken(String content, Long timastamp) {
		String salt = getSalt("");
		String token = "";
		if (!salt.equals("")) {
			token = MD5(timastamp + salt + content);
		}
		return token;
	}

	/**
	 * 
	 * @Title: MD5
	 * @Description: 加密
	 * @param @param s
	 * @param @return 参数
	 * @return String 返回类型
	 */
	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			return byte2hex(mdInst.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

}

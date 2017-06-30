package com.ehome.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * token加密解密工具类
 *
 * @author xianweijia
 *
 */
public class OldSystemTokenUtil {

	private static String key = "afe8d937dd8692737a9f9349aa4bcfdc";// 至少8位的秘钥
	private final static String DES = "DES";
	private final static String KEY = "afe8d937dd8692737a9f9349aa4bcfdc";

	private final static String appid = "ehome";
	private final static String secret = "afe8d937dd8692737a9f9349aa4bcfdc";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String str = "1111111111111110";
		// String s = createToken(str);
		// System.out.println(s);
		// System.out.println(authToken("theToken:lac7rf9BBWI="));
		//

		// getToken FIXME 郑水洪
//		long time = System.currentTimeMillis();
//		time= 1476005076895L;
//		String uid = "10203";
//		String str1 = String.valueOf(time) + uid + secret;
//		System.out.println(str1);
//		System.out.println(getToken(str1));
//
//		String aString= "2016-10-10 09:17:20:200";
//		String bString="afe8d937dd8692737a9f9349aa4bcios";
//		String temp = aString+bString;
//		System.out.println("temp:"+temp);
//		System.out.println(getToken(temp));
//

//		System.out.println(ChangeUtil.authObj2s(TokenUtil.createToken("2016-10-10 09:17:20:200")));
//		System.out.println(getToken("2016-10-10 09:17:20:200afe8d937dd8692737a9f9349aandroid"));
//		System.out.println(getToken("2016-10-10 09:17:21:200afe8d937dd8692737a9f9349aandroid"));
//		System.out.println(getToken("11111").length());
//		System.out.println("6711d3830d55da54ccc53ae3dfc842c".length());

		//System.out.println(DigestUtils.md5Hex("2016-10-10 09:17:20:200afe8d937dd8692737a9f9349aandroid"));
		//System.out.println(DigestUtils.md5Hex("2016-10-10 09:17:21:200afe8d937dd8692737a9f9349aandroid"));
	}

	public static String getToken(String str,String appid) {

		// 根据MD5算法生成MessageDigest对象
		MessageDigest md5 = null;
		byte[] resultBytes = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] srcBytes = str.getBytes("utf8");
			// 使用srcBytes更新摘要
			md5.update(srcBytes);
			// 完成哈希计算，得到result
			resultBytes = md5.digest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		if(resultBytes!=null) {
		    for (byte b:resultBytes) {
	            String hexString = Integer.toHexString(b&0xff);
	            if (hexString.length() < 2) {
	                hexString = "0"+hexString;
	            }
	            sb.append(hexString);
	        }
		}

		return sb.toString();
	}




	/**
	 * 根据键值进行加密
	 *
	 * @param data
	 * @param key
	 *            加密键
	 * @return
	 */
	public static String createToken(String data,String aStr) throws Exception {

		if (key == null || key.equals("")) {
			key = KEY;
		}
		if (data == null || data.equals("")) {
			data = "";
		}
		byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
		System.out.println("字节:"+bt.length);
		String strs = "theToken:" + new BASE64Encoder().encode(bt);
		return strs;
	}

	 public static String createToken(String data)throws Exception {

         if (key==null || key.equals("")) {
             key=KEY;
         }
         if (data == null || data.equals("")) {
      	   data = "";
         }

		try {
			byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
			String strs = "theToken:"+ new BASE64Encoder().encode(bt);
	        return strs;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}

     }


	/**
	 * 根据键值进行解密
	 *
	 * @param data
	 * @param key
	 *            加密键
	 * @return
	 */
	public static String authToken(String data) throws Exception {
		if (data == null || data.equals("")) {
			return "";
		}

		if (data.startsWith("theToken:")) {
			data = data.replace("theToken:", "").trim();
		} else {
			return data;
		}

		if (key == null || key.equals("")) {
			key = KEY;
		}

		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt, "utf-8");
	}

	/**
	 * 根据键值进行加密
	 *
	 * @param data
	 * @param key
	 *            加密键
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	/**
	 * 根据键值进行解密
	 *
	 * @param data
	 * @param key
	 *            加密键
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}




    /**
     * MD5加密算法
     * @param str   需要转化为MD5码的字符串
     * @return  返回一个32位16进制的字符串
     */
    public static String toMd5(String str) {
        StringBuffer md5Code = new StringBuffer();
        try {
        //获取加密方式为md5的算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length() < 2) {
                    hexString = "0"+hexString;
                }
                md5Code = md5Code.append(b);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Code.toString();

    }

}

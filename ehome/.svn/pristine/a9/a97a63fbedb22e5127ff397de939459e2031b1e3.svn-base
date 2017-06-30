/**
 * @Project:ZGHome-Common
 * @FileName:RasUtil.java
 */
package com.ehome.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Title:RasUtil
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月20日
 * @version:
 */
public class RSA {

	private final static Logger logger = LoggerFactory.getLogger(RSA.class);
	// 通过命令行openssl 生成公钥和私钥，复制进来

	// app公钥
	// private static final String DEFAULT_PUBLIC_KEY =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZIrJjUdwCLu3yUfeQ61zz4ind98DUrGQJtPnGeZzWv4yUGTkhBYBSR/vmoWMkMEM4dpRs6q09devUabe8dRVwIH4lcUw2pXknICVxCEY2IrqnAVe8zLZ9plldN4nPeUSxTTue3fLzM6EHldh4QBzqM22M38sSbqyj2GPLb60KIQIDAQAB";
	// private static final String DEFAULT_PRIVATE_KEY =
	// "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANkismNR3AIu7fJR95DrXPPiKd33wNSsZAm0+cZ5nNa/jJQZOSEFgFJH++ahYyQwQzh2lGzqrT1169Rpt7x1FXAgfiVxTDaleScgJXEIRjYiuqcBV7zMtn2mWV03ic95RLFNO57d8vMzoQeV2HhAHOozbYzfyxJurKPYY8tvrQohAgMBAAECgYEAnNvj4rwQI8OjQZrVLWvaCpUniaKhOwIOgrSqfCb+lmRqOdK+4owG12oJH+MWq93xsah4MPaXj/7QDvMQOKSchr49oXqQJuF2LqzcaABdpklZkpGStvpshf0zVadl4kq6z2vpd+1MxdKa0eV1bDyfScnmWrFc9svaBpc9dfoa5QECQQD/I04glM7bzuIz7ZkKaRf054rNh52vBTG/VJpyVni5HoTJWlv0cJSAOrku97cMTX7p2FFcdszcJjFeHmd5OtHlAkEA2d6E/pqspUIEXZSP8QdjWo3VSSdeok1eXTwzs6l3iWZ8l6TaRy4sWhyoNCPhuKLULmqOLigEo04Gle8mPG7DjQJBANwjt06PdyjbifuqP4lnAl+yxOXv5NNV1QW0Vwrep4160rH1gDAaT1eQ06W94TgBScc1eYYhj0NIxQHjk/5bDS0CQQC8B5i+Z92Srrw3y4zDF34+2+P0w76f0qgbTWbNqveYpNPoyIT+I7JxfwQII+OeRyFagen9mdoO2rtCUWithWbNAkALsssPG5iB/VEtHRDU1V57wJ5zzw3KAxj/hMK8PA9gLCZAAiZvqozIE4ETMQWfCniEU4jQAtAV7E5wRGxEkg0O";

	// 后台公私钥
	private static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwWmOTh4FFNKe1WiKlQUXKEzhl0RRwwIW3Kxfhoif/TYZQjDO21uW10/zmFKKkvIVGtBOAr4jiBRpGrlWGin9Xp0FazfDIkXhn2QgIK1GTzBSG1cNdG8P0RrSDuEjE4n0dhDpBtxk6keNfa+S5kqCpT675RIDIqkJ/vzYOvhiy+QIDAQAB";
	private static final String DEFAULT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALBaY5OHgUU0p7VaIqVBRcoTOGXRFHDAhbcrF+GiJ/9NhlCMM7bW5bXT/OYUoqS8hUa0E4CviOIFGkauVYaKf1enQVrN8MiReGfZCAgrUZPMFIbVw10bw/RGtIO4SMTifR2EOkG3GTqR419r5LmSoKlPrvlEgMiqQn+/Ng6+GLL5AgMBAAECgYEAqjRS7sxVg4myY2G/FmfNB59NS1djtv7ZD1PUFL0AhXM5Mvxd3RXDVp5m81jvMLeSmg6q1ASWxC3Qkm8ntFZvV3oP2c/Ibbz6j5SmdyLzpaI4Ja9Ftp2zfCRGncwoUNnu8UoreEqKzQVsQaOy2S7vdhJFxti/ZB9YD/B+kQ4FwAECQQDoksbjX/fWYnmRQd/nvZ9lv1oIJd1a/mtBArn83mLDF7K0Qhm5DGIMRTkW1p9Y8K/8h8Y2kzxFWOwdYCbYB4IBAkEAwh3lNCtBGCwj3q59UPzPUaK859xByHgcaOgxClh5VDCkv8LJX957TbYUyIffxmroG9Gq1AKTHiow08DBqEtA+QJAfxVtDmOrRn9K1F+9nKDWD8AOzzL5kLZDHyQBQza1mstBQBR6f85PaUsOFfey3OOwFnqOidXMckxTwP2AXD1GAQJAK7TXepuSmIK3wNfA149Ujqj5p6rsFNsWanh9MjshdldNgO+2r6NEWAb7Pvt44xAHGtxmfj3mKhCN1d+8usvW4QJBAJ0wrlMg9g2EBSQsEv5uRpqLWiuPfUOz1T6S0DrsNAWF249edKpTAFRrtHtjQnZbP50OcwRN28O84MMyWfixB3Q=";

	private static RSA me;

	public static void main(String[] args) {
		// RSA rsaEncryptor = RSA.getRSA();
		try {
			String test = "a111111";
			String testRSAEnWith64 = RSA.encryptPublicKey(test);

			String testRSADeWith64 = RSA
					.decryptPrivateKey("nWjFHJ2lu5y0RhmGyRsy6pwGXtwAv+LdlFiVCzx7vIlqKCJymoyh59t+YOwdtYYWKyoA5AFHdZE9/JMSKnTprkgvS3vq79A1DiArb//6Nt6Knn+05jGZvObM0ACC5ag/e81GyTCXeAf3IesKvgG1t93U1HJrCVgUtDthLPmiN2E=");
			// String testRSADeWith64 = RSA.decryptPrivateKey(testRSAEnWith64);
			System.out.println("\nEncrypt: \n" + testRSAEnWith64);
			System.out.println("\nDecrypt: \n" + testRSADeWith64);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param publicKeyFilePath
	 * @param privateKeyFilePath
	 */
	public RSA(String publicKeyFilePath, String privateKeyFilePath)
			throws Exception {
		String public_key = getKeyFromFile(publicKeyFilePath);
		String private_key = getKeyFromFile(privateKeyFilePath);
		loadPublicKey(DEFAULT_PUBLIC_KEY);
		loadPrivateKey(DEFAULT_PRIVATE_KEY);
	}

	public RSA() {
		// 获取配置文件里面的公钥和私钥，
		// String publickey =
		// StringUtil.obj2String(PropertyPlaceholder.getProperty("publickey"),
		// DEFAULT_PUBLIC_KEY);
		// String privatekey =
		// StringUtil.obj2String(PropertyPlaceholder.getProperty("privatekey_app"),
		// DEFAULT_PRIVATE_KEY);

		String publickey = StringUtils.obj2String(
				SpringPropertiesUtil.get("publickey"), DEFAULT_PUBLIC_KEY);
		String privatekey = StringUtils
				.obj2String(SpringPropertiesUtil.get("privatekey_app"),
						DEFAULT_PRIVATE_KEY);
		try {
			this.loadPublicKey(publickey);
			this.loadPrivateKey(privatekey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

	}

	/**
	 * 单例
	 *
	 * @return
	 */
	public static RSA getRSA() {
		if (me == null) {
			me = new RSA();
		}
		return me;
	}

	public String getKeyFromFile(String filePath) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				filePath));

		String line = null;
		List<String> list = new ArrayList<String>();
		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}

		// remove the firt line and last line
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < list.size() - 1; i++) {
			stringBuilder.append(list.get(i)).append("\r");
		}

		String key = stringBuilder.toString();
		return key;
	}

	/**
	 * 加密-公钥
	 *
	 * @param content
	 * @return
	 */
	public static String encryptPublicKey(String content) {
		RSA rsa = RSA.getRSA();

		String encode = "";
		try {
			encode = rsa.encryptWithBase64(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return encode;
	}

	/**
	 * decrypt 解密-私钥
	 *
	 * @param content
	 * @return
	 */
	public static String decryptPrivateKey(String content) {
		RSA rsa = RSA.getRSA();
		String decode = "";
		try {
			decode = rsa.decryptWithBase64(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return decode;
	}

	//
	public String decryptWithBase64(String base64String) throws Exception {
		// sun.misc.BASE64Decoder
		byte[] binaryData = decrypt(getPrivateKey(),
				new BASE64Decoder().decodeBuffer(base64String));
		String string = new String(binaryData);
		return string;
	}

	//
	public String encryptWithBase64(String string) throws Exception {

		// sun.misc.BASE64Encoder
		byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
		String base64String = new BASE64Encoder().encodeBuffer(binaryData);
		return base64String;
	}

	/**
	 * 私钥
	 */
	public RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	public RSAPublicKey publicKey;

	/**
	 * 获取私钥
	 *
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 *
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对
	 */
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//避免null
		if (null != keyPairGen) {
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
			this.publicKey = (RSAPublicKey) keyPair.getPublic();
		}
	}

	/**
	 * 从文件中输入流中加载公钥
	 *
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 *
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (IOException e) {
			throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 *
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (IOException e) {
			throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 加密过程
	 *
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// , new
																// BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 *
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// , new
																// BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 字节数据转十六进制字符串
	 *
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

}
package com.ehome.cloud.app.sys.sdk;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	//选择测试账号
	private static final String geetest_id = "48a6ebac4ebc6642d68c217fca33eb4d";
	private static final String geetest_key = "4f1c085290bec5afdc54df73535fc361";
	
	//拖拉测试账号
//	private static final String geetest_id = "002bc30ff1eef93e912f45814945e752";
//	private static final String geetest_key = "4193a0e3247b82a26f563d595c447b1a";
	
	//陨石的账号（第三代）
//	private static final String geetest_id = "f0941ed42ffabe817838f0c6a4ba194c";
//	private static final String geetest_key = "425a0146d6c07552e490e8383ba32adc";
	
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}

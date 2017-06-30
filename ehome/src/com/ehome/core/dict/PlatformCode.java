package com.ehome.core.dict;

/**
 * PlatformCode
 * @author hsu
 *
 */
public enum PlatformCode {

	web(1, "web"), 
	wap(2, "wap"), 
	android(3, "android"), 
	ios(4, "IOS"), 
	weixin(5, "weixin"), 
	other(6, "other");

	private int code;
	private String msg;

	PlatformCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

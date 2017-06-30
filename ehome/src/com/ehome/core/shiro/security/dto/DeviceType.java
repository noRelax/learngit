package com.ehome.core.shiro.security.dto;

/**
 * 区分登录设备类型(PC和APP)
 * 
 * @Title:DeviceType
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月17日 上午10:04:58
 * @version:
 */
public enum DeviceType {

	PC("Pc"), APP("App"), THIRDPATH("ThirdPath");

	private String type;

	private DeviceType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}
}
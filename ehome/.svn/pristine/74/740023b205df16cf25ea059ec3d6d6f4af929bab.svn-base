package com.ehome.core.shiro.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义的用户/密码身份认证Token 继承自UsernamePasswordToken
 * 
 * @Title:CustomizedUsernamePasswordToken
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月17日 上午10:11:15
 * @version:
 */
public class CustomizedUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3474530369489487932L;

	/**
	 * 记录设备类型 用于区分APP或者PC端用户
	 */
	private String deviceType;

	public CustomizedUsernamePasswordToken(final String userName,
			final String password, String deviceType) {
		super(userName, password);
		this.setDeviceType(deviceType);
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}

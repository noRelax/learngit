package com.ehome.core.shiro.security.dto;

import java.io.Serializable;
import java.util.List;

import com.ehome.cloud.sys.dto.AppLoginInfoDto;
import com.ehome.cloud.sys.dto.LoginInfoDto;

/**
 * 会话缓存实体对象
 * 
 * @Title:CacheDTO
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月22日 上午10:02:40
 * @version:
 */
public class CacheDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7271744473569727027L;

	private Serializable session;// 用户会话信息

	private LoginInfoDto loginInfoDto;// 用户登录信息

	private AppLoginInfoDto appLoginInfoDto;// App用户登录信息

	private String token;// 用户cookie

	private Integer userId;// 登录用户ID

	private List<String> permissions;// 登录用户权限集合

	private List<String> roles;// 登录用户角色集合

	public AppLoginInfoDto getAppLoginInfoDto() {
		return appLoginInfoDto;
	}

	public void setAppLoginInfoDto(AppLoginInfoDto appLoginInfoDto) {
		this.appLoginInfoDto = appLoginInfoDto;
	}

	public LoginInfoDto getLoginInfoDto() {
		return loginInfoDto;
	}

	public void setLoginInfoDto(LoginInfoDto loginInfoDto) {
		this.loginInfoDto = loginInfoDto;
	}

	public Serializable getSession() {
		return session;
	}

	public void setSession(Serializable session) {
		this.session = session;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

package com.ehome.cloud.sys.model;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

/**
 * 角色用户关联中间表实体类
 * @Title:RoleUserModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 上午10:09:37
 * @version:
 */
public class RoleUserModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4079743336361371872L;

	private Integer roleId;

	private Integer userId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

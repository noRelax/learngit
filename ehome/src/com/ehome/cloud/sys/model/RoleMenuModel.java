package com.ehome.cloud.sys.model;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

/**
 * 角色菜单关联中间表实体类
 * @Title:RoleMenuModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 上午10:07:39
 * @version:
 */
public class RoleMenuModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4296622437877890139L;

	private Integer roleId;

	private Integer menuId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}

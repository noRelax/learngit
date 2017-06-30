package com.ehome.cloud.sys.model;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

/**
 * 用户菜单中间表实体类
 * @Title:UserMenuModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 上午10:11:21
 * @version:
 */
public class UserMenuModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108103885580346953L;

	private Integer userId;

	private Integer menuId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}

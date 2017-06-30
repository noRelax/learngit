package com.ehome.cloud.sys.model;

import java.io.Serializable;

/**
 * 用户分组实体类
 * @author tcr
 * @version
 * @date 2017年2月6日 下午16:34
 *
 */
public class UserGroupIdModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Integer id; //id
	private Integer userId;//用户ID
	private Integer groupId;//分组id

	public Integer getUserId() {
		return userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}

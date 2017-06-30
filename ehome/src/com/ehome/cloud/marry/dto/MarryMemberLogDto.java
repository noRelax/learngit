package com.ehome.cloud.marry.dto;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:MarryMemberLogDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 上午10:56:08
 * @version:
 */
public class MarryMemberLogDto extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8308984688487836812L;
	private Integer marryMemberId;
	private String event;
	private Integer uid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	private Integer createUser;

	public Integer getMarryMemberId() {
		return marryMemberId;
	}

	public void setMarryMemberId(Integer marryMemberId) {
		this.marryMemberId = marryMemberId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
}

package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:MarryMemberLog
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 上午10:53:23
 * @version:
 */
@Table(name = "t_marry_member_log")
public class MarryMemberLog extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3793160725419442657L;
	
	@Column(name = "marry_member_id")
	private Integer marryMemberId;
	@Column(name = "event")
	private String event;
	@Column(name = "uid")
	private Integer uid;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "create_user")
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

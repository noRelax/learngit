package com.ehome.cloud.ankang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:AnKangCupModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月16日 下午5:09:29
 * @version:
 */
@Table(name = "t_ankang_cup")
public class AnKangCupModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7314262487128231312L;

	@Column(name = "game_name")
	private String gameName;
	@Column(name = "begin_time")
	private Date beginTime;
	@Column(name = "end_time")
	private Date endTime;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "create_user")
	private Integer createUser;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

package com.ehome.cloud.ankang.dto;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:AnKangCupDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月16日 下午5:05:34
 * @version:
 */
public class AnKangCupDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3652370854988914759L;

	private String gameName;

	private Date beginTime;

	private Date endTime;

	private Date createTime;

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

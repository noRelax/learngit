package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: MarryLoveModel
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月20日 下午2:16:55
 * @version
 */
@Table(name = "t_marry_love")
public class MarryLoveModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1366758835148074287L;

	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "love_app_user_id")
	private Integer loveAppUserId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "statu")
	private Integer statu;
	@Column(name = "goldCoin")
	private Integer goldCoin;

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getLoveAppUserId() {
		return loveAppUserId;
	}

	public void setLoveAppUserId(Integer loveAppUserId) {
		this.loveAppUserId = loveAppUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatu() {
		return statu;
	}

	public void setStatu(Integer statu) {
		this.statu = statu;
	}

	public Integer getGoldCoin() {
		return goldCoin;
	}

	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
	}

}

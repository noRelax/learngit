package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: ThumpUpModel
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月19日 下午4:47:33
 * @version
 */
@Table(name = "t_marry_thumb")
public class MarryThumpUpModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885786073467529677L;

	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "photo_id")
	private Integer photoId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "source_device")
	private Integer sourceDevice;
	@Column(name = "goldCoin")
	private Integer goldCoin;

	public Integer getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getGoldCoin() {
		return goldCoin;
	}

	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
	}

}

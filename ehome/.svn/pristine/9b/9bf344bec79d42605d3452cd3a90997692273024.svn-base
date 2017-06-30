package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title: MarryCommentModel.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午4:50:22
 * @version
 */
@Table(name = "t_marry_comment")
public class MarryCommentModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6869884327283908742L;

	@Column(name = "comment")
	private String comment;
	@Column(name = "photo_id")
	private Integer photoId;
	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "public_time")
	private Date publicTime;
	@Column(name = "is_shielding")
	private Integer isShielding;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public Integer getIsShielding() {
		return isShielding;
	}

	public void setIsShielding(Integer isShielding) {
		this.isShielding = isShielding;
	}

	public Integer getGoldCoin() {
		return goldCoin;
	}

	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
	}
	
}

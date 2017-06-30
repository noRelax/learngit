package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:MarryPhoto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月17日 上午10:37:06
 * @version:
 */
@Table(name = "t_marry_photo")
public class MarryPhoto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347507388525538069L;

	@Column(name = "picture_id")
	private Integer pictureId;
	@Column(name = "picture_url")
	private String pictureUrl;
	@Column(name = "idea")
	private String idea;
	@Column(name = "location")
	private String location;
	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "thumb_up_num")
	private Integer thumbUpNum;
	@Column(name = "comment_num")
	private Integer commentNum;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "public_time")
	private Date publicTime;
	@Column(name = "is_recommended")
	private Integer isRecommended;
	@Column(name = "is_shielding")
	private Integer isShielding;
	@Column(name = "select_require")
	private Integer selectRequire;
	@Column(name = "source_device")
	private Integer sourceDevice;
	@Column(name = "blacklist")
	private Integer blacklist;
	@Column(name = "is_new")
	private Integer isNew;

	public Integer getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Integer blacklist) {
		this.blacklist = blacklist;
	}

	public Integer getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public Integer getSelectRequire() {
		return selectRequire;
	}

	public void setSelectRequire(Integer selectRequire) {
		this.selectRequire = selectRequire;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(Integer thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public Integer getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}

	public Integer getIsShielding() {
		return isShielding;
	}

	public void setIsShielding(Integer isShielding) {
		this.isShielding = isShielding;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

}

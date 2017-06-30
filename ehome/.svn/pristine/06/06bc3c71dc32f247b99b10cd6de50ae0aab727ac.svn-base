package com.ehome.cloud.marry.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:MarryPhotoLogDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 上午10:20:09
 * @version:
 */
public class MarryPhotoLogDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1972792300245201296L;
	private Integer photoId;
	private String event;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private Integer uid;
	private Integer isShielding;
	private Integer isRecommended;
	private String shieldingReason;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;

	public Integer getIsShielding() {
		return isShielding;
	}

	public void setIsShielding(Integer isShielding) {
		this.isShielding = isShielding;
	}

	public Integer getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getShieldingReason() {
		return shieldingReason;
	}

	public void setShieldingReason(String shieldingReason) {
		this.shieldingReason = shieldingReason;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
}

package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:MarryPhotoLog
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 上午10:20:05
 * @version:
 */
@Table(name = "t_marray_photo_log")
public class MarryPhotoLog extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034066306800592089L;
	
	@Column(name = "photo_id")
	private Integer photoId;
	@Column(name = "event")
	private String event;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "uid")
	private Integer uid;
	@Column(name = "is_shielding")
	private Integer isShielding;
	@Column(name = "is_recommended")
	private Integer isRecommended;
	@Column(name = "shielding_reason")
	private String shieldingReason;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
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

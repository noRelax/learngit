package com.ehome.cloud.marry.dto;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:MarryCommentDto
 * @Description:TODO
 * @author:tcr
 * @date:2017年4月17日 下午11:19
 * @version:
 */
public class MarryCommentDto extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8659771554540830405L;
	/**
	 * 婚恋评论DTO
	 */
	private Integer id;
	private String comment;// 评论
	private Integer photoId;// 照片id
	private Integer appUserId;// UID即APP用户ID
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publicTime;// 发布时间
	@CodeField(code = "CODE_IS_SHIEIDING", renderField = "isShieldingName")
	private Integer isShielding;// 是否屏蔽 0不是1是
	private String startPublictTime;
	private String endPublicTime;
	private String isShieldingName;
	private Integer sourceDevice;
	
	private String keyword;
	private Integer selectName;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getSelectName() {
		return selectName;
	}

	public void setSelectName(Integer selectName) {
		this.selectName = selectName;
	}

	public Integer getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getStartPublictTime() {
		return startPublictTime;
	}

	public void setStartPublictTime(String startPublictTime) {
		this.startPublictTime = startPublictTime;
	}

	public String getEndPublicTime() {
		return endPublicTime;
	}

	public void setEndPublicTime(String endPublicTime) {
		this.endPublicTime = endPublicTime;
	}

	public String getIsShieldingName() {
		return isShieldingName;
	}

	public void setIsShieldingName(String isShieldingName) {
		this.isShieldingName = isShieldingName;
	}

}

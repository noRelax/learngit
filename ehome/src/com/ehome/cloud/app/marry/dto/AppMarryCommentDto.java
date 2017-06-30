package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: AppMarryCommentDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午8:39:51
 * @version
 */
public class AppMarryCommentDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7766765801870375515L;

	private String comment;
	private Integer photoId;
	private Integer appUserId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publicTime;
	private Integer isShielding;
	private String commentatorPortrait; // 评论人头像路径
	private String commentatorNickName; // 评论人昵称
	private List<AppMarryReplyDto> list = new ArrayList<AppMarryReplyDto>();

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

	public String getCommentatorPortrait() {
		return commentatorPortrait;
	}

	public void setCommentatorPortrait(String commentatorPortrait) {
		this.commentatorPortrait = commentatorPortrait;
	}

	public String getCommentatorNickName() {
		return commentatorNickName;
	}

	public void setCommentatorNickName(String commentatorNickName) {
		this.commentatorNickName = commentatorNickName;
	}

	public List<AppMarryReplyDto> getList() {
		return list;
	}

	public void setList(List<AppMarryReplyDto> list) {
		this.list = list;
	}

}

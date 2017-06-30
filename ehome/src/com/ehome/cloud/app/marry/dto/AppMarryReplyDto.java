package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;
import java.util.Date;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: AppMarryReplyDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午8:43:42
 * @version
 */
public class AppMarryReplyDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2755217008552497230L;

	private Integer commentId;
	private Integer fromUserID; // 回复人ID
	private Integer toUserId; // 被回复人ID
	private String replyMsg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	private String fromUserPortrait; // 回复人头像url
	private String fromUserNickName; // 回复人昵称
	private String toUserNickName; // 被回复人昵称

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getFromUserID() {
		return fromUserID;
	}

	public void setFromUserID(Integer fromUserID) {
		this.fromUserID = fromUserID;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFromUserPortrait() {
		return fromUserPortrait;
	}

	public void setFromUserPortrait(String fromUserPortrait) {
		this.fromUserPortrait = fromUserPortrait;
	}

	public String getFromUserNickName() {
		return fromUserNickName;
	}

	public void setFromUserNickName(String fromUserNickName) {
		this.fromUserNickName = fromUserNickName;
	}

	public String getToUserNickName() {
		return toUserNickName;
	}

	public void setToUserNickName(String toUserNickName) {
		this.toUserNickName = toUserNickName;
	}

}

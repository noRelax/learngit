package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title: MarryReplyModel.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午4:59:41
 * @version
 */
@Table(name = "t_marry_reply")
public class MarryReplyModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9157280721084485528L;

	@Column(name = "coment_id")
	private Integer commentId;
	@Column(name = "from_user_id")
	private Integer fromUserID; // 回复人ID
	@Column(name = "to_user_id")
	private Integer toUserId; // 被回复人ID
	@Column(name = "reply_msg")
	private String replyMsg;
	@Column(name = "create_date")
	private Date createDate;

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

}

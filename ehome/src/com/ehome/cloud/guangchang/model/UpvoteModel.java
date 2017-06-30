/**
 * @Project:ZGHome
 * @FileName:UpvoteModel.java
 */
package com.ehome.cloud.guangchang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 点赞实体类
 * 
 * @Title:UpvoteModel
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
@Table(name = "t_gc_upvote")
public class UpvoteModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2588276440240443697L;

	private Integer discussionId;

	private Integer userId;

	private Date upvotetime;

	public Integer getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(Integer discussionId) {
		this.discussionId = discussionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getUpvotetime() {
		return upvotetime;
	}

	public void setUpvotetime(Date upvotetime) {
		this.upvotetime = upvotetime;
	}
}

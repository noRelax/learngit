package com.ehome.cloud.guangchang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 广场评论实体类
 * @Title:CommentModel
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
@Table(name = "t_gc_comment")
public class CommentModel extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8061079110712427165L;

	private Integer discussionId;
    
    private Integer userId;

    private int isanonymous;

    private String content;

    private Date commenttime;
    
    private Integer replyuserId;

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

    public int getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(int isanonymous) {
        this.isanonymous = isanonymous;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }
    
    public Integer getReplyuserId() {
    	return replyuserId;
    }
    
    public void setReplyuserId(Integer replyuserId) {
    	this.replyuserId = replyuserId;
    }
}
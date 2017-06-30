/**
 * @Project:ZGHome
 * @FileName:TopicModel.java
 */
package com.ehome.cloud.guangchang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 广场话题实体类
 * 
 * @Title:TopicModel
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
@Table(name = "t_gc_topic")
public class TopicModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5044858605434999650L;

	private Integer appuserId;// 后台发布话题选择appuser的默认用户

	private String title;

	private String description;

	private int isofficial;

	private String subtitle;

	private String topicrule;

	private Integer ordernum;

	private Integer discussnum;

	private Date createtime;

	private int status;

	private String pictureUrl;

	private Integer userId;

	public Integer getAppUserId() {
		return appuserId;
	}

	public void setAppUserId(Integer appuserId) {
		this.appuserId = appuserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsofficial() {
		return isofficial;
	}

	public void setIsofficial(int isofficial) {
		this.isofficial = isofficial;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTopicrule() {
		return topicrule;
	}

	public void setTopicrule(String topicrule) {
		this.topicrule = topicrule;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public Integer getDiscussnum() {
		return discussnum;
	}

	public void setDiscussnum(Integer discussnum) {
		this.discussnum = discussnum;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

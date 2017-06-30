package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: MarryMember.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月18日 下午5:30:05
 * @version
 */
@Table(name = "t_marry_member")
public class MarryMemberModel extends BaseEntity implements Serializable {

	public MarryMemberModel(Integer appUserId) {
		super();
		this.appUserId = appUserId;
	}

	public MarryMemberModel() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5718488894114935247L;

	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "member_id")
	private Integer memberId;
	@Column(name = "gold_coins")
	private Integer goldCoins;
	@Column(name = "photo_num")
	private Integer photoNum;
	@Column(name = "comment_num")
	private Integer commentNum;
	@Column(name = "thumb_up_num")
	private Integer thumbUpNum;
	@Column(name = "report_num")
	private Integer reportNum;
	@Column(name = "is_blacklist")
	private Integer isBackList;
	@Column(name = "hometown")
	private String hometown;
	@Column(name = "interest_number")
	private Integer interestNum;
	@Column(name = "dating_statu")
	private Integer datingStatu;
	@Column(name = "marry_demand")
	private Integer marryDemand;
	@Column(name = "weixing")
	private String weixing;
	@Column(name = "self_introduction")
	private String selfIntroduction;
	@Column(name = "star")
	private Integer star;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "active_time")
	private Date activeTime;
	@Column(name = "marital_status")
	private Integer maritalStatus;
	@Column(name = "dict_age")
	private Integer dictAge;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime;
	@Column(name = "nick_name")
    private String nickName;
	@Column(name = "portrait")
	private String portrait;
	@Column(name = "work_place")
	private String workPlace;
	@Column(name = "sex")
	private Integer sex;
	
	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getDictAge() {
		return dictAge;
	}

	public void setDictAge(Integer dictAge) {
		this.dictAge = dictAge;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getGoldCoins() {
		return goldCoins;
	}

	public void setGoldCoins(Integer goldCoins) {
		this.goldCoins = goldCoins;
	}

	public Integer getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(Integer thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}

	public Integer getReportNum() {
		return reportNum;
	}

	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}

	public Integer getIsBackList() {
		return isBackList;
	}

	public void setIsBackList(Integer isBackList) {
		this.isBackList = isBackList;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public Integer getInterestNum() {
		return interestNum;
	}

	public void setInterestNum(Integer interestNum) {
		this.interestNum = interestNum;
	}

	public Integer getDatingStatu() {
		return datingStatu;
	}

	public void setDatingStatu(Integer datingStatu) {
		this.datingStatu = datingStatu;
	}

	public Integer getMarryDemand() {
		return marryDemand;
	}

	public void setMarryDemand(Integer marryDemand) {
		this.marryDemand = marryDemand;
	}


	public String getSelfIntroduction() {
		return selfIntroduction;
	}

	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getWeixing() {
		return weixing;
	}

	public void setWeixing(String weixing) {
		this.weixing = weixing;
	}

}
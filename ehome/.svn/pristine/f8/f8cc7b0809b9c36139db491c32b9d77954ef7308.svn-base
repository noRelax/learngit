package com.ehome.cloud.marry.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class MarryMemberDto extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4587200311148451293L;
	/**
	 * 婚恋会员DTO
	 */
	private Integer id;
	private Integer appUserId;// UID即APP用户ID
	private Integer memberId;// 会员ID 关联会员表
	private Integer goldCoins;// 金币数
	private Integer photoNum;// 照片数
	private Integer commentNum;// 评论数
	private Integer thumbUpNum;// 点赞数
	private Integer reportNum;// 举报数
	@CodeField(code = "CODE_BLACKLIST", renderField = "isBlacklistName")
	private Integer isBlacklist;// 是否黑名单
	private String hometown;// 家乡
	private Integer interestNumber;// 兴趣个数
	@CodeField(code = "CODE_DATING_STATU", renderField = "datingStatuName")
	private Integer datingStatu;// 约会状态，参考数据字典
	@CodeField(code = "CODE_SELECT_REQUIRE", renderField = "marryDemandName")
	private Integer marryDemand;// 婚恋需求，参考数据字典
	private String weixing;// 微信号
	private String selfIntroduction;// 自我介绍
	@CodeField(code = "CODE_STAR", renderField = "starName")
	private Integer star;// 数字字典 星座
	private String nickName;// 昵称
	private String portrait;// 头像
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	@CodeField(code = "CODE_SEX", renderField = "sexName")
	private Integer sex;// '性别''''0''''是男,''''1''''是女',
	private String height;// 身高
	private String birthday;// 出生日期
	private String workPlace;// 工作地点（对应婚恋的工作生活在字段）
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;// 最后登录时间
	private String event;// 事件
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// 创建时间
	private String uid;// app用户ID
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date activeTime;
	@CodeField(code = "CODE_MARITAL_STATUS", renderField = "maritalStatusName")
	private Integer maritalStatus;
	@CodeField(code = "CODE_AGE", renderField = "dictAgeName")
	private Integer dictAge;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	@CodeField(code = "CODE_ANNUAL_INCOME", renderField = "annualIncomeName")
	private Integer annualIncome;
	private Integer createUserId;
	private Integer operatorType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date operatorTime;
	private Integer marryUserId;

	private String isBlacklistName;
	private String datingStatuName;
	private String marryDemandName;
	private String starName;
	private String maritalStatusName;
	private String dictAgeName;
	private String annualIncomeName;
	private String sexName;
	
	private String keyword;
	private Integer selectName;
	private List<Integer> marryMoldelList;
	private String fieldName;
	private String fieldSort;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldSort() {
		return fieldSort;
	}

	public void setFieldSort(String fieldSort) {
		this.fieldSort = fieldSort;
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(Integer isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public Integer getInterestNumber() {
		return interestNumber;
	}

	public void setInterestNumber(Integer interestNumber) {
		this.interestNumber = interestNumber;
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

	public String getWeixing() {
		return weixing;
	}

	public void setWeixing(String weixing) {
		this.weixing = weixing;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public Integer getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getIsBlacklistName() {
		return isBlacklistName;
	}

	public void setIsBlacklistName(String isBlacklistName) {
		this.isBlacklistName = isBlacklistName;
	}

	public String getDatingStatuName() {
		return datingStatuName;
	}

	public void setDatingStatuName(String datingStatuName) {
		this.datingStatuName = datingStatuName;
	}

	public String getMarryDemandName() {
		return marryDemandName;
	}

	public void setMarryDemandName(String marryDemandName) {
		this.marryDemandName = marryDemandName;
	}

	public String getStarName() {
		return starName;
	}

	public void setStarName(String starName) {
		this.starName = starName;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getDictAgeName() {
		return dictAgeName;
	}

	public void setDictAgeName(String dictAgeName) {
		this.dictAgeName = dictAgeName;
	}

	public String getAnnualIncomeName() {
		return annualIncomeName;
	}

	public void setAnnualIncomeName(String annualIncomeName) {
		this.annualIncomeName = annualIncomeName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public List<Integer> getMarryMoldelList() {
		return marryMoldelList;
	}

	public void setMarryMoldelList(List<Integer> marryMoldelList) {
		this.marryMoldelList = marryMoldelList;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public Integer getMarryUserId() {
		return marryUserId;
	}

	public void setMarryUserId(Integer marryUserId) {
		this.marryUserId = marryUserId;
	}

}

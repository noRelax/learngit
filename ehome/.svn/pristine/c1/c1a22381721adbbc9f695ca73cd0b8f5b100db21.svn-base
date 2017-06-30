package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;
import java.util.List;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

public class AppMarryMemberDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8977152846600430768L;

	private Integer appUserId;
	private Integer memberId;
	@CodeField(code = "CODE_STAR", renderField = "starName")
	private Integer star;
	private Integer interestNum; // 兴趣个数
	private String weixing;// 微信号

	@CodeField(code = "CODE_DATING_STATU", renderField = "datingStatuName")
	private Integer datingStatu;// 约会状态
	private String selfIntroduction; // 自我介绍
	private String goldCoins;

	private List<AppInterestDto> interestings; // 兴趣
	private String datingStatuName;
	private String starName;

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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
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

	public String getSelfIntroduction() {
		return selfIntroduction;
	}

	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}

	public List<AppInterestDto> getInterestings() {
		return interestings;
	}

	public void setInterestings(List<AppInterestDto> interestings) {
		this.interestings = interestings;
	}

	public String getDatingStatuName() {
		return datingStatuName;
	}

	public void setDatingStatuName(String datingStatuName) {
		this.datingStatuName = datingStatuName;
	}

	public String getStarName() {
		return starName;
	}

	public void setStarName(String starName) {
		this.starName = starName;
	}

	public String getGoldCoins() {
		return goldCoins;
	}

	public void setGoldCoins(String goldCoins) {
		this.goldCoins = goldCoins;
	}

	public String getWeixing() {
		return weixing;
	}

	public void setWeixing(String weixing) {
		this.weixing = weixing;
	}
	
}

package com.ehome.cloud.marry.dto;

import java.io.Serializable;

import com.ehome.core.annotation.CodeField;

/**
 * 
 * @Title:StatisticsDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月19日 下午5:20:43
 * @version:
 */
public class StatisticsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061885440241003510L;

	// 年收入
	@CodeField(code = "CODE_ANNUAL_INCOME", renderField = "annualIncomeName")
	private Integer annualIncome;
	// 用户数
	private Integer userNum;
	// 用户百分比
	private String userPercent;
	// 年龄
	@CodeField(code = "CODE_AGE", renderField = "dictAgeName")
	private Integer dictAge;

	private String dictAgeName;

	private String annualIncomeName;

	@CodeField(code = "CODE_SELECT_REQUIRE", renderField = "interestTypeName")
	private Integer interestType;

	private String interestTypeName;

	public Integer getInterestType() {
		return interestType;
	}

	public void setInterestType(Integer interestType) {
		this.interestType = interestType;
	}

	public String getInterestTypeName() {
		return interestTypeName;
	}

	public void setInterestTypeName(String interestTypeName) {
		this.interestTypeName = interestTypeName;
	}

	public Integer getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getAnnualIncomeName() {
		return annualIncomeName;
	}

	public void setAnnualIncomeName(String annualIncomeName) {
		this.annualIncomeName = annualIncomeName;
	}

	public String getDictAgeName() {
		return dictAgeName;
	}

	public void setDictAgeName(String dictAgeName) {
		this.dictAgeName = dictAgeName;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public String getUserPercent() {
		return userPercent;
	}

	public void setUserPercent(String userPercent) {
		this.userPercent = userPercent;
	}

	public Integer getDictAge() {
		return dictAge;
	}

	public void setDictAge(Integer dictAge) {
		this.dictAge = dictAge;
	}
}

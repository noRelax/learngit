package com.ehome.cloud.help.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: HelpRightExplainDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月28日 下午2:40:03
 * @version
 */
public class HelpRightExplainDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303878254985274448L;

	private String title;
	@CodeField(code = "CODE_EXPLAIN_TYPE", renderField = "explainTypeName")
	private Integer explainType;
	private Integer provinceId;
	private Integer cityId;
	private BigDecimal incomeLimit;
	private String rightOrHelpType;
	private String serviceButtunType;
	private String phone;
	private String content;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	private String createPerson;
	private String explainTypeName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExplainTypeName() {
		return explainTypeName;
	}

	public void setExplainTypeName(String explainTypeName) {
		this.explainTypeName = explainTypeName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public BigDecimal getIncomeLimit() {
		return incomeLimit;
	}

	public void setIncomeLimit(BigDecimal incomeLimit) {
		this.incomeLimit = incomeLimit;
	}

	public String getRightOrHelpType() {
		return rightOrHelpType;
	}

	public void setRightOrHelpType(String rightOrHelpType) {
		this.rightOrHelpType = rightOrHelpType;
	}

	public String getServiceButtunType() {
		return serviceButtunType;
	}

	public void setServiceButtunType(String serviceButtunType) {
		this.serviceButtunType = serviceButtunType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Integer getExplainType() {
		return explainType;
	}

	public void setExplainType(Integer explainType) {
		this.explainType = explainType;
	}

}

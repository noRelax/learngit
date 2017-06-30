package com.ehome.cloud.help.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 帮扶维权说明实体类
 * 
 * @Title: HelpRightExplainModel.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月28日 上午11:40:01
 * @version
 */
@Table(name = "t_help_right_explain")
public class HelpRightExplainModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152382654057542645L;

	@Column(name = "title")
	private String title;
	@Column(name = "explain_type")
	private Integer explainType;
	@Column(name = "province_id")
	private Integer provinceId;
	@Column(name = "city_id")
	private Integer cityId;
	@Column(name = "income_limit")
	private BigDecimal incomeLimit;
	@Column(name = "right_or_help_type")
	private String rightOrHelpType;
	@Column(name = "service_buttun_type")
	private String serviceButtunType;
	@Column(name = "phone")
	private String phone;
	@Column(name = "content")
	private String content;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "create_person")
	private String createPerson;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getExplainType() {
		return explainType;
	}

	public void setExplainType(Integer explainType) {
		this.explainType = explainType;
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

}

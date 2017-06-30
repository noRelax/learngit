package com.ehome.cloud.help.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: SignTableDto
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月23日 上午11:34:31
 * @version
 */
public class SignTableDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4374116927142614110L;

	private String SignTableName;
	private BigDecimal totalAmount;
	private Integer totalEmployer;
	@CodeField(code = "CODE_AMOUNT_SOURCE", renderField = "amountSourceName")
	private Integer amountSource;
	@CodeField(code = "CODE_HELP_TYPE", renderField = "helpProjectName")
	private Integer helpProject;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	private Integer cityId;

	private String amountSourceName;
	private String helpProjectName;

	public String getAmountSourceName() {
		return amountSourceName;
	}

	public void setAmountSourceName(String amountSourceName) {
		this.amountSourceName = amountSourceName;
	}

	public String getHelpProjectName() {
		return helpProjectName;
	}

	public void setHelpProjectName(String helpProjectName) {
		this.helpProjectName = helpProjectName;
	}

	public String getSignTableName() {
		return SignTableName;
	}

	public void setSignTableName(String signTableName) {
		SignTableName = signTableName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalEmployer() {
		return totalEmployer;
	}

	public void setTotalEmployer(Integer totalEmployer) {
		this.totalEmployer = totalEmployer;
	}

	public Integer getAmountSource() {
		return amountSource;
	}

	public void setAmountSource(Integer amountSource) {
		this.amountSource = amountSource;
	}

	public Integer getHelpProject() {
		return helpProject;
	}

	public void setHelpProject(Integer helpProject) {
		this.helpProject = helpProject;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}

package com.ehome.cloud.help.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title: SignTableModel.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月22日 上午11:38:18
 * @version
 */
@Table(name = "t_signledtable")
public class SignTableModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5396958489501415148L;
	
	@Column(name = "signtable_name")
	private String signTableName;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Column(name = "total_employer")
	private Integer totalEmployer;
	@Column(name = "amount_source")
	private Integer amountSource;
	@Column(name = "help_project")
	private Integer helpProject;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "city_id")
	private Integer cityId;

	

	public String getSignTableName() {
		return signTableName;
	}

	public void setSignTableName(String signTableName) {
		this.signTableName = signTableName;
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

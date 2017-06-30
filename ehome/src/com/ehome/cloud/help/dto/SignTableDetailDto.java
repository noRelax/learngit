package com.ehome.cloud.help.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

/**
 * @Title: SignTableDetailDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月23日 下午9:13:07
 * @version
 */
public class SignTableDetailDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1249691749604676485L;

	private String name;
	@CodeField(code = "CODE_SEX", renderField = "sexName")
	private Integer sex;
	private Integer age;
	private String idCard;
	private String cardNum;
	private String companyOrAddress;
	private BigDecimal amountNum;
	@CodeField(code = "CODE_AMOUNT_SOURCE", renderField = "amountSourceName")
	private Integer amountSource;
	private String cellphone;
	@CodeField(code = "CODE_HELP_TYPE", renderField = "helpProjectName")
	private Integer helpProject;
	private String employerNum;
	private String documentNum;
	private String remark;

	private String sexName;
	private String helpProjectName;
	private String amountSourceName;

	private Date birthday;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
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

	public String getHelpProjectName() {
		return helpProjectName;
	}

	public void setHelpProjectName(String helpProjectName) {
		this.helpProjectName = helpProjectName;
	}

	public String getAmountSourceName() {
		return amountSourceName;
	}

	public void setAmountSourceName(String amountSourceName) {
		this.amountSourceName = amountSourceName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCompanyOrAddress() {
		return companyOrAddress;
	}

	public void setCompanyOrAddress(String companyOrAddress) {
		this.companyOrAddress = companyOrAddress;
	}

	public BigDecimal getAmountNum() {
		return amountNum;
	}

	public void setAmountNum(BigDecimal amountNum) {
		this.amountNum = amountNum;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmployerNum() {
		return employerNum;
	}

	public void setEmployerNum(String employerNum) {
		this.employerNum = employerNum;
	}

	public String getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

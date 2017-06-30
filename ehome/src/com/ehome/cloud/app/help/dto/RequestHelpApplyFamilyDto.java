package com.ehome.cloud.app.help.dto;

import java.io.Serializable;

/**
 * App接口请求参数对象
 * 
 * @Title:RequestHelpApplyFamilyDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月24日 上午10:00:51
 * @version:
 */
public class RequestHelpApplyFamilyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -472315411558590062L;
	private Integer helpApplyId;
	private Integer memberId;
	private String name;
	private String familyRelationship;
	private String sex;
	private String political;
	private String idCard;
	private String health;
	private String monthlyIncome;
	private String identity;
	private String medicalInsurance;
	private String unitSchool;

	public Integer getHelpApplyId() {
		return helpApplyId;
	}

	public void setHelpApplyId(Integer helpApplyId) {
		this.helpApplyId = helpApplyId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyRelationship() {
		return familyRelationship;
	}

	public void setFamilyRelationship(String familyRelationship) {
		this.familyRelationship = familyRelationship;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(String medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	public String getUnitSchool() {
		return unitSchool;
	}

	public void setUnitSchool(String unitSchool) {
		this.unitSchool = unitSchool;
	}

}

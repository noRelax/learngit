package com.ehome.cloud.help.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:MemberHelpModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月21日 下午3:49:04
 * @version:
 */
@Table(name = "t_member_help")
public class MemberHelpModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5678868388205665807L;

	@Column(name = "help_apply_id")
	private Integer helpApplyId;
	@Column(name = "member_id")
	private Integer memberId;
	@Column(name = "reasons")
	private String reasons;
	@Column(name = "income_avg")
	private BigDecimal incomeAvg;
	@Column(name = "help_type")
	private Integer helpType;
	@Column(name = "family_num")
	private Integer familyNum;
	@Column(name = "card_type")
	private Integer cardType;
	@Column(name = "brithday")
	private Date brithday;
	@Column(name = "difficult_type")
	private Integer difficultType;
	@Column(name = "health")
	private Integer health;
	@Column(name = "disability_type")
	private Integer disabilityType;
	@Column(name = "work_status")
	private Integer workStatus;
	@Column(name = "model_type")
	private Integer modelType;
	@Column(name = "housing_type")
	private Integer housingType;
	@Column(name = "housing_area")
	private String housingArea;
	@Column(name = "phone")
	private String phone;
	@Column(name = "concat_type")
	private String concatType;
	@Column(name = "postcode")
	private String postcode;
	@Column(name = "work_year")
	private Date workYear;
	@Column(name = "industry")
	private Integer industry;
	@Column(name = "company")
	private String company;
	@Column(name = "unit_properties")
	private Integer unitProperties;
	@Column(name = "company_status")
	private Integer companyStatus;
	@Column(name = "is_single_parent")
	private Integer isSingleParent;
	@Column(name = "monthly_income")
	private BigDecimal monthlyIncome;
	@Column(name = "family_salary_income")
	private BigDecimal familySalaryIncome;
	@Column(name = "income_total")
	private BigDecimal incomeTotal;
	@Column(name = "avg_monthly_income")
	private BigDecimal avgMonthlyIncome;
	@Column(name = "medical_insurance")
	private Integer medicalInsurance;
	@Column(name = "is_has_self_save")
	private Integer isHasSelfSave;
	@Column(name = "is_zero_job")
	private Integer isZeroJob;
	@Column(name = "poverty_causes")
	private String povertyCauses;
	@Column(name = "open_bank")
	private String openBank;
	@Column(name = "branch_bank")
	private String branchBank;
	@Column(name = "card_num")
	private String cardNum;
	@Column(name = "create_time")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public BigDecimal getIncomeAvg() {
		return incomeAvg;
	}

	public void setIncomeAvg(BigDecimal incomeAvg) {
		this.incomeAvg = incomeAvg;
	}

	public Integer getHelpType() {
		return helpType;
	}

	public void setHelpType(Integer helpType) {
		this.helpType = helpType;
	}

	public Integer getFamilyNum() {
		return familyNum;
	}

	public void setFamilyNum(Integer familyNum) {
		this.familyNum = familyNum;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public Integer getDifficultType() {
		return difficultType;
	}

	public void setDifficultType(Integer difficultType) {
		this.difficultType = difficultType;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public Integer getDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(Integer disabilityType) {
		this.disabilityType = disabilityType;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}

	public Integer getHousingType() {
		return housingType;
	}

	public void setHousingType(Integer housingType) {
		this.housingType = housingType;
	}

	public String getHousingArea() {
		return housingArea;
	}

	public void setHousingArea(String housingArea) {
		this.housingArea = housingArea;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getConcatType() {
		return concatType;
	}

	public void setConcatType(String concatType) {
		this.concatType = concatType;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Date getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Date workYear) {
		this.workYear = workYear;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getUnitProperties() {
		return unitProperties;
	}

	public void setUnitProperties(Integer unitProperties) {
		this.unitProperties = unitProperties;
	}

	public Integer getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(Integer companyStatus) {
		this.companyStatus = companyStatus;
	}

	public Integer getIsSingleParent() {
		return isSingleParent;
	}

	public void setIsSingleParent(Integer isSingleParent) {
		this.isSingleParent = isSingleParent;
	}

	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public BigDecimal getFamilySalaryIncome() {
		return familySalaryIncome;
	}

	public void setFamilySalaryIncome(BigDecimal familySalaryIncome) {
		this.familySalaryIncome = familySalaryIncome;
	}

	public BigDecimal getIncomeTotal() {
		return incomeTotal;
	}

	public void setIncomeTotal(BigDecimal incomeTotal) {
		this.incomeTotal = incomeTotal;
	}

	public BigDecimal getAvgMonthlyIncome() {
		return avgMonthlyIncome;
	}

	public void setAvgMonthlyIncome(BigDecimal avgMonthlyIncome) {
		this.avgMonthlyIncome = avgMonthlyIncome;
	}

	public Integer getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(Integer medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	public Integer getIsHasSelfSave() {
		return isHasSelfSave;
	}

	public void setIsHasSelfSave(Integer isHasSelfSave) {
		this.isHasSelfSave = isHasSelfSave;
	}

	public Integer getIsZeroJob() {
		return isZeroJob;
	}

	public void setIsZeroJob(Integer isZeroJob) {
		this.isZeroJob = isZeroJob;
	}

	public String getPovertyCauses() {
		return povertyCauses;
	}

	public void setPovertyCauses(String povertyCauses) {
		this.povertyCauses = povertyCauses;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

}

package com.ehome.cloud.help.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
 * @Title:MemberHelpDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月25日 下午4:19:17
 * @version:
 */
public class MemberHelpDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2697686743119608590L;
	
	private Integer helpApplyId;
	private Integer memberId;
	private String reasons;
	private BigDecimal incomeAvg;
	@CodeField(code = "CODE_HELP_TYPE", renderField = "helpTypeName")
	private Integer helpType;
	private Integer familyNum;
	@CodeField(code = "CODE_CARD_TYPE", renderField = "cardTypeName")
	private Integer cardType;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date brithday;
	@CodeField(code = "CODE_DIFFICULT_TYPE", renderField = "difficultTypeName")
	private Integer difficultType;
	@CodeField(code = "CODE_HEALTH_STATUS", renderField = "healthName")
	private Integer health;
	@CodeField(code = "CODE_DISABILITY_TYPE", renderField = "disabilityTypeName")
	private Integer disabilityType;
	@CodeField(code = "CODE_WORK_STATUS", renderField = "workStatusName")
	private Integer workStatus;
	@CodeField(code = "CODE_WORKER_MODEL_TYPE", renderField = "modelTypeName")
	private Integer modelType;
	@CodeField(code = "CODE_HOUSING_TYPE", renderField = "housingTypeName")
	private Integer housingType;
	private String housingArea;
	private String phone;
	private String concatType;
	private String postcode;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date workYear;
	@CodeField(code = "CODE_INDUSTRY_INVOLVED", renderField = "industryName")
	private Integer industry;
	private String company;
	@CodeField(code = "CODE_UNIT_NATURE", renderField = "unitPropertiesName")
	private Integer unitProperties;
	@CodeField(code = "CODE_COMPANY_CONDITION", renderField = "companyStatusName")
	private Integer companyStatus;
	@CodeField(code = "CODE_SINGLE_PARENT", renderField = "isSingleParentName")
	private Integer isSingleParent;
	private BigDecimal monthlyIncome;
	private BigDecimal familySalaryIncome;
	private BigDecimal incomeTotal;
	private BigDecimal avgMonthlyIncome;
	@CodeField(code = "CODE_SURANCE_STATUS", renderField = "medicalInsuranceName")
	private Integer medicalInsurance;
	@CodeField(code = "CODE_HELP_ONESELF_ABILITY", renderField = "isHasSelfSaveName")
	private Integer isHasSelfSave;
	@CodeField(code = "CODE_ZERO_WORK_FAMILY", renderField = "isZeroJobName")
	private Integer isZeroJob;
	//@CodeField(code = "CODE_STUCK_REASON", renderField = "povertyCausesName")
	private String povertyCauses;
	private String openBank;
	private String branchBank;
	private String cardNum;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	
	private String helpTypeName;
	private String cardTypeName;
	private String difficultTypeName;
	private String healthName;
	private String disabilityTypeName;
	private String workStatusName;
	private String modelTypeName;
	private String housingTypeName;
	private String industryName;
	private String unitPropertiesName;
	private String companyStatusName;
	private String isSingleParentName;
	private String medicalInsuranceName;
	private String isHasSelfSaveName;
	private String isZeroJobName;
	private String povertyCausesName;
	private String brithdayName;
	private String workYearName;
	
	public String getBrithdayName() {
		return brithdayName;
	}
	public void setBrithdayName(String brithdayName) {
		this.brithdayName = brithdayName;
	}
	public String getWorkYearName() {
		return workYearName;
	}
	public void setWorkYearName(String workYearName) {
		this.workYearName = workYearName;
	}
	public String getHelpTypeName() {
		return helpTypeName;
	}
	public void setHelpTypeName(String helpTypeName) {
		this.helpTypeName = helpTypeName;
	}
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	public String getDifficultTypeName() {
		return difficultTypeName;
	}
	public void setDifficultTypeName(String difficultTypeName) {
		this.difficultTypeName = difficultTypeName;
	}
	public String getHealthName() {
		return healthName;
	}
	public void setHealthName(String healthName) {
		this.healthName = healthName;
	}
	public String getDisabilityTypeName() {
		return disabilityTypeName;
	}
	public void setDisabilityTypeName(String disabilityTypeName) {
		this.disabilityTypeName = disabilityTypeName;
	}
	public String getWorkStatusName() {
		return workStatusName;
	}
	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}
	public String getModelTypeName() {
		return modelTypeName;
	}
	public void setModelTypeName(String modelTypeName) {
		this.modelTypeName = modelTypeName;
	}
	public String getHousingTypeName() {
		return housingTypeName;
	}
	public void setHousingTypeName(String housingTypeName) {
		this.housingTypeName = housingTypeName;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getUnitPropertiesName() {
		return unitPropertiesName;
	}
	public void setUnitPropertiesName(String unitPropertiesName) {
		this.unitPropertiesName = unitPropertiesName;
	}
	public String getCompanyStatusName() {
		return companyStatusName;
	}
	public void setCompanyStatusName(String companyStatusName) {
		this.companyStatusName = companyStatusName;
	}
	public String getIsSingleParentName() {
		return isSingleParentName;
	}
	public void setIsSingleParentName(String isSingleParentName) {
		this.isSingleParentName = isSingleParentName;
	}
	public String getMedicalInsuranceName() {
		return medicalInsuranceName;
	}
	public void setMedicalInsuranceName(String medicalInsuranceName) {
		this.medicalInsuranceName = medicalInsuranceName;
	}
	public String getIsHasSelfSaveName() {
		return isHasSelfSaveName;
	}
	public void setIsHasSelfSaveName(String isHasSelfSaveName) {
		this.isHasSelfSaveName = isHasSelfSaveName;
	}
	public String getIsZeroJobName() {
		return isZeroJobName;
	}
	public void setIsZeroJobName(String isZeroJobName) {
		this.isZeroJobName = isZeroJobName;
	}
	public String getPovertyCausesName() {
		return povertyCausesName;
	}
	public void setPovertyCausesName(String povertyCausesName) {
		this.povertyCausesName = povertyCausesName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}

package com.ehome.cloud.help.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:HelpApplyModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月21日 下午3:52:04
 * @version:
 */
@Table(name = "t_help_apply")
public class HelpApplyModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8173625500926840879L;

	@Column(name = "employer_no")
	private String employerNo;
	@Column(name = "difficult_type")
	private Integer difficultType;
	@Column(name = "name")
	private String name;
	@Column(name = "sex")
	private Integer sex;
	@Column(name = "nation")
	private Integer nation;
	@Column(name = "political")
	private Integer political;
	@Column(name = "member_id")
	private Integer memberId;
	@Column(name = "id_card")
	private String idCard;
	@Column(name = "brithday")
	private Date brithday;
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
	@Column(name = "marital_status")
	private Integer maritalStatus;
	@Column(name = "card_type")
	private Integer cardType;
	@Column(name = "address")
	private String address;
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
	@Column(name = "family_num")
	private Integer familyNum;
	@Column(name = "avg_monthly_income")
	private BigDecimal avgMonthlyIncome;
	@Column(name = "seat_registered")
	private String seatRegistered;
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
	@Column(name = "help_type")
	private Integer helpType;
	@Column(name = "help_unit")
	private String helpUnit;
	@Column(name = "inputting_user")
	private String inputtingUser;
	@Column(name = "approval_user")
	private String approvalUser;
	@Column(name = "create_user")
	private String createUser;
	@Column(name = "reasons")
	private String reasons;
	@Column(name = "income_avg")
	private BigDecimal incomeAvg;
	@Column(name = "is_sign")
	private Integer isSign;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "union_chair_id")
	private Integer unionChairId;
	@Column(name = "up_org_id")
	private Integer upOrgId;
	@Column(name = "id_card_img_id")
	private String idCardImgId;
	@Column(name = "fam_id_card_img_id")
	private String famIdCardImgId;
	@Column(name = "poverty_img_id")
	private String povertyImgId;
	@Column(name = "approval_status")
	private Integer approvalStatus;
	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "apply_file_status")
	private Integer applyFileStatus;
	@Column(name = "amount_source")
	private Integer amountSource;
	@Column(name = "amount_number")
	private BigDecimal amountNumber;
	@Column(name = "process_instance_id")
	private String processInstanceId;
	


	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public BigDecimal getAmountNum() {
		return amountNumber;
	}

	public void setAmountNum(BigDecimal amountNumber) {
		this.amountNumber = amountNumber;
	}

	public Integer getAmountSource() {
		return amountSource;
	}

	public void setAmountSource(Integer amountSource) {
		this.amountSource = amountSource;
	}

	public BigDecimal getAmountNumber() {
		return amountNumber;
	}

	public void setAmountNumber(BigDecimal amountNumber) {
		this.amountNumber = amountNumber;
	}


	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}


	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}


	public String getEmployerNo() {
		return employerNo;
	}

	public void setEmployerNo(String employerNo) {
		this.employerNo = employerNo;
	}

	public Integer getDifficultType() {
		return difficultType;
	}

	public void setDifficultType(Integer difficultType) {
		this.difficultType = difficultType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}

	public Integer getPolitical() {
		return political;
	}

	public void setPolitical(Integer political) {
		this.political = political;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
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

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getFamilyNum() {
		return familyNum;
	}

	public void setFamilyNum(Integer familyNum) {
		this.familyNum = familyNum;
	}

	public BigDecimal getAvgMonthlyIncome() {
		return avgMonthlyIncome;
	}

	public void setAvgMonthlyIncome(BigDecimal avgMonthlyIncome) {
		this.avgMonthlyIncome = avgMonthlyIncome;
	}

	public String getSeatRegistered() {
		return seatRegistered;
	}

	public void setSeatRegistered(String seatRegistered) {
		this.seatRegistered = seatRegistered;
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

	public String getIdCardImgId() {
		return idCardImgId;
	}

	public void setIdCardImgId(String idCardImgId) {
		this.idCardImgId = idCardImgId;
	}

	public String getFamIdCardImgId() {
		return famIdCardImgId;
	}

	public void setFamIdCardImgId(String famIdCardImgId) {
		this.famIdCardImgId = famIdCardImgId;
	}

	public String getPovertyImgId() {
		return povertyImgId;
	}

	public void setPovertyImgId(String povertyImgId) {
		this.povertyImgId = povertyImgId;
	}

	public Integer getHelpType() {
		return helpType;
	}

	public void setHelpType(Integer helpType) {
		this.helpType = helpType;
	}

	public String getHelpUnit() {
		return helpUnit;
	}

	public void setHelpUnit(String helpUnit) {
		this.helpUnit = helpUnit;
	}


	public String getInputtingUser() {
		return inputtingUser;
	}

	public void setInputtingUser(String inputtingUser) {
		this.inputtingUser = inputtingUser;
	}

	public String getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUnionChairId() {
		return unionChairId;
	}

	public void setUnionChairId(Integer unionChairId) {
		this.unionChairId = unionChairId;
	}

	public Integer getUpOrgId() {
		return upOrgId;
	}

	public void setUpOrgId(Integer upOrgId) {
		this.upOrgId = upOrgId;
	}

}

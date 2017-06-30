package com.ehome.cloud.app.help.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * App接口请求参数对象
 * 
 * @Title:RequestHelpApplyDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月24日 上午10:00:32
 * @version:
 */
public class RequestHelpApplyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7240778305211585591L;
	private Integer id;
	private String difficultType;
	private String name;
	private String sex;
	private String nation;
	private String political;
	private Integer memberId;
	private String idCard;
	private String brithday;
	private String health;
	private String disabilityType;
	private String workStatus;
	private String modelType;
	private String housingType;
	private String housingArea;
	private String phone;
	private String concatType;
	private String postcode;
	private String workYear;
	private String industry;
	private String maritalStatus;
	private String cardType;
	private String address;
	private String company;
	private String unitProperties;
	private String companyStatus;
	private String isSingleParent;
	private String monthlyIncome;
	private String familySalaryIncome;
	private String incomeTotal;
	private Integer familyNum;
	private String avgMonthlyIncome;
	private String seatRegistered;
	private String medicalInsurance;
	private String isHasSelfSave;
	private String isZeroJob;
	private String povertyCauses;
	private String openBank;
	private String branchBank;
	private String cardNum;
	private String helpType;
	private String helpUnit;
	private String reasons;
	private String incomeAvg;
	private String isSign;
	//private Integer unionChairId;
	private String idCardImgId;
	private String famIdCardImgId;
	private String povertyImgId;

	private String idCardImgUrl;
	private String famIdCardImgUrl;
	private String povertyImgUrl;
	private String basicUnionName;
	private Integer basicUnionId;

	private List<RequestHelpApplyFamilyDto> listHelpApplyFamily = new ArrayList<>();

	private List<Map<String, Object>> workFlowList = new ArrayList<>();
	
	
	private String taskType;

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public List<Map<String, Object>> getWorkFlowList() {
		return workFlowList;
	}

	public void setWorkFlowList(List<Map<String, Object>> workFlowList) {
		this.workFlowList = workFlowList;
	}

	public Integer getBasicUnionId() {
		return basicUnionId;
	}

	public void setBasicUnionId(Integer basicUnionId) {
		this.basicUnionId = basicUnionId;
	}

	public String getBasicUnionName() {
		return basicUnionName;
	}

	public void setBasicUnionName(String basicUnionName) {
		this.basicUnionName = basicUnionName;
	}

	public String getIdCardImgUrl() {
		return idCardImgUrl;
	}

	public void setIdCardImgUrl(String idCardImgUrl) {
		this.idCardImgUrl = idCardImgUrl;
	}

	public String getFamIdCardImgUrl() {
		return famIdCardImgUrl;
	}

	public void setFamIdCardImgUrl(String famIdCardImgUrl) {
		this.famIdCardImgUrl = famIdCardImgUrl;
	}

	public String getPovertyImgUrl() {
		return povertyImgUrl;
	}

	public void setPovertyImgUrl(String povertyImgUrl) {
		this.povertyImgUrl = povertyImgUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDifficultType() {
		return difficultType;
	}

	public void setDifficultType(String difficultType) {
		this.difficultType = difficultType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
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

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getHousingType() {
		return housingType;
	}

	public void setHousingType(String housingType) {
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

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
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

	public String getUnitProperties() {
		return unitProperties;
	}

	public void setUnitProperties(String unitProperties) {
		this.unitProperties = unitProperties;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getIsSingleParent() {
		return isSingleParent;
	}

	public void setIsSingleParent(String isSingleParent) {
		this.isSingleParent = isSingleParent;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getFamilySalaryIncome() {
		return familySalaryIncome;
	}

	public void setFamilySalaryIncome(String familySalaryIncome) {
		this.familySalaryIncome = familySalaryIncome;
	}

	public String getIncomeTotal() {
		return incomeTotal;
	}

	public void setIncomeTotal(String incomeTotal) {
		this.incomeTotal = incomeTotal;
	}

	public Integer getFamilyNum() {
		return familyNum;
	}

	public void setFamilyNum(Integer familyNum) {
		this.familyNum = familyNum;
	}

	public String getAvgMonthlyIncome() {
		return avgMonthlyIncome;
	}

	public void setAvgMonthlyIncome(String avgMonthlyIncome) {
		this.avgMonthlyIncome = avgMonthlyIncome;
	}

	public String getSeatRegistered() {
		return seatRegistered;
	}

	public void setSeatRegistered(String seatRegistered) {
		this.seatRegistered = seatRegistered;
	}

	public String getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(String medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	public String getIsHasSelfSave() {
		return isHasSelfSave;
	}

	public void setIsHasSelfSave(String isHasSelfSave) {
		this.isHasSelfSave = isHasSelfSave;
	}

	public String getIsZeroJob() {
		return isZeroJob;
	}

	public void setIsZeroJob(String isZeroJob) {
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

	public String getHelpType() {
		return helpType;
	}

	public void setHelpType(String helpType) {
		this.helpType = helpType;
	}

	public String getHelpUnit() {
		return helpUnit;
	}

	public void setHelpUnit(String helpUnit) {
		this.helpUnit = helpUnit;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getIncomeAvg() {
		return incomeAvg;
	}

	public void setIncomeAvg(String incomeAvg) {
		this.incomeAvg = incomeAvg;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

//	public Integer getUnionChairId() {
//		return unionChairId;
//	}
//
//	public void setUnionChairId(Integer unionChairId) {
//		this.unionChairId = unionChairId;
//	}

	public List<RequestHelpApplyFamilyDto> getListHelpApplyFamily() {
		return listHelpApplyFamily;
	}

	public void setListHelpApplyFamily(
			List<RequestHelpApplyFamilyDto> listHelpApplyFamily) {
		this.listHelpApplyFamily = listHelpApplyFamily;
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

	@Override
	public String toString() {
		return "RequestHelpApplyDto [id=" + id + ", difficultType="
				+ difficultType + ", name=" + name + ", sex=" + sex
				+ ", nation=" + nation + ", political=" + political
				+ ", memberId=" + memberId + ", idCard=" + idCard
				+ ", brithday=" + brithday + ", health=" + health
				+ ", disabilityType=" + disabilityType + ", workStatus="
				+ workStatus + ", modelType=" + modelType + ", housingType="
				+ housingType + ", housingArea=" + housingArea + ", phone="
				+ phone + ", concatType=" + concatType + ", postcode="
				+ postcode + ", workYear=" + workYear + ", industry="
				+ industry + ", maritalStatus=" + maritalStatus + ", cardType="
				+ cardType + ", address=" + address + ", company=" + company
				+ ", unitProperties=" + unitProperties + ", companyStatus="
				+ companyStatus + ", isSingleParent=" + isSingleParent
				+ ", monthlyIncome=" + monthlyIncome + ", familySalaryIncome="
				+ familySalaryIncome + ", incomeTotal=" + incomeTotal
				+ ", familyNum=" + familyNum + ", avgMonthlyIncome="
				+ avgMonthlyIncome + ", seatRegistered=" + seatRegistered
				+ ", medicalInsurance=" + medicalInsurance + ", isHasSelfSave="
				+ isHasSelfSave + ", isZeroJob=" + isZeroJob
				+ ", povertyCauses=" + povertyCauses + ", openBank=" + openBank
				+ ", branchBank=" + branchBank + ", cardNum=" + cardNum
				+ ", helpType=" + helpType + ", helpUnit=" + helpUnit
				+ ", reasons=" + reasons + ", incomeAvg=" + incomeAvg
				+ ", isSign=" + isSign + ", basicUnionId=" + basicUnionId
				+ ", idCardImgId=" + idCardImgId + ", famIdCardImgId="
				+ famIdCardImgId + ", povertyImgId=" + povertyImgId
				+ ", listHelpApplyFamily=" + listHelpApplyFamily + "]";
	}

}

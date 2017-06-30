package com.ehome.cloud.member.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ehome.core.annotation.ExcelField;

/**
 * 帮扶信息批量导入
 * @Title:HelpMemberImportDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月24日 下午12:30:12
 * @version:
 */
public class HelpMemberImportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4824288441144885081L;
	@ExcelField(columnName = "编号")
	private String employerNo;
	@ExcelField(columnName = "困难类别")
	private String difficultType;
	@ExcelField(columnName = "姓名")
	private String name;
	@ExcelField(columnName = "民族")
	private String nation;
	@ExcelField(columnName = "性别")
	private String sex;
	@ExcelField(columnName = "政治面貌")
	private String political;
	@ExcelField(columnName = "身份证号")
	private String idCard;
	@ExcelField(columnName = "出生日期")
	private String brithday;
	@ExcelField(columnName = "健康状况")
	private String health;
	@ExcelField(columnName = "残疾类别")
	private String disabilityType;
	@ExcelField(columnName = "工作状态")
	private String workStatus;
	@ExcelField(columnName = "劳模类型")
	private String modelType;
	@ExcelField(columnName = "住房类型")
	private String housingType;
	@ExcelField(columnName = "建筑面积")
	private String housingArea;
	@ExcelField(columnName = "手机号码")
	private String phone;
	@ExcelField(columnName = "其他联系方式")
	private String concatType;
	@ExcelField(columnName = "邮政编码")
	private String postcode;
	@ExcelField(columnName = "工作时间")
	private String workYear;
	@ExcelField(columnName = "所属行业")
	private String industry;
	@ExcelField(columnName = "婚姻状态")
	private String maritalStatus;
	@ExcelField(columnName = "户口类型")
	private String cardType;
	@ExcelField(columnName = "家庭住址")
	private String address;
	@ExcelField(columnName = "工作单位")
	private String company;
	@ExcelField(columnName = "单位性质")
	private String unitProperties;
	@ExcelField(columnName = "企业状态")
	private String companyStatus;
	@ExcelField(columnName = "是否单亲")
	private String   isSingleParent;
	@ExcelField(columnName = "本人月平均收入")
	private BigDecimal monthlyIncome;
	@ExcelField(columnName = "家庭年度总收入")
	private BigDecimal incomeTotal;
	@ExcelField(columnName = "家庭人口")
	private Integer familyNum;
	@ExcelField(columnName = "家庭月人均收入")
	private BigDecimal avgMonthlyIncome;
	@ExcelField(columnName = "户口所在地")
	private String seatRegistered;
	@ExcelField(columnName = "是否进入医保")
	private String medicalInsurance;
	@ExcelField(columnName = "是否有一定自救能力")
	private String isHasSelfSave;
	@ExcelField(columnName = "是否为零就业家庭")
	private String isZeroJob;
	@ExcelField(columnName = "主要致困原因")
	private String povertyCauses;
	@ExcelField(columnName = "开户银行")
	private String openBank;
	@ExcelField(columnName = "支行名称")
	private String branchBank;
	@ExcelField(columnName = "银行卡号")
	private String cardNum;
	@ExcelField(columnName = "建档人")
	private String inputtingUser;
	@ExcelField(columnName = "审核人")
	private String approvalUser;
	@ExcelField(columnName = "录入人")
	private String createUser;
	@ExcelField(columnName = "建档日期")
	private String createTime;
	//家庭成员1信息
	@ExcelField(columnName = "家庭成员1姓名")
	private String members1;
	@ExcelField(columnName = "家庭成员1关系")
	private String familyRelationship1;	
	@ExcelField(columnName = "家庭成员1性别")
	private String sex1;	
	@ExcelField(columnName = "家庭成员1政治面貌")
	private String political1;	
	@ExcelField(columnName = "家庭成员1身份证号")
	private String idCard1;	
	@ExcelField(columnName = "家庭成员1健康状况")
	private String health1;
	@ExcelField(columnName = "家庭成员1月收入")
	private String monthlyIncome1;
	@ExcelField(columnName = "家庭成员1身份")
	private String identity1;
	@ExcelField(columnName = "家庭成员1单位或学校")
	private String unitSchool1;
	//家庭成员2信息
	@ExcelField(columnName = "家庭成员2姓名")
	private String members2;
	@ExcelField(columnName = "家庭成员2关系")
	private String familyRelationship2;	
	@ExcelField(columnName = "家庭成员2性别")
	private String sex2;	
	@ExcelField(columnName = "家庭成员2政治面貌")
	private String political2;	
	@ExcelField(columnName = "家庭成员2身份证号")
	private String idCard2;	
	@ExcelField(columnName = "家庭成员2健康状况")
	private String health2;
	@ExcelField(columnName = "家庭成员2月收入")
	private String monthlyIncome2;
	@ExcelField(columnName = "家庭成员2身份")
	private String identity2;
	@ExcelField(columnName = "家庭成员2单位或学校")
	private String unitSchool2;
	//家庭成员3信息
	@ExcelField(columnName = "家庭成员3姓名")
	private String members3;
	@ExcelField(columnName = "家庭成员3关系")
	private String familyRelationship3;	
	@ExcelField(columnName = "家庭成员3性别")
	private String sex3;	
	@ExcelField(columnName = "家庭成员3政治面貌")
	private String political3;	
	@ExcelField(columnName = "家庭成员3身份证号")
	private String idCard3;	
	@ExcelField(columnName = "家庭成员3健康状况")
	private String health3;
	@ExcelField(columnName = "家庭成员3月收入")
	private String monthlyIncome3;
	@ExcelField(columnName = "家庭成员3身份")
	private String identity3;
	@ExcelField(columnName = "家庭成员3单位或学校")
	private String unitSchool3;
	//家庭成员1信息
	@ExcelField(columnName = "家庭成员4姓名")
	private String members4;
	@ExcelField(columnName = "家庭成员4关系")
	private String familyRelationship4;	
	@ExcelField(columnName = "家庭成员4性别")
	private String sex4;	
	@ExcelField(columnName = "家庭成员4政治面貌")
	private String political4;	
	@ExcelField(columnName = "家庭成员4身份证号")
	private String idCard4;	
	@ExcelField(columnName = "家庭成员4健康状况")
	private String health4;
	@ExcelField(columnName = "家庭成员4月收入")
	private String monthlyIncome4;
	@ExcelField(columnName = "家庭成员4身份")
	private String identity4;
	@ExcelField(columnName = "家庭成员4单位或学校")
	private String unitSchool4;
	//家庭成员1信息
	@ExcelField(columnName = "家庭成员5姓名")
	private String members5;
	@ExcelField(columnName = "家庭成员5关系")
	private String familyRelationship5;	
	@ExcelField(columnName = "家庭成员5性别")
	private String sex5;	
	@ExcelField(columnName = "家庭成员5政治面貌")
	private String political5;	
	@ExcelField(columnName = "家庭成员5身份证号")
	private String idCard5;	
	@ExcelField(columnName = "家庭成员5健康状况")
	private String health5;
	@ExcelField(columnName = "家庭成员5月收入")
	private String monthlyIncome5;
	@ExcelField(columnName = "家庭成员5身份")
	private String identity5;
	@ExcelField(columnName = "家庭成员5单位或学校")
	private String unitSchool5;
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEmployerNo() {
		return employerNo;
	}
	public void setEmployerNo(String employerNo) {
		this.employerNo = employerNo;
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
	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
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
	public String getMembers1() {
		return members1;
	}
	public void setMembers1(String members1) {
		this.members1 = members1;
	}
	public String getFamilyRelationship1() {
		return familyRelationship1;
	}
	public void setFamilyRelationship1(String familyRelationship1) {
		this.familyRelationship1 = familyRelationship1;
	}
	public String getSex1() {
		return sex1;
	}
	public void setSex1(String sex1) {
		this.sex1 = sex1;
	}
	public String getPolitical1() {
		return political1;
	}
	public void setPolitical1(String political1) {
		this.political1 = political1;
	}
	public String getIdCard1() {
		return idCard1;
	}
	public void setIdCard1(String idCard1) {
		this.idCard1 = idCard1;
	}
	public String getHealth1() {
		return health1;
	}
	public void setHealth1(String health1) {
		this.health1 = health1;
	}
	public String getMonthlyIncome1() {
		return monthlyIncome1;
	}
	public void setMonthlyIncome1(String monthlyIncome1) {
		this.monthlyIncome1 = monthlyIncome1;
	}
	public String getIdentity1() {
		return identity1;
	}
	public void setIdentity1(String identity1) {
		this.identity1 = identity1;
	}
	public String getUnitSchool1() {
		return unitSchool1;
	}
	public void setUnitSchool1(String unitSchool1) {
		this.unitSchool1 = unitSchool1;
	}
	public String getMembers2() {
		return members2;
	}
	public void setMembers2(String members2) {
		this.members2 = members2;
	}
	public String getFamilyRelationship2() {
		return familyRelationship2;
	}
	public void setFamilyRelationship2(String familyRelationship2) {
		this.familyRelationship2 = familyRelationship2;
	}
	public String getSex2() {
		return sex2;
	}
	public void setSex2(String sex2) {
		this.sex2 = sex2;
	}
	public String getPolitical2() {
		return political2;
	}
	public void setPolitical2(String political2) {
		this.political2 = political2;
	}
	public String getIdCard2() {
		return idCard2;
	}
	public void setIdCard2(String idCard2) {
		this.idCard2 = idCard2;
	}
	public String getHealth2() {
		return health2;
	}
	public void setHealth2(String health2) {
		this.health2 = health2;
	}
	public String getMonthlyIncome2() {
		return monthlyIncome2;
	}
	public void setMonthlyIncome2(String monthlyIncome2) {
		this.monthlyIncome2 = monthlyIncome2;
	}
	public String getIdentity2() {
		return identity2;
	}
	public void setIdentity2(String identity2) {
		this.identity2 = identity2;
	}
	public String getUnitSchool2() {
		return unitSchool2;
	}
	public void setUnitSchool2(String unitSchool2) {
		this.unitSchool2 = unitSchool2;
	}
	public String getMembers3() {
		return members3;
	}
	public void setMembers3(String members3) {
		this.members3 = members3;
	}
	public String getFamilyRelationship3() {
		return familyRelationship3;
	}
	public void setFamilyRelationship3(String familyRelationship3) {
		this.familyRelationship3 = familyRelationship3;
	}
	public String getSex3() {
		return sex3;
	}
	public void setSex3(String sex3) {
		this.sex3 = sex3;
	}
	public String getPolitical3() {
		return political3;
	}
	public void setPolitical3(String political3) {
		this.political3 = political3;
	}
	public String getIdCard3() {
		return idCard3;
	}
	public void setIdCard3(String idCard3) {
		this.idCard3 = idCard3;
	}
	public String getHealth3() {
		return health3;
	}
	public void setHealth3(String health3) {
		this.health3 = health3;
	}
	public String getMonthlyIncome3() {
		return monthlyIncome3;
	}
	public void setMonthlyIncome3(String monthlyIncome3) {
		this.monthlyIncome3 = monthlyIncome3;
	}
	public String getIdentity3() {
		return identity3;
	}
	public void setIdentity3(String identity3) {
		this.identity3 = identity3;
	}
	public String getUnitSchool3() {
		return unitSchool3;
	}
	public void setUnitSchool3(String unitSchool3) {
		this.unitSchool3 = unitSchool3;
	}
	public String getMembers4() {
		return members4;
	}
	public void setMembers4(String members4) {
		this.members4 = members4;
	}
	public String getFamilyRelationship4() {
		return familyRelationship4;
	}
	public void setFamilyRelationship4(String familyRelationship4) {
		this.familyRelationship4 = familyRelationship4;
	}
	public String getSex4() {
		return sex4;
	}
	public void setSex4(String sex4) {
		this.sex4 = sex4;
	}
	public String getPolitical4() {
		return political4;
	}
	public void setPolitical4(String political4) {
		this.political4 = political4;
	}
	public String getIdCard4() {
		return idCard4;
	}
	public void setIdCard4(String idCard4) {
		this.idCard4 = idCard4;
	}
	public String getHealth4() {
		return health4;
	}
	public void setHealth4(String health4) {
		this.health4 = health4;
	}
	public String getMonthlyIncome4() {
		return monthlyIncome4;
	}
	public void setMonthlyIncome4(String monthlyIncome4) {
		this.monthlyIncome4 = monthlyIncome4;
	}
	public String getIdentity4() {
		return identity4;
	}
	public void setIdentity4(String identity4) {
		this.identity4 = identity4;
	}
	public String getUnitSchool4() {
		return unitSchool4;
	}
	public void setUnitSchool4(String unitSchool4) {
		this.unitSchool4 = unitSchool4;
	}
	public String getMembers5() {
		return members5;
	}
	public void setMembers5(String members5) {
		this.members5 = members5;
	}
	public String getFamilyRelationship5() {
		return familyRelationship5;
	}
	public void setFamilyRelationship5(String familyRelationship5) {
		this.familyRelationship5 = familyRelationship5;
	}
	public String getSex5() {
		return sex5;
	}
	public void setSex5(String sex5) {
		this.sex5 = sex5;
	}
	public String getPolitical5() {
		return political5;
	}
	public void setPolitical5(String political5) {
		this.political5 = political5;
	}
	public String getIdCard5() {
		return idCard5;
	}
	public void setIdCard5(String idCard5) {
		this.idCard5 = idCard5;
	}
	public String getHealth5() {
		return health5;
	}
	public void setHealth5(String health5) {
		this.health5 = health5;
	}
	public String getMonthlyIncome5() {
		return monthlyIncome5;
	}
	public void setMonthlyIncome5(String monthlyIncome5) {
		this.monthlyIncome5 = monthlyIncome5;
	}
	public String getIdentity5() {
		return identity5;
	}
	public void setIdentity5(String identity5) {
		this.identity5 = identity5;
	}
	public String getUnitSchool5() {
		return unitSchool5;
	}
	public void setUnitSchool5(String unitSchool5) {
		this.unitSchool5 = unitSchool5;
	}

	
}

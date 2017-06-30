package com.ehome.cloud.member.dto;

import java.io.Serializable;

import com.ehome.core.annotation.ExcelField;

/**
 * 会员导入Dto
 * 
 * @Title:MemberImportDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月23日 上午11:20:49
 * @version:
 */
public class MemberImportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4588526983164336251L;

	@ExcelField(columnName = "*姓名")
	private String memberName;

	@ExcelField(columnName = "*性别")
	private String sex;

	@ExcelField(columnName = "*身份证号码")
	private String idCard;

	@ExcelField(columnName = "*联系电话")
	private String tel;

	@ExcelField(columnName = "民族")
	private String nation;

	@ExcelField(columnName = "学历")
	private String education;

	@ExcelField(columnName = "学位")
	private String degree;

	@ExcelField(columnName = "政治面貌")
	private String political;

	@ExcelField(columnName = "户口所在地")
	private String registeredAddress;

	@ExcelField(columnName = "现居地址")
	private String address;

	@ExcelField(columnName = "工作单位及职务")
	private String job;

	@ExcelField(columnName = "有何特长")
	private String specialty;

	@ExcelField(columnName = "工会基层委员会意见")
	private String opinion;

	@ExcelField(columnName = "备注")
	private String remark;

	@ExcelField(columnName = "会员状态")
	private String status;
	
	@ExcelField(columnName = "婚姻状况")
	private String maritalStatus;

	@ExcelField(columnName = "家庭成员1")
	private String memberHome1;

	@ExcelField(columnName = "家庭成员1联系方式")
	private String memberHome1Contact;
	
	@ExcelField(columnName = "家庭成员1关系")
	private String familyRelationship1;

	@ExcelField(columnName = "家庭成员2")
	private String memberHome2;

	@ExcelField(columnName = "家庭成员2联系方式")
	private String memberHome2Contact;
	
	@ExcelField(columnName = "家庭成员2关系")
	private String familyRelationship2;

	@ExcelField(columnName = "家庭成员3")
	private String memberHome3;

	@ExcelField(columnName = "家庭成员3联系方式")
	private String memberHome3Contact;
	
	@ExcelField(columnName = "家庭成员3关系")
	private String familyRelationship3;

	@ExcelField(columnName = "家庭成员4")
	private String memberHome4;

	@ExcelField(columnName = "家庭成员4联系方式")
	private String memberHome4Contact;
	
	@ExcelField(columnName = "家庭成员4关系")
	private String familyRelationship4;

	@ExcelField(columnName = "企业名称")
	private String companyName;

	@ExcelField(columnName = "职务")
	private String job2;

	@ExcelField(columnName = "入职时间")
	private String joinedDate;

	@ExcelField(columnName = "离职时间")
	private String releaseDate;
	
	private Integer province;
	
	private Integer city;

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getFamilyRelationship1() {
		return familyRelationship1;
	}

	public void setFamilyRelationship1(String familyRelationship1) {
		this.familyRelationship1 = familyRelationship1;
	}

	public String getFamilyRelationship2() {
		return familyRelationship2;
	}

	public void setFamilyRelationship2(String familyRelationship2) {
		this.familyRelationship2 = familyRelationship2;
	}

	public String getFamilyRelationship3() {
		return familyRelationship3;
	}

	public void setFamilyRelationship3(String familyRelationship3) {
		this.familyRelationship3 = familyRelationship3;
	}

	public String getFamilyRelationship4() {
		return familyRelationship4;
	}

	public void setFamilyRelationship4(String familyRelationship4) {
		this.familyRelationship4 = familyRelationship4;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJob2() {
		return job2;
	}

	public void setJob2(String job2) {
		this.job2 = job2;
	}

	public String getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(String joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberHome1() {
		return memberHome1;
	}

	public void setMemberHome1(String memberHome1) {
		this.memberHome1 = memberHome1;
	}

	public String getMemberHome1Contact() {
		return memberHome1Contact;
	}

	public void setMemberHome1Contact(String memberHome1Contact) {
		this.memberHome1Contact = memberHome1Contact;
	}

	public String getMemberHome2() {
		return memberHome2;
	}

	public void setMemberHome2(String memberHome2) {
		this.memberHome2 = memberHome2;
	}

	public String getMemberHome2Contact() {
		return memberHome2Contact;
	}

	public void setMemberHome2Contact(String memberHome2Contact) {
		this.memberHome2Contact = memberHome2Contact;
	}

	public String getMemberHome3() {
		return memberHome3;
	}

	public void setMemberHome3(String memberHome3) {
		this.memberHome3 = memberHome3;
	}

	public String getMemberHome3Contact() {
		return memberHome3Contact;
	}

	public void setMemberHome3Contact(String memberHome3Contact) {
		this.memberHome3Contact = memberHome3Contact;
	}

	public String getMemberHome4() {
		return memberHome4;
	}

	public void setMemberHome4(String memberHome4) {
		this.memberHome4 = memberHome4;
	}

	public String getMemberHome4Contact() {
		return memberHome4Contact;
	}

	public void setMemberHome4Contact(String memberHome4Contact) {
		this.memberHome4Contact = memberHome4Contact;
	}

}

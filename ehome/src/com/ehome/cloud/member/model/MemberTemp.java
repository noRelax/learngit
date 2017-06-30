package com.ehome.cloud.member.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 批量导入会员临时表记录
 * 
 * @Title:MemberTempRecord
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月27日 下午6:55:01
 * @version:
 */
@Table(name = "t_member_temp")
public class MemberTemp extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6264170198483536622L;

	@Column(name="userId")
	private Integer userId;
	@Column(name="memberName")
	private String memberName;
	@Column(name="sex")
	private String sex;
	@Column(name="idCard")
	private String idCard;
	@Column(name="tel")
	private String tel;
	@Column(name="nation")
	private String nation;
	@Column(name="education")
	private String education;
	@Column(name="degree")
	private String degree;
	@Column(name="political")
	private String political;
	@Column(name="address")
	private String address;
	@Column(name="job")
	private String job;
	@Column(name="specialty")
	private String specialty;
	@Column(name="opinion")
	private String opinion;
	@Column(name="remark")
	private String remark;
	@Column(name="status")
	private String status;
	@Column(name="memberHome1")
	private String memberHome1;
	@Column(name="memberHome1Contact")
	private String memberHome1Contact;
	@Column(name="memberHome2")
	private String memberHome2;
	@Column(name="memberHome2Contact")
	private String memberHome2Contact;
	@Column(name="memberHome3")
	private String memberHome3;
	@Column(name="memberHome3Contact")
	private String memberHome3Contact;
	@Column(name="memberHome4")
	private String memberHome4;
	@Column(name="memberHome4Contact")
	private String memberHome4Contact;
	@Column(name="companyName")
	private String companyName;
	@Column(name="job2")
	private String job2;
	@Column(name="joinedDate")
	private String joinedDate;
	@Column(name="releaseDate")
	private String releaseDate;
	@Column(name="maritalStatus")
	private String maritalStatus;	
	@Column(name="familyRelationship1")
	private String familyRelationship1;
	@Column(name="familyRelationship2")
	private String familyRelationship2;
	@Column(name="familyRelationship3")
	private String familyRelationship3;
	@Column(name="familyRelationship4")
	private String familyRelationship4;
	@Column(name="province")
	private Integer province;
    @Column(name="city")
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

}

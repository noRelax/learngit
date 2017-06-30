package com.ehome.cloud.member.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 会员管理
 * 
 * @Title:Member
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月21日 下午5:19:21
 * @version:
 */
@Table(name = "t_member")
public class Member extends BaseEntity implements Serializable {
	public Member( Integer appUserId) {
		this.appUserId =appUserId; 
	}

	public Member() {
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5514352709435844749L;

	@Column(name = "member_name")
	private String memberName;
	@Column(name = "basic_union_id")
	private Integer basicUnionId;
	@Column(name = "upper_union_id")
	private Integer upperUnionId;
	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "status")
	private Integer status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "birthday")
	private Date birthday;
	@Column(name = "position")
	private String position;
	@Column(name = "job")
	private String job;
	@Column(name = "political")
	private Integer political;
	@Column(name = "nation")
	private Integer nation;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "id_card")
	private String idCard;
	@Column(name = "sex")
	private Integer sex;
	@Column(name = "education")
	private Integer education;
	@Column(name = "degree")
	private Integer degree;
	@Column(name = "tel")
	private String tel;
	@Column(name = "address")
	private String address;
	@Column(name = "specialty")
	private String specialty;
	@Column(name = "opinion")
	private String opinion;
	@Column(name = "remark")
	private String remark;
	@Column(name = "audit_status")
	private Integer auditStatus;
	@Column(name = "user_resource")
	private Integer userResource;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="is_active")
	private Integer isActive;
	@Column(name="province")
	private Integer province;
	@Column(name="city")
	private Integer city;
	@Column(name="area")
	private Integer area;
	@Column(name="marital_status")
	private Integer maritalStatus;

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

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

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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

	public Integer getBasicUnionId() {
		return basicUnionId;
	}

	public void setBasicUnionId(Integer basicUnionId) {
		this.basicUnionId = basicUnionId;
	}

	public Integer getUpperUnionId() {
		return upperUnionId;
	}

	public void setUpperUnionId(Integer upperUnionId) {
		this.upperUnionId = upperUnionId;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getPolitical() {
		return political;
	}

	public void setPolitical(Integer political) {
		this.political = political;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getUserResource() {
		return userResource;
	}

	public void setUserResource(Integer userResource) {
		this.userResource = userResource;
	}

}

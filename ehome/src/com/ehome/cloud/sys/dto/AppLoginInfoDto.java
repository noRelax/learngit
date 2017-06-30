package com.ehome.cloud.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.cloud.sys.model.RoleModel;

/**
 * APP端登录帐号实体对象
 * 
 * @Title:AppLoginInfoDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月17日 下午3:21:20
 * @version:
 */
public class AppLoginInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3565989251783015667L;

	private Integer id;

	private String userAccount;

	private String userPassword;

	private String userName;

	private String userEmail;

	private String userTel;

	private String userMobile;

	private String userDesc;

	private Date createTime;

	private Integer status;

	private Integer activeFlag;

	private Integer orgainId;

	private Integer userType;

	private Integer sex;

	private Integer province;

	private Integer city;

	private Integer county;

	private String address;

	private Integer sourceDevice;
	
	private Integer isMember;
	
	private Date lastLoginTime;

	private Integer street;
	
	private String imei;
	
	private String realName;
	
	private String ip;
	
	private String nickName;
	
	private String portrait;
	
	private String signature;
	
	private String invitationCode;
	
	private String regCode;
	
	private String invitationCodeFrom;
	
	private Integer fromType;
	
	private String fromKey;
	
	private Integer blackType;
	
	private String salt;

	private List<RoleModel> roleList = new ArrayList<>();

	private List<MenuModel> menuList = new ArrayList<>();
	
	private String workPlace;
	
	private String height;
	
	private String birthday;
	
	private Integer annualIncome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Integer getOrgainId() {
		return orgainId;
	}

	public void setOrgainId(Integer orgainId) {
		this.orgainId = orgainId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Integer getCounty() {
		return county;
	}

	public void setCounty(Integer county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getStreet() {
		return street;
	}

	public void setStreet(Integer street) {
		this.street = street;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getInvitationCodeFrom() {
		return invitationCodeFrom;
	}

	public void setInvitationCodeFrom(String invitationCodeFrom) {
		this.invitationCodeFrom = invitationCodeFrom;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public String getFromKey() {
		return fromKey;
	}

	public void setFromKey(String fromKey) {
		this.fromKey = fromKey;
	}

	public Integer getBlackType() {
		return blackType;
	}

	public void setBlackType(Integer blackType) {
		this.blackType = blackType;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<RoleModel> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleModel> roleList) {
		this.roleList = roleList;
	}

	public List<MenuModel> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuModel> menuList) {
		this.menuList = menuList;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Integer annualIncome) {
		this.annualIncome = annualIncome;
	}
}

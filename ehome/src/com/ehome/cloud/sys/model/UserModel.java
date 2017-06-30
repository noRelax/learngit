package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户管理实体类
 * 
 * @Title:UserModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月20日 上午10:51:51
 * @version:
 */
public class UserModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3164998080646844412L;

	private String userAccount;

	private String userPassword;

	private String userName;

	private String userEmail;

	private String userTel;

	private String userMobile;

	private String userDesc;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private Integer status;

	private Integer activeFlag;

	private Integer orgainId;

	@CodeField(code = "CODE_USER_TYPE", renderField = "userTypeName")
	private Integer userType;

	private Integer sex;

	private Integer province;

	private Integer city;

	private Integer county;

	private String address;

	private Integer street;

	private String salt;

	private Integer appUserId;

	private Integer createUser;

	private Integer deptId;

	private Integer baseUnionId;

	private String deptName;

	private String baseUnionName;

	private String orgainName;

	private String userTypeName;

	private String roleName;

	// private List<RoleModel> roles = new ArrayList<>();
	//
	// private List<MenuModel> permissions = new ArrayList<>();

	// public List<RoleModel> getRoles() {
	// return roles;
	// }
	//
	// public void setRoles(List<RoleModel> roles) {
	// this.roles = roles;
	// }
	//
	// public List<MenuModel> getPermissions() {
	// return permissions;
	// }
	//
	// public void setPermissions(List<MenuModel> permissions) {
	// this.permissions = permissions;
	// }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBaseUnionName() {
		return baseUnionName;
	}

	public void setBaseUnionName(String baseUnionName) {
		this.baseUnionName = baseUnionName;
	}

	public String getOrgainName() {
		return orgainName;
	}

	public void setOrgainName(String orgainName) {
		this.orgainName = orgainName;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getBaseUnionId() {
		return baseUnionId;
	}

	public void setBaseUnionId(Integer baseUnionId) {
		this.baseUnionId = baseUnionId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
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

	public Integer getStreet() {
		return street;
	}

	public void setStreet(Integer street) {
		this.street = street;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}

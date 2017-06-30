package com.ehome.cloud.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.cloud.sys.model.RoleModel;

/**
 * 登录帐号实体对象
 * 
 * @Title:LoginInfoDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月10日 下午2:38:55
 * @version:
 */
public class LoginInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144288982977081736L;

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

	private Integer street;

	private String salt;
	
	private Integer deptId;
	
	private Integer baseUnionId;

	private List<RoleModel> roleList = new ArrayList<>();

	private List<MenuModel> menuList = new ArrayList<>();

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

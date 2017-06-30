package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:RoleModel
 * @Description:角色管理实体类
 * @author:zsh
 * @date:2017年2月6日
 * @version 1.0,2017年2月6日
 * @{tags}
 */
public class RoleModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1806235645519719038L;
	
	private Integer id;// int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
	private String roleCode;// varchar(30) NOT NULL COMMENT '角色编码',
	private Integer roleType;// int(11) DEFAULT NULL COMMENT '角色类型',
	private String roleName;// varchar(50) DEFAULT NULL COMMENT '角色名称',
	private String roleDesc;// varchar(200) DEFAULT NULL COMMENT '角色描述',
	private Integer status;// tinyint(1) DEFAULT NULL COMMENT '0正常 1冻结 -1删除',
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;// datetime DEFAULT NULL COMMENT '创建时间',
	private Integer isAppType;//是否是app角色类型    0 不是 1是
	private Integer creator;
    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return Returns the roleCode.
     */
    public String getRoleCode() {
        return roleCode;
    }
    /**
     * @param roleCode The roleCode to set.
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    /**
     * @return Returns the roleType.
     */
    public Integer getRoleType() {
        return roleType;
    }
    /**
     * @param roleType The roleType to set.
     */
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
    /**
     * @return Returns the roleName.
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * @param roleName The roleName to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    /**
     * @return Returns the roleDesc.
     */
    public String getRoleDesc() {
        return roleDesc;
    }
    /**
     * @param roleDesc The roleDesc to set.
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
    /**
     * @return Returns the status.
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * @return Returns the createTime.
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime The createTime to set.
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * @return Returns the isAppType.
     */
    public Integer getIsAppType() {
        return isAppType;
    }
    /**
     * @param isAppType The isAppType to set.
     */
    public void setIsAppType(Integer isAppType) {
        this.isAppType = isAppType;
    }
    /**
     * @return Returns the creator.
     */
    public Integer getCreator() {
        return creator;
    }
    /**
     * @param creator The creator to set.
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }
}

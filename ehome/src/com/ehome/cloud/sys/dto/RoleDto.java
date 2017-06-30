package com.ehome.cloud.sys.dto;

import java.io.Serializable;

import com.ehome.cloud.sys.model.RoleModel;

/**
 * 
 * @Title:RoleModel
 * @Description:角色dto类
 * @author:zsh
 * @date:2017年2月6日
 * @version 1.0,2017年2月6日
 * @{tags}
 */
public class RoleDto extends RoleModel implements Serializable {
	
    private static final long serialVersionUID = -1339746573085907548L;
	
	private Integer isAdmin;
	private Integer isMyrole;
	private Boolean isShowButton;
    /**
     * @return Returns the isAdmin.
     */
    public Integer getIsAdmin() {
        return isAdmin;
    }
    /**
     * @param isAdmin The isAdmin to set.
     */
    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
    /**
     * @return Returns the isMyrole.
     */
    public Integer getIsMyrole() {
        return isMyrole;
    }
    /**
     * @param isMyrole The isMyrole to set.
     */
    public void setIsMyrole(Integer isMyrole) {
        this.isMyrole = isMyrole;
    }
    /**
     * @return Returns the isShowButton.
     */
    public Boolean getIsShowButton() {
        return isShowButton;
    }
    /**
     * @param isShowButton The isShowButton to set.
     */
    public void setIsShowButton(Boolean isShowButton) {
        this.isShowButton = isShowButton;
    }	
}

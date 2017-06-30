package com.ehome.cloud.sys.dto;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

public class ConfigModuleDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840780122493684726L;

	private String moduleName;
	private String remark;
	@CodeField(code = "CODE_STATUS", renderField = "statusName")
	private Integer status;
	private String icon;
	private Integer sort;
	private Integer unloadId;
	private Date createTime;
	private String statusName;
    private String cityName;
    private String moduleCode;
    @CodeField(code = "CODE_MODULE_TYPE", renderField = "moduleTypeName")
    private Integer moduleType;
    private String moduleTypeName;
    private Integer channelId;

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleTypeName() {
		return moduleTypeName;
	}

	public void setModuleTypeName(String moduleTypeName) {
		this.moduleTypeName = moduleTypeName;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getUnloadId() {
		return unloadId;
	}

	public void setUnloadId(Integer unloadId) {
		this.unloadId = unloadId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

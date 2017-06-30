package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
/**
 * 
 * @Title:ConfigModule
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月8日 下午7:06:32
 * @version:
 */
@Table(name = "t_module")
public class ConfigModule extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9198374979203685591L;

	@Column(name = "module_name")
	private String moduleName;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private Integer status;
	@Column(name = "icon")
	private String icon;
	@Column(name="sort")
	private Integer sort;
	@Column(name="unload_id")
	private Integer unloadId;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name="module_code")
	private String moduleCode;
	@Column(name="module_type")
	private Integer moduleType;
	@Column(name="channel_id")
	private Integer channelId;

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

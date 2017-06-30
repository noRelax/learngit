package com.ehome.cloud.sys.model;

import java.io.Serializable;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/*
 * @Title:OrgainModel
 * @Description:系统组织架构表对应实体
 * @author:zsh
 * @date:2017年2月3日
 * @version 1.0,2017年2月3日
 * @{tags}
  */
@Table(name = "t_orgain")
public class OrgainModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6991533936274876774L;

	//private Integer id;// int(11) NOT NULL AUTO_INCREMENT COMMENT '自编号',
	private String orgainCode;// varchar(50) DEFAULT NULL COMMENT '组织架构编码',
	private String orgainName;// varchar(100) DEFAULT NULL COMMENT '组织架构名称',
	private Integer parentId;// int(11) DEFAULT NULL COMMENT '组织架构父节点',
	private Integer sort;// int(11) unsigned zerofill DEFAULT NULL COMMENT '排序号',
	private Integer isEndLevel;// tinyint(1) DEFAULT '0' COMMENT '终节点标识 0 不是 1 是 ',
	private String remark;// varchar(500) DEFAULT NULL COMMENT '备注说明',
	private Integer orgainType;// tinyint(1) NOT NULL COMMENT '0 上级工会   1 部门    2 基层工会 ',
	private Integer levelNum;// int(6) NOT NULL COMMENT '节点层级  从1开始',
	private Integer province;// int(11) DEFAULT NULL COMMENT '省id',
	private Integer city;// int(11) DEFAULT NULL COMMENT '城市id',
	private Integer county;// int(11) DEFAULT NULL COMMENT '县区id',
	private Integer street;// int(11) NOT NULL COMMENT '街道id',
	private Integer status;// tinyint(1) NOT NULL COMMENT '0正常 -1 删除',
	private String pids;//varchar(255) NOT NULL COMMENT  '当前的id和上级所有pid.      比如 广东id是0   广州 1  佛山2   天河区3。南海4。     （天河区的pid是0，1，3） (南海则0，2，4)',

	//	public Integer getId() {
	//		return id;
	//	}
	//	public void setId(Integer id) {
	//		this.id = id;
	//	}
	public String getOrgainCode() {
		return orgainCode;
	}

	public void setOrgainCode(String orgainCode) {
		this.orgainCode = orgainCode;
	}

	public String getOrgainName() {
		return orgainName;
	}

	public void setOrgainName(String orgainName) {
		this.orgainName = orgainName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsEndLevel() {
		return isEndLevel;
	}

	public void setIsEndLevel(Integer isEndLevel) {
		this.isEndLevel = isEndLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrgainType() {
		return orgainType;
	}

	public void setOrgainType(Integer orgainType) {
		this.orgainType = orgainType;
	}

	public Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
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

	public Integer getStreet() {
		return street;
	}

	public void setStreet(Integer street) {
		this.street = street;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}
}

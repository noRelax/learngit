/**
 * @Project:ZGHome
 * @FileName:AreaModel.java
 */
package com.ehome.cloud.area.model;

import java.io.Serializable;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title:AreaModel
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月24日
 * @version:
 */
@Table(name = "t_area")
public class AreaModel extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -30761397064434808L;

	private String areaName;

	private Integer pid;

	private String shortName;

	private String lng;

	private String lat;

	private Boolean level;

	private String position;

	private Integer sort;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Boolean getLevel() {
		return level;
	}

	public void setLevel(Boolean level) {
		this.level = level;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}

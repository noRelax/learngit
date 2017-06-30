package com.ehome.cloud.life.model;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

public class LifeCityModel extends BaseEntity implements Serializable {

	/**
	 * 生活服务地市实体类
	 */
	private static final long serialVersionUID = 1L;

	private Integer provinceId;
	private Integer cityId;
	private Integer lifeCityId;
	private String areaName;
	private String provinceName;
	private String cityName;
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getLifeCityId() {
		return lifeCityId;
	}
	public void setLifeCityId(Integer lifeCityId) {
		this.lifeCityId = lifeCityId;
	}
}

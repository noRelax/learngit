package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
/**
 * 
 * @Title:ConfigModuleCity
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月8日 下午7:06:36
 * @version:
 */
@Table(name="t_module_city")
public class ConfigModuleCity extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567916024533173911L;
	
	@Column(name="module_id")
	private Integer moduleId;
	
	@Column(name="city_id")
	private Integer cityId;
	
	@Column(name="city_name")
    private String cityName;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="province_id")
	private Integer provinceId;
	
	@Column(name="province_name")
	private String provinceName;

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

/**
 * @Project:ZGHome
 * @FileName:ClassifyModel.java
 */
package com.ehome.cloud.puhui.model;

/**
 * @Title:ClassifyModel
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月28日
 * @version:
 */

import java.util.Date;

import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
@Table(name="t_ph_classify")
public class ClassifyModel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7340443781733249026L;

	private String name;

	private Integer sort;

	private Date createtime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
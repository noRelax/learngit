package com.ehome.cloud.life.model;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

public class AreaModel extends BaseEntity implements Serializable {

	/**
	 * 省市实体类
	 */
	private static final long serialVersionUID = 1L;

	private Integer uid;
	private Integer pid;
	private String type;
	private String value;
	private String label;
	private String parent;
	private Integer level;
	
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
}

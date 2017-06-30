package com.ehome.cloud.sys.model;

import java.io.Serializable;

/**
 * Ztree树形数据结构
 * @Title:TreeModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月15日 下午5:12:57
 * @version:
 */
public class TreeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4359312820628999334L;

	private Integer id;
	private String name;
	private Integer pId;
	private Integer orgainType;
	private Boolean isParent;
	private Integer isEndLevel;

	public Integer getIsEndLevel() {
		return isEndLevel;
	}

	public void setIsEndLevel(Integer isEndLevel) {
		this.isEndLevel = isEndLevel;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getOrgainType() {
		return orgainType;
	}

	public void setOrgainType(Integer orgainType) {
		this.orgainType = orgainType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
}

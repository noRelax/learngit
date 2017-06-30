package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 数据字典实体类
 * 
 * @Title:DictionaryModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:28:24
 * @version:
 */
@Table(name = "t_dictionary")
public class Dictionary extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 48872473096718501L;

	@Column(name = "dict_type_id")
	private Integer dictTypeId;
	@Column(name = "dict_key")
	private String dictKey;
	@Column(name = "dict_value")
	private String dictValue;
	@Column(name = "sort")
	private Integer sort;
	@Column(name = "remark")
	private String remark;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "create_user")
	private Integer createUser;

	public Integer getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(Integer dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

}

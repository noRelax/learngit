package com.ehome.cloud.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 数据字典类型实体
 * 
 * @Title:DictionaryTypeModel
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:28:39
 * @version:
 */
@Table(name = "t_dictionary_type")
public class DictionaryType extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8160290504091866151L;

	@Column(name = "type_code")
	private String typeCode;
	@Column(name = "type_name")
	private String typeName;
	@Column(name = "remark")
	private String remark;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

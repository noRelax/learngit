package com.ehome.cloud.sys.dto;

import java.io.Serializable;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:DictionaryDto
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月28日 下午12:15:31
 * @version:
 */
public class DictionaryDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1210256747555133150L;
	private String typeCode;
	private String dictKey;
	private String dictValue;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
}

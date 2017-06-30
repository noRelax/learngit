package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

public class AppInterestDto extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7018866152476101671L;

	private Integer appUserId;

	@CodeField(code = "CODE_MARRY_INTEREST", renderField = "interestName")
	private Integer interestId;

	private String interestName;

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getInterestId() {
		return interestId;
	}

	public void setInterestId(Integer interestId) {
		this.interestId = interestId;
	}

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

}

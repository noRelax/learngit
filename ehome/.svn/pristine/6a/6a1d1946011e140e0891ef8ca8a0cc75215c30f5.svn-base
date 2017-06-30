package com.ehome.cloud.marry.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: GoldCoinDto.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月14日 下午12:26:00
 * @version
 */
public class GoldCoinDto extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3225936793569747722L;
	@CodeField(code = "CODE_GOLDCOIN_CHANGE_REASON", renderField = "goldCoinsChangeName")
	private Integer goldCoinsChangeType;
	private Integer goldCoinsChangeNum;
	private Integer goldCoinsChangeDerection;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date changeTime;
	private Integer appUserId;
	private BigDecimal totalNum;
	private String remark;
	private Integer sourceDevice;
	private String goldCoinsChangeName;

	public Integer getSourceDevice() {
		return sourceDevice;
	}

	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

	public Integer getGoldCoinsChangeType() {
		return goldCoinsChangeType;
	}

	public void setGoldCoinsChangeType(Integer goldCoinsChangeType) {
		this.goldCoinsChangeType = goldCoinsChangeType;
	}

	public Integer getGoldCoinsChangeNum() {
		return goldCoinsChangeNum;
	}

	public void setGoldCoinsChangeNum(Integer goldCoinsChangeNum) {
		this.goldCoinsChangeNum = goldCoinsChangeNum;
	}

	public Integer getGoldCoinsChangeDerection() {
		return goldCoinsChangeDerection;
	}

	public void setGoldCoinsChangeDerection(Integer goldCoinsChangeDerection) {
		this.goldCoinsChangeDerection = goldCoinsChangeDerection;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public String getGoldCoinsChangeName() {
		return goldCoinsChangeName;
	}

	public void setGoldCoinsChangeName(String goldCoinsChangeName) {
		this.goldCoinsChangeName = goldCoinsChangeName;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package com.ehome.cloud.marry.model;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title: GoldCoinRulesModel
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 上午10:23:45
 * @version
 */
@Table(name = "t_marry_goldcoin_rules")
public class GoldCoinRulesModel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 14683916167541270L;
	@Column(name = "gold_chage_reason")
	private String goldCoinChangeReason;
	@Column(name = "gold_chage_number")
	private Integer goldCoinChangeNum;
	@Column(name = "remark")
	private String remark;
	private Integer dayLimit; // 每日限制金币额度

	public String getGoldCoinChangeReason() {
		return goldCoinChangeReason;
	}

	public void setGoldCoinChangeReason(String goldCoinChangeReason) {
		this.goldCoinChangeReason = goldCoinChangeReason;
	}

	public Integer getGoldCoinChangeNum() {
		return goldCoinChangeNum;
	}

	public void setGoldCoinChangeNum(Integer goldCoinChangeNum) {
		this.goldCoinChangeNum = goldCoinChangeNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

}

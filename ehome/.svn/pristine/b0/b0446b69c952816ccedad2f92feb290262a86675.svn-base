package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @Title:MarryShielding
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月26日 上午10:54:02
 * @version:
 */
@Table(name = "t_marry_shielding")
public class MarryShielding extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7893169661866356191L;

	@Column(name = "marry_user_id")
	private Integer marryUserId;
	@Column(name = "create_user_id")
	private Integer createUserId;
	@Column(name = "operator_type")
	private Integer operatorType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "operator_time")
	private Date operatorTime;



	public Integer getMarryUserId() {
		return marryUserId;
	}

	public void setMarryUserId(Integer marryUserId) {
		this.marryUserId = marryUserId;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

}

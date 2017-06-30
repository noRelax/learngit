package com.ehome.cloud.marry.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:MarryUserInterest
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月25日 上午10:00:27
 * @version:
 */
@Table(name = "t_marry_user_interest")
public class MarryUserInterest extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -529849566738495549L;
	
	@Column(name = "app_user_id")
	private Integer appUserId;
	@Column(name = "marry_demand")
	private Integer marryDemand;
	@Column(name = "operator_date")
	private Date operatorDate;
	@Column(name = "source_device")
	private Integer sourceDevice;
	public Integer getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getMarryDemand() {
		return marryDemand;
	}
	public void setMarryDemand(Integer marryDemand) {
		this.marryDemand = marryDemand;
	}
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public Integer getSourceDevice() {
		return sourceDevice;
	}
	public void setSourceDevice(Integer sourceDevice) {
		this.sourceDevice = sourceDevice;
	}

}

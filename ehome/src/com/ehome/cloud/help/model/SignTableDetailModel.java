package com.ehome.cloud.help.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * @Title: SignTableDetail.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月22日 上午11:31:38
 * @version
 */

@Table(name = "t_signledtable_detail")
public class SignTableDetailModel extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -295586988860674382L;
	
	@Column(name = "amount_num")
	private BigDecimal amountNum;
	@Column(name = "fk_helpapplyid")
	private Integer helpApplyId;
	@Column(name = "fk_signid")
	private Integer signId;
	@Column(name = "employer_num")
	private String employerNum;
	@Column(name = "document_num")
	private String documentNum;


	public Integer getHelpApplyId() {
		return helpApplyId;
	}

	public void setHelpApplyId(Integer helpApplyId) {
		this.helpApplyId = helpApplyId;
	}

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public String getEmployerNum() {
		return employerNum;
	}

	public void setEmployerNum(String employerNum) {
		this.employerNum = employerNum;
	}

	public String getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(String documentNum) {
		this.documentNum = documentNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public BigDecimal getAmountNum() {
		return amountNum;
	}

	public void setAmountNum(BigDecimal amountNum) {
		this.amountNum = amountNum;
	}
}

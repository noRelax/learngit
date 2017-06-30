package com.ehome.cloud.member.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;

/**
 * 
 * @Title:MemberRecord
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月24日 下午3:13:00
 * @version:
 */
@Table(name="t_member_record")
public class MemberRecord extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4586230200089898213L;
	
	@Column(name = "from_basic_union_id")
	private Integer fromBasicUnionId;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "audit_status")
	private Integer auditStatus;
	
	@Column(name = "audit_desc")
	private String auditDesc;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "to_basic_union_id")
	private Integer toBasicUnionId;

	public Integer getFromBasicUnionId() {
		return fromBasicUnionId;
	}

	public void setFromBasicUnionId(Integer fromBasicUnionId) {
		this.fromBasicUnionId = fromBasicUnionId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getToBasicUnionId() {
		return toBasicUnionId;
	}

	public void setToBasicUnionId(Integer toBasicUnionId) {
		this.toBasicUnionId = toBasicUnionId;
	}
	
	

}

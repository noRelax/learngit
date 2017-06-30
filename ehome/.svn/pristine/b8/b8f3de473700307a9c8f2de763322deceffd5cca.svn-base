package com.ehome.cloud.member.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ehome.core.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 个人工作简历
 * 
 * @Title:MemberResume
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月1日 上午10:34:57
 * @version:
 */
@Table(name = "t_member_resume")
public class MemberResume extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223798133682486608L;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "member_id")
	private Integer memberId;

	@Column(name = "job")
	private String job;

	@Column(name = "joined_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date joinedDate;

	@Column(name = "release_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date releaseDate;

	@Column(name = "job_content")
	private String jobContent;

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}

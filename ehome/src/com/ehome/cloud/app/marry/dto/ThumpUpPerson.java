package com.ehome.cloud.app.marry.dto;

import java.io.Serializable;

import com.ehome.core.annotation.CodeField;
import com.ehome.core.model.BaseEntity;

/**
 * 赞过的人 值对象
 * 
 * @Title: ThumpUpPerson.java
 * @Description: TODO
 * @author hl@diandianwifi
 * @date 2017年4月24日 下午5:50:13
 * @version
 */
public class ThumpUpPerson extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8289235621873302438L;

	private Integer appUserId; // t_app_use 表主键ID
	private Integer MemberId; // t_member表主键ID
	private String portrait;
	private String nickName;
	private String jobTitle;
	private String workPlace;
	private Integer isLoved;

	@CodeField(code = "CODE_MEMBER_STATUS", renderField = "memberStatuName")
	private Integer memberStatu; // 会员状态

	private String memberStatuName;

	public Integer getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}

	public Integer getMemberId() {
		return MemberId;
	}

	public void setMemberId(Integer memberId) {
		MemberId = memberId;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public Integer getIsLoved() {
		return isLoved;
	}

	public void setIsLoved(Integer isLoved) {
		this.isLoved = isLoved;
	}

	public Integer getMemberStatu() {
		return memberStatu;
	}

	public void setMemberStatu(Integer memberStatu) {
		this.memberStatu = memberStatu;
	}

	public String getMemberStatuName() {
		return memberStatuName;
	}

	public void setMemberStatuName(String memberStatuName) {
		this.memberStatuName = memberStatuName;
	}

}

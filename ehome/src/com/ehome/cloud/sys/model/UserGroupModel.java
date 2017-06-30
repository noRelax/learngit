package com.ehome.cloud.sys.model;

import java.io.Serializable;
import java.util.Date;

import com.ehome.core.annotation.CodeField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户分组实体类
 * @author tcr
 * @version
 * @date 2017年2月6日 下午16:34
 *
 */
public class UserGroupModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private Integer id;
	private String groupName;//分组名称
	private Integer memberNum;//成员数
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//创建时间
	@CodeField(code = "CODE_GROUP_TYPE", renderField = "groupTypeName")
	private Integer groupType;//分组类型
	private String groupTypeName;

	public String getGroupTypeName() {
		return groupTypeName;
	}

	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
}

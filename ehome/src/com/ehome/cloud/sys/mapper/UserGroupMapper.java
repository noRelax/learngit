package com.ehome.cloud.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.model.UserGroupModel;
import com.ehome.core.frame.MyMapper;
import com.ehome.core.util.PageData;

/**
 * @title UserGroupMapper
 * @Description:TODO
 * @author tcr
 * @date 2017年2月7日  11:15
 * @version 1.0
 */
public interface UserGroupMapper extends MyMapper<UserGroupModel> {
	//查询前台用户列表
	List<UserGroupModel> queryQTForList(String keyword);

	//查询后台用户列表
	List<UserGroupModel> queryHTForList(@Param("groupType") Integer groupType,
			@Param("keyword") String keyword);

	//查找id
	UserGroupModel queryById(Integer id);

	//插入数据
	int insertGroup(UserGroupModel userGroupModel);

	//更新数据
	int updateGroup(UserGroupModel userGroupModel);

	//删除数据
	int deleteGroupById(@Param("groupId") Integer userId,
			@Param("userId") Integer groupId);

	//插入用户分组表
	int insertUserGroup(PageData pd);

	void insertUserGroupTmp(@Param("map") Map<String, Object> map);

	List<Integer> queryUserId(@Param("groupId") Integer groupId);

	Integer queryByType(@Param("groupId") Integer groupId);

	List<Integer> queryAppUserId(@Param("groupId") Integer groupId);

	int deleteAppGroupById(@Param("groupId") Integer groupId,
			@Param("userId") Integer userId);

	int deleteById(@Param("id") Integer id);

	List<UserGroupModel> queryHTByList(@Param("groupType") Integer groupType);

	List<UserGroupModel> queryQTByList(@Param("groupType") Integer groupType);

	UserGroupModel queryEditById(@Param("id") Integer id);

	UserGroupModel queryMemberNum(@Param("groupId") Integer groupId);

	UserGroupModel queryQTMemberNum(@Param("groupId") Integer ugId);

	int deleteAllUserGroup(@Param("groupId") Integer groupId,
			@Param("USER_IDS") Integer[] USER_IDS);

	int deleteAppAllUserGroup(@Param("groupId") Integer groupId,
			@Param("USER_IDS") Integer[] USER_IDS);
}

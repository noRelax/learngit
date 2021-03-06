package com.ehome.cloud.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.model.RoleModel;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:RoleMapper
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月6日
 * @version 1.0,2017年2月6日
 * @{tags
 */
public interface RoleMapper extends MyMapper<RoleModel> {

	int insertRole(RoleModel roleModel);

	int updateRole(RoleModel roleModel);

	int deleteRoleById(Integer id);

//	List<RoleModel> queryForList(@Param("keyword") String keyword,@Param("creator") Integer creator);

	RoleModel queryById(Integer id);

	List<RoleModel> queryRoleList(@Param("loginUserId") Integer loginUserId,
			@Param("userId") Integer userId,
			@Param("authRoleList") List<Integer> authRoleList,
			@Param("unAuthRoleList") List<Integer> unAuthRoleList,
			@Param("roleName") String roleName,
			@Param("roleType") Integer roleType);

	List<RoleModel> querySelectRoleList(@Param("userId") Integer userId,
			@Param("authRoleList") List<Integer> authRoleList,
			@Param("unAuthRoleList") List<Integer> unAuthRoleList);

	void insertAuthRole(@Param("userId") Integer userId,
			@Param("authRoleList") List<Integer> authRoleList);

	void deleteAuthRole(@Param("userId") Integer userId,
			@Param("unAuthRoleList") List<Integer> unAuthRoleList);

	List<RoleModel> queryAuthRoleByUserId(@Param("userId") Integer userId,
			@Param("isAppType") Integer isAppType);

	List<RoleModel> queryAuthRoleByAppUserId(@Param("userId") Integer userId,
			@Param("isAppType") Integer isAppType);

	/**
	 * 
	 * @param roleId
	 * @return
	 */
	String queryMenus(Integer roleId);

    /**
     * //TODO 添加方法功能描述
     * @param map
     * @return
     */
    List<RoleModel> queryForList(Map<String, Object> map);
}

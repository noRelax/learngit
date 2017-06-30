package com.ehome.cloud.sys.service;

import java.util.List;

import com.ehome.cloud.sys.model.RoleModel;
import com.ehome.core.frame.BusinessException;

/**
 * 
 * @Title:IRoleService
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月6日
 * @version 1.0,2017年2月6日
 * @{tags
 */
public interface IRoleService {

	int insertRole(RoleModel roleModel) throws Exception;

	int deleteRoleById(Integer id) throws Exception;

	List<RoleModel> queryForList(String keyword, Integer page, Integer rows,Integer creator,List<Integer> list)
			throws Exception;

	RoleModel queryById(Integer id) throws Exception;

	int updateRole(RoleModel roleModel) throws Exception;

	List<RoleModel> queryRoleList(Integer loginUserId, Integer userId,
			List<Integer> authRoleList, List<Integer> unAuthRoleList,
			String roleName, Integer roleType, Integer page, Integer rows)
			throws BusinessException;

	List<RoleModel> querySelectRoleList(Integer userId,
			List<Integer> authRoleList, List<Integer> unAuthRoleList,
			Integer page, Integer rows) throws BusinessException;

	void saveAuthRole(Integer userId, List<Integer> authRoleList,
			List<Integer> unAuthRoleList) throws BusinessException;

	List<RoleModel> queryAuthRoleByUserId(Integer userId, Integer isAppType)
			throws BusinessException;

	List<RoleModel> queryAuthRoleByAppUserId(Integer userId, Integer isAppType)
			throws BusinessException;

	/**
	 * 
	 * @return
	 */
	String queryMenus(Integer roleId) throws Exception;
}

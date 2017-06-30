package com.ehome.cloud.sys.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.mapper.RoleMapper;
import com.ehome.cloud.sys.model.RoleModel;
import com.ehome.cloud.sys.service.IRoleService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.CollectionUtils;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:RoleServiceImpl
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月6日
 * @version 1.0,2017年2月6日
 * @{tags
 */
@Service("roleService")
public class RoleServiceImpl extends BaseService<RoleModel> implements
		IRoleService {

	@Resource
	private RoleMapper roleMapper;

	/**
	 * 
	 * @param roleModel
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertRole(RoleModel roleModel) throws Exception {
		return roleMapper.insertRole(roleModel);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteRoleById(Integer id) throws Exception {
		return roleMapper.deleteRoleById(id);
	}

	/**
	 * 
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<RoleModel> queryForList(String keyword, Integer page,
			Integer rows,Integer creator,List<Integer> list) throws Exception {
		PageHelper.startPage(page, rows);// 分页
		HashMap<String, Object > map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("creator", creator);
		map.put("list", list);
		return roleMapper.queryForList(map);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public RoleModel queryById(Integer id) throws Exception {
		return roleMapper.queryById(id);
	}

	/**
	 * 
	 * @param roleModel
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateRole(RoleModel roleModel) throws Exception {
		return roleMapper.updateRole(roleModel);
	}

	@Override
	public List<RoleModel> queryRoleList(Integer loginUserId, Integer userId,
			List<Integer> authRoleList, List<Integer> unAuthRoleList,
			String roleName, Integer roleType, Integer page, Integer rows)
			throws BusinessException {
		PageHelper.startPage(page, rows, true);
		List<RoleModel> roleList = roleMapper.queryRoleList(loginUserId,
				userId, authRoleList, unAuthRoleList, roleName, roleType);
		return roleList;
	}

	@Override
	public List<RoleModel> querySelectRoleList(Integer userId,
			List<Integer> authRoleList, List<Integer> unAuthRoleList,
			Integer page, Integer rows) throws BusinessException {
		PageHelper.startPage(page, rows, true);
		List<RoleModel> roleList = roleMapper.querySelectRoleList(userId,
				authRoleList, unAuthRoleList);
		return roleList;
	}

	@Override
	public void saveAuthRole(Integer userId, List<Integer> authRoleList,
			List<Integer> unAuthRoleList) throws BusinessException {
		if (CollectionUtils.isNotEmpty(authRoleList)) {
			roleMapper.insertAuthRole(userId, authRoleList);
		}
		if (CollectionUtils.isNotEmpty(unAuthRoleList)) {
			roleMapper.deleteAuthRole(userId, unAuthRoleList);
		}
	}

	@Override
	public List<RoleModel> queryAuthRoleByUserId(Integer userId,
			Integer isAppType) throws BusinessException {
		return roleMapper.queryAuthRoleByUserId(userId, isAppType);
	}

	@Override
	public List<RoleModel> queryAuthRoleByAppUserId(Integer userId,
			Integer isAppType) throws BusinessException {
		return roleMapper.queryAuthRoleByAppUserId(userId, isAppType);
	}

	@Override
	public String queryMenus(Integer roleId) throws Exception {
		return roleMapper.queryMenus(roleId);
	}
}

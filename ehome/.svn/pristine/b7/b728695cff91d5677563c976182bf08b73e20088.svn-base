package com.ehome.cloud.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.mapper.UserGroupMapper;
import com.ehome.cloud.sys.mapper.UserMapper;
import com.ehome.cloud.sys.model.UserGroupModel;
import com.ehome.cloud.sys.service.IUserGroupService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.PageData;
import com.github.pagehelper.PageHelper;

/**
 * 用户分组业务Service处理类
 * 
 * @Title:UserGroupServiceImpl
 * @Description:TODO
 * @author:tcr
 * @date:2017年2月7日
 * @version:
 */
@Service("userGroupService")
public class UserGroupServiceImpl extends BaseService<UserGroupModel> implements
		IUserGroupService {
	@Resource
	private UserGroupMapper userGroupMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public List<UserGroupModel> queryQTForList(String keyword, Integer page,
			Integer rows) throws Exception {
		PageHelper.startPage(page, rows);// 分页
		return userGroupMapper.queryQTForList(keyword);
	}

	@Override
	public List<UserGroupModel> queryHTForList(Integer groupType, String keyword, Integer page,
			Integer rows) throws Exception {
		PageHelper.startPage(page, rows);// 分页
		return userGroupMapper.queryHTForList(groupType,keyword);
	}

	@Override
	public UserGroupModel queryById(Integer id) throws Exception {
		return userGroupMapper.queryById(id);
	}

	@Override
	public int insertGroup(UserGroupModel userGroupModel) throws BusinessException {
		return userGroupMapper.insertGroup(userGroupModel);
	}

	public int updateGroup(UserGroupModel userGroupModel) throws BusinessException {
		return userGroupMapper.updateGroup(userGroupModel);
	}

	@Override
	public int deleteGroupById(Integer groupId,Integer userId) throws Exception {
		return userGroupMapper.deleteGroupById(groupId,userId);
	}

	@Override
	public int insertUserGroup(PageData pd) throws Exception {
		return userGroupMapper.insertUserGroup(pd);
	}

	@Override
	public List<Integer> queryUserId(Integer groupId) throws BusinessException {
		return userGroupMapper.queryUserId(groupId);
	}

	@Override
	public Integer queryByType(Integer groupId) throws BusinessException {
		
		return userGroupMapper.queryByType(groupId);
	}

	@Override
	public List<Integer> queryAppUserId(Integer groupId) throws BusinessException {
		
		return userGroupMapper.queryAppUserId(groupId);
	}

	@Override
	public int deleteAppGroupById(Integer groupId, Integer userId)
			throws Exception {
		
		return userGroupMapper.deleteAppGroupById(groupId,userId);
	}

	@Override
	public int deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return userGroupMapper.deleteById(id);
	}

	@Override
	public List<UserGroupModel> queryHTByList(Integer groupType, int page,
			int rows) throws BusinessException {
		PageHelper.startPage(page, rows);// 分页
		return userGroupMapper.queryHTByList(groupType);
	}

	@Override
	public List<UserGroupModel> queryQTByList(Integer groupType, int page,
			int rows) throws BusinessException {
		PageHelper.startPage(page, rows);// 分页
		return userGroupMapper.queryQTByList(groupType);
	}

	@Override
	public UserGroupModel queryEditById(Integer id)
			throws BusinessException {
		return userGroupMapper.queryEditById(id);
	}

	@Override
	public UserGroupModel queryMemberNum(Integer groupId)
			throws BusinessException {
		return userGroupMapper.queryMemberNum(groupId);
	}

	@Override
	public UserGroupModel queryQTMemberNum(Integer ugId)
			throws BusinessException {
		return userGroupMapper.queryQTMemberNum(ugId);
	}

	@Override
	public int deleteAllUserGroup(Integer groupId, Integer[] USER_IDS)
			throws BusinessException {
		// TODO Auto-generated method stub
		return userGroupMapper.deleteAllUserGroup(groupId,USER_IDS);
	}

	@Override
	public int deleteAppAllUserGroup(Integer groupId, Integer[] USER_IDS)
			throws BusinessException {
		// TODO Auto-generated method stub
		return userGroupMapper.deleteAppAllUserGroup(groupId,USER_IDS);
	}
}

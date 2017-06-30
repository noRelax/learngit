package com.ehome.cloud.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.mapper.UserMapper;
import com.ehome.cloud.sys.model.UserModel;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

/**
 * 用户管理业务Service处理类
 * 
 * @Title:UserServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月20日 上午10:45:02
 * @version:
 */
@Service("userService")
public class UserServiceImpl extends BaseService<UserModel> implements
		IUserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public List<UserModel> queryForList(String userName, String roleName,
			String orgName, Integer deptId, Integer userId, Integer start,
			Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> userList = userMapper.queryForList(userName, roleName,
				orgName, deptId, userId);
		return userList;
	}

	@Override
	public int insertUser(UserModel userModel) throws BusinessException {
		try {
			return userMapper.insertUser(userModel);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Duplicate entry")
					&& e.getMessage().contains("user_account")) {
				throw new BusinessException("登录帐号:" + userModel.getUserAccount()
						+ "已存在！");
			}
			throw e;
		}
	}

	@Override
	public int updateUser(UserModel userModel) throws BusinessException {
		try {
			return userMapper.updateUser(userModel);
		} catch (RuntimeException e) {
			if (e.getMessage().contains("Duplicate entry")
					&& e.getMessage().contains("user_account")) {
				throw new BusinessException("登录帐号:" + userModel.getUserAccount()
						+ "已存在！");
			}
			throw e;
		}
	}

	@Override
	public int deleteUser(Integer id) throws BusinessException {
		return userMapper.deleteUserById(id);
	}

	@Override
	public UserModel queryByLoginName(String loginName)
			throws BusinessException {
		return userMapper.queryByLoginName(loginName);
	}

	@Override
	public UserModel queryById(Integer id) throws BusinessException {
		return userMapper.queryById(id);
	}

	@Override
	public UserModel checkUserAndPassWord(String loginName, String password)
			throws BusinessException {
		return userMapper.checkUserAndPassWord(loginName, password);
	}

	@Override
	public int updateStatus(Integer status, Integer id)
			throws BusinessException {
		return userMapper.updateStatus(status, id);
	}

	@Override
	public int updatePassword(String salt, String userPassword, Integer id)
			throws BusinessException {
		return userMapper.updatePassword(id, userPassword, salt);
	}
    
	@Override
	public void insertMember(Integer groupId,Integer[] List)
			throws BusinessException {
			userMapper.insertMember(groupId,List);
	}
	
	@Override
	public List<UserModel> queryMemberList(List<Integer> userIdList,Integer groupId, Integer start,
			Integer pageSize)
			throws Exception {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> queryMemberList = userMapper.queryMemberList(userIdList,groupId);
		return queryMemberList;
	}

	@Override
	public void insertAppMember(Integer groupId, Integer[] List)
			throws BusinessException {
		userMapper.insertAppMember(groupId,List);
	}

	@Override
	public List<UserModel> queryAppMemberList(List<Integer> userIdList,Integer groupId,Integer start,
			Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> queryAppMemberList = userMapper.queryAppMemberList(userIdList,groupId);
		return queryAppMemberList;
	}

	@Override
	public void insertAppMembers(Integer groupId, Integer[] USER_IDS)
			throws BusinessException {
		userMapper.insertAppMembers(groupId,USER_IDS);
	}

	@Override
	public void insertMembers(Integer groupId, Integer[] USER_IDS)
			throws BusinessException {
		userMapper.insertMembers(groupId,USER_IDS);
	}

	@Override
	public List<UserModel> queryForUserGroupList(String userName,
			Integer start, Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> userGroupList = userMapper.queryForUserGroupList(userName);
		return userGroupList;
	}

	@Override
	public List<UserModel> queryHTMemberList(List<Integer> userIdList,String userName,
			Integer groupId, Integer start, Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> houtaiMemberList = userMapper.queryHTMemberList(userIdList,userName,groupId);
		return houtaiMemberList;
	}

	@Override
	public List<UserModel> queryQTMemberList(List<Integer> userIdList,
			String userName, Integer groupId, Integer start, Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<UserModel> qiantaiMemberList = userMapper.queryQTMemberList(userIdList,userName,groupId);
		return qiantaiMemberList;
	}

	@Override
	public List<String> selectByBaseUnionId(Integer baseUnionId)
			throws Exception { 
		// TODO Auto-generated method stub
		return userMapper.selectByBaseUnionId(baseUnionId);
	}

	@Override
	public int seletctUpperUnionId(Integer baseUnionId) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.seletctUpperUnionId(baseUnionId);
	}

	@Override
	public List<String> selectUserByDeptId(List<String> deptId) throws BusinessException {
		return userMapper.selectUserByDeptId(deptId);
	}

	@Override
	public Integer selectByDeptId(Integer id) throws BusinessException {
		// TODO Auto-generated method stub
		return userMapper.selectByDeptId(id);
	}

	@Override
	public Integer selectSuperUserId(Integer deptId)
			throws BusinessException {
		// TODO Auto-generated method stub
		return userMapper.selectSuperUserId(deptId);
	}
}

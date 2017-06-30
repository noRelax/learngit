package com.ehome.cloud.sys.service;

import java.util.List;

import com.ehome.cloud.sys.model.UserModel;
import com.ehome.core.frame.BusinessException;

/**
 * 
 * @Title:IUserService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月20日 上午10:43:40
 * @version:
 * 
 */
public interface IUserService {

	int insertUser(UserModel userModel) throws BusinessException;

	int updateUser(UserModel userModel) throws BusinessException;

	int deleteUser(Integer id) throws BusinessException;

	List<UserModel> queryForList(String userName, String roleName,
			String orgName, Integer deptId, Integer userId, Integer start,
			Integer pageSize) throws BusinessException;

	UserModel queryByLoginName(String loginName) throws BusinessException;

	UserModel queryById(Integer id) throws BusinessException;

	UserModel checkUserAndPassWord(String loginName, String password)
			throws BusinessException;

	int updateStatus(Integer status, Integer id) throws BusinessException;

	int updatePassword(String salt, String userPassword, Integer id)
			throws BusinessException;

	void insertMember(Integer groupId, Integer[] list) throws BusinessException;

	List<UserModel> queryMemberList(List<Integer> userIdList, Integer groupId,
			Integer start, Integer pageSize) throws Exception;

	void insertAppMember(Integer groupId, Integer[] List)
			throws BusinessException;

	List<UserModel> queryAppMemberList(List<Integer> userIdList,
			Integer groupId, Integer start, Integer pageSize)
			throws BusinessException;

	void insertAppMembers(Integer groupId, Integer[] USER_IDS)
			throws BusinessException;

	void insertMembers(Integer groupId, Integer[] USER_IDS)
			throws BusinessException;

	List<UserModel> queryForUserGroupList(String userName, Integer start,
			Integer pageSize) throws BusinessException;

	List<UserModel> queryHTMemberList(List<Integer> userIdList,
			String userName, Integer groupId, Integer start, Integer pageSize)
			throws BusinessException;

	List<UserModel> queryQTMemberList(List<Integer> userIdList,
			String userName, Integer groupId, Integer start, Integer pageSize)
			throws BusinessException;

	List<String> selectByBaseUnionId(Integer baseUnionId) throws Exception;

	int seletctUpperUnionId(Integer baseUnionId) throws Exception;

	List<String> selectUserByDeptId(List<String> deptIdList)
			throws BusinessException;

	Integer selectByDeptId(Integer id) throws BusinessException;

	Integer selectSuperUserId(Integer deptId) throws BusinessException;
}

package com.ehome.cloud.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.dto.AppUserDto;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.core.frame.MyMapper;

/**
 * App用户Mapper
 * 
 * @Title:AppUserMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月13日 上午10:41:39
 * @version:
 */
public interface AppUserMapper extends MyMapper<AppUserModel> {

	/**
	 * 登录帐号查询
	 * 
	 * @param userAccount
	 * @return
	 */
	AppUserDto queryLoginAccount(String userAccount);

	/**
	 * 
	 * //TODO 根据第三方传入的openId和fromType查询
	 * @param userAccount
	 * @param fromType
	 * @return
	 */
	AppUserDto queryByThirdPath(@Param("userAccount") String userAccount,
			@Param("fromType") Integer fromType);

	/**
	 * 根据openId fromType isThird查找第三方账户记录
	 * //TODO 添加方法功能描述
	 * @param openId
	 * @param fromType
	 * @return
	 */
	AppUserDto queryByOpenId(@Param("openId") String openId,
			@Param("fromType") Integer fromType);

	/**
	 * 
	 * @param roleMap
	 * @return
	 */
	int saveAppRole(Map<String, Object> roleMap);

	/**
	 * 
	 * @param userName
	 * @param roleName
	 * @param orgName
	 * @param deptId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> queryForList(@Param("keyword") String keyword,
			@Param("blackTypeId") Integer blackTypeId);

	/**
	 * @param app_user_id
	 * @return
	 * 
	 */
	int deleteAppRole(Integer app_user_id);

	/**
	 * @param map
	 * @return
	 * 
	 */
	int deleteCode(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 * 
	 */
	int insertCode(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 * 
	 */
	List<Map<String, Object>> querySysUser(Map<String, String> map);

	/**
	 * @param map
	 * @return
	 * 
	 */
	int insertSysUser(Map<String, String> map);

	/**
	 * @param map
	 * @return
	 * 
	 */
	int updateSysUser(Map<String, String> map);

	/**
	 * 更新状态
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	int updateStatus(@Param("status") String status, @Param("ids") String[] ids);

	/**
	 * 查询前端用户信息
	 * @param id
	 * @return
	 */
	Map<String, Object> queryInfo(@Param("id") int id);

	/**
	 * 更新黑名单类型
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	int updateBlackType(@Param("id") int id, @Param("blackType") int blackType);
}

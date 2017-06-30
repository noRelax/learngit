package com.ehome.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ehome.cloud.app.marry.service.IAppMarryService;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.cloud.sys.dto.AppUserDto;
import com.ehome.cloud.sys.mapper.AppUserMapper;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.HttpUtils;
import com.ehome.core.util.MD5;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.SpringPropertiesUtil;
import com.ehome.core.util.StringUtils;
import com.github.pagehelper.PageHelper;

/**
 * app用户服务
 * 
 * @Title:AppUserServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月13日 上午10:43:51
 * @version:
 */
@Service("appUserService")
public class AppUserServiceImpl extends BaseService<AppUserModel> implements
		IAppUserService {

	@Resource
	private AppUserMapper appUserMapper;

	@Resource
	private IMemberService memberService;

	@Resource
	private IAppMarryService appMarryService;

	@Resource
	private IGoldCoinService goldCoinService;

	@Override
	public AppUserDto queryByLoginName(String userAccount) {
		return appUserMapper.queryLoginAccount(userAccount);
	}

	/**
	 * 
	 * @param roleMap
	 * @return
	 */
	@Override
	public int saveAppRole(Map<String, Object> roleMap) {
		return appUserMapper.saveAppRole(roleMap);
	}

	@Override
	public List<Map<String, Object>> queryForList(String keyword,
			Integer blackTypeId, Integer start, Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<Map<String, Object>> appUserList = appUserMapper.queryForList(
				keyword, blackTypeId);
		return appUserList;
	}

	/**
	 * 
	 * @param app_user_id
	 * @return
	 */
	@Override
	public int deleteAppRole(Integer app_user_id) {
		return appUserMapper.deleteAppRole(app_user_id);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int deleteCode(Map<String, Object> map) {
		return appUserMapper.deleteCode(map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int insertCode(Map<String, Object> map) {
		return appUserMapper.insertCode(map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String, Object>> querySysUser(Map<String, String> map) {
		return appUserMapper.querySysUser(map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int insertSysUser(Map<String, String> map) {
		return appUserMapper.insertSysUser(map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public int updateSysUser(Map<String, String> map) {
		return appUserMapper.updateSysUser(map);
	}

	@Override
	public void workForLogin(AppUserDto appUserModel, String imei,
			Integer source_device) {
		// 登录后更新用户登录状态
		final Integer userId = appUserModel.getId();
		if (null != appUserModel.getCurLoginTime())
			appUserModel.setLastLoginTime(appUserModel.getCurLoginTime());// 非第一次登录则记录上次登录时间
		appUserModel.setCurLoginTime(new Date());// 每次登录都记录本次登录时间
		appUserModel.setImei(imei);
		AppUserModel appUser = new AppUserModel();
		BeanUtils.copyProperties(appUserModel, appUser);
		if (NumberUtils.isNull(appUserModel.getSourceDevice())
				|| appUserModel.getSourceDevice().equals(1)) {
			appUser.setSourceDevice(source_device);
		}
		this.updateNotNull(appUser);
		if (null == appUserModel.getLastLoginTime()
				&& appUserModel.getIsMember().intValue() == 1) {// 第一次登录的时候且是会员用户
			// 异步更新会员激活状态
			new Thread(new Runnable() {
				@Override
				public void run() {
					Member member = memberService
							.queryMemberByAppUserId(userId);
					if (null != member && member.getIsActive().intValue() == 0) {
						member.setIsActive(1);
						memberService.updateNotNull(member);
					}
				};
			}).start();
		}
	}

	/**
	 * 
	 * @param map
	 * @return 
	 */
	@Override
	public void updateUserInfo(Map<String, Object> map,
			AppUserModel appUserModel, MarryMemberModel marryMemberModel) {
		String defaultValue = null;
		String oldOsIp = StringUtils.obj2String(
				SpringPropertiesUtil.get("oldOsIp"), null);
		AppUserModel appUserModelTemp = selectByKey(appUserModel.getId());
		//婚恋放开一下代码  
		MarryMemberModel marryMemberModelTemp = appMarryService
				.selectOne(new MarryMemberModel(appUserModel.getId()));
		if (marryMemberModelTemp == null) {
			if (appUserModel.getHeight() != null
					&& !"".equals(appUserModel.getHeight())) {
				appMarryService.save(marryMemberModel);
				goldCoinService.completeInfoAddGoldCoins(appUserModel.getId());
				if (StringUtils.isNotBlank(appUserModel.getPortrait())) {
					if (!NumberUtils.isNull(appUserModel.getId())
							&& NumberUtils.neZero(appUserModel.getId())) {
						goldCoinService.uploadPortraitAddGoldCoins(appUserModel
								.getId());
					} else {
						AppUserModel entity = new AppUserModel();
						entity.setUserAccount(appUserModelTemp.getUserAccount());
						AppUserModel model = this.selectOne(entity);
						goldCoinService.uploadPortraitAddGoldCoins(model
								.getId());
					}
				}
			}
		} else {
			marryMemberModel.setId(marryMemberModelTemp.getId());
			appMarryService.updateNotNull(marryMemberModel);//更新婚恋信息
		}
		appUserModel.setUserAccount(null);
		updateNotNull(appUserModel);//更新t_app_user个人信息
		Map<String, String> sysMap = new HashMap<String, String>();
		sysMap.put("nickname",
				MapUtils.getString(map, "nick_name", defaultValue));
		if (MapUtils.getString(map, "sex", defaultValue) != null
				&& MapUtils.getString(map, "sex", defaultValue).equals("2")) {
			sysMap.put("sex", "0");
		}
		sysMap.put("cityId", MapUtils.getString(map, "city", defaultValue));
		sysMap.put("cityName",
				MapUtils.getString(map, "cityName", defaultValue));
		sysMap.put("signature",
				MapUtils.getString(map, "signature", defaultValue));
		//		sysMap.put(
		//				"userId",
		//				MapUtils.getString(map, "user_account",
		//						appUserModelTemp.getUserAccount()));
		sysMap.put("ID", MapUtils.getString(map, "id", defaultValue));
		sysMap.put("portrait",
				MapUtils.getString(map, "portrait", defaultValue));
		String result = HttpUtils.URLGet("http://" + oldOsIp
				+ "/ehome/appMyhome/updateUserinfo.do", sysMap, "UTF-8");
		if (StringUtils.isNoneBlank(result)) {
			HashMap<String, String> resultMap = JSON.parseObject(result,
					new TypeReference<HashMap<String, String>>() {
					});
			//System.out.println(resultMap);
			if (!"2000000".equals(MapUtils.getString(resultMap, "status",
					defaultValue))) {
				appUserModel.setUserAccount(null);
				updateNotNull(appUserModelTemp);
				return;
			}
		}
		appMarryService.insertInterests(map);//插入兴趣爱好表
		//同步会员的婚恋状况
		Member memberTemp = new Member(appUserModel.getId());
		Member member = memberService.selectOne(memberTemp);
		if (member != null) {
			member.setMaritalStatus(marryMemberModel.getMaritalStatus());
			member.setSex(marryMemberModel.getSex());
			memberService.updateNotNull(member);
		}
	}

	/**
	 * 更新状态
	 */
	@Override
	public int updateStatus(String status, String[] ids) {
		// TODO Auto-generated method stub
		return appUserMapper.updateStatus(status, ids);
	}

	/**
	 * 查询前端用户信息
	 */
	@Override
	public Map<String, Object> queryInfo(int id) {
		// TODO Auto-generated method stub
		return appUserMapper.queryInfo(id);
	}

	@Override
	public int updateBlackType(int id, int blackType) {
		// TODO Auto-generated method stub
		return appUserMapper.updateBlackType(id, blackType);
	}

	/**
	 * //TODO 添加override说明
	 * @see com.ehome.cloud.sys.service.IAppUserService#queryByThirdPath(java.lang.String, java.lang.Integer)
	 **/
	@Override
	public AppUserDto queryByThirdPath(String userAccount, Integer fromType) {
		return appUserMapper.queryByThirdPath(userAccount, fromType);
	}

	/**
	 * //TODO 添加override说明
	 * @see com.ehome.cloud.sys.service.IAppUserService#queryByThridPathLogin(java.lang.String, java.lang.Integer)
	 **/
	@Override
	public AppUserDto queryByThridPathLogin(String openId, Integer fromType) {
		AppUserDto appUserDto = appUserMapper.queryByOpenId(openId, fromType);
		//已有第三方帐号此处做分支判断：1.该帐号已经绑定手机号码 2.未绑定手机号码
		if (null != appUserDto) {
			if (StringUtils.isNotBlank(appUserDto.getUserMobile())) {
				//已绑定手机号码要根据手机号码找到绑定的账户记录,之后就当成普通的app账户来执行认证操作了
				return appUserMapper.queryLoginAccount(appUserDto
						.getUserMobile());
			} else {
				//未绑定手机号码 直接返回这个对象,调用的是第三方Realm来执行认证操作。
				return appUserDto;
			}
		}
		return null;
	}

	/**
	 * //TODO 添加override说明
	 * @throws Exception 
	 * @see com.ehome.cloud.sys.service.IAppUserService#bindMobileByThirdPath(java.lang.Integer, java.lang.String, java.lang.String)
	 **/
	@Override
	public AppUserModel bindMobileByThirdPath(Integer id, String userPassword,
			String userMobile) throws Exception {
		String oldOsIp = StringUtils.obj2String(
				SpringPropertiesUtil.get("oldOsIp"), null);
		Map<String, String> map = new HashMap<>();
		//获取第三方应用登录的记录
		AppUserModel user = this.selectByKey(id);
		user.setUserMobile(userMobile);
		this.updateNotNull(user);
		//先判断手机号码是否已存在
		AppUserModel queryUser = new AppUserModel();
		queryUser.setUserAccount(userMobile);
		queryUser.setIsThird(0);
		queryUser = this.selectOne(queryUser);
		if (null == queryUser) {
			//创建一个与第三方应用绑定的新帐号
			AppUserModel addUser = new AppUserModel();
			addUser.setNickName(userMobile);
			addUser.setUserAccount(userMobile);
			addUser.setUserMobile(userMobile);
			addUser.setCreateTime(new Date());
			String salt = UUID.randomUUID().toString().replaceAll("-", "");
			Md5Hash md5Hash = new Md5Hash(MD5.md5(userPassword.trim()), salt, 2);
			addUser.setSalt(salt);
			addUser.setUserPassword(md5Hash.toString());
			addUser.setSourceDevice(user.getSourceDevice());
			addUser.setStatus(0);
			addUser.setIsMember(0);
			addUser.setIsThird(0);
			this.saveNotNull(addUser);
			// 配置角色
			Map<String, Object> roleMap = new HashMap<>();
			List<Map<String, Object>> list = new ArrayList<>();
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("app_user_id", addUser.getId());
			// FIXME 暂时固定一个角色id
			tempMap.put("role_id", 21);
			list.add(tempMap);
			roleMap.put("list", list);
			this.saveAppRole(roleMap);
			map = this.getParam(map, addUser);
			map = this.getParam(map, user);
			String rpString = HttpUtils.URLPost("http://" + oldOsIp
					+ "/ehome/appRegister/bundlePhone.do", map, "UTF-8");
			if (StringUtils.isNoneBlank(rpString)) {
				HashMap<String, String> resultMap = JSON.parseObject(rpString,
						new TypeReference<HashMap<String, String>>() {
						});
				//System.out.println(resultMap);
				if (!"2000000".equals(MapUtils.getString(resultMap, "status",
						""))) {
					//System.out.println("rp>>" + rpString);
					throw new Exception("调用更新旧库接口出错");
				}
			}
		} else {
			String salt = UUID.randomUUID().toString().replaceAll("-", "");
			Md5Hash md5Hash = new Md5Hash(MD5.md5(userPassword.trim()), salt, 2);
			queryUser.setSalt(salt);
			queryUser.setUserPassword(md5Hash.toString());
			queryUser.setSourceDevice(user.getSourceDevice());
			queryUser.setIsThird(0);
			this.updateNotNull(queryUser);
		}
		return queryUser;
	}

	public Map<String, String> getParam(Map<String, String> resultMap,
			AppUserModel user) {
		resultMap.put(user.getIsThird().equals(0) ? "id" : "t_id", user.getId()
				+ "");
		resultMap.put(user.getIsThird().equals(0) ? "user_id" : "t_user_id",
				user.getUserAccount());
		resultMap.put(user.getIsThird().equals(0) ? "password" : "t_password",
				user.getUserPassword());
		resultMap.put(user.getIsThird().equals(0) ? "name" : "t_name",
				StringUtils.isNotBlank(user.getUserName()) ? user.getUserName()
						: "");
		resultMap.put(user.getIsThird().equals(0) ? "phone" : "t_phone",
				user.getUserMobile());
		resultMap.put(user.getIsThird().equals(0) ? "portrait" : "t_portrait",
				StringUtils.isNotBlank(user.getPortrait()) ? user.getPortrait()
						: "");
		resultMap.put(user.getIsThird().equals(0) ? "nickname" : "t_nickname",
				user.getNickName());
		resultMap.put(user.getIsThird().equals(0) ? "createDate"
				: "t_createDate", DateUtils.getTime(user.getCreateTime()));
		resultMap.put(user.getIsThird().equals(0) ? "IDcard" : "t_IDcard",
				StringUtils.isNotBlank(user.getIdCard()) ? user.getIdCard()
						: "");
		resultMap.put(
				user.getIsThird().equals(0) ? "macType" : "t_macType",
				!NumberUtils.isNull(user.getSourceDevice()) ? user
						.getSourceDevice() + "" : "");
		resultMap.put(user.getIsThird().equals(0) ? "invitation_code"
				: "t_invitation_code", StringUtils.isNotBlank(user
				.getInvitationCode()) ? user.getInvitationCode() : "");
		resultMap.put(user.getIsThird().equals(0) ? "reg_cod" : "t_reg_cod",
				StringUtils.isNotBlank(user.getRegCode()) ? user.getRegCode()
						: "");
		resultMap.put(user.getIsThird().equals(0) ? "invitation_code_from"
				: "t_invitation_code_from", StringUtils.isNotBlank(user
				.getInvitationCodeFrom()) ? user.getInvitationCodeFrom() : "");
		resultMap.put(user.getIsThird().equals(0) ? "is_third" : "t_is_third",
				user.getIsThird() + "");
		return resultMap;
	}
}

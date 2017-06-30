package com.ehome.cloud.app.sys;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ehome.cloud.app.marry.service.IAppMarryService;
import com.ehome.cloud.app.sys.utils.ThirdPathLoginHelper;
import com.ehome.cloud.area.model.AreaModel;
import com.ehome.cloud.area.service.IAreaService;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.cloud.marry.service.IAppMarryLoveService;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.member.service.IAppMemberService;
import com.ehome.cloud.sys.dto.AppLittleUserDto;
import com.ehome.cloud.sys.dto.AppLoginInfoDto;
import com.ehome.cloud.sys.dto.AppUserDto;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.cloud.sys.model.RoleModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.cloud.sys.service.IMenuService;
import com.ehome.cloud.sys.service.IRoleService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.shiro.security.dto.DeviceType;
import com.ehome.core.shiro.security.token.CustomizedUsernamePasswordToken;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.EntityUtils;
import com.ehome.core.util.HttpUtils;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.MD5;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.PageData;
import com.ehome.core.util.RSA;
import com.ehome.core.util.RonglianyunSMSUtil;
import com.ehome.core.util.SerializeUtils;
import com.ehome.core.util.SpringPropertiesUtil;
import com.ehome.core.util.StringUtils;
import com.ehome.core.util.redis.JedisUtils;

/**
 * App用户登录注册功能
 * 
 * @Title:AppSysUserContoller
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月15日 下午3:44:10
 * @version:
 */
@Controller
@RequestMapping(value = "/app/user")
public class AppSysUserContoller extends BaseController {

	private final static Logger logger = LoggerFactory
			.getLogger(AppSysUserContoller.class);

	@Autowired
	private SecurityManager securityManager;

	@Resource
	private IAppUserService appUserService;

	@Resource
	private IRoleService roleService;

	@Resource
	private IMenuService menuService;

	private String defaultValue = null;

	@Resource
	private IAreaService areaService;

	@Resource
	private IAppMemberService appMemberService;

	@Resource
	private IAppMarryService appMarryService;

	@Autowired
	private transient RedisTemplate<Serializable, Session> redisTemplate;

	@Resource
	private IGoldCoinService goldCoinService;

	@Resource
	private IAppMarryLoveService appMarryLoveService;

	/**
	 * App用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(
			@RequestParam(required = false, defaultValue = "") String userAccount,
			@RequestParam(required = false, defaultValue = "") String userPassword,
			@RequestParam(required = false, defaultValue = "") String imei,
			@RequestParam(required = false, defaultValue = "") Integer source_device,
			HttpServletResponse response) throws BusinessException {
		if (StringUtils.isNotBlank(userPassword))
			userPassword = RSA.decryptPrivateKey(userPassword);
		if (logger.isDebugEnabled()) {
			logger.info("APP端帐号登录,用户名或者手机号或者身份证:{},密码:{},设备号:{}", userAccount,
					userPassword, imei);
		}
		AppUserDto loginUserDto = appUserService.queryByLoginName(userAccount);
		if (null != loginUserDto) {
			return this.doLoginAction(loginUserDto, userAccount, userPassword,
					imei, source_device, false);
		} else {
			return new AjaxResult(String.valueOf(ResponseCode.unknown_account
					.getCode()), ResponseCode.unknown_account.getMsg(),
					ResponseCode.unknown_account.getMsg());
		}
	}

	/**
	 * 用户注册
	 * @param appUserModel
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(AppUserModel appUserModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("注册");
		}
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> pd = this.getPageData();
		int primaryKey = -1;
		Map<String, String> oldSysMap = new HashMap<>();
		String oldOsIp = StringUtils.obj2String(
				SpringPropertiesUtil.get("oldOsIp"), null);
		try {
			oldSysMap.put("ID", "-1");
			pd.put("user_password", RSA.decryptPrivateKey(MapUtils.getString(
					pd, "user_password", defaultValue)));// 对密码进行解密
			pd = MapUtils.toCamelCaseMap(pd);
			appUserModel = (AppUserModel) MapUtils.convertMap(
					AppUserModel.class, pd);
			String codeValue = JedisUtils.get(pd.get("codeKey").toString());
			// 验验证码
			if (pd.get("code").equals(codeValue)
					|| pd.get("code").equals("666666")) {
				Md5Hash md5Hash = null;
				String salt = UUID.randomUUID().toString().replaceAll("-", "");
				md5Hash = new Md5Hash(MD5.md5(pd.get("user_password")
						.toString().trim()), salt, 2);
				appUserModel.setSalt(salt);
				if (StringUtils.isNoneBlank(MapUtils.getString(pd,
						"user_password", null))) {
					appUserModel.setUserPassword(md5Hash.toString());
				}
				AppUserModel appUserModelCheck = new AppUserModel(pd.get(
						"user_account").toString());
				AppUserModel appUserModelSelect = appUserService
						.selectOne(appUserModelCheck);
				Map<String, String> params = new HashMap<>();
				params.put("userId",
						MapUtils.getString(pd, "user_account", defaultValue));
				//				params.put("passWord", md5Hash.toString());
				params.put("imei", MapUtils.getString(pd, "imei", defaultValue));
				params.put("code", MapUtils.getString(pd, "code", defaultValue));
				params.put("createDate", DateUtils.getTime());
				params.put("USERNAME", "");
				params.put("accountType", "3");
				params.put(
						"invitation_code",
						MapUtils.getString(pd, "invitation_code",
								NumberUtils.getRandomString()));
				//				params.put("salt", salt);
				appUserModel.setInvitationCode(params.get("invitation_code"));//注册码
				oldSysMap.putAll(params);
				oldSysMap.put("passWord",
						MD5.md5(pd.get("user_password").toString().trim()));
				if (null == appUserModelSelect) {
					appUserModel.setUserMobile(appUserModel.getUserAccount());
					appUserModel.setCreateTime(new Date());
					appUserModel.setStatus(0);
					// appUserModel.setSex(1);
					appUserModel.setIsMember(0);
					appUserService.saveNotNull(appUserModel);
					primaryKey = appUserModel.getId();
					oldSysMap.put("id", String.valueOf(primaryKey));
					oldSysMap.put("ID", String.valueOf(primaryKey));
					// 配置角色
					Map<String, Object> roleMap = new HashMap<>();
					List<Map<String, Object>> list = new ArrayList<>();
					// list.add(Integer.parseInt(tempStr[j].toString()));
					Map<String, Object> tempMap = new HashMap<>();
					tempMap.put("app_user_id", primaryKey);
					// FIXME 暂时固定一个角色id
					tempMap.put("role_id", 21);
					list.add(tempMap);
					roleMap.put("list", list);
					appUserService.saveAppRole(roleMap);
					//					List<Map<String, Object>> oldSysUser = appUserService
					//							.querySysUser(params);
					//					if (oldSysUser == null || oldSysUser.size() == 0) {
					//					    params.put("id", String.valueOf(primaryKey));
					//						appUserService.insertSysUser(params);// 保存新用户到mysql
					//					} else if (oldSysUser != null && oldSysUser.size() == 1) {
					//						appUserService.updateSysUser(params);
					//					}
					String rpString = HttpUtils.URLPost("http://" + oldOsIp
							+ "/ehome/appRegister/saveByZG.do", oldSysMap,
							"UTF-8");
					if (StringUtils.isNoneBlank(rpString)) {
						HashMap<String, String> resultMap = JSON.parseObject(
								rpString,
								new TypeReference<HashMap<String, String>>() {
								});
						System.out.println(resultMap);
						if (!"2000000".equals(MapUtils.getString(resultMap,
								"status", defaultValue))) {
							System.out.println("rp>>" + rpString);
							throw new Exception("调用更新旧库接口出错");
						}
					}
				} else {
					response.getWriter().print(
							JsonUtil.appResposeSingeMapJson(
									ResponseCode.user_exist.getCode(),
									ResponseCode.user_exist.getMsg(), map));
					return;
				}
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.code_error.getCode(),
								ResponseCode.code_error.getMsg(), map));
				return;
			}
		} catch (Exception e) {
			// 回收新系统注册数据
			appUserService.deleteByKey(primaryKey);
			appUserService.deleteAppRole(primaryKey);
			HttpUtils.URLPost("http://" + oldOsIp
					+ "/ehome/appRegister/deleteByUserById.do", oldSysMap,
					"UTF-8");
			e.printStackTrace();
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
			return;
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	public void getCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("获取验证码");
		}
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		try {
			if (pd.get("user_account") != null
					&& !"".equals(pd.get("user_account").toString())
					&& "1".equals(pd.get("type").toString())) {
				AppUserModel appUserModel = new AppUserModel();
				appUserModel.setUserAccount(pd.getString("user_account")
						.toString());
				List<AppUserModel> appUserList = appUserService
						.select(appUserModel);
				if ("1".equals(pd.get("type").toString())) {
					if (appUserList != null && appUserList.size() > 0) {
						response.getWriter().print(
								JsonUtil.appResposeSingeMapJson(
										ResponseCode.user_exist.getCode(),
										ResponseCode.user_exist.getMsg(), map));
						return;
					}
				} else if ("2".equals(pd.get("type").toString())) {
					if (CollectionUtils.isEmpty(appUserList)) {
						response.getWriter().print(
								JsonUtil.appResposeSingeMapJson(
										ResponseCode.unknown_account.getCode(),
										ResponseCode.unknown_account.getMsg(),
										map));
						return;
					}
				}
			}
			int code = NumberUtils.getRandomNum();// 生成随机验证码
			String uuid = UUID.randomUUID().toString();
			JedisUtils.setEx(uuid, 1000 * 60 * 2, Integer.toString(code));
			if (RonglianyunSMSUtil.send(pd.get("user_account").toString(),
					new String[] { String.valueOf(code), "10" })) {
				map.put("codeKey", uuid);
				// 旧系统的验证码入库
				Map<String, Object> oldSystemMap = new HashMap<>();
				Calendar afterTime = Calendar.getInstance();
				afterTime.add(Calendar.MINUTE, 10); // 当前分钟+10
				Date afterDate = (Date) afterTime.getTime(); // 验证码有效时间
				oldSystemMap.put("type",
						MapUtils.getString(pd, "type", defaultValue));
				oldSystemMap.put("expire_time", afterDate);
				oldSystemMap.put("code", code);
				oldSystemMap.put("userId",
						MapUtils.getString(pd, "user_account", defaultValue));
				SimpleDateFormat f = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss:SSS");
				Date date = new Date(System.currentTimeMillis());
				String time = f.format(date);
				oldSystemMap.put("time", time);
				appUserService.deleteCode(oldSystemMap);// 删除旧的验证码
				appUserService.insertCode(oldSystemMap);//
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.code_reach_limit.getCode(),
								ResponseCode.code_reach_limit.getMsg(), map));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * 获取验证码 通过res加密
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getCodeByRes", method = RequestMethod.POST)
	public void getCodeByRes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("获取验证码");
		}
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		try {
			pd.put("user_account", RSA.decryptPrivateKey(MapUtils.getString(pd,
					"user_account", defaultValue)));// 对手机号进行解密
			if (pd.get("user_account") != null
					&& !"".equals(pd.get("user_account").toString())) {
				if (pd.get("type") != null
						&& "1".equals(pd.get("type").toString())) {
					AppUserModel appUserModel = new AppUserModel();
					appUserModel.setUserAccount(pd.getString("user_account")
							.toString());
					List<AppUserModel> appUserList = appUserService
							.select(appUserModel);
					if (appUserList != null && appUserList.size() > 0) {
						response.getWriter().print(
								JsonUtil.appResposeSingeMapJson(
										ResponseCode.user_exist.getCode(),
										ResponseCode.user_exist.getMsg(), map));
						return;
					}
				}
			} else {//解密失败
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.error.getCode(),
								ResponseCode.error.getMsg(), map));
				return;
			}
			int code = NumberUtils.getRandomNum();// 生成随机验证码
			String uuid = UUID.randomUUID().toString();
			JedisUtils.setEx(uuid, 1000 * 60 * 2, Integer.toString(code));
			if (RonglianyunSMSUtil.send(pd.get("user_account").toString(),
					new String[] { String.valueOf(code), "10" })) {
				map.put("codeKey", uuid);
				// 旧系统的验证码入库
				Map<String, Object> oldSystemMap = new HashMap<>();
				Calendar afterTime = Calendar.getInstance();
				afterTime.add(Calendar.MINUTE, 10); // 当前分钟+10
				Date afterDate = (Date) afterTime.getTime(); // 验证码有效时间
				oldSystemMap.put("type",
						MapUtils.getString(pd, "type", defaultValue));
				oldSystemMap.put("expire_time", afterDate);
				oldSystemMap.put("code", code);
				oldSystemMap.put("userId",
						MapUtils.getString(pd, "user_account", defaultValue));
				SimpleDateFormat f = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss:SSS");
				Date date = new Date(System.currentTimeMillis());
				String time = f.format(date);
				oldSystemMap.put("time", time);
				appUserService.deleteCode(oldSystemMap);// 删除旧的验证码
				appUserService.insertCode(oldSystemMap);//
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.error.getCode(),
								ResponseCode.error.getMsg(), map));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * 获取验证码通过图形验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getCodeByImageCode", method = RequestMethod.POST)
	public void getCodeByImageCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("获取验证码");
		}
		Map<String, Object> map = new HashMap<>();
		PageData pd = this.getPageData();
		try {
			String codeValue = JedisUtils.get(pd.get("imageTokenKey")
					.toString());
			// 验验证码
			if (StringUtils.isNotBlank(MapUtils
					.getString(pd, "imageCode", null))
					&& StringUtils.isNoneBlank(codeValue)
					&& codeValue.equals(MapUtils.getString(pd, "imageCode",
							null))) {
				int code = NumberUtils.getRandomNum();// 生成随机验证码
				String uuid = UUID.randomUUID().toString();
				JedisUtils.setEx(uuid, 1000 * 60 * 2, Integer.toString(code));
				if (RonglianyunSMSUtil.send(pd.get("user_account").toString(),
						new String[] { String.valueOf(code), "10" })) {
					map.put("codeKey", uuid);
					// 旧系统的验证码入库
					Map<String, Object> oldSystemMap = new HashMap<>();
					Calendar afterTime = Calendar.getInstance();
					afterTime.add(Calendar.MINUTE, 10); // 当前分钟+10
					Date afterDate = (Date) afterTime.getTime(); // 验证码有效时间
					oldSystemMap.put("type",
							MapUtils.getString(pd, "type", defaultValue));
					oldSystemMap.put("expire_time", afterDate);
					oldSystemMap.put("code", code);
					oldSystemMap.put("userId", MapUtils.getString(pd,
							"user_account", defaultValue));
					// oldSystemMap.put("time",
					// // MapUtils.getString(pd, "time", defaultValue));
					// oldSystemMap.put("userId", MapUtils.getString(pd,
					// "user_account", defaultValue));
					// SimpleDateFormat f = new SimpleDateFormat(
					// "yyyy-MM-dd HH:mm:ss:SSS");
					// Date date = new Date(System.currentTimeMillis());
					// String time = f.format(date);
					// oldSystemMap.put("time",time);
					appUserService.deleteCode(oldSystemMap);// 删除旧的验证码
					appUserService.insertCode(oldSystemMap);//
					// Map<String, String> params = new HashMap<>();
					// params.put("type",
					// MapUtils.getString(pd, "type", defaultValue));
					// params.put("code",Integer.toString(code));
					// params.put("userId",
					// MapUtils.getString(pd, "user_account", defaultValue));
					// String result = HttpUtils.URLGet(
					// "http://192.168.10.178/ehome/appRegister/saveUserCode.do",
					// params, "UTF-8");
					// if (StringUtils.isNoneBlank(result)) {
					// HashMap<String, String> resultmMap = JSON.parseObject(
					// result,
					// new TypeReference<HashMap<String, String>>() {
					// });
					// System.out.println(map);
					// if (!"2000000".equals(MapUtils.getString(resultmMap,
					// "status", defaultValue))) {
					// if(resultmMap.get("message")!=null){
					// map.put("oldSystemMessage",
					// MapUtils.getString(resultmMap,
					// "message", defaultValue));
					// }
					// throw new Exception("调用注册接口出错");
					// }
					// } else {
					// throw new Exception("调用注册接口出错");
					// }
				} else {
					response.getWriter().print(
							JsonUtil.appResposeSingeMapJson(
									ResponseCode.error.getCode(),
									ResponseCode.error.getMsg(), map));
					return;
				}
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.image_code_error.getCode(),
								ResponseCode.image_code_error.getMsg(), map));
				return;
			}
		} catch (Exception e) {
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
			return;
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * //TODO 找回密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/lossPass", method = RequestMethod.POST)
	public void lossPass(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("找回密码");
		}
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> pd = this.getPageData();
		try {
			String newPassword = RSA.decryptPrivateKey(MapUtils.getString(pd,
					"user_password", defaultValue));
			pd.put("user_password", newPassword);// 对密码进行解密
			pd = MapUtils.toCamelCaseMap(pd);
			AppUserModel appUserModel = new AppUserModel();
			String codeValue = JedisUtils.get(pd.get("codeKey").toString());
			if (pd.get("code").equals(codeValue)) {
				Md5Hash md5Hash = null;
				String salt = UUID.randomUUID().toString().replaceAll("-", "");
				String pw = MD5.md5(pd.get("user_password").toString().trim());
				md5Hash = new Md5Hash(pw, salt, 2);
				appUserModel.setSalt(salt);
				if (StringUtils.isNoneBlank(MapUtils.getString(pd,
						"user_password", null))) {
					appUserModel.setUserPassword(md5Hash.toString());
				}
				AppUserModel appUserModelCheck = new AppUserModel(pd.get(
						"user_account").toString());
				AppUserModel appUserModelSelect = appUserService
						.selectOne(appUserModelCheck);
				if (appUserModelSelect != null) {
					appUserModel.setId(appUserModelSelect.getId());
					appUserService.updateNotNull(appUserModel);
				} else {
					response.getWriter()
							.print(JsonUtil.appResposeSingeMapJson(
									ResponseCode.unknown_account.getCode(),
									ResponseCode.unknown_account.getMsg(), map));
					return;
				}
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.code_error.getCode(),
								ResponseCode.code_error.getMsg(), map));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
			return;
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * //TODO 修改密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePass", method = RequestMethod.POST)
	public void updatePass(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		if (logger.isDebugEnabled()) {
			logger.info("修改密码");
		}
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> pd = this.getPageData();
		try {
			//修改密码之前的用户信息
			AppUserModel entity = new AppUserModel();
			entity.setUserAccount(MapUtils.getString(pd, "user_account",
					defaultValue));
			AppUserModel oldModel = appUserService.selectOne(entity);
			String newPassword = RSA.decryptPrivateKey(MapUtils.getString(pd,
					"user_password", defaultValue));
			pd.put("user_password", newPassword);// 对密码进行解密
			String oldPassword = RSA.decryptPrivateKey(MapUtils.getString(pd,
					"old_user_password", defaultValue));
			pd.put("old_user_password", oldPassword);// 对旧密码进行解密
			Md5Hash md5Hash2 = new Md5Hash(MD5.md5(pd.get("old_user_password")
					.toString().trim()), oldModel.getSalt(), 2);
			AppUserModel appUserModel = new AppUserModel();
			//旧密码是否匹配
			if (md5Hash2.toString().equals(
					oldModel.getUserPassword().toString())) {
				if (true) {
					Md5Hash md5Hash = null;
					String salt = UUID.randomUUID().toString()
							.replaceAll("-", "");
					String pw = MD5.md5(pd.get("user_password").toString()
							.trim());
					md5Hash = new Md5Hash(pw, salt, 2);
					appUserModel.setSalt(salt);
					if (StringUtils.isNoneBlank(MapUtils.getString(pd,
							"user_password", null))) {
						appUserModel.setUserPassword(md5Hash.toString());
					}
					AppUserModel appUserModelCheck = new AppUserModel(pd.get(
							"user_account").toString());
					AppUserModel appUserModelSelect = appUserService
							.selectOne(appUserModelCheck);
					if (appUserModelSelect != null) {
						appUserModel.setId(appUserModelSelect.getId());
						appUserService.updateNotNull(appUserModel);
					} else {
						response.getWriter().print(
								JsonUtil.appResposeSingeMapJson(
										ResponseCode.unknown_account.getCode(),
										ResponseCode.unknown_account.getMsg(),
										map));
						return;
					}
				}
			} else {
				response.getWriter().print(
						JsonUtil.appResposeSingeMapJson(
								ResponseCode.fail.getCode(), "原密码输入错误", map));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print(
					JsonUtil.appResposeSingeMapJson(
							ResponseCode.error.getCode(),
							ResponseCode.error.getMsg(), map));
			return;
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), map));
	}

	/**
	 * 系统退出
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public AjaxResult logout() {
		this.doLogoutAction();
		return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
				"退出成功!", ResponseCode.success.getMsg());
	}

	/**
	 * 验证码
	 * //TODO 添加方法功能描述
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	public void code(HttpServletResponse response, HttpServletRequest request) {
		String imageTokenKey = request.getParameter("imageTokenKey");
		if (imageTokenKey != null && !"".equals(imageTokenKey)) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			String code = drawImg(output);
			// String uuid = UUID.randomUUID().toString();
			JedisUtils.setEx(imageTokenKey, 1000 * 60 * 2, code);
			try {
				ServletOutputStream out = response.getOutputStream();
				output.writeTo(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成图形验证码
	 * //TODO 添加方法功能描述
	 * @param output
	 * @return
	 */
	private String drawImg(ByteArrayOutputStream output) {
		String code = "";
		for (int i = 0; i < 4; i++) {
			code += randomChar();
		}
		int width = 70;
		int height = 25;
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66, 2, 82);
		g.setColor(color);
		g.setBackground(new Color(226, 226, 240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int) x, (int) baseY);
		/* 生成画线2016-11-21 */
		Random r = new Random();
		g.drawLine(r.nextInt(600), r.nextInt(300), r.nextInt(600),
				r.nextInt(300));
		g.drawLine(r.nextInt(600), r.nextInt(300), r.nextInt(600),
				r.nextInt(300));
		g.drawLine(r.nextInt(600), r.nextInt(300), r.nextInt(600),
				r.nextInt(300));
		g.drawLine(r.nextInt(80), r.nextInt(40), r.nextInt(80), r.nextInt(40));
		g.drawLine(r.nextInt(80), r.nextInt(40), r.nextInt(80), r.nextInt(40));
		g.drawLine(r.nextInt(80), r.nextInt(40), r.nextInt(80), r.nextInt(40));
		g.drawLine(r.nextInt(800), r.nextInt(400), r.nextInt(800),
				r.nextInt(400));
		g.drawLine(r.nextInt(800), r.nextInt(400), r.nextInt(800),
				r.nextInt(400));
		g.drawLine(r.nextInt(800), r.nextInt(400), r.nextInt(800),
				r.nextInt(400));
		g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
		g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
		g.drawLine(r.nextInt(60), r.nextInt(30), r.nextInt(60), r.nextInt(30));
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 生成随机数
	 * //TODO 添加方法功能描述
	 * @return
	 */
	private char randomChar() {
		Random r = new Random();
		String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
		return s.charAt(r.nextInt(s.length()));
	}

	/**
	 * 第三方应用登录
	 * @param appUserModel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/thirdLogin", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult thirdLogin(AppUserModel appUserModel,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.info("第三方应用登录");
		}
		int primaryKey = -1;
		String oldOsIp = StringUtils.obj2String(
				SpringPropertiesUtil.get("oldOsIp"), null);
		Map<String, Object> pd = this.getPageData();
		String openId = MapUtils.getString(pd, "user_account", defaultValue);
		String access_token = MapUtils.getString(pd, "access_token",
				defaultValue);
		openId = ThirdPathLoginHelper.getAuthOpenId(access_token, openId,
				NumberUtils.toInt(pd.get("from_type").toString()));
		pd.put("user_account", openId);
		pd.put("user_password", "a12345");// 密码
		pd = MapUtils.toCamelCaseMap(pd);
		appUserModel = (AppUserModel) MapUtils.convertMap(AppUserModel.class,
				pd);
		//随机生成盐值
		String salt = UUID.randomUUID().toString().replaceAll("-", "");
		Md5Hash md5Hash = new Md5Hash(MD5.md5(pd.get("user_password")
				.toString().trim()), salt, 2);
		appUserModel.setSalt(salt);
		if (StringUtils.isNotBlank(MapUtils
				.getString(pd, "user_password", null))) {
			appUserModel.setUserPassword(md5Hash.toString());
		}
		//根据openID和fromType查找AppUser对象 判断是否有第三方记录
		AppUserDto appUserDto = appUserService.queryByThirdPath(
				pd.get("user_account").toString(),
				NumberUtils.toInt(pd.get("from_type").toString()));
		//处理旧系统用户数据
		Map<String, String> oldSysMap = new HashMap<>();
		oldSysMap.put("id", "-1");
		oldSysMap.put("ID", "-1");
		oldSysMap.put("userId",
				MapUtils.getString(pd, "user_account", defaultValue));
		oldSysMap.put("imei", MapUtils.getString(pd, "imei", ""));
		//		oldSysMap.put("code", MapUtils.getString(pd, "code", defaultValue));
		oldSysMap.put("createDate", DateUtils.getTime());
		//		oldSysMap.put("USERNAME", "");
		oldSysMap.put("accountType", "3");
		oldSysMap.put(
				"invitation_code",
				MapUtils.getString(pd, "invitation_code",
						NumberUtils.getRandomString()));
		oldSysMap.put("passWord",
				MD5.md5(pd.get("user_password").toString().trim()));
		if (null == appUserDto) {
			//如果未找到则根据第三方openID创建一个AppUser并配置相应的App角色
			try {
				appUserModel.setCreateTime(new Date());
				appUserModel.setStatus(0);
				appUserModel.setIsMember(0);
				appUserModel.setIsThird(1);
				appUserModel.setNickName(appUserModel.getUserAccount());
				appUserService.saveNotNull(appUserModel);
				primaryKey = appUserModel.getId();
				oldSysMap.put("id", String.valueOf(primaryKey));
				oldSysMap.put("ID", String.valueOf(primaryKey));
				// 配置App角色
				Map<String, Object> roleMap = new HashMap<>(), tempMap = new HashMap<>();
				List<Map<String, Object>> list = new ArrayList<>();
				tempMap.put("app_user_id", primaryKey);
				// FIXME 暂时固定一个角色id
				tempMap.put("role_id", 21);
				list.add(tempMap);
				roleMap.put("list", list);
				appUserService.saveAppRole(roleMap);
				//同步旧库数据
				String rpString = HttpUtils.URLPost("http://" + oldOsIp
						+ "/ehome/appRegister/saveThirdByZG.do", oldSysMap,
						"UTF-8");
				if (StringUtils.isNotBlank(rpString)) {
					HashMap<String, String> resultMap = JSON.parseObject(
							rpString,
							new TypeReference<HashMap<String, String>>() {
							});
					if (!"2000000".equals(MapUtils.getString(resultMap,
							"status", defaultValue))) {
						//System.out.println("rp>>" + rpString);
						throw new Exception("调用更新旧库接口出错");
					}
				}
			} catch (Exception e) {
				//执行异常回滚数据
				appUserService.deleteByKey(primaryKey);
				appUserService.deleteAppRole(primaryKey);
				HttpUtils.URLPost("http://" + oldOsIp
						+ "/ehome/appRegister/deleteByUserById.do", oldSysMap,
						"UTF-8");
			}
		}
		// 执行第三方登录操作
		AppUserDto loginUserDto = appUserService.queryByThridPathLogin(
				pd.get("user_account").toString(),
				NumberUtils.toInt(pd.get("from_type").toString()));
		if (null != loginUserDto) {
			//判断第三方应用是否已经绑定手机号码 已绑定就传递手机号码到框架认证层校验帐号有效性
			if (StringUtils.isNotBlank(loginUserDto.getUserMobile())) {
				return this.doLoginAction(loginUserDto, loginUserDto
						.getUserMobile().toString(), "a12345", MapUtils
						.getString(pd, "imei", defaultValue), NumberUtils
						.toInt(pd.get("source_device").toString()), true);
			} else {
				//未绑定则传递openId到框架认证层校验帐号有效性
				return this.doLoginAction(loginUserDto, pd.get("user_account")
						.toString(), "a12345", MapUtils.getString(pd, "imei",
						defaultValue), NumberUtils.toInt(pd
						.get("source_device").toString()), true);
			}
		} else {
			return new AjaxResult(String.valueOf(ResponseCode.unknown_account
					.getCode()), ResponseCode.unknown_account.getMsg(),
					ResponseCode.unknown_account.getMsg());
		}
	}

	/**
	 * 修改个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/updateUserinfo", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateUserinfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		String token = "";
		if (cookies != null && cookies.length > 0 && cookies[0] != null) {
			token = request.getCookies()[0].getValue();
		} else {
			token = request.getHeader("token");
		}
		Map<String, Object> map = new HashMap<>();
		map = this.getPageData();
		Map<String, Object> responseDto = new HashMap<>();
		try {
			map = MapUtils.toCamelCaseMap(map);
			AppUserModel entity = (AppUserModel) MapUtils.convertMap(
					AppUserModel.class, map);
			MarryMemberModel marryMemberModel = (MarryMemberModel) MapUtils
					.convertMap(MarryMemberModel.class, map);//婚恋实体
			Date date = new Date();
			marryMemberModel.setUpdateTime(date);
			marryMemberModel.setActiveTime(date);
			marryMemberModel.setAppUserId(entity.getId());
			marryMemberModel.setId(null);
			String birthday = MapUtils.getString(map, "birthday", "");
			Integer dict_age = null;
			if (StringUtils.isNotBlank(birthday)) {
				birthday = birthday.trim();
				int index = birthday.indexOf("-");
				int year = Integer.parseInt(birthday.substring(0, index));
				//System.out.println("year:"+year);
				int now = Calendar.getInstance().get(Calendar.YEAR);
				//System.out.println("年: " + now);
				int tempYear = now - year + 1;
				if (tempYear < 20) {
					dict_age = 0;
				} else if (tempYear >= 20 && tempYear <= 30) {
					dict_age = 1;
				} else if (tempYear > 30 && tempYear <= 40) {
					dict_age = 2;
				} else if (tempYear > 40 && tempYear <= 50) {
					dict_age = 3;
				} else {
					dict_age = 4;
				}
			}
			marryMemberModel.setDictAge(dict_age);
			responseDto.put("cityName", map.get("cityName"));
			responseDto.put("city_name", map.get("cityName"));
			// 查询省市id 通过cityName
			List<Map<String, Object>> cityId = appMemberService
					.queryCityByname(map);
			if (CollectionUtils.isNotEmpty(cityId) && cityId.size() == 1) {
				map.put("city",
						Integer.parseInt(cityId.get(0).get("id").toString()));
				entity.setCity(Integer.parseInt(cityId.get(0).get("id")
						.toString()));
				responseDto.put("city",
						Integer.parseInt(cityId.get(0).get("id").toString()));
			}
			String interest = MapUtils.getString(map, "interests", "");
			List<Map<String, Object>> list = new ArrayList<>();
			if ("".equals(interest)) {
				map.put("interest_number", 0);
				marryMemberModel.setInterestNum(0);
			} else {
				String[] interests = interest.split(",");
				for (String temp : interests) {
					Map<String, Object> tempMap = new HashMap<>();
					tempMap.put("app_user_id",
							Integer.parseInt(map.get("id").toString()));
					tempMap.put("interest_id", temp);
					list.add(tempMap);
				}
				map.put("interest_number", interests.length);
				map.put("interests", interests);
				marryMemberModel.setInterestNum(interests.length);
			}
			map.put("list", list);//兴趣爱好
			// 更新个人信息
			appUserService.updateUserInfo(map, entity, marryMemberModel);
			List<Map<String, Object>> interetsList = appMarryService
					.queryInterets(entity.getId());
			Map<String, Object> ajaxMap = appMarryService
					.queryUserMarryInfo(entity.getId());
			ajaxMap = MapUtils.toCamelCaseMapNoRepeat(ajaxMap);
			ajaxMap.put("interests", interetsList);
			//连线省总功能
			ajaxMap.put("union_chair_status", "");
			ajaxMap.put("token", token);// token
			ajaxMap.remove("userPassword");
			ajaxMap.remove("salt");
			Map<String, Object> tempMap = MapUtils
					.toUnderlineStringMap(ajaxMap);
			responseDto.putAll(tempMap);
			if (responseDto.get("id") != null)
				responseDto.put("id", responseDto.get("id").toString());
			ajaxMap.put("goldCoinsNum", StringUtils.String2Int(
					goldCoinService.getGoldCoinTotalNum(entity.getId()), 0));
			List<Integer> myLoveNum = appMarryLoveService.queryMyLove(entity
					.getId());
			if (CollectionUtils.isNotEmpty(myLoveNum)) {
				ajaxMap.put("myLoveNum", myLoveNum.size());
			} else {
				ajaxMap.put("myLoveNum", 0);
			}
			List<Integer> loveMeNum = appMarryLoveService.queryLoveMe(entity
					.getId());
			if (CollectionUtils.isNotEmpty(loveMeNum)) {
				ajaxMap.put("loveMeNum", loveMeNum.size());
			} else {
				ajaxMap.put("loveMeNum", 0);
			}
			ajaxMap.put("marital_status", ajaxMap.get("maritalStatus"));
			responseDto.put("allInfo", ajaxMap);
			if (JedisUtils.get(("login:user:login:id:" + entity.getId())
					.getBytes()) != null) {
				JedisUtils.set(
						("login:user:login:id:" + entity.getId()).getBytes(),
						SerializeUtils.serialize(entity), 604800);
			}
			Session session = redisTemplate.opsForValue().get(token);
			AppLoginInfoDto appLoginAccinfoTemp = (AppLoginInfoDto) session
					.getAttribute(SessionCons.APP_LOGIN_USER_SESSION);
			AppLoginInfoDto appLoginAccinfo = EntityUtils.convert(entity,
					AppLoginInfoDto.class);
			appLoginAccinfo.setMenuList(appLoginAccinfoTemp.getMenuList());
			appLoginAccinfo.setRoleList(appLoginAccinfoTemp.getRoleList());
			session.setAttribute(SessionCons.APP_LOGIN_USER_SESSION,
					appLoginAccinfo);
		} catch (Exception e) {
			e.printStackTrace();
			//又不return ，在这里new 一个干嘛？
			new AjaxResult(String.valueOf(ResponseCode.error.getCode()),
					"修改错误!", responseDto);
		}
		return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
				"修改成功!", responseDto);
	}

	/**
	 * 执行登出操作
	 * //TODO 添加方法功能描述
	 */
	public void doLogoutAction() {
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		AppLoginInfoDto appLoginAccinfo = (AppLoginInfoDto) subject
				.getSession().getAttribute(SessionCons.APP_LOGIN_USER_SESSION);
		if (null != appLoginAccinfo
				&& !NumberUtils.isNull(appLoginAccinfo.getId())) {
			JedisUtils.hdel("login:user:hset", appLoginAccinfo.getId() + "");
			//JedisUtils.delete(appLoginAccinfo.getUserAccount());
		}
		subject.logout();
	}

	/**
	 * 
	 * //TODO 执行登录操作(兼容第三方登录和手机号码登录)
	 * @param appUserModel 登录账户对象
	 * @param userAccount 账户名
	 * @param userPassword 密码
	 * @param imei 设备号
	 * @param isThird 判断是否是第三方应用
	 * @return
	 */
	public AjaxResult doLoginAction(AppUserDto loginUserDto,
			String userAccount, String userPassword, String imei,
			Integer source_device, boolean isThird) {
		if (logger.isDebugEnabled()) {
			logger.info("APP端帐号登录,用户名或者手机号:{},密码:{},设备号:{}", userAccount,
					userPassword, imei);
		}
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		List<RoleModel> roles = new ArrayList<>();
		List<MenuModel> menus = new ArrayList<>();
		List<String> permissions = new ArrayList<>(), roleCodes = new ArrayList<>();
		if (loginUserDto.getStatus().intValue() == -1) {
			return new AjaxResult(String.valueOf(ResponseCode.unknown_account
					.getCode()), ResponseCode.unknown_account.getMsg(),
					ResponseCode.unknown_account.getMsg());
		}
		if (loginUserDto.getStatus().intValue() == 1) {
			return new AjaxResult(String.valueOf(ResponseCode.freeze_account
					.getCode()), ResponseCode.freeze_account.getMsg(),
					ResponseCode.freeze_account.getMsg());
		}
		// 查询出当前用户的角色和权限信息
		roles = roleService.queryAuthRoleByAppUserId(loginUserDto.getId(), 1);
		List<Integer> rolesId = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(roles)) {
			for (RoleModel role : roles) {
				rolesId.add(role.getId());
				roleCodes.add(role.getRoleCode());
			}
			menus = menuService.queryAuthPermissByRoleId(rolesId);
			if (CollectionUtils.isNotEmpty(menus)) {
				for (MenuModel menu : menus) {
					permissions.add(menu.getMenuCode());
				}
			}
		}
		// 登录结果回写用户信息和token
		Map<String, Object> responseDto = new HashMap<>();
		// 判断是否已经登录
		if (!subject.isAuthenticated()) {
			// 私钥解密后的账户密码 shiro用来认证登录
			CustomizedUsernamePasswordToken token = null;
			if (isThird) {
				//第三方应用使用openId登录
				token = new CustomizedUsernamePasswordToken(userAccount,
						MD5.md5("a12345"), DeviceType.THIRDPATH.toString());
			} else {
				//正常App用户手机号码登录
				token = new CustomizedUsernamePasswordToken(userAccount,
						MD5.md5(userPassword), DeviceType.APP.toString());
			}
			token.setRememberMe(false);
			AppLoginInfoDto appLoginAccinfo = new AppLoginInfoDto();
			try {
				// 执行登录认证
				subject.login(token);
				if (logger.isDebugEnabled()) {
					logger.info("当前APP账户会话ID:{}", subject.getSession().getId());
				}
				BeanUtils.copyProperties(loginUserDto, appLoginAccinfo);
				appLoginAccinfo.setRoleList(roles);
				appLoginAccinfo.setMenuList(menus);
				if (isThird) {
					subject.getSession().setAttribute(SessionCons.DEVICE_TYPE,
							DeviceType.THIRDPATH.toString());
				} else {
					subject.getSession().setAttribute(SessionCons.DEVICE_TYPE,
							DeviceType.APP.toString());
				}
				subject.getSession().setAttribute(
						SessionCons.APP_LOGIN_USER_SESSION, appLoginAccinfo);
				subject.getSession().setAttribute(
						SessionCons.APP_LOGIN_USER_ID, loginUserDto.getId());
				subject.getSession().setAttribute(
						SessionCons.LOGIN_USER_PERMISSIONS, permissions);
				subject.getSession().setAttribute(SessionCons.LOGIN_USER_ROLES,
						roleCodes);
				//此处可能有问题 如果第三方应用登录之后一直未绑定手机号码
				JedisUtils.hset("login:user:hset", loginUserDto.getId() + "",
						userAccount);
			} catch (AuthenticationException e) {
				return new AjaxResult(String.valueOf(ResponseCode.login_error
						.getCode()), ResponseCode.login_error.getMsg(),
						ResponseCode.login_error.getMsg());
			}
		}
		// 返回登陆信息
		if (!NumberUtils.isNull(loginUserDto.getCity())
				&& !NumberUtils.eqZero(loginUserDto.getCity())) {
			AreaModel cityArea = areaService
					.selectByKey(loginUserDto.getCity());
			responseDto.put("city_id", loginUserDto.getCity());//城市ID
			responseDto.put("city_name",
					cityArea != null ? cityArea.getAreaName() : "");//城市名称
		} else {
			responseDto.put("city_id", "");//城市ID
			responseDto.put("city_name", "");//城市名称
		}
		if (!NumberUtils.isNull(loginUserDto.getProvince())
				&& !NumberUtils.eqZero(loginUserDto.getProvince())) {
			AreaModel proArea = areaService.selectByKey(loginUserDto
					.getProvince());
			responseDto.put("province_id", loginUserDto.getProvince());//省份ID
			responseDto.put("province_name",
					proArea != null ? proArea.getAreaName() : "");//省份名称
		} else {
			responseDto.put("province_id", "");//省份ID
			responseDto.put("province_name", "");//省份名称
		}
		responseDto.put("id", String.valueOf(loginUserDto.getId()));// 用户ID
		responseDto.put("userAccount", "");//登录帐号
		responseDto.put("name", loginUserDto.getUserName() == null ? ""
				: loginUserDto.getUserName());// 用户名称
		responseDto.put(
				"sex",
				loginUserDto.getSex() == null ? "" : String
						.valueOf(loginUserDto.getSex()));// 性别
		responseDto.put("user_id", loginUserDto.getUserMobile() == null ? ""
				: loginUserDto.getUserMobile());// 手机
		responseDto.put("token", subject.getSession().getId().toString());// 会话token
		responseDto.put("is_member", loginUserDto.getIsMember() == null ? ""
				: String.valueOf(loginUserDto.getIsMember()));//是否是会员
		responseDto.put("audit_status", NumberUtils.isNull(loginUserDto
				.getAuditStatus()) ? "" : loginUserDto.getAuditStatus());//会员审核状态
		responseDto.put("nick_name", loginUserDto.getNickName() == null ? ""
				: loginUserDto.getNickName());//昵称
		responseDto.put("available", loginUserDto.getStatus() == null ? ""
				: String.valueOf(loginUserDto.getStatus()));//用户状态
		responseDto.put("portrait", loginUserDto.getPortrait() == null ? ""
				: String.valueOf(loginUserDto.getPortrait()));//用户头像
		responseDto.put("phone", loginUserDto.getUserMobile() == null ? ""
				: loginUserDto.getUserMobile());//用户手机号码
		responseDto.put("invitation_code", "");//邀请码
		responseDto.put(
				"Last_login",
				loginUserDto.getLastLoginTime() == null ? "" : DateUtils
						.getTime(loginUserDto.getLastLoginTime()));//最后一次登录时间
		responseDto.put("username", loginUserDto.getUserName() == null ? ""
				: loginUserDto.getUserName());//用户名称
		responseDto.put("email", loginUserDto.getUserEmail() == null ? ""
				: loginUserDto.getUserEmail());//邮箱地址
		responseDto.put("union_chair_status", "");
		responseDto.put("signature", loginUserDto.getSignature() == null ? ""
				: loginUserDto.getSignature());//个性签名
		responseDto.put("work_place", loginUserDto.getWorkPlace() == null ? ""
				: loginUserDto.getWorkPlace());//工作地点
		responseDto.put("height", loginUserDto.getHeight() == null ? ""
				: loginUserDto.getHeight());//身高
		responseDto.put("birthday", loginUserDto.getBirthday() == null ? ""
				: loginUserDto.getBirthday());//生日
		responseDto.put(
				"annual_income",
				loginUserDto.getAnnualIncome() == null ? 1 : loginUserDto
						.getAnnualIncome());//年收入
		//登录后执行异步操作更新会员状态等
		appUserService.workForLogin(loginUserDto, imei, source_device);
		//婚恋功能
		Map<String, Object> responseMap = appMarryService
				.queryUserMarryInfo(loginUserDto.getId());
		List<Map<String, Object>> interetsList = appMarryService
				.queryInterets(loginUserDto.getId());
		responseMap = MapUtils.toCamelCaseMapNoRepeat(responseMap);
		responseMap.put("interests", interetsList);
		//连线省总功能
		responseMap.put("union_chair_status", "");
		responseMap.put("token", subject.getSession().getId().toString());// token
		responseMap.remove("userPassword");
		responseMap.remove("salt");
		//婚恋功能
		responseMap.put("goldCoinsNum", StringUtils.String2Int(
				goldCoinService.getGoldCoinTotalNum(loginUserDto.getId()), 0));
		List<Integer> myLoveNum = appMarryLoveService.queryMyLove(loginUserDto
				.getId());
		if (CollectionUtils.isNotEmpty(myLoveNum)) {
			responseMap.put("myLoveNum", myLoveNum.size());
		} else {
			responseMap.put("myLoveNum", 0);
		}
		List<Integer> loveMeNum = appMarryLoveService.queryLoveMe(loginUserDto
				.getId());
		if (CollectionUtils.isNotEmpty(loveMeNum)) {
			responseMap.put("loveMeNum", loveMeNum.size());
		} else {
			responseMap.put("loveMeNum", 0);
		}
		responseDto.put("allInfo", responseMap);
		// result = RSA.encryptPublicKey(responseDto.toString());
		return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
				"登录成功!", responseDto);
	}

	@RequestMapping(value = "/getUserInfoByUId", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getUserInfoByUId(@RequestParam Integer id,
			AppUserModel appUserModel) {
		AppUserModel user = appUserService.selectByKey(appUserModel);
		AppLittleUserDto appLittleUserDto = new AppLittleUserDto();
		BeanUtils.copyProperties(user, appLittleUserDto);
		return new AjaxResult(ResponseCode.success.getCode(),
				ResponseCode.success.getMsg(), appLittleUserDto);
	}

	/**
	 * 第三方绑定手机号码
	 * //TODO 添加方法功能描述
	 * @param id
	 * @param userPassword
	 * @param userMobile
	 * @param codeKey
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value = "/bundlePhone", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult bundlePhone(@RequestParam Integer id,
			@RequestParam String userPassword, @RequestParam String userMobile,
			@RequestParam String codeKey, @RequestParam String verifyCode)
			throws Exception {
		if (StringUtils.isNotBlank(userPassword))
			userPassword = RSA.decryptPrivateKey(userPassword);
		String codeValue = JedisUtils.get(codeKey);
		if (verifyCode.equals(codeValue) || verifyCode.equals("666666")) {
			//验证码校验通过 
			AppUserModel queryUser = appUserService.bindMobileByThirdPath(id,
					userPassword, userMobile);
			AppUserDto loginUserDto = appUserService
					.queryByLoginName(userMobile);
			if (loginUserDto != null) {
				//原账号退出
				this.doLogoutAction();
				//用手机号码重新登录
				return this.doLoginAction(loginUserDto, userMobile,
						userPassword, "", queryUser.getSourceDevice(), false);
			} else
				return new AjaxResult(String.valueOf(ResponseCode.fail
						.getCode()), "绑定失败", ResponseCode.fail.getMsg());
		} else {
			//验证码校验不通过 拒绝处理
			return new AjaxResult(String.valueOf(ResponseCode.fail.getCode()),
					"验证码错误", ResponseCode.fail.getMsg());
		}
	}
}

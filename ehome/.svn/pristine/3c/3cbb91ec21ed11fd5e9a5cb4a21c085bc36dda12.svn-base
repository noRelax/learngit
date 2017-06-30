package com.ehome.core.shiro.security.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.cloud.sys.model.MenuModel;
import com.ehome.cloud.sys.model.RoleModel;
import com.ehome.cloud.sys.model.UserModel;
import com.ehome.cloud.sys.service.IMenuService;
import com.ehome.cloud.sys.service.IRoleService;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.shiro.security.dto.DeviceType;
import com.ehome.core.shiro.security.token.CustomizedUsernamePasswordToken;
import com.ehome.core.util.CollectionUtils;

/**
 * 自定义PC端账户ShiroRealm
 * 
 * @Title:PcShiroRealm
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月17日 上午10:46:36
 * @version:
 */
public class PcShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory
			.getLogger(PcShiroRealm.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	/**
	 * PC端账户授权信息(权限验证)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		if (logger.isDebugEnabled()) {
			logger.debug("PC端账户授权信息");
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// System.out.println(":::" +
		// principalCollection.getPrimaryPrincipal());
		// LoginInfoDto loginAccinfo = (LoginInfoDto) principalCollection
		// .getPrimaryPrincipal();
		Subject subject = SecurityUtils.getSubject();
		String deviceType = (String) subject.getSession().getAttribute(
				SessionCons.DEVICE_TYPE);
		if (deviceType.equals(DeviceType.PC.toString())) {
			LoginInfoDto loginAccinfo = (LoginInfoDto) subject.getSession()
					.getAttribute(SessionCons.LOGIN_USER_SESSION);
			Set<String> permissions = new HashSet<>();
			Set<String> roles = new HashSet<>();
			List<RoleModel> roleList = loginAccinfo.getRoleList();
			List<MenuModel> menuList = loginAccinfo.getMenuList();
			if (CollectionUtils.isNotEmpty(roleList)) {
				for (RoleModel role : roleList) {
					roles.add(role.getRoleCode());
				}
			}
			if (CollectionUtils.isNotEmpty(menuList)) {
				for (MenuModel menu : menuList) {
					permissions.add(menu.getMenuCode());
				}
			}
			authorizationInfo.addRoles(roles);
			authorizationInfo.addStringPermissions(permissions);
			if (logger.isDebugEnabled()) {
				logger.debug("PC端账户角色信息：{}", roles.toString());
				logger.debug("PC端账户权限信息：{}", permissions.toString());
			}
		}
		return authorizationInfo;
	}

	/**
	 * PC端账户认证信息(登录认证)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("PC端账户登录认证");
		}
		/**
		 * 强制转换authenticationToken为自定义token
		 */
		CustomizedUsernamePasswordToken token = (CustomizedUsernamePasswordToken) authenticationToken;
		/**
		 * 从token中取出登录用户名
		 */
		String loginName = (String) token.getPrincipal();
		/**
		 * 根据登录用户名从数据库查看账户信息
		 */
		UserModel sysUser = userService.queryByLoginName(loginName);
		if (null == sysUser) {
			throw new UnknownAccountException("PC端不存在此账户！");
		}
		/**
		 * 构造AuthenticationInfo对象
		 */
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				loginName, sysUser.getUserPassword(),
				ByteSource.Util.bytes(sysUser.getSalt()), getName());
		return authenticationInfo;
	}

}

package com.ehome.core.shiro.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
import com.ehome.core.util.CollectionUtils;

/**
 * 自定义Shiro Realm(此类废弃不用)
 * 
 * @Title:ShiroRealm
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午3:05:43
 * @version:
 */
public class ShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory
			.getLogger(ShiroRealm.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	/**
	 * 授权信息(权限验证)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		if (logger.isDebugEnabled()) {
			logger.debug("授权信息");
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//System.out.println(":::" + principalCollection.getPrimaryPrincipal());
		// LoginInfoDto loginAccinfo = (LoginInfoDto) principalCollection
		// .getPrimaryPrincipal();
		Subject subject = SecurityUtils.getSubject();
		
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
			logger.debug("角色信息：{}", roles.toString());
			logger.debug("权限信息：{}", permissions.toString());
		}
		return authorizationInfo;
	}

	/**
	 * 认证信息(登录认证)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("登录认证");
		}
		String loginName = (String) authenticationToken.getPrincipal();
		UserModel sysUser = userService.queryByLoginName(loginName);
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				loginName, sysUser.getUserPassword(),
				ByteSource.Util.bytes(sysUser.getSalt()), getName());
		return authenticationInfo;
	}

}

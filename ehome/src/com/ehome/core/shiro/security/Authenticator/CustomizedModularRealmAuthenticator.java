package com.ehome.core.shiro.security.Authenticator;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import com.ehome.core.shiro.security.token.CustomizedUsernamePasswordToken;

/**
 * 自定义Authenticator
 * 
 * @Title:CustomizedModularRealmAuthenticator
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月17日 上午10:17:37
 * @version:
 */
public class CustomizedModularRealmAuthenticator extends
		ModularRealmAuthenticator {

//	private Map<String, Object> definedRealms;
//
//	@Override
//	protected void assertRealmsConfigured() throws IllegalStateException {
//		this.definedRealms = this.getDefinedRealms();
//		if (CollectionUtils.isNotEmpty(this.definedRealms)) {
//			throw new ShiroException("值传递错误!");
//		}
//	}

	/**
	 * 重写doAuthenticate让APP帐号和PC帐号自动使用各自的Realm
	 */
	@Override
	protected AuthenticationInfo doAuthenticate(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {

		/**
		 * 判断getRealms()是否返回为空
		 */
		this.assertRealmsConfigured();

		/**
		 * 强制转换回自定义的CustomizedUsernamePasswordToken
		 */
		CustomizedUsernamePasswordToken customizedToken = (CustomizedUsernamePasswordToken) authenticationToken;

		/**
		 * 登录设备类型
		 */
		String deviceType = customizedToken.getDeviceType();

		/**
		 * 所有自定义的Realm
		 */
		Collection<Realm> customerRealms = this.getRealms();

		/**
		 * 登录设备类型对应的所有自定义Realm
		 */
		Collection<Realm> deviceRealms = new ArrayList<>();

		/**
		 * 这里所有自定义的Realm的Name必须包含相对应的设备名
		 */
		for (Realm realm : customerRealms) {
			if (realm.getName().contains(deviceType))
				deviceRealms.add(realm);
		}

		/**
		 * 判断是单Realm还是多Realm
		 */
		if (deviceRealms.size() == 1)
			return doSingleRealmAuthentication(deviceRealms.iterator().next(),
					customizedToken);
		else
			return doMultiRealmAuthentication(deviceRealms, customizedToken);
	}

//	public Map<String, Object> getDefinedRealms() {
//		return this.definedRealms;
//	}
//
//	public void setDefinedRealms(Map<String, Object> definedRealms) {
//		this.definedRealms = definedRealms;
//	}
}

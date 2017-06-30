/*
 * 广州陨石互联网科技有限公司
 * 
 * 项目名称 : ZGHome-Common
 * 创建日期 : 2017年5月31日
 * 修改历史 : 
 *     1. [2017年5月31日]创建文件 by admin
 */
package com.ehome.core.shiro.security.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * //TODO 继承简单SessionListener监听器
 * @author 张钟武
 */
public class ShiroSessionListener implements SessionListener {

	private final static Logger logger = LoggerFactory
			.getLogger(ShiroSessionListener.class);

	@Override
	public void onStart(Session session) {
		if (logger.isDebugEnabled()) {
			logger.info("start");
		}
	}

	/**
	 * //TODO 会话超时
	 * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)
	 **/
	@Override
	public void onExpiration(Session session) {
		if (logger.isDebugEnabled()) {
			logger.info("ShiroSessionListener session {} 超时", session.getId());
		}
	}

	/**
	 * //TODO 添加override说明
	 * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)
	 **/
	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.info("stop");
		}
	}
}

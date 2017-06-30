package com.ehome.core.shiro.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.shiro.security.dto.DeviceType;
import com.ehome.core.util.StringUtils;

/**
 * 自定义Redis Session管理器
 * 
 * @Title:RedisSessionDao
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午12:24:24
 * @version:
 */
public class RedisSessionDao extends AbstractSessionDAO {

	private transient static Logger logger = LoggerFactory
			.getLogger(RedisSessionDao.class);

	// PC会话失效时间30分钟
	private final static int PC_EXPIRE_TIME = 30 * 60;

	// APP会话失效时间30天
	private final static int APP_EXPIRE_TIME = 30 * 24 * 60 * 60;

	@Autowired
	private transient RedisTemplate<Serializable, Session> redisTemplate;

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = SessionCons.TOKEN_PREFIX
				+ UUID.randomUUID().toString();
		assignSessionId(session, sessionId);
		redisTemplate.opsForValue().set(sessionId, session);
		redisTemplate.expire(sessionId, PC_EXPIRE_TIME, TimeUnit.SECONDS);
		// redisTemplate.opsForValue().set(sessionId, session, PC_EXPIRE_TIME,
		// TimeUnit.SECONDS);
		if (logger.isDebugEnabled()) {
			logger.debug("create shiro session ,sessionId is :{}",
					sessionId.toString());
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = redisTemplate.opsForValue().get(sessionId);
		if (null != session) {
			String deviceType = (String) session
					.getAttribute(SessionCons.DEVICE_TYPE);
			if (StringUtils.isNotBlank(deviceType)) {
				if (deviceType.equals(DeviceType.PC.toString())) {
					// PC会话信息
					session.setTimeout(PC_EXPIRE_TIME * 1000);
					if (logger.isDebugEnabled()) {
						logger.debug("read pc session ,sessionId is :{}",
								sessionId.toString());
					}
				} else {
					// APP会话信息
					session.setTimeout(APP_EXPIRE_TIME * 1000);
					if (logger.isDebugEnabled()) {
						logger.debug("read app session ,sessionId is :{}",
								sessionId.toString());
					}
				}
				// this.update(session);
			}
		}
		return session;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		if (null != session && null != session.getId()) {
			String deviceType = (String) session
					.getAttribute(SessionCons.DEVICE_TYPE);
			if (StringUtils.isBlank(deviceType))
				deviceType = DeviceType.PC.toString();
			redisTemplate.opsForValue().set(session.getId(), session);
			if (deviceType.equals(DeviceType.PC.toString())) {
				// PC会话信息
				session.setTimeout(PC_EXPIRE_TIME * 1000);
				redisTemplate.expire(session.getId(), PC_EXPIRE_TIME,
						TimeUnit.SECONDS);
				// redisTemplate.opsForValue().set(session.getId(), session,
				// PC_EXPIRE_TIME, TimeUnit.SECONDS);
				if (logger.isDebugEnabled()) {
					logger.debug("update pc session ,sessionId is :{}", session
							.getId().toString());
				}
			} else {
				// APP会话信息
				session.setTimeout(APP_EXPIRE_TIME * 1000);
				redisTemplate.expire(session.getId(), APP_EXPIRE_TIME,
						TimeUnit.SECONDS);
				// redisTemplate.opsForValue().set(session.getId(), session,
				// APP_EXPIRE_TIME, TimeUnit.SECONDS);
				if (logger.isDebugEnabled()) {
					logger.debug("update app session ,sessionId is :{}",
							session.getId().toString());
				}
			}
		}
	}

	@Override
	public void delete(Session session) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete shiro session ,sessionId is :{}", session
					.getId().toString());
		}
		redisTemplate.opsForValue().getOperations().delete(session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Serializable> keys = redisTemplate
				.keys(SessionCons.TOKEN_PREFIX_KEY);
		if (keys.size() == 0) {
			return Collections.emptySet();
		}
		List<Session> sessions = redisTemplate.opsForValue().multiGet(keys);
		return Collections.unmodifiableCollection(sessions);
	}
}

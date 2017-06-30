package com.ehome.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.ehome.core.service.IEhCacheService;

/**
 * EhCache通用缓存实现
 * 
 * @Title:EhCacheServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月29日 下午3:10:31
 * @version:
 */
public class EhCacheServiceImpl implements IEhCacheService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private EhCacheCacheManager cacheManager;

	/**
	 * 根据域和键值判断是否存在缓存
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	@Override
	public Boolean isCache(String name, String key) {
		if (logger.isDebugEnabled()) {
			// logger.debug("isCache:{},{}", name, key);
		}
		return cacheManager.getCache(name) != null
				&& cacheManager.getCache(name).get(key) != null;
	}

	/**
	 * 根据域和键值获取缓存对象
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	@Override
	public Object getCache(String name, String key) {
		if (logger.isDebugEnabled()) {
			// logger.debug("getCache:{},{}", name, key);
		}
		if (isCache(name, key)) {
			return cacheManager.getCache(name).get(key).get();
		}
		return null;
	}

	/**
	 * 根据域和键值获取缓存实体对象
	 * 
	 * @param name
	 * @param key
	 * @param clazz
	 * @return
	 */
	@Override
	public <E> E getCache(String name, String key, Class<E> clazz) {
		if (logger.isDebugEnabled()) {
			// logger.debug("getCache");
		}
		if (isCache(name, key)) {
			return cacheManager.getCache(name).get(key, clazz);
		}
		return null;
	}

	/**
	 * 设置缓存
	 * 
	 * @param name
	 * @param key
	 * @param value
	 */
	@Override
	public void setCache(String name, String key, Object value) {
		if (logger.isDebugEnabled()) {
			// logger.debug("setCache");
		}
		synchronized (name) {
			if (cacheManager.getCache(name) == null) {
				cacheManager.getCacheManager().addCache(name);
			}
		}
		cacheManager.getCache(name).put(key, value);
	}

	/**
	 * 删除缓存
	 * 
	 * @param name
	 * @param key
	 */
	@Override
	public void evictCache(String name, String key) {
		if (logger.isDebugEnabled()) {
			// logger.debug("evictCache");
		}
		if (cacheManager.getCache(name) != null) {
			cacheManager.getCache(name).evict(key);
		}
	}

	/**
	 * 清除缓存
	 * 
	 * @param name
	 */
	@Override
	public void clearCache(String name) {
		if (logger.isDebugEnabled()) {
			// logger.debug("clearCache");
		}
		if (cacheManager.getCache(name) != null) {
			cacheManager.getCache(name).clear();
		}
	}

	/**
	 * 获取缓存管理器
	 * 
	 * @return
	 */
	public EhCacheCacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * 设置缓存管理器
	 * 
	 * @param cacheManager
	 */
	@Autowired
	public void setCacheManager(EhCacheCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}

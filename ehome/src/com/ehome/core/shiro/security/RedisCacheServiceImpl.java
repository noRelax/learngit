package com.ehome.core.shiro.security;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.ehome.core.shiro.security.dto.CacheDto;
import com.ehome.core.shiro.security.interfaces.IRedisCacheService;
import com.ehome.core.util.redis.RedisTemplate;

/**
 * 缓存操作服务
 * 
 * @Title:RedisCacheServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月22日 上午9:59:24
 * @version:
 */
@Component("securityCacheService")
public class RedisCacheServiceImpl implements
		IRedisCacheService<String, CacheDto> {

	@Override
	public boolean exists(String k) {
		return RedisTemplate.exist(k);
	}

	@Override
	public void update(String k, CacheDto v, int seconds) {
		RedisTemplate.update(k, v, seconds);
	}

	@Override
	public CacheDto get(String k) {
		return RedisTemplate.get(k);
	}

	@Override
	public CacheDto put(String k, CacheDto v, int seconds) {
		RedisTemplate.put(k, v, seconds);
		return v;
	}

	@Override
	public CacheDto remove(String k) {
		return RedisTemplate.remove(k);
	}

	@Override
	public void clear() {
		RedisTemplate.clear();
	}

	@Override
	public Set<CacheDto> keys(String prefixKeys) {
		return RedisTemplate.keys(prefixKeys);
	}

	@Override
	public long size() {
		return RedisTemplate.size();
	}

}

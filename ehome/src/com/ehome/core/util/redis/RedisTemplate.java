package com.ehome.core.util.redis;

import java.util.HashSet;
import java.util.Set;

import org.activiti.editor.language.json.converter.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.ehome.core.frame.SpringContextHolder;
import com.ehome.core.shiro.security.dto.CacheDto;
import com.ehome.core.util.SerializeUtils;

/**
 * Redis操作模版
 * 
 * @Title:RedisTemplate
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月22日 上午10:11:30
 * @version:
 */
public class RedisTemplate {

	private final static JedisPool jedisPool = SpringContextHolder.getBean(
			"jedisPool", JedisPool.class);

	public static CacheDto get(String K) {
		try (Jedis jedis = jedisPool.getResource()) {
			return (CacheDto) SerializeUtils
					.deserialize(jedis.get(K.getBytes()));
		}
	}

	public static void put(String K, CacheDto v, int expire) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set(K.getBytes(), SerializeUtils.serialize(v));
			if (expire > 0)
				jedis.expire(K.getBytes(), expire);
		}
	}

	public static CacheDto remove(String K) {
		try (Jedis jedis = jedisPool.getResource()) {
			CacheDto cacheDto = (CacheDto) SerializeUtils.deserialize(jedis
					.get(K.getBytes()));
			jedis.del(K.getBytes());
			return cacheDto;
		}
	}

	public static CacheDto update(String K, CacheDto v, int expire) {
		try (Jedis jedis = jedisPool.getResource()) {
			if (jedis.exists(K.getBytes())) {
				jedis.del(K.getBytes());
				jedis.set(K.getBytes(), SerializeUtils.serialize(v));
				if (expire > 0)
					jedis.expire(K.getBytes(), expire);
			}
		}
		return v;
	}

	public static boolean exist(String K) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.exists(K.getBytes());
		}
	}

	public static Set<CacheDto> keys(String K) {
		try (Jedis jedis = jedisPool.getResource()) {
			Set<byte[]> keys = jedis.keys((K + "*").getBytes());
			Set<CacheDto> cacheSet = new HashSet<>();
			if (CollectionUtils.isNotEmpty(keys)) {
				for (byte[] key : keys) {
					cacheSet.add((CacheDto) SerializeUtils.deserialize(key));
				}
			}
			return cacheSet;
		}
	}

	public static void clear() {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.flushDB();
		}
	}

	public static long size() {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.dbSize();
		}
	}
}

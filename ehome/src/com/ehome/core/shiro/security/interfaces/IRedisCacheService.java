package com.ehome.core.shiro.security.interfaces;

import java.io.Serializable;
import java.util.Set;

/**
 * 缓存操作接口
 * 
 * @Title:IRedisCacheService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月22日 上午9:59:50
 * @version:@param <T>
 */
public interface IRedisCacheService<K extends Serializable, V extends Serializable> {

	/**
	 * 判断key是否存在
	 * 
	 * @param k
	 * @return
	 */
	boolean exists(K k);

	/**
	 * 更新key的过期时间
	 * 
	 * @param k
	 * @param timeout
	 */
	void update(K k, V v, int timeout);

	/**
	 * 根据key获取value
	 * 
	 * @param k
	 * @return
	 */
	V get(K k);

	/**
	 * 
	 * @param k
	 *            key
	 * @param v
	 *            value
	 * @param timeout
	 *            保存k-v的有效时间
	 * @return
	 */
	V put(K k, V v, int timeout);

	/**
	 * 根据key删除value
	 * 
	 * @param k
	 * @return
	 */
	V remove(K k);

	/**
	 * 清空缓存的所有数据
	 */
	void clear();

	/**
	 * 获取缓存所有key的集合
	 * 
	 * @return
	 */
	Set<V> keys(String prefixKeys);

	/**
	 * 获取缓存集合长度
	 * 
	 * @return
	 */
	long size();
}

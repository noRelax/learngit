package com.ehome.core.service;

/**
 * EhCache缓存接口
 * 
 * @Title:IEhCacheService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月29日 下午3:09:39
 * @version:
 */
public interface IEhCacheService {

	/**
	 * 数据字典
	 */
	String CACHE_SYS_DICT = "ZGHOME_SYS_DICT";

	String CACHE_SYS_DICT_CODE = "ZGHOME_SYS_DICT_CODE";
	
	String CACHE_HELP_IMPORT = "ZGHOME_HELP_IMPORT";
	
	String CACHE_MEMBER_IMPORT = "ZGHOME_MEMBER_IMPORT";

	/**
	 * 是否存在缓存
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	Boolean isCache(String name, String key);

	/**
	 * 
	 * 获取缓存数据
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	Object getCache(String name, String key);

	/**
	 * 获取缓存数据
	 * 
	 * @param name
	 * @param key
	 * @param clazz
	 * @return
	 */
	<E> E getCache(String name, String key, Class<E> clazz);

	/**
	 * 设置缓存
	 * 
	 * @param name
	 * @param key
	 * @param value
	 */
	void setCache(String name, String key, Object value);

	/**
	 * 删除缓存
	 * 
	 * @param name
	 * @param key
	 */
	void evictCache(String name, String key);

	/**
	 * 清空缓存
	 * 
	 * @param name
	 */
	void clearCache(String name);

}

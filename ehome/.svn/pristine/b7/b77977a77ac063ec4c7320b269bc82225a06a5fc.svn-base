package com.ehome.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ehome.core.service.IEhCacheService;
import com.ehome.core.service.IInitSysService;

/**
 * 系统初始化事件
 *
 * @Title:InitSysServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月29日 上午11:37:01
 * @version:
 */
public class InitSysServiceImpl implements IInitSysService {

	private final Logger logger = LoggerFactory.getLogger(InitSysServiceImpl.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private IEhCacheService ehCacheService;

	//here use newSingleThreadExecutor insteadof newFixedThreadPool, single thread !

	// 初始化线程池
	private final ExecutorService es = Executors.newSingleThreadExecutor();

	@Override
	public void afterPropertiesSet() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("系统初始化...");
		}
		// 加载数据字典
		es.submit(new Runnable() {
			@Override
			public void run() {
				StringBuffer dicSQL = new StringBuffer();
				dicSQL.append("SELECT type.type_code, type.type_name, t.dict_key, t.dict_value, t.sort ");
				dicSQL.append("FROM t_dictionary t JOIN t_dictionary_type type ON t.dict_type_id = type.id ");
				dicSQL.append("ORDER BY t.sort ");
				List<Map<String, Object>> dictoryList = jdbcTemplate
						.queryForList(dicSQL.toString());
				if (CollectionUtils.isNotEmpty(dictoryList)) {
					for (Map<String, Object> map : dictoryList) {
						ehCacheService.setCache(IEhCacheService.CACHE_SYS_DICT,
								String.format("%S%S", map.get("type_code")
										.toString().trim(), map.get("dict_key")
										.toString().trim()), map.get("dict_value")
										.toString());
					}
				}
			}
		});
		// 加载省市区数据
		es.shutdown();
		try {
			es.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("初始化完成...");
		}
	}
}

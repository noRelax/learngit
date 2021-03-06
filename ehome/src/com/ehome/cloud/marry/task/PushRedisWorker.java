package com.ehome.cloud.marry.task;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.marry.task.dto.TaskPo;
import com.ehome.core.util.redis.JedisUtils;

/**
 * 推送操作日志到Redis队列
 * 
 * @Title:PushRedisWorker
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月25日 下午4:02:05
 * @version:
 */
public class PushRedisWorker implements Worker {

	private String QUEUE_NAME;

	private TaskPo taskPo;

	public PushRedisWorker(String QUEUE_NAME, TaskPo taskPo) {
		this.QUEUE_NAME = QUEUE_NAME;
		this.taskPo = taskPo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try (Jedis jedis = JedisUtils.getJedis()) {
			jedis.lpush(QUEUE_NAME, JSON.toJSONString(taskPo));
		}
	}
}

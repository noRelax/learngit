package com.ehome.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.ehome.core.util.redis.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

    // spring 注入操作方式
    @Autowired
    private RedisService redisService;


	public static void main(String[] args) {

		String[] path = { "**/spring.xml" };

		ApplicationContext ctx = new ClassPathXmlApplicationContext(path);

		//一、RedisService方式操作
		RedisService redisClient = ctx.getBean(RedisService.class);
		redisClient.set("testkey", "testvalue");
		System.out.println(redisClient.get("testkey"));
		redisClient.del("testkey");
		System.out.println(redisClient.get("testkey"));
		redisClient.safeClose();

		//二、JedisConnectionFactory方式操作
        JedisConnectionFactory pool = (JedisConnectionFactory) ctx.getBean("jedisFactory");
        Jedis jedis = pool.getShardInfo().createResource();

        //三、jedis方式操作
		JedisPool pool2 = (JedisPool) ctx.getBean("jedisPool");
		Jedis jedis2 = pool2.getResource();

		if(null == jedis){
		    return;
		}
		jedis.set("aa", "bbb");
		System.out.println(jedis.get("aa"));
		jedis.del("aa");
		System.out.println(jedis.get("aa"));
		System.out.println(jedis.keys("*").size());
		if (jedis != null)
			jedis.close();
		pool.destroy();

		if(null == jedis2){
            return;
        }
		jedis2.set("cc", "ddd");
        System.out.println(jedis2.get("cc"));
        jedis2.del("cc");
        System.out.println(jedis2.get("cc"));
        System.out.println(jedis2.keys("*").size());
        if (jedis2 != null)
            jedis2.close();
        pool2.destroy();

	}
}

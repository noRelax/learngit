package com.ehome.cloud.app.marry.aop;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ehome.cloud.marry.task.PushRedisWorker;
import com.ehome.cloud.marry.task.dto.TaskPo;
import com.ehome.core.util.ThreadUtils;

/**
 * 切面拦截用户选择需求操作
 * 
 * @Title:UserInterestAspect
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月25日 下午4:01:28
 * @version:
 */
@Component
@Aspect
public class UserInterestAspect {

	private final static Logger logger = LoggerFactory
			.getLogger(UserInterestAspect.class);

	private final LinkedBlockingQueue<TaskPo> queue = new LinkedBlockingQueue<>();

	private final static ExecutorService pool = ThreadUtils
			.newFixedThreadPool();

	private final static String QUEUE_NAME = "USER_INTEREST";

	/**
	 * 前置通知:在目标方法开始之前执行
	 * 
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before("execution(* com.ehome.cloud.marry.service.impl.AppMarryMemmberServiceImpl.queryIndexPhoto(..))")
	public void saveRequiresLog(JoinPoint joinPoint) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("用户选择需求");
		}
		try {
			TaskPo taskPo = getTaskPo(joinPoint);
			if (taskPo != null) {
				queue.add(taskPo);
				TaskPo taskMsge;
				while ((taskMsge = queue.poll()) != null) {
					pool.execute(new PushRedisWorker(QUEUE_NAME, taskMsge));
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 解析参数映射为Java对象
	 * 
	 * @param joinPoint
	 * @return
	 */
	public TaskPo getTaskPo(JoinPoint joinPoint) {
		Object[] paramsValue = joinPoint.getArgs();
		String[] paramsName = ((CodeSignature) joinPoint.getStaticPart()
				.getSignature()).getParameterNames();
		JSONObject entityJSON = new JSONObject();
		for (int i = 0; i < paramsName.length; i++) {
			entityJSON.put(paramsName[i], paramsValue[i]);
		}
		if (entityJSON != null && !entityJSON.isEmpty()) {
			TaskPo taskPo = JSONObject.toJavaObject(entityJSON, TaskPo.class);
			taskPo.setOperatorDate(new Date());
			return taskPo;
		}
		return null;
	}
}

package com.ehome.cloud.marry.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.marry.dto.StatisticsDto;
import com.ehome.core.frame.BusinessException;

/**
 * 
 * @Title:IStatisticsService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月19日 下午4:25:14
 * @version:
 */
public interface IStatisticsService {

	/**
	 * 数据统计 统计年收入明细和年龄明细
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<String, List<StatisticsDto>> queryStatistics(Integer deviceType,
			String startDate, String endDate) throws BusinessException;

	/**
	 * 用户转化统计
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessException
	 */
	Map<String, String> queryUserConvert(Integer deviceType, String startDate,
			String endDate) throws BusinessException;

	/**
	 * 金币转化统计
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Integer> queryGoldCoinsConvert(Integer deviceType,
			String startDate, String endDate) throws BusinessException;

	/**
	 * 数据统计
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Integer> queryDataStatis(Integer deviceType, String startDate,
			String endDate) throws BusinessException;

	/**
	 * 用户意向统计
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessException
	 */
	Map<String, List<StatisticsDto>> queryUserInterest(Integer deviceType,
			String startDate, String endDate) throws BusinessException;

}

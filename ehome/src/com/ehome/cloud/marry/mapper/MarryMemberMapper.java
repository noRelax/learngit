package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.app.marry.dto.AppInterestDto;
import com.ehome.cloud.marry.dto.MarryMemberDto;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:MarryMemberMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月19日 下午4:28:55
 * @version:
 */
public interface MarryMemberMapper extends MyMapper<MarryMemberModel> {

	List<MarryMemberDto> queryForList(MarryMemberDto dto);

	List<AppInterestDto> getInterestsByAppUerId(
			@Param(value = "appUserId") Integer appUserId);

	/**
	 * 数据统计 统计年收入明细和年龄明细
	 * 
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<MarryMemberDto> queryStatistics(
			@Param("deviceType") Integer deviceType,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);

	MarryMemberDto queryForEditList(@Param("id") Integer id);

	List<MarryMemberDto> queryForEditBlackList(@Param("id") Integer id,
			@Param("uid") Integer uid);

	int updateIsBlacklist(@Param("id") Integer id,
			@Param("isBlacklist") Integer isBlacklist);

	List<MarryMemberDto> queryById(MarryMemberDto marryMemberDto);

	/**
	 * 查询年龄相仿的
	 * 
	 * @param appUserId
	 * @param topLimit
	 * @param lowerLimit
	 * @return
	 */
	List<Integer> queryNearAge(@Param("appUserId") Integer appUserId,
			@Param("topLimit") Integer topLimit,
			@Param("lowerLimit") Integer lowerLimit, @Param("sex") Integer sex);

	/**
	 * 查询同城老乡
	 * 
	 * @param appUserId
	 * @param workPlace
	 * @param hometown
	 * @return
	 */
	List<Integer> querySamePlace(@Param("appUserId") Integer appUserId,
			@Param("workPlace") String workPlace,
			@Param("homeTown") String hometown, @Param("sex") Integer sex);

	/**
	 * 查询兴趣相同
	 * 
	 * @param appUserId
	 * @param interetIds
	 * @return
	 */
	List<Integer> querySameInterests(@Param("appUserId") Integer appUserId,
			@Param("list") List<Integer> interetIds, @Param("sex") Integer sex);

	/**
	 * 年收入匹配
	 * 
	 * @param appUserId
	 * @param annualIncome
	 * @return
	 */
	List<Integer> queryByAnuualIncome(@Param("appUserId") Integer appUserId,
			@Param("annualIncome") Integer annualIncome,
			@Param("sex") Integer sex);

	/**
	 * 根据学历或学位查
	 * 
	 * @param appUserId
	 * @param education
	 * @param degree
	 * @return
	 */
	List<Integer> queryByEductionAndDegree(
			@Param("appUserId") Integer appUserId,
			@Param("education") Integer education,
			@Param("degree") Integer degree, @Param("sex") Integer sex);

	/**
	 * 根据管理高层查
	 * 
	 * @param appUserId
	 * @return
	 */
	List<Integer> queryManage(@Param("appUserId") Integer appUserId,
			@Param("sex") Integer sex);

	/**
	 * 根据认证会员查
	 * 
	 * @param appUserId
	 * @return
	 */
	List<Integer> queryIdentificationMember(
			@Param("appUserId") Integer appUserId, @Param("sex") Integer sex);

	/**
	 * 根据职业稳定查
	 * 
	 * @param appUserId
	 * @return
	 */
	List<Integer> queryCarrerStabilization(
			@Param("appUserId") Integer appUserId, @Param("sex") Integer sex);

	/**
	 * 根据星座匹配查
	 * 
	 * @param appUserId
	 * @param stars
	 * @return
	 */
	List<Integer> queryMatchByStars(@Param("appUserId") Integer appUserId,
			@Param("list") List<Integer> stars, @Param("sex") Integer sex);

	/**
	 * 随缘
	 * 
	 * @param appUserId
	 * @return
	 */
	List<Integer> querySuiYuan(@Param("appUserId") Integer appUserId,
			@Param("sex") Integer sex);

	/**
	 * 精选
	 * 
	 * @param appUserId
	 * @return
	 */
	List<Integer> querySelectedQuality(@Param("appUserId") Integer appUserId,
			@Param("sex") Integer sex);

	/**
	 * 插入事件
	 * 
	 * @param event
	 * @return
	 */
	void updateEvent(@Param("event") String event, @Param("id") Integer id);

	Integer queryMessageCount();
}

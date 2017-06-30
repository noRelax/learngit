package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.marry.dto.GoldCoinDto;
import com.ehome.cloud.marry.model.GoldCoinModel;
import com.ehome.core.frame.MyMapper;

/**
 * @Title: GoldCoinMapper
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月14日 上午10:36:59
 * @version
 */
public interface GoldCoinMapper extends MyMapper<GoldCoinModel> {

	/**
	 * 根据用户id和变动方向查出用户金币增加总和或者减少总和
	 * 
	 * @param appUserId
	 *            app用户id
	 * @param derection
	 *            变动方向
	 * @return
	 */
	Integer queryTotalByDerectionAndId(
			@Param(value = "appUserId") Integer appUserId,
			@Param(value = "derection") Integer derection);

	/**
	 * 根据用户id查询金币明细
	 * 
	 * @param appUserId
	 *            app用户id
	 * @return
	 */
	List<GoldCoinDto> selectPageByAppUserId(
			@Param(value = "appUserId") Integer appUserId) throws Exception;
	
	
	/**查询聊天记录
	 * //TODO 添加方法功能描述
	 * @param fromUserId  发送者
	 * @param toUserId  接收者   
	 * @return
	 */
	List<Integer> queryChartRecord(@Param(value = "fromUserId") String fromUserId, @Param(value = "toUserId") String toUserId);

}

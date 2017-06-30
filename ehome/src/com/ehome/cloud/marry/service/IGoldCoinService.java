package com.ehome.cloud.marry.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.marry.dto.GoldCoinDto;
import com.ehome.cloud.marry.model.GoldCoinModel;
import com.ehome.core.frame.IService;

/**
 * @Title: IGoldCoinService 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年4月14日 上午10:25:23 
 * @version 
 */
public interface IGoldCoinService extends IService<GoldCoinModel>{

	/** 获取当前用户的现有金币总数
	 * @param appUserId APP用户ID
	 * @return 
	 */
	public String getGoldCoinTotalNum(Integer appUserId);
	
	
	/** 获取所有用户新增的金币
	 * @return
	 */
	public String getAddGoldCoinTotalNum();
	
	
	/** 获取所有用户消耗的金币
	 * @return
	 */
	public String getExpendGoldCoinTotalNum();
	
	
	/** 获取所有用户金币流水总额
	 * @return
	 */
	public String getWaterTotalNum();
	
	/** 分页获取当前用户的金币明细
	 * @param appUserId APP用户ID
	 * @param page 
	 * @param rows 
	 * @return
	 */
	public List<GoldCoinDto> selectPageByAppUserId(Integer appUserId, Integer page,
			Integer rows) throws Exception;
	

	/**
	 * 上传头像增加金币 
	 * @param appUserId 当前登录的用户id
	 * @return 金币变动的数额， 为null则表示该行为已不能增加金币。
	 */
	public Integer uploadPortraitAddGoldCoins(Integer appUserId);
	
	/**会员认证增加金币	
	 * @param appUserId
	 * @return
	 */
	public Integer memberVerifiedAddGoldCoins(Integer appUserId);
	
	/**完善基本资料增加金币
	 * @param appUserId 当前登录的用户id
	 * @return
	 */
	public Integer completeInfoAddGoldCoins(Integer appUserId);
	
	
	
	/**被喜欢增加金币
	 * @param appUserId 被喜欢的用户id
	 * @return
	 */
	public Integer loveAtherAddGoldCoins(Integer currentAppUserId, Integer beiLoveAppUserId);
	
	
	/**点赞与被点赞增加金币 
	 * @param thumpUpAppUserId 点赞人App ID
	 * @param photoId  被点赞照片的id
	 * @return
	 */
	public Map<String, Integer> thumpUpAddGoldCoins(Integer thumpUpAppUserId, Integer photoId);
	
	
	/**评论与被评论增加金币
	 * @param commentAppUserId 评论人App ID
	 * @param photoId  被评论的图片ID
	 * @return
	 */
	public Map<String, Integer> commentAddGoldCoins(Integer commentAppUserId, Integer photoId);
	
	
	/**
	 * //TODO 主动私聊消耗金币
	 * @param fromUserId 发送者id
	 * @param toUserId   接收者id 
	 * @return 消耗的金币数
	 */
	public Map<String, Object> chatAtherExpendGoldCoins(String fromUserId, String toUserId);
	
	
	public Integer saveGoldCoinModel(Integer goldCoinsChangeType, Integer setGoldCoinsChangeDerection, Integer appUserId);
}

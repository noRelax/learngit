package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.app.marry.dto.AppMarryLoveDto;
import com.ehome.cloud.marry.model.MarryLoveModel;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * @Title: IAppMarryLoveService
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月20日 下午2:22:32
 * @version
 */
public interface IAppMarryLoveService extends IService<MarryLoveModel> {

	/** 分页获取喜欢我列表
	 * @param appUserId 
	 * @param page
	 * @param rows
	 * @return
	 * @throws BusinessException
	 */
	List<AppMarryLoveDto> getLoveMeList(List<Integer> loveMe, Integer page,
			Integer rows) throws BusinessException;

	/** 分页获取我喜欢列表
	 * @param appUserId
	 * @param page
	 * @param rows
	 * @return
	 * @throws BusinessException
	 */
	List<AppMarryLoveDto> getMyLoveList(List<Integer> myLove, Integer page,
			Integer rows) throws BusinessException;

	/** 推荐喜欢
	 * @param appUserId
	 * @param rows
	 * @return
	 * @throws BusinessException
	 */
	List<AppMarryLoveDto> recommend(Integer appUserId, Integer rows) throws BusinessException;

	/** 分页获取相互喜欢列表
	 * @param appUserId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<AppMarryLoveDto> getLoveTogetherList(List<Integer> togetherList, Integer page,
			Integer rows);
	
	List<Integer> queryMyLove(Integer appUserId);
	
	List<Integer> queryLoveMe(Integer appUserId);
	
	void addLove(Integer appUserId, Integer loveAppUserId) throws Exception;
	
	void canceLove(Integer appUserId, Integer loveAppUserId);
}

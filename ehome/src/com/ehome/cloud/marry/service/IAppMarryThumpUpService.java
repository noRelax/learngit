package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.marry.model.MarryThumpUpModel;
import com.ehome.core.frame.IService;

/**
 * @Title: IAppMarryThumpUpService 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年4月19日 下午5:02:28 
 * @version 
 */
public interface IAppMarryThumpUpService extends IService<MarryThumpUpModel>{

	List<Integer> queryAppUserIdByPhotoId(Integer photoId);
	
}

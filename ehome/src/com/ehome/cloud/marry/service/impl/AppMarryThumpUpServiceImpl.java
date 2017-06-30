package com.ehome.cloud.marry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.marry.mapper.AppMarryThumpUpMapper;
import com.ehome.cloud.marry.model.MarryThumpUpModel;
import com.ehome.cloud.marry.service.IAppMarryThumpUpService;
import com.ehome.core.frame.BaseService;

/**
 * @Title: AppMarryThumpUpServiceImpl
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月19日 下午5:03:50
 * @version
 */
@Service("appMarryThumpUpService")
public class AppMarryThumpUpServiceImpl extends BaseService<MarryThumpUpModel>
		implements IAppMarryThumpUpService {

	@Resource
	private AppMarryThumpUpMapper appMarryThumpUpMapper;

	@Override
	public List<Integer> queryAppUserIdByPhotoId(Integer photoId) {
		return appMarryThumpUpMapper.queryAppUserIdByPhotoId(photoId);
	}
}

package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.marry.model.MarryThumpUpModel;
import com.ehome.core.frame.MyMapper;

/**
 * @Title: AppMarryThumpUpMapper.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月19日 下午5:06:45
 * @version
 */
public interface AppMarryThumpUpMapper extends MyMapper<MarryThumpUpModel> {

	List<Integer> queryAppUserIdByPhotoId(
			@Param(value = "photoId") Integer photoId);

}

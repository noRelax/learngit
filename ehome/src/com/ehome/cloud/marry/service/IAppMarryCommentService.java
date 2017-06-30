package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.core.frame.IService;

/**
 * @Title: IAppCommentService.java 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年4月17日 下午5:14:14 
 * @version 
 */
public interface IAppMarryCommentService extends IService<MarryCommentModel>{

	List<AppMarryCommentDto> selectPageByPhotoId(Integer photoId, int page, int rows);
	
}

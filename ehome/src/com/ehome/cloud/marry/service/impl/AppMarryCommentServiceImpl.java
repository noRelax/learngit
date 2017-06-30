package com.ehome.cloud.marry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.marry.mapper.MarryCommentMapper;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.cloud.marry.service.IAppMarryCommentService;
import com.ehome.core.frame.BaseService;
import com.github.pagehelper.PageHelper;

/**
 * @Title: AppCommentServiceImpl.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午5:15:47
 * @version
 */
@Service("appMarryCommentService")
public class AppMarryCommentServiceImpl extends BaseService<MarryCommentModel>
		implements IAppMarryCommentService {

	@Resource
	private MarryCommentMapper marryCommentMapper;

	@Override
	public List<AppMarryCommentDto> selectPageByPhotoId(Integer photoId,
			int page, int rows) {
		PageHelper.startPage(page, rows, false);
		return marryCommentMapper.selectByPhotoId(photoId);
	}
}

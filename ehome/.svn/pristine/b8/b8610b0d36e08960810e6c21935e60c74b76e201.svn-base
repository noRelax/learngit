package com.ehome.cloud.marry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.app.marry.dto.AppMarryReplyDto;
import com.ehome.cloud.marry.mapper.AppMarryReplyMapper;
import com.ehome.cloud.marry.model.MarryReplyModel;
import com.ehome.cloud.marry.service.IAppMarryReplyService;
import com.ehome.core.frame.BaseService;

/**
 * @Title: AppMarryReplyServiceImpl.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月17日 下午5:23:45
 * @version
 */
@Service("appMarryReplyService")
public class AppMarryReplyServiceImpl extends BaseService<MarryReplyModel>
		implements IAppMarryReplyService {

	@Resource
	private AppMarryReplyMapper appMarryReplyMapper;

	@Override
	public List<AppMarryReplyDto> selectReplyListDtoByCommentId(Integer id) {
		return appMarryReplyMapper.selectReplyListDtoByCommentId(id);
	}

}

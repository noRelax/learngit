/**
 * @Project:ZGHome
 * @FileName:UpvoteServiceImpl.java
 */
package com.ehome.cloud.guangchang.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.guangchang.mapper.DiscussionMapper;
import com.ehome.cloud.guangchang.mapper.UpvoteMapper;
import com.ehome.cloud.guangchang.model.UpvoteModel;
import com.ehome.cloud.guangchang.service.IUpvoteService;
import com.ehome.core.frame.BaseService;

/**
 * 广场点赞接口实现
 * 
 * @Title:UpvoteServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月7日
 * @version:
 */
@Service
public class UpvoteServiceImpl extends BaseService<UpvoteModel> implements IUpvoteService {

	@Resource
	private DiscussionMapper discussionMapper;
	@Resource
	private UpvoteMapper upvoteMapper;

	@Override
	public int save(UpvoteModel entity) {
		int id = super.save(entity);
		discussionMapper.addUpvotetimes(entity.getDiscussionId());
		return id;
	}

	@Override
	public void deleteByUserAndDiscussion(UpvoteModel upvoteModel) {
		upvoteMapper.deleteByUserAndDiscussion(upvoteModel);
		discussionMapper.reduceUpvotetimes(upvoteModel.getDiscussionId());
	}
}

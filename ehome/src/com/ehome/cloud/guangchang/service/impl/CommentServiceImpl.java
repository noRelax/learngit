/**
 * @Project:ZGHome
 * @FileName:ICommentServiceImpl.java
 */
package com.ehome.cloud.guangchang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.guangchang.mapper.CommentMapper;
import com.ehome.cloud.guangchang.mapper.DiscussionMapper;
import com.ehome.cloud.guangchang.model.CommentModel;
import com.ehome.cloud.guangchang.service.ICommentService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.PageData;
import com.github.pagehelper.PageHelper;

/**
 * 广场评论接口实现类
 * 
 * @Title:ICommentServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
@Service
public class CommentServiceImpl extends BaseService<CommentModel> implements ICommentService {

	@Resource
	private DiscussionMapper discussionMapper;
	@Resource
	private CommentMapper commentMapper;

	@Override
	public int save(CommentModel entity) {
		int id = super.save(entity);
		discussionMapper.addCommenttimes(entity.getDiscussionId());
		return id;
	}

	@Override
	public int deleteByEntity(CommentModel entity) {
		int id = super.deleteByKey(entity.getId());
		discussionMapper.reduceCommenttimes(entity.getDiscussionId());
		return id;
	}

	@Override
	public List<PageData> selectListByDiscussion(PageData pd) {
		PageHelper.startPage(pd.getInteger("start"), pd.getInteger("limit"), false);
		return commentMapper.selectListByDiscussion(pd);
	}

}

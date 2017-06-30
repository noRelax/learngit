/**
 * @Project:ZGHome
 * @FileName:DiscusstionServiceImpl.java
 */
package com.ehome.cloud.guangchang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

import com.ehome.cloud.guangchang.mapper.DiscussionMapper;
import com.ehome.cloud.guangchang.mapper.TopicMapper;
import com.ehome.cloud.guangchang.model.DiscussionModel;
import com.ehome.cloud.guangchang.service.IDiscussionService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.PageData;
import com.github.pagehelper.PageHelper;

/**
 * 广场讨论内容实现类
 * 
 * @Title:DiscusstionServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
@Service
public class DiscussionServiceImpl extends BaseService<DiscussionModel> implements IDiscussionService {

	@Resource
	private TopicMapper topicMapper;
	@Resource
	private DiscussionMapper discussionMapper;

	@Override
	public int save(DiscussionModel entity) {
		int id = super.save(entity);
		topicMapper.addDiscussnum(entity.getTopicId());
		return id;
	}

	@Override
	public List<PageData> selectForList(PageData pd) {
		PageHelper.startPage(pd.getInteger("start"), pd.getInteger("limit"), false);
		return discussionMapper.selectForList(pd);
	}

	@Override
	public PageData selectByDiscussion(Integer id) {
		return discussionMapper.selectByDiscussion(id);
	}

	@Override
	public List<DiscussionModel> selectDiscussionList(PageData pd) {
		PageHelper.startPage(pd.getInteger("start"), pd.getInteger("limit"), false);

		Condition condition = new Condition(DiscussionModel.class);
		Condition.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("userId", pd.getInteger("userId"));
		criteria.andEqualTo("isanonymous", pd.getInteger("isanonymous"));
		condition.orderBy("createtime").desc();
		return discussionMapper.selectByCondition(condition);
	}
	
	@Override
	public List<PageData> FindDiscussionList(PageData pd, int page, int rows) {
		PageHelper.startPage(page, rows);
		return discussionMapper.FindDiscussionList(pd);
	}

	// @Override
	// public void addCommenttimes(Integer id) {
	// discussionMapper.addCommenttimes(id);
	// }
	//
	// @Override
	// public void reduceCommenttimes(Integer id) {
	// discussionMapper.reduceCommenttimes(id);
	//
	// }
	//
	// @Override
	// public void addUpvotetimes(Integer id) {
	// discussionMapper.addUpvotetimes(id);
	// }
	//
	// @Override
	// public void reduceUpvotetimes(Integer id) {
	// discussionMapper.reduceUpvotetimes(id);
	// }
}

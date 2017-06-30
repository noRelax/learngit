/**
 * @Project:ZGHome
 * @FileName:ITopicServiceImpl.java
 */
package com.ehome.cloud.guangchang.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.guangchang.mapper.TopicMapper;
import com.ehome.cloud.guangchang.model.TopicModel;
import com.ehome.cloud.guangchang.service.ITopicService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.PageData;
import com.github.pagehelper.PageHelper;

/**
 * 广场话题接口实现
 * 
 * @Title:ITopicServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月7日
 * @version:
 */
@Service
public class TopicServiceImpl extends BaseService<TopicModel> implements ITopicService {

	@Resource
	private TopicMapper topicMapper;

	public List<Map<String,Object>> selectListPage(PageData pd, int page, int rows) {
		PageHelper.startPage(page, rows);
		return topicMapper.selectListPage(pd);
	}
	
	public List<Map<String,Object>> selectFileList(PageData pd) {
		return topicMapper.selectFileList(pd);
	}
	
	public PageData findListItem(Integer id) {
		return topicMapper.findListItem(id);
	}
	
	@Override
	public List<PageData> selectForList(PageData pd) {
		PageHelper.startPage(pd.getInteger("start"), pd.getInteger("limit"), false);
		return topicMapper.selectForList(pd);
	}
	
	@Override
	public List<PageData> selectTopicAndDiscussionList(PageData pd) {
		PageHelper.startPage(pd.getInteger("start"), pd.getInteger("limit"), false);
		// TODO Auto-generated method stub
		return topicMapper.selectTopicAndDiscussionList(pd);
	}
}

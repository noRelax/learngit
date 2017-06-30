package com.ehome.cloud.guangchang.mapper;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.guangchang.model.TopicModel;
import com.ehome.core.frame.MyMapper;
import com.ehome.core.util.PageData;

/**
 * 广场话题映射接口
 * 
 * @Title:TopicMapper
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
public interface TopicMapper extends MyMapper<TopicModel> {
	
	List<Map<String,Object>> selectListPage(PageData pd);
	
	PageData findListItem(Integer id);
	
	List<PageData> selectForList(PageData pd);

	List<Map<String,Object>> selectFileList(PageData pd);
	
	List<PageData> selectTopicAndDiscussionList(PageData pd);

	void addDiscussnum(Integer id);
}

/**
 * @Project:ZGHome
 * @FileName:DiscussionMapper.java
 */
package com.ehome.cloud.guangchang.mapper;

import java.util.List;

import com.ehome.cloud.guangchang.model.DiscussionModel;
import com.ehome.core.frame.MyMapper;
import com.ehome.core.util.PageData;

/**
 * 广场讨论映射接口
 * 
 * @Title:DiscussionMapper
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
public interface DiscussionMapper extends MyMapper<DiscussionModel> {
	
	void addCommenttimes(Integer id);

	void reduceCommenttimes(Integer id);

	void addUpvotetimes(Integer id);

	void reduceUpvotetimes(Integer id);

	List<PageData> selectForList(PageData pd);
	
	List<PageData> FindDiscussionList(PageData pd);
	
	PageData selectByDiscussion(Integer id);
}

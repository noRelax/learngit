/**
 * @Project:ZGHome
 * @FileName:IPictureService.java
 */
package com.ehome.cloud.guangchang.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.guangchang.model.TopicModel;
import com.ehome.core.frame.IService;
import com.ehome.core.util.PageData;

/**
 * 广场话题接口
 * 
 * @Title:ITopicService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
public interface ITopicService extends IService<TopicModel> {

	List<PageData> selectTopicAndDiscussionList(PageData pd);

	List<Map<String,Object>> selectListPage(PageData pd, int page, int rows);
	
	List<PageData> selectForList(PageData pd);

	List<Map<String,Object>> selectFileList(PageData pd);
	
	PageData findListItem(Integer id);
}

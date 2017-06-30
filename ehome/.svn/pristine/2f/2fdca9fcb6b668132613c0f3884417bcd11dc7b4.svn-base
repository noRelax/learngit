/**
 * @Project:ZGHome
 * @FileName:IDiscussionService.java
 */
package com.ehome.cloud.guangchang.service;

import java.util.List;

import com.ehome.cloud.guangchang.model.DiscussionModel;
import com.ehome.core.frame.IService;
import com.ehome.core.util.PageData;

/**
 * 广场讨论接口
 * 
 * @Title:IDiscussionService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月13日
 * @version:
 */
public interface IDiscussionService extends IService<DiscussionModel> {
	List<PageData> selectForList(PageData pd);

	PageData selectByDiscussion(Integer id);
	
	List<DiscussionModel> selectDiscussionList(PageData pd);
	
	List<PageData> FindDiscussionList(PageData pd, int page, int rows);
}

/**
 * @Project:ZGHome
 * @FileName:ICommentService.java
 */
package com.ehome.cloud.guangchang.service;

import java.util.List;

import com.ehome.cloud.guangchang.model.CommentModel;
import com.ehome.core.frame.IService;
import com.ehome.core.util.PageData;

/**
 * 广场评论接口
 * 
 * @Title:ICommentService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
public interface ICommentService extends IService<CommentModel> {

	int deleteByEntity(CommentModel commentModel);
	
	/**
	 * 根据id与用户表联查
	 * @param pd
	 * @return
	 */
	List<PageData> selectListByDiscussion(PageData pd);
}

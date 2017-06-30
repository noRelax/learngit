package com.ehome.cloud.guangchang.mapper;

import java.util.List;

import com.ehome.cloud.guangchang.model.CommentModel;
import com.ehome.core.frame.MyMapper;
import com.ehome.core.util.PageData;

/**
 * 广场评论映射文件
 * 
 * @Title:CommentMapper
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月6日
 * @version:
 */
public interface CommentMapper extends MyMapper<CommentModel> {

	List<PageData> selectListByDiscussion(PageData pd);
}

package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.app.marry.dto.AppMarryReplyDto;
import com.ehome.cloud.marry.model.MarryReplyModel;
import com.ehome.core.frame.IService;

/**
 * @Title: IAppMarryReplyService.java 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年4月17日 下午5:21:20 
 * @version 
 */
public interface IAppMarryReplyService extends IService<MarryReplyModel>{

	List<AppMarryReplyDto> selectReplyListDtoByCommentId(Integer id);
	
}

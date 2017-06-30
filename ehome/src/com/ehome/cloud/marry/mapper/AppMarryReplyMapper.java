package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.app.marry.dto.AppMarryReplyDto;
import com.ehome.cloud.marry.model.MarryReplyModel;
import com.ehome.core.frame.MyMapper;

public interface AppMarryReplyMapper extends MyMapper<MarryReplyModel> {

	List<AppMarryReplyDto> selectReplyListDtoByCommentId(
			@Param(value = "comentId") Integer id);

}

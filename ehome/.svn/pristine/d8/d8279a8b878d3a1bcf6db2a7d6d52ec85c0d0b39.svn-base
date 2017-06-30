package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.marry.dto.MarryCommentDto;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.core.frame.MyMapper;

public interface MarryCommentMapper extends MyMapper<MarryCommentModel> {

	List<MarryCommentDto> queryForList(MarryCommentDto dto);

	int updateStatus(@Param("isShielding") Integer isShielding,
			@Param("id") Integer id);

	int updateShielding(@Param("isShielding") Integer isShielding,
			@Param("photoIdsList") List<Integer> photoIdsList);

	List<AppMarryCommentDto> selectByPhotoId(
			@Param(value = "photoId") Integer photoId);

}

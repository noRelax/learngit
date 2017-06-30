package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.marry.dto.MarryCommentDto;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

public interface IMarryCommentService extends IService<MarryCommentModel> {

	List<MarryCommentDto> queryForList(MarryCommentDto dto, Integer start,
			Integer pageSize) throws BusinessException;

	int updateShielding(Integer isShielding, List<Integer> photoIdsList)
			throws BusinessException;

	int updateStatus(Integer isShielding, Integer id);

}

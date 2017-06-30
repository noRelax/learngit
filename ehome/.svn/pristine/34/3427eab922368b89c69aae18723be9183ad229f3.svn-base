package com.ehome.cloud.marry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.marry.dto.MarryCommentDto;
import com.ehome.cloud.marry.mapper.MarryCommentMapper;
import com.ehome.cloud.marry.model.MarryCommentModel;
import com.ehome.cloud.marry.service.IMarryCommentService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:MarryCommentServiceImpl
 * @Description:TODO
 * @author:tcr
 * @date:2017年4月17日 上午11:20:22
 * @version:
 */
@Service("marryCommentService")
public class MarryCommentServiceImp extends BaseService<MarryCommentModel>
		implements IMarryCommentService {

	@Resource
	private MarryCommentMapper marryCommentMapper;

	@Override
	public List<MarryCommentDto> queryForList(MarryCommentDto dto,
			Integer start, Integer pageSize) {
		PageHelper.startPage(start, pageSize);
		return marryCommentMapper.queryForList(dto);
	}

	@Override
	public int updateStatus(Integer isShielding, Integer id) {
		// TODO Auto-generated method stub
		return marryCommentMapper.updateStatus(isShielding, id);
	}

	@Override
	public int updateShielding(Integer isShielding, List<Integer> photoIdsList)
			throws BusinessException {
		return marryCommentMapper.updateShielding(isShielding, photoIdsList);
	}

}

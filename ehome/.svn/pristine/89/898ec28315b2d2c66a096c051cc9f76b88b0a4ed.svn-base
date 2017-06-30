package com.ehome.cloud.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.member.dto.MemberHomeDto;
import com.ehome.cloud.member.mapper.MemberHomeMapper;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.cloud.member.service.IMemberHomeService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:MemberHomeService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月22日 上午11:52:02
 * @version:
 */
@Service("memberHomeService")
public class MemberHomeServiceImpl extends BaseService<MemberHome> implements
		IMemberHomeService {
	
	@Resource
	private MemberHomeMapper memberHomeMapper;

	@Override
	public List<MemberHomeDto> queryForList(Integer memberId, Integer start,
			Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		return memberHomeMapper.queryForList(memberId);
	}

}

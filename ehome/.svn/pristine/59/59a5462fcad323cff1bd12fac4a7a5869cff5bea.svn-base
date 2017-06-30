package com.ehome.cloud.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.member.mapper.MemberResumeMapper;
import com.ehome.cloud.member.model.MemberResume;
import com.ehome.cloud.member.service.IMemberResumeService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:MemberResumeServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月1日 上午10:39:25
 * @version:
 */
@Service("memberResumeService")
public class MemberResumeServiceImpl extends BaseService<MemberResume>
		implements IMemberResumeService {

	@Resource
	private MemberResumeMapper memberResumeMapper;

	@Override
	public List<MemberResume> queryForList(Integer memberId, Integer start,
			Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		return memberResumeMapper.queryForList(memberId);
	}

}

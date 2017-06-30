package com.ehome.cloud.member.service;

import java.util.List;

import com.ehome.cloud.member.model.MemberResume;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IMemberResumeService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月1日 上午10:37:38
 * @version:
 */
public interface IMemberResumeService extends IService<MemberResume> {
	List<MemberResume> queryForList(Integer memberId, Integer start,
			Integer pageSize) throws BusinessException;
}

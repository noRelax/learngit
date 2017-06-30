package com.ehome.cloud.member.service;

import java.util.List;

import com.ehome.cloud.member.dto.MemberHomeDto;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IMemberHomeService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月22日 上午11:51:17
 * @version:
 */
public interface IMemberHomeService extends IService<MemberHome> {

	List<MemberHomeDto> queryForList(Integer memberId, Integer start,
			Integer pageSize) throws BusinessException;
}

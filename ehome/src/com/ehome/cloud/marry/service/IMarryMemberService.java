package com.ehome.cloud.marry.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ehome.cloud.marry.dto.MarryMemberDto;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

public interface IMarryMemberService extends IService<MarryMemberModel> {

	List<MarryMemberDto> queryForList(MarryMemberDto dto, int start,
			int pageSize) throws BusinessException;

	MarryMemberDto queryForEditList(Integer id) throws BusinessException;

	List<MarryMemberDto> queryForEditBlackList(Integer id, Integer uid,
			int start, int pageSize) throws BusinessException;

	int updateIsBlacklist(Integer id, Integer isBlacklist, Integer curUserId)
			throws BusinessException;

	List<MarryMemberDto> queryById(MarryMemberDto marryMemberDto)
			throws BusinessException;

	void exportMember(List<MarryMemberDto> marryMoldelList,
			HttpServletResponse response) throws BusinessException, IOException;

	void updateEvent(String event, Integer id) throws BusinessException;

}

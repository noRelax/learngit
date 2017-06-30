package com.ehome.cloud.member.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ehome.cloud.member.dto.MemberDto;
import com.ehome.cloud.member.dto.MemberImportDto;
import com.ehome.cloud.member.model.Member;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IMemberService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月21日 下午5:27:15
 * @version:
 */
public interface IMemberService extends IService<Member> {

	List<MemberDto> queryForList(MemberDto memberDto, Integer start,
			Integer pageSize) throws BusinessException;

	List<MemberDto> queryExport(MemberDto memberDto) throws BusinessException;

	List<MemberDto> queryExportOrg(MemberDto memberDto)
			throws BusinessException;

	Map<String, Object> saveImportMember(List<MemberImportDto> listMember,
			Integer userId, Integer baseOrgId, Integer upOrgId)
			throws BusinessException;

	int insertMember(Integer userId, Integer orgId, MemberDto memberDto)
			throws BusinessException;

	MemberDto checkIdCard(Integer userId, String idCard)
			throws BusinessException;

	void updateMember(MemberDto memberDto, MemberDto queryMember)
			throws BusinessException;

	Member queryMemberByAppUserId(Integer appUserId);

	void updateStatus(Integer auditStatus, List<Integer> memberIdList,
			String auditDesc, Integer baseOrgId) throws BusinessException;

	void transfer(Integer auditStatus, List<Integer> memberIdList,
			String auditDesc, Integer baseOrgId) throws BusinessException;

	void deleteMember(Integer id) throws BusinessException;

	void saveTemp(Integer userId) throws BusinessException;

	void deleteTemp(Integer userId) throws BusinessException;

	void exportMember(List<MemberDto> memberList, HttpServletResponse response)
			throws BusinessException, IOException;

	Integer seletctUpperUnionId(Integer memberId)throws Exception;

	List<Map<String, Object>> selectMemberList(Integer baseUnionId)throws Exception;
}

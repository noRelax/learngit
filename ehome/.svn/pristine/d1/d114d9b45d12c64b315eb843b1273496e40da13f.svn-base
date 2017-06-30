package com.ehome.cloud.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.member.dto.MemberDto;
import com.ehome.cloud.member.model.Member;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:MemberMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月21日 下午5:29:00
 * @version:
 */
public interface MemberMapper extends MyMapper<Member> {

	List<MemberDto> queryForList(MemberDto member);

	MemberDto queryByIdCard(@Param("idCard") String idCard);

	void updateStatus(@Param("auditStatus") Integer auditStatus,
			@Param("memberIdList") List<Integer> memberIdList);

	Integer seletctUpperUnionId(@Param("memberId") Integer memberId);

	List<Map<String, Object>> selectMemberList(@Param("memberId") Integer memberId);
}

package com.ehome.cloud.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.member.dto.MemberHomeDto;
import com.ehome.cloud.member.model.MemberHome;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:MemberHomeMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月22日 上午11:58:36
 * @version:
 */
public interface MemberHomeMapper extends MyMapper<MemberHome> {

	List<MemberHomeDto> queryForList(@Param("memberId") Integer memberId);
}

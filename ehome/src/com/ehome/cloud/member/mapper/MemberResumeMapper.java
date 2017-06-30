package com.ehome.cloud.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.member.model.MemberResume;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:MemberResumeMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月1日 上午10:40:42
 * @version:
 */
public interface MemberResumeMapper extends MyMapper<MemberResume> {

	List<MemberResume> queryForList(@Param("memberId") Integer memberId);

}

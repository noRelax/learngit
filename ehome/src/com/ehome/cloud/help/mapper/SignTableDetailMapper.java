package com.ehome.cloud.help.mapper;

import java.util.List;

import com.ehome.cloud.help.dto.SignTableDetailDto;
import com.ehome.cloud.help.model.SignTableDetailModel;
import com.ehome.core.frame.MyMapper;

/**
 * @Title: SignTableDetailMapper.java 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年3月22日 下午12:08:39 
 * @version 
 */
public interface SignTableDetailMapper extends MyMapper<SignTableDetailModel>{
	
	List<SignTableDetailDto> selectDtoBySignId(Integer signId);
	
}

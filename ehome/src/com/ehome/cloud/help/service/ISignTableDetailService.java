package com.ehome.cloud.help.service;

import java.util.List;

import com.ehome.cloud.help.dto.SignTableDetailDto;
import com.ehome.cloud.help.model.SignTableDetailModel;
import com.ehome.core.frame.IService;

/**
 * @Title: ISignTableDetailService
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年3月22日 下午12:05:33 
 * @version 
 */
public interface ISignTableDetailService extends IService<SignTableDetailModel>{
	
	List<SignTableDetailDto> selectDtoBySignId(Integer signId);
}

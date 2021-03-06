package com.ehome.cloud.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.help.dto.SignTableDetailDto;
import com.ehome.cloud.help.mapper.SignTableDetailMapper;
import com.ehome.cloud.help.model.SignTableDetailModel;
import com.ehome.cloud.help.service.ISignTableDetailService;
import com.ehome.core.frame.BaseService;

/**
 * @Title: SignTableDetailServiceImpl
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月22日 下午12:06:38
 * @version
 */
@Service("signTableDetailService")
public class SignTableDetailServiceImpl extends
		BaseService<SignTableDetailModel> implements ISignTableDetailService {
	
	@Resource
	private SignTableDetailMapper signTableDetailMapper;
	
	@Override
	public List<SignTableDetailDto> selectDtoBySignId(Integer signId) {
		return signTableDetailMapper.selectDtoBySignId(signId);
	}
}

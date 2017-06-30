package com.ehome.cloud.sys.service;

import java.util.List;

import com.ehome.cloud.sys.dto.AdvertiseDto;
import com.ehome.cloud.sys.model.Advertise;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IAdvertiseService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月14日 下午4:05:22
 * @version:
 */
public interface IAdvertiseService extends IService<Advertise> {

	List<AdvertiseDto> queryForList(String keyword, Integer start,
			Integer pageSize) throws BusinessException;
}

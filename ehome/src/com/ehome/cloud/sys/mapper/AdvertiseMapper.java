package com.ehome.cloud.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.dto.AdvertiseDto;
import com.ehome.cloud.sys.model.Advertise;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:AdvertiseMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月14日 下午3:58:13
 * @version:
 */
public interface AdvertiseMapper extends MyMapper<Advertise> {

	List<AdvertiseDto> queryForList(@Param("keyword") String keyword);

}

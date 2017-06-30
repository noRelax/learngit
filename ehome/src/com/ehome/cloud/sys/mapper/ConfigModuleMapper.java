package com.ehome.cloud.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.dto.ConfigModuleDto;
import com.ehome.cloud.sys.model.ConfigModule;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:ConfigModuleMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月8日 下午5:30:33
 * @version:
 */
public interface ConfigModuleMapper extends MyMapper<ConfigModule> {

	List<ConfigModuleDto> queryConfigList(@Param("moduleName") String moduleName);

	ConfigModuleDto queryMaxSort();

	List<ConfigModule> queryByCityId(@Param("lifeCityId") Integer lifeCityId);
	
	void deleteCityId(@Param("lifeConfigId") Integer lifeConfigId);

	void deleteUnloadId(@Param("unloadId") Integer unloadId);
}

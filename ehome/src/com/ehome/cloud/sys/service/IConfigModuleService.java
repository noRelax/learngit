package com.ehome.cloud.sys.service;

import java.util.List;

import com.ehome.cloud.sys.dto.ConfigModuleDto;
import com.ehome.cloud.sys.model.ConfigModule;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;
/**
 * 
 * @Title:IConfigModuleService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月9日 上午11:36:41
 * @version:
 */
public interface IConfigModuleService extends IService<ConfigModule> {

	List<ConfigModuleDto> queryConfigList(String moduleName, Integer start,
			Integer pageSize) throws BusinessException;

	ConfigModuleDto queryMaxSort();

	List<ConfigModule> queryByCityId(Integer lifeCityId)
			throws BusinessException;

	void deleteCityId(Integer lifeConfigId) throws BusinessException;

	void insertCfgModule(ConfigModule configModule, String json)
			throws BusinessException;

	void updateCfgModule(ConfigModule configModule, String json)
			throws BusinessException;
	
	void deleteCfgModule(Integer id, Integer unloadId) throws BusinessException;
	
	void deleteUnloadId(Integer unloadId);

}

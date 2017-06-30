package com.ehome.cloud.life.service;

import java.util.List;

import com.ehome.cloud.life.model.AreaModel;
import com.ehome.cloud.life.model.LifeCityModel;
import com.ehome.cloud.life.model.LifeConfigModel;
import com.ehome.core.frame.BusinessException;

public interface ILifeConfigService {

	List<LifeConfigModel> queryLifeConfigList(String lifeName,String provinceId,String cityId,Integer start,Integer pageSize) throws BusinessException;

	LifeConfigModel queryById(Integer lifeId) throws BusinessException;

	int updateLife(LifeConfigModel lifeConfigModel) throws BusinessException;

	int insertLife(LifeConfigModel lifeConfigModel) throws BusinessException;

    int deleteLife(Integer id) throws BusinessException;

	List<LifeConfigModel> querylocalList(String lifeName,Integer localId,int page, int rows);

	int updateLocal(LifeConfigModel lifeConfigModel);

	int insertLocal(LifeConfigModel lifeConfigModel);

	LifeConfigModel queryLocalById(Integer localId);

	List<Integer> querydataList(String lifeName,String startTime, String endTime,Integer start,Integer pageSize) throws BusinessException;

	String queryIcon()throws BusinessException;

	List<AreaModel> queryArea() throws BusinessException;

	void insertCity(List<Integer> list, Integer lifeCityId,Integer provinceId) throws BusinessException;

	List<LifeConfigModel> queryByCityId(Integer lifeCityId) throws BusinessException;

	void deleteCityId(Integer lifeConfigId) throws BusinessException;

	void insertsCity(LifeConfigModel lifeCityModel);

	List<LifeCityModel> selectByArea();

	List<LifeCityModel> selectByCity(String provinceId);

	List<AreaModel> queryAreaName(String areaName);

	void deleteUnloadId(Integer unloadId);

	LifeConfigModel queryMaxSort();

	LifeConfigModel queryLocalMaxSort(Integer parentId);

	List<LifeConfigModel> queryByLifeId(Integer id);

	void deleteLocalId(Integer parentId);

	List<LifeConfigModel> queryByParentId(Integer parentId);

	void deleteParUnloadId(LifeConfigModel unloadIds);

	List<LifeConfigModel> queryByPLifeId(Integer id);

	LifeConfigModel queryByIds(Integer id);

	void deletelocalUnloadId(Integer unloadId);

	void deleteLifeCityId(Integer id);
  
}

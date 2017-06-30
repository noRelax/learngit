package com.ehome.cloud.life.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.life.mapper.LifeConfigMapper;
import com.ehome.cloud.life.model.AreaModel;
import com.ehome.cloud.life.model.LifeCityModel;
import com.ehome.cloud.life.model.LifeConfigModel;
import com.ehome.cloud.life.service.ILifeConfigService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

@Service("lifeConfigService")
public class LifeConfigServiceImpl extends BaseService<LifeConfigModel>
		implements ILifeConfigService {

	@Resource
	private LifeConfigMapper lifeConfigMapper;

	@Override
	public List<LifeConfigModel> queryLifeConfigList(String lifeName,
			String provinceId, String cityId, Integer start, Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<LifeConfigModel> lifeConfigList = lifeConfigMapper
				.queryLifeConfigList(lifeName, provinceId, cityId);
		return lifeConfigList;
	}

	@Override
	public LifeConfigModel queryById(Integer lifeId) throws BusinessException {
		return lifeConfigMapper.queryById(lifeId);
	}

	@Override
	public int insertLife(LifeConfigModel lifeConfigModel)
			throws BusinessException {
		lifeConfigMapper.insertLife(lifeConfigModel);
		return lifeConfigModel.getId();
	}

	@Override
	public int deleteLife(Integer id) throws BusinessException {
		return lifeConfigMapper.deleteLife(id);
	}

	@Override
	public int updateLife(LifeConfigModel lifeConfigModel)
			throws BusinessException {
		return lifeConfigMapper.updateLife(lifeConfigModel);
	}

	@Override
	public List<LifeConfigModel> querylocalList(String lifeName,
			Integer localId, int start, int pageSize) {
		PageHelper.startPage(start, pageSize, true);
		List<LifeConfigModel> localList = lifeConfigMapper.querylocalList(
				lifeName, localId);
		return localList;
	}

	@Override
	public int updateLocal(LifeConfigModel lifeConfigModel) {
		return lifeConfigMapper.updateLocal(lifeConfigModel);
	}

	@Override
	public int insertLocal(LifeConfigModel lifeConfigModel) {
		return lifeConfigMapper.insertLocal(lifeConfigModel);
	}

	@Override
	public LifeConfigModel queryLocalById(Integer localId) {
		return lifeConfigMapper.queryLocalById(localId);
	}

	@Override
	public List<Integer> querydataList(String lifeName, String startTime,
			String endTime, Integer start, Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<Integer> dataList = lifeConfigMapper.querydataList(lifeName,
				startTime, endTime);
		return dataList;
	}

	@Override
	public String queryIcon() throws BusinessException {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryIcon();
	}

	@Override
	public List<AreaModel> queryArea() throws BusinessException {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryArea();
	}

	@Override
	public void insertCity(List<Integer> list, Integer lifeCityId,
			Integer provinceId) {
		lifeConfigMapper.insertCity(list, lifeCityId, provinceId);
	}

	@Override
	public List<LifeConfigModel> queryByCityId(Integer lifeCityId)
			throws BusinessException {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryByCityId(lifeCityId);
	}

	@Override
	public void deleteCityId(Integer lifeConfigId) throws BusinessException {
		lifeConfigMapper.deleteCityId(lifeConfigId);
	}

	@Override
	public void insertsCity(LifeConfigModel lifeCityModel)
			throws BusinessException {
		lifeConfigMapper.insertsCity(lifeCityModel);
	}

	@Override
	public List<LifeCityModel> selectByArea() {
		return lifeConfigMapper.selectByArea();
	}

	@Override
	public List<LifeCityModel> selectByCity(String provinceId) {
		return lifeConfigMapper.selectByCity(provinceId);
	}

	@Override
	public List<AreaModel> queryAreaName(String areaName) {
		return lifeConfigMapper.queryAreaName(areaName);
	}

	@Override
	public void deleteUnloadId(Integer unloadId) {
		lifeConfigMapper.deleteUnloadId(unloadId);
	}

	@Override
	public LifeConfigModel queryMaxSort() {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryMaxSort();
	}

	@Override
	public LifeConfigModel queryLocalMaxSort(Integer parentId) {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryLocalMaxSort(parentId);
	}

	@Override
	public List<LifeConfigModel> queryByLifeId(Integer id) {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryByLifeId(id);
	}

	@Override
	public void deleteLocalId(Integer parentId) {
		// TODO Auto-generated method stub
		lifeConfigMapper.deleteLocalId(parentId);
	}

	@Override
	public List<LifeConfigModel> queryByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryByParentId(parentId);
	}

	@Override
	public void deleteParUnloadId(LifeConfigModel unloadId) {
		lifeConfigMapper.deleteParUnloadId(unloadId);
	}

	@Override
	public List<LifeConfigModel> queryByPLifeId(Integer id) {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryByPLifeId(id);
	}

	@Override
	public LifeConfigModel queryByIds(Integer id) {
		// TODO Auto-generated method stub
		return lifeConfigMapper.queryByIds(id);
	}

	@Override
	public void deletelocalUnloadId(Integer unloadId) {
		// TODO Auto-generated method stub
		lifeConfigMapper.deletelocalUnloadId(unloadId);
	}

	@Override
	public void deleteLifeCityId(Integer id) {
		// TODO Auto-generated method stub
		lifeConfigMapper.deleteLifeCityId(id);
	}
}

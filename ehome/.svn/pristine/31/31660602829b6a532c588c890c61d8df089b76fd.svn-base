package com.ehome.cloud.life.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.life.model.AreaModel;
import com.ehome.cloud.life.model.LifeCityModel;
import com.ehome.cloud.life.model.LifeConfigModel;
import com.ehome.core.frame.MyMapper;

/**
 * 生活服务管理Mapper
 * 
 * @Title:LifeConfigMapper
 * @Description:TODO
 * @author:TCR
 * @date:2017年2月17日 上午16:58:12
 * @version:
 */

public interface LifeConfigMapper extends MyMapper<LifeConfigModel> {

	List<LifeConfigModel> queryLifeConfigList(@Param("lifeName") String lifeName,@Param("provinceId") String provinceId,@Param("cityId") String cityId);

	LifeConfigModel queryById(@Param("lifeId") Integer lifeId);

	int updateLife(LifeConfigModel lifeConfigModel);

	int insertLife(LifeConfigModel lifeConfigModel);

	int deleteLife(Integer id);

	List<LifeConfigModel> querylocalList(@Param("lifeName")String lifeName, @Param("localId") Integer localId);

	int updateLocal(LifeConfigModel lifeConfigModel);

	int insertLocal(LifeConfigModel lifeConfigModel);

	LifeConfigModel queryLocalById(Integer localId);

	List<Integer> querydataList(@Param("lifeName") String lifeName, @Param("startTime") String startTime, @Param("endTime") String endTime);

	String queryIcon();

	List<AreaModel> queryArea();

	void insertCity(@Param("list") List<Integer> list, @Param("lifeCityId") Integer lifeCityId,@Param("provinceId") Integer provinceId);

	List<LifeConfigModel> queryByCityId(@Param("lifeCityId") Integer lifeCityId);

	void deleteCityId(@Param("lifeConfigId") Integer lifeConfigId);

	void insertsCity(LifeConfigModel lifeCityModel);

	List<LifeCityModel> selectByArea();

	List<LifeCityModel> selectByCity(@Param("provinceId") String provinceId);

	List<AreaModel> queryAreaName(@Param("areaName") String areaName);

	void deleteUnloadId(@Param("unloadId") Integer unloadId);

	LifeConfigModel queryMaxSort();

	LifeConfigModel queryLocalMaxSort(@Param("parentId") Integer parentId);

	List<LifeConfigModel> queryByLifeId(@Param("id") Integer id);

	void deleteLocalId(@Param("parentId") Integer parentId);

	List<LifeConfigModel> queryByParentId(@Param("parentId") Integer parentId);

	void deleteParUnloadId(LifeConfigModel unloadId);

	List<LifeConfigModel> queryByPLifeId(@Param("id") Integer id);

	LifeConfigModel queryByIds(@Param("id") Integer id);

	void deletelocalUnloadId(@Param("unloadId") Integer unloadId);

	void deleteLifeCityId(@Param("id") Integer id);
}

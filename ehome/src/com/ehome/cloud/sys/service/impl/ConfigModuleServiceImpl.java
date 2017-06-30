package com.ehome.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehome.cloud.sys.dto.ConfigModuleDto;
import com.ehome.cloud.sys.mapper.ConfigModuleMapper;
import com.ehome.cloud.sys.model.ConfigModule;
import com.ehome.cloud.sys.model.ConfigModuleCity;
import com.ehome.cloud.sys.service.IConfigModuleCityService;
import com.ehome.cloud.sys.service.IConfigModuleService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.util.StringUtils;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Title:ConfigModuleServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月8日 下午5:31:27
 * @version:
 */
@Service("configModuleService")
public class ConfigModuleServiceImpl extends BaseService<ConfigModule>
		implements IConfigModuleService {

	@Resource
	private ConfigModuleMapper configModuleMapper;

	@Resource
	private IConfigModuleCityService configModuleCityService;

	@Override
	public List<ConfigModuleDto> queryConfigList(String moduleName,
			Integer start, Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		List<ConfigModuleDto> lifeConfigList = configModuleMapper
				.queryConfigList(moduleName);
		return lifeConfigList;
	}

	@Override
	public ConfigModuleDto queryMaxSort() {
		return configModuleMapper.queryMaxSort();
	}

	@Override
	public List<ConfigModule> queryByCityId(Integer lifeCityId)
			throws BusinessException {
		return configModuleMapper.queryByCityId(lifeCityId);
	}

	@Override
	public void deleteCityId(Integer lifeConfigId) throws BusinessException {
		configModuleMapper.deleteCityId(lifeConfigId);
	}

	@Override
	public void insertCfgModule(ConfigModule configModule, String json)
			throws BusinessException {
		this.save(configModule);
		Integer moduleId = configModule.getId();
		if (StringUtils.isNotBlank(json)) {
			JSONArray ja = JSON.parseArray(json);
			List<ConfigModuleCity> listCfgCity = new ArrayList<>();
			for (Object obj : ja) {
				JSONObject js = JSONObject.parseObject(obj.toString());
				if (null != js) {
					Integer pid = js.getInteger("pid");
					String pname = js.getString("pname");
					JSONArray children = JSON.parseArray(js
							.getString("children"));
					if (children != null) {
						for (Object chi : children) {
							ConfigModuleCity cityModel = new ConfigModuleCity();
							cityModel.setProvinceId(pid);
							cityModel.setProvinceName(pname);
							cityModel.setModuleId(moduleId);
							cityModel.setCreateTime(new Date());
							JSONObject cjs = JSONObject.parseObject(chi
									.toString());
							Integer cid = cjs.getInteger("cid");
							String cname = cjs.getString("cname");
							cityModel.setCityId(cid);
							cityModel.setCityName(cname);
							listCfgCity.add(cityModel);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(listCfgCity)) {
				configModuleCityService.insertList4Mysql(listCfgCity);
			}
		}
	}

	@Override
	public void updateCfgModule(ConfigModule configModule, String json)
			throws BusinessException {
		this.updateByKey(configModule);
		if (StringUtils.isNotBlank(json)) {
			JSONArray ja = JSON.parseArray(json);
			Condition condition = new Condition(ConfigModuleCity.class);
			condition.createCriteria().andEqualTo("moduleId",
					configModule.getId());
			List<ConfigModuleCity> cfgCityList = configModuleCityService
					.selectByCondition(condition);
			if (CollectionUtils.isNotEmpty(cfgCityList)) {
				for (ConfigModuleCity city : cfgCityList)
					configModuleCityService.deleteByKey(city.getId());
			}
			List<ConfigModuleCity> listCfgCity = new ArrayList<>();
			for (Object obj : ja) {
				JSONObject js = JSONObject.parseObject(obj.toString());
				if (null != js) {
					Integer pid = js.getInteger("pid");
					String pname = js.getString("pname");
					JSONArray children = JSON.parseArray(js
							.getString("children"));
					if (children != null) {
						for (Object chi : children) {
							ConfigModuleCity cityModel = new ConfigModuleCity();
							cityModel.setProvinceId(pid);
							cityModel.setProvinceName(pname);
							cityModel.setModuleId(configModule.getId());
							JSONObject cjs = JSONObject.parseObject(chi
									.toString());
							Integer cid = cjs.getInteger("cid");
							String cname = cjs.getString("cname");
							cityModel.setCityId(cid);
							cityModel.setCityName(cname);
							listCfgCity.add(cityModel);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(listCfgCity)) {
				configModuleCityService.insertList4Mysql(listCfgCity);
			}
		}
	}

	@Override
	public void deleteCfgModule(Integer id, Integer unloadId)
			throws BusinessException {
		Condition condition = new Condition(ConfigModuleCity.class);
		condition.createCriteria().andEqualTo("moduleId", id);
		List<ConfigModuleCity> cfgCityList = configModuleCityService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(cfgCityList)) {
			for (ConfigModuleCity city : cfgCityList)
				configModuleCityService.deleteByKey(city.getId());
		}
		this.deleteByKey(id);
		this.deleteUnloadId(unloadId);
	}

	@Override
	public void deleteUnloadId(Integer unloadId) {
		configModuleMapper.deleteUnloadId(unloadId);
	}
}

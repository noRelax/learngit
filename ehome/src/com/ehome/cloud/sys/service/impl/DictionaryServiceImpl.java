package com.ehome.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.dto.DictionaryDto;
import com.ehome.cloud.sys.mapper.DictionaryMapper;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.model.DictionaryType;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IDictionaryTypeService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.service.IEhCacheService;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageHelper;

/**
 * 数据字典接口服务实现
 * 
 * @Title:DictionaryServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:39:01
 * @version:
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends BaseService<Dictionary> implements
		IDictionaryService {

	@Resource
	private DictionaryMapper dictionaryMapper;

	@Resource
	private IDictionaryTypeService dictionaryTypeService;

	@Resource
	private IEhCacheService ehCacheService;

	/**
	 * 分页查询字典代码项集合
	 */
	@Override
	public List<Dictionary> queryForList(Integer dictTypeId, Integer start,
			Integer pageSize) throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		return dictionaryMapper.queryForList(dictTypeId);
	}

	/**
	 * 根据字典代码和字典代码项key查找字典代码项Value
	 */
	@Override
	public String getRenderFieldValue(String code, String fieldValue)
			throws BusinessException {
		Object object = ehCacheService.getCache(IEhCacheService.CACHE_SYS_DICT,
				String.format("%S%S", code, fieldValue));
		if (null == object) {
			return dictionaryMapper.getRenderFieldValue(code, fieldValue);
		} else {
			return object.toString();
		}
	}

	/**
	 * 根据字典代码查找字典代码项集合
	 */
	@Override
	@Cacheable({ IEhCacheService.CACHE_SYS_DICT_CODE })
	public List<Dictionary> queryByCode(String code) throws BusinessException {
		return dictionaryMapper.queryByCode(code);
	}

	/**
	 * 根据字典代码集合查找字典代码项Value集合
	 */
	@Override
	public List<String> getByCode(List<String> codeList)
			throws BusinessException {
		return dictionaryMapper.getByCode(codeList);
	}

	/**
	 * 根据字典代码和字典代码项Value查找字典代码项Key
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getFieldKeyByCode(String code, String dictValue)
			throws BusinessException {
		Object object = ehCacheService.getCache(
				IEhCacheService.CACHE_SYS_DICT_CODE, code);
		if (null != object && object instanceof ArrayList<?>) {
			String dictKey = "";
			List<Dictionary> dictList = (List<Dictionary>) object;
			if (CollectionUtils.isNotEmpty(dictList)) {
				for (Dictionary dict : dictList) {
					if (dict.getDictValue().equals(dictValue)) {
						dictKey = dict.getDictKey();
						break;
					}
				}
			}
			return dictKey;
		} else {
			return dictionaryMapper.getFieldKeyByCode(code, dictValue);
		}
	}

	/**
	 * 保存和修改字典类型
	 */
	@Override
	public void saveDictType(Integer id, String typeCode, String typeName,
			String remark) throws BusinessException {
		// 修改
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			DictionaryType dtModel = dictionaryTypeService.selectByKey(id);
			if (null != dtModel) {
				dtModel.setTypeCode(typeCode);
				dtModel.setTypeName(typeName);
				dtModel.setRemark(remark);
				dictionaryTypeService.updateByKey(dtModel);
			}
		} else {
			// 新增
			DictionaryType dtModel = new DictionaryType();
			dtModel.setTypeCode(typeCode);
			dtModel.setTypeName(typeName);
			dtModel.setRemark(remark);
			ehCacheService.setCache(IEhCacheService.CACHE_SYS_DICT_CODE,
					typeCode, Collections.EMPTY_LIST);
			dictionaryTypeService.save(dtModel);
		}
	}

	/**
	 * 根据字典类型ID删除字典类型和字典代码项
	 */
	@Override
	public void deleteByDictTypeId(Integer dictTypeId) throws BusinessException {
		ehCacheService.clearCache(IEhCacheService.CACHE_SYS_DICT);
		ehCacheService.clearCache(IEhCacheService.CACHE_SYS_DICT_CODE);
		dictionaryMapper.deleteByDictTypeId(dictTypeId);
		dictionaryTypeService.deleteByKey(dictTypeId);
	}

	/**
	 * 保存字典代码项
	 */
	@Override
	public void saveDict(Integer id, Integer dictTypeId, String dictKey,
			String dictValue, Integer sort, String remark, Integer userId)
			throws BusinessException {
		DictionaryType dictType = dictionaryTypeService.selectByKey(dictTypeId);
		// 修改
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			Dictionary dtModel = this.selectByKey(id);
			Dictionary dictModel = new Dictionary();
			BeanUtils.copyProperties(dtModel, dictModel);
			if (null != dtModel) {
				dtModel.setDictKey(dictKey);
				dtModel.setDictValue(dictValue);
				dtModel.setSort(sort);
				dtModel.setRemark(remark);
				if (null != dictType) {
					Object object = ehCacheService.getCache(
							IEhCacheService.CACHE_SYS_DICT_CODE,
							dictType.getTypeCode());
					if (null != object && object instanceof ArrayList<?>) {
						@SuppressWarnings("unchecked")
						List<Dictionary> dictList = (List<Dictionary>) object;
						if (CollectionUtils.isNotEmpty(dictList)) {
							dictList.remove(dictModel);
							dictList.add(dtModel);
							ehCacheService.setCache(
									IEhCacheService.CACHE_SYS_DICT_CODE,
									dictType.getTypeCode(), dictList);
						}
					}
					ehCacheService.setCache(IEhCacheService.CACHE_SYS_DICT,
							String.format("%S%S", dictType.getTypeCode(),
									dictKey), dictValue);
				}
				this.updateByKey(dtModel);
			}
		} else {
			// 新增
			Dictionary dtModel = new Dictionary();
			dtModel.setDictTypeId(dictTypeId);
			dtModel.setDictKey(dictKey);
			dtModel.setDictValue(dictValue);
			dtModel.setSort(sort);
			dtModel.setRemark(remark);
			dtModel.setCreateUser(userId);
			dtModel.setCreateTime(new Date());
			if (null != dictType) {
				Object object = ehCacheService.getCache(
						IEhCacheService.CACHE_SYS_DICT_CODE,
						dictType.getTypeCode());
				if (null != object && object instanceof ArrayList<?>) {
					@SuppressWarnings("unchecked")
					List<Dictionary> dictList = (List<Dictionary>) object;
					if (CollectionUtils.isNotEmpty(dictList)) {
						dictList.add(dtModel);
						ehCacheService.setCache(
								IEhCacheService.CACHE_SYS_DICT_CODE,
								dictType.getTypeCode(), dictList);
					}
				}
				ehCacheService.setCache(IEhCacheService.CACHE_SYS_DICT,
						String.format("%S%S", dictType.getTypeCode(), dictKey),
						dictValue);
			}
			this.save(dtModel);
		}
	}

	/**
	 * 根据主键ID删除字典代码项
	 */
	@Override
	public void deleteByDictId(Integer dictId) throws BusinessException {
		if (dictId != 0) {
			Dictionary dict = this.selectByKey(dictId);
			dict.getDictTypeId();
			DictionaryType dictType = dictionaryTypeService.selectByKey(dict
					.getDictTypeId());
			if (null != dictType) {
				ehCacheService.evictCache(IEhCacheService.CACHE_SYS_DICT_CODE,
						dictType.getTypeCode());
				ehCacheService.evictCache(
						IEhCacheService.CACHE_SYS_DICT,
						String.format("%S%S", dictType.getTypeCode(),
								dict.getDictKey()));
			}
			this.deleteByKey(dictId);
		}
	}

	@Override
	public List<DictionaryDto> getByCodeList(List<String> codeList)
			throws BusinessException {
		return dictionaryMapper.getByCodeList(codeList);
	}
}

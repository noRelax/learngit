package com.ehome.cloud.sys.service;

import java.util.List;

import com.ehome.cloud.sys.dto.DictionaryDto;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 数据字典接口服务
 * 
 * @Title:IDictionaryService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:34:09
 * @version:
 */
public interface IDictionaryService extends IService<Dictionary> {

	List<Dictionary> queryForList(Integer dictTypeId, Integer start,
			Integer pageSize) throws BusinessException;

	List<Dictionary> queryByCode(String code) throws BusinessException;

	String getRenderFieldValue(String code, String fieldValue)
			throws BusinessException;

	List<String> getByCode(List<String> codeList) throws BusinessException;

	String getFieldKeyByCode(String code, String dictValue)
			throws BusinessException;

	void saveDictType(Integer id, String typeCode, String typeName,
			String remark) throws BusinessException;

	void deleteByDictTypeId(Integer dictTypeId) throws BusinessException;

	void saveDict(Integer id, Integer dictTypeId, String dictKey,
			String dictValue, Integer sort, String remark, Integer userId)
			throws BusinessException;

	void deleteByDictId(Integer dictId) throws BusinessException;

	List<DictionaryDto> getByCodeList(List<String> codeList)
			throws BusinessException;

}

package com.ehome.cloud.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.sys.mapper.DictionaryTypeMapper;
import com.ehome.cloud.sys.model.DictionaryType;
import com.ehome.cloud.sys.service.IDictionaryTypeService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.BusinessException;
import com.github.pagehelper.PageHelper;

/**
 * 数据字典代码类型实现
 * 
 * @Title:DictionaryTypeServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:38:41
 * @version:
 */
@Service("dictionaryTypeService")
public class DictionaryTypeServiceImpl extends BaseService<DictionaryType>
		implements IDictionaryTypeService {

	@Resource
	private DictionaryTypeMapper dictionaryTypeMapper;
 
	@Override
	public List<DictionaryType> queryForList(String typeName,
			Integer userId, Integer start, Integer pageSize)
			throws BusinessException {
		PageHelper.startPage(start, pageSize, true);
		return dictionaryTypeMapper.queryForList(typeName);
	}
}

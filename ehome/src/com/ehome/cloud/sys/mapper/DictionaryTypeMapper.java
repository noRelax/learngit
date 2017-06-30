package com.ehome.cloud.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.model.DictionaryType;
import com.ehome.core.frame.MyMapper;

/**
 * 数据字典代码Dao
 * 
 * @Title:DictionaryTypeMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:40:38
 * @version:
 */
public interface DictionaryTypeMapper extends MyMapper<DictionaryType> {

	List<DictionaryType> queryForList(@Param("typeName") String typeName);
}

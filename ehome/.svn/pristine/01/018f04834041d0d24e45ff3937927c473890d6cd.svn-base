package com.ehome.cloud.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.dto.DictionaryDto;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.core.frame.MyMapper;

/**
 * 数据字典DAO
 * 
 * @Title:DictionaryMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:40:16
 * @version:
 */
public interface DictionaryMapper extends MyMapper<Dictionary> {

	List<Dictionary> queryForList(@Param("dictTypeId") Integer dictTypeId);

	String getRenderFieldValue(@Param("code") String code,
			@Param("fieldValue") String fieldValue);

	String getFieldKeyByCode(@Param("code") String code,
			@Param("dictValue") String dictValue);

	List<Dictionary> queryByCode(@Param("code") String code);

	void deleteByDictTypeId(@Param("dictTypeId") Integer dictTypeId);

	List<String> getByCode(@Param("codeList") List<String> codeList);

	List<DictionaryDto> getByCodeList(@Param("codeList") List<String> codeList); 
}

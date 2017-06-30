package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.marry.dto.MarryPhotoDto;
import com.ehome.cloud.marry.model.MarryPhoto;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.MyMapper;

/**
 * 
 * @Title:MarryPhotoMapper
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月17日 上午11:28:35
 * @version:
 */
public interface MarryPhotoMapper extends MyMapper<MarryPhoto> {

	List<MarryPhotoDto> queryForList(@Param("searchType") Integer searchType,
			@Param("keyword") String keyword,
			@Param("selectRequire") Integer selectRequire,
			@Param("isRecommended") Integer isRecommended,
			@Param("isShielding") Integer isShielding,
			@Param("startPublictTime") String startPublictTime,
			@Param("endPublicTime") String endPublicTime,
			@Param("fieldName") String fieldName,
			@Param("fieldSort") String fieldSort) throws BusinessException;
}

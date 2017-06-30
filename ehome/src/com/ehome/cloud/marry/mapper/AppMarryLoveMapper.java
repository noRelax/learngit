package com.ehome.cloud.marry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.app.marry.dto.AppMarryLoveDto;
import com.ehome.cloud.marry.model.MarryLoveModel;
import com.ehome.core.frame.MyMapper;

/**
 * @Title: AppMarryLoveMapper.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月20日 下午2:25:48
 * @version
 */
public interface AppMarryLoveMapper extends MyMapper<MarryLoveModel> {

	List<AppMarryLoveDto> randomSelectOppositeSex(
			@Param(value = "list") List<Integer> loved,
			@Param(value = "sex") Integer sex,
			@Param(value = "rows") Integer rows);

	List<AppMarryLoveDto> selectDtoByAppUserIdList(
			@Param(value = "list") List<Integer> loved);

	List<Integer> queryMyLove(@Param(value = "appUserId") Integer appUserId);

	List<Integer> queryLoveMe(@Param(value = "appUserId") Integer appUserId);

}

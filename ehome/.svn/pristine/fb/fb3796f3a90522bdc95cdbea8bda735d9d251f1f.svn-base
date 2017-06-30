package com.ehome.cloud.marry.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

import com.ehome.cloud.marry.dto.MarryPhotoLogDto;
import com.ehome.cloud.marry.model.MarryPhotoLog;
import com.ehome.cloud.marry.service.IMarryPhotoLogService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.util.EntityUtils;

/**
 * 
 * @Title:MarryPhotoLogServiceImpl
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 下午2:38:22
 * @version:
 */
@Service("marryPhotoLogService")
public class MarryPhotoLogServiceImpl extends BaseService<MarryPhotoLog>
		implements IMarryPhotoLogService {

	@Override
	public List<MarryPhotoLogDto> findLogList(Integer id, Integer pageNum,
			Integer pageSize) {
		Condition condition = new Condition(MarryPhotoLog.class);
		condition.createCriteria().andEqualTo("photoId", id);
		List<MarryPhotoLog> list = this.selectPageByCondition(condition,
				pageNum, pageSize);
		List<MarryPhotoLogDto> dtoList = EntityUtils.entityConvertDto(list,
				MarryPhotoLogDto.class);
		return dtoList;
	}

}

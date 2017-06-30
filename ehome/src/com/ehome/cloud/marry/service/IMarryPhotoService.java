package com.ehome.cloud.marry.service;

import java.util.List;

import com.ehome.cloud.marry.dto.MarryPhotoDto;
import com.ehome.cloud.marry.dto.MarryPhotoLogDto;
import com.ehome.cloud.marry.model.MarryPhoto;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IMarryPhotoService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月17日 上午11:19:08
 * @version:
 */
public interface IMarryPhotoService extends IService<MarryPhoto> {

	List<MarryPhotoDto> queryForList(MarryPhotoDto marryPhotoDto,
			Integer start, Integer pageSize) throws BusinessException;

	void updateShielding(Integer userId, Integer isShielding,
			List<Integer> photoIdsList) throws BusinessException;

	void saveEvent(Integer userId, Integer blacklist,
			MarryPhotoLogDto marryPhotoLogDto, Integer isAddRec,
			Integer isAddShield) throws BusinessException;

}

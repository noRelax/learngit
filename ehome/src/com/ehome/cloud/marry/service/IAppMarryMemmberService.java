package com.ehome.cloud.marry.service;

import java.util.Map;

import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.core.frame.IService;

public interface IAppMarryMemmberService extends IService<MarryMemberModel> {

	/**
	 * @param appUserId
	 *            当前登录的App 用户userId
	 * @param marryDemand
	 *            所选择的婚恋需求
	 * @param page
	 *            分页数据
	 * @param sourceDevice
	 *            来源设备 1web,2wap, 3android, 4IOS，5weixin, 6other
	 * @return
	 */
	Map<String, Object> queryIndexPhoto(Integer appUserId, Integer marryDemand,
			Integer page, Integer sourceDevice);

}

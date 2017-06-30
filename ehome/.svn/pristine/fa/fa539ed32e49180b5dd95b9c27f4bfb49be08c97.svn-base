/**
 * @Project:ZGHome
 * @FileName:IRightsService.java
 */
package com.ehome.cloud.help.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.help.model.RightsModel;
import com.ehome.core.frame.IService;

/**
 * @Title:IRightsService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年4月25日
 * @version:
 */
public interface IRightsService extends IService<RightsModel> {

	boolean saveRightsInfoAndSubmitTask(String instanceId, String taskId, Map<String, String> map, String userId);

	List<RightsModel> selectForListByCondition(Map<String, String> map, int pageNum, int pageSize);

}

/**
 * @Project:ZGHome
 * @FileName:RightServiceImpl.java
 */
package com.ehome.cloud.help.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.activiti.services.WorkFlowService;
import com.ehome.cloud.help.mapper.RightsMapper;
import com.ehome.cloud.help.model.RightsModel;
import com.ehome.cloud.help.service.IRightsService;
import com.ehome.core.frame.BaseService;
import com.ehome.core.frame.SpringContextHolder;
import com.github.pagehelper.PageHelper;

/**
 * @Title:RightServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年4月25日
 * @version:
 */
@Service
public class RightServiceImpl extends BaseService<RightsModel> implements IRightsService {

	@Resource
	private RightsMapper rightsMapper;

	/**
	 * 提交审批，判断如果流程结束则归档
	 */
	public boolean saveRightsInfoAndSubmitTask(String instanceId, String taskId, Map<String, String> map,
			String userId) {
		WorkFlowService workFlowService = SpringContextHolder.getBean(WorkFlowService.class);
		workFlowService.submitTaskFormData(taskId, map, userId);
		try {
			if (workFlowService.processIsEnd(instanceId) == 0) {
				saveRightsInfo(map, instanceId);
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * 归档保存
	 * 
	 * @param map
	 * @param instanceId
	 * @return
	 */
	public int saveRightsInfo(Map<String, String> map, String instanceId) {
		RightsModel rightModel = new RightsModel();
		rightModel.setName(map.get("name"));
		rightModel.setSex(map.get("sex"));
		rightModel.setNation(map.get("nation"));
		rightModel.setBirthday(map.get("birthday"));
		rightModel.setIdcard(map.get("IDcard"));
		rightModel.setRegistered(map.get("registered"));
		rightModel.setCompany(map.get("company"));
		rightModel.setAddress(map.get("address"));
		rightModel.setEducation(map.get("education"));
		rightModel.setHometel(map.get("homeTel"));
		rightModel.setWorkphone(map.get("workPhone"));
		rightModel.setLegalproceedings(map.get("legalProceedings"));
		rightModel.setHealth(map.get("health"));
		rightModel.setIndustry(map.get("industry"));
		rightModel.setRightapplytype(map.get("rightApplyType"));
		rightModel.setPostcode(map.get("postcode"));
		rightModel.setReasons(map.get("reasons"));
		rightModel.setUnionid(map.get("unionId"));
		rightModel.setParentunionid(map.get("parentUnionId"));
		rightModel.setUserid(map.get("userId"));
		rightModel.setIdcardpics(map.get("idCardPics"));
		rightModel.setProofpics(map.get("proofPics"));
		rightModel.setApplicationtime(map.get("applicationTime"));
		rightModel.setProcessinstanceid(instanceId);
		rightModel.setResult(map.get("direction"));
		rightModel.setFiletime(new Date());
		rightModel.setRemark("");

		return rightsMapper.insert(rightModel);
	}

	@Override
	public List<RightsModel> selectForListByCondition(Map<String, String> map, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return rightsMapper.selectForListByCondition(map);
	}

}

package com.ehome.cloud.help.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.help.dto.HelpApplyDto;
import com.ehome.cloud.help.dto.HelpApplyUserDto;
import com.ehome.cloud.help.model.HelpApplyFamilyModel;
import com.ehome.cloud.help.model.HelpApplyModel;
import com.ehome.cloud.member.dto.HelpMemberImportDto;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.IService;

/**
 * 
 * @Title:IHelpApplyService
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月22日 下午12:23:49
 * @version:
 */
public interface IHelpApplyService extends IService<HelpApplyModel> {

	Map<String, Object> saveHelpApply(HelpApplyModel helpApply,
			List<HelpApplyFamilyModel> familyList, Integer userId,
			String flowId, String taskId) throws Exception;

	Map<String, Object> saveImportHelpMember(
			List<HelpMemberImportDto> listHelpMember, Integer userId,
			Integer baseOrgId, Integer upOrgId);

	void updateMoney(Integer helpApplyId, String moneyFrom, String moneyNum);

	void saveApproval(String applyMsg, String resultId, String moneyFrom,
			String moneyNum, String taskId, String processInstanceId,
			Integer helpApplyId, String userId, Integer deptId, String userName)
			throws BusinessException;

	void updateApplyFileStatus(Integer id) throws BusinessException;

	List<HelpApplyDto> selectByApplyFileList(List<Integer> id,String name, String startTime,
			String endTime, Integer page, Integer rows)throws BusinessException;

	void insertProcessInstanceId(Integer id, String processInstanceId);

	void updateUserId(Integer helpApplyId,Integer userId)throws BusinessException;

	List<HelpApplyDto> selectByApplyFileSuperList(String name,
			String startTime, String endTime, Integer page, Integer rows)throws BusinessException;

	List<HelpApplyUserDto> selectHelpApplyId(Integer userId)throws BusinessException;

	void updateApplyFileEndManStatus(Integer id) throws BusinessException;

	void updateApplyFileEndStatus(Integer id) throws BusinessException;

	boolean saveRightsInfoAndSubmitTask(Integer helpApplyId, String instanceId);
}

package com.ehome.cloud.puhui.mapper;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.core.frame.MyMapper;

public interface PhOrderMapper extends MyMapper<PhOrder> {

	PhOrder selectByOrderId(Integer id);

	Integer deleteByPrimaryKey(String id);

	int insertOrder(PhOrder phOrder);

	int insertSelective(PhOrder phOrder);

	int updateByPrimaryKeySelective(PhOrder phOrder);

	int updateByPrimaryKey(PhOrder phOrder);

	List<PhOrder> selectListByOrder(PhOrder phOrder);

	Map<String, Object> selectStatistics(PhOrder ph);

	int updateSettleById(PhOrder phOrder);

	int updateExportTime(PhOrder phOrder);

	Map<String, Object> selectStatisticsByIds(String[] ids);

	int batchUpdateSettleByIds(Map map);

	String selectImgByMerchantId(Integer id);

}

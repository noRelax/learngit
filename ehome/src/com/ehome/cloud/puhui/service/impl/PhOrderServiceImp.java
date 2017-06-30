package com.ehome.cloud.puhui.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.puhui.mapper.PhOrderMapper;
import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.cloud.puhui.service.IPhOrderService;
import com.ehome.core.frame.BaseService;
import com.github.pagehelper.PageHelper;

@Service("phOrderService")
public class PhOrderServiceImp extends BaseService<PhOrder> implements
		IPhOrderService {

	@Resource
	private PhOrderMapper mapper;

	@Override
	public int insertOrder(PhOrder phOrder) {
		return mapper.insertOrder(phOrder);
	}

	@Override
	public List<PhOrder> selectListByOrder(PhOrder phOrder,
			Integer iDisplayStart, Integer iDisplayLength) {
		PageHelper.startPage(iDisplayStart, iDisplayLength,true);
		return mapper.selectListByOrder(phOrder);
	}
	
	@Override
	public List<PhOrder> selectListByOrderApp(PhOrder phOrder,
			Integer iDisplayStart, Integer iDisplayLength) {
		PageHelper.startPage(iDisplayStart, iDisplayLength,false);
		return mapper.selectListByOrder(phOrder);
	}

	@Override
	public List<PhOrder> selectListByOrderNoPage(PhOrder phOrder) {
		return mapper.selectListByOrder(phOrder);
	}

	@Override
	public Map<String, Object> selectStatistics(PhOrder ph) {
		return mapper.selectStatistics(ph);
	}

	@Override
	public Map<String, Object> selectStatisticsByIds(String[] ids) {
		return mapper.selectStatisticsByIds(ids);
	}

	@Override
	public PhOrder selectByOrderId(Integer id) {
		return mapper.selectByOrderId(id);
	}

	@Override
	public int updateSettleById(PhOrder phOrder) {
		return mapper.updateSettleById(phOrder);
	}

	@Override
	public int updateExportTime(PhOrder phOrder) {
		return mapper.updateExportTime(phOrder);
	}

	@Override
	public int batchUpdateSettleByIds(Map map) {
		return mapper.batchUpdateSettleByIds(map);
	}

	@Override
	public String selectImgByMerchantId(Integer id) {
		return mapper.selectImgByMerchantId(id);
	}

}

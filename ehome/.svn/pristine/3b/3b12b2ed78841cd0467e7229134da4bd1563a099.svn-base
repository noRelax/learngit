package com.ehome.cloud.puhui.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.puhui.model.PhOrder;

/**
 * 普惠商家订单服务类
 * 
 * @author kokJuis
 * @version 1.0
 * @date 2017-2-22
 */
public interface IPhOrderService {

	/**
	 * 插入一条数据
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-23
	 * @param phOrder
	 * @return
	 */
	public int insertOrder(PhOrder phOrder);

	/**
	 * 查询订单列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-23
	 * @param phOrder
	 * @return
	 */
	public List<PhOrder> selectListByOrder(PhOrder phOrder,
			Integer iDisplayStart, Integer iDisplayLength);

	public List<PhOrder> selectListByOrderNoPage(PhOrder phOrder);
	
	public List<PhOrder> selectListByOrderApp(PhOrder phOrder,
			Integer iDisplayStart, Integer iDisplayLength);

	/**
	 * 查询统计
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @return
	 */
	public Map<String, Object> selectStatistics(PhOrder ph);

	public Map<String, Object> selectStatisticsByIds(String[] ids);

	/**
	 * 通过id查询订单
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param id
	 * @return
	 */
	public PhOrder selectByOrderId(Integer id);

	/**
	 * 更新结算状态
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param id
	 * @return
	 */
	public int updateSettleById(PhOrder phOrder);

	/**
	 * 更新导出时间
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-1
	 * @param phOrder
	 * @return
	 */
	public int updateExportTime(PhOrder phOrder);

	/**
	 * 批量结算
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-1
	 * @param map
	 * @return
	 */
	public int batchUpdateSettleByIds(Map map);

	/**
	 * 查询一张店铺图片
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-6
	 * @param id
	 * @return
	 */
	public String selectImgByMerchantId(Integer id);

}

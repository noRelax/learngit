package com.ehome.cloud.puhui.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.area.model.AreaModel;
import com.ehome.cloud.area.service.IAreaService;
import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.cloud.puhui.service.IPhOrderService;
import com.ehome.cloud.puhui.util.ExportExcel;
import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.util.DateUtils;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/phOrder/*")
public class PhOrderController extends BaseController {

	@Resource(name = "phOrderService")
	private IPhOrderService phOrderService;

	@Resource
	private IAreaService iAreaService;

	/**
	 * 到订单列表
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goOrderList")
	public ModelAndView goOrderList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("puhui/order/phOrderList.html");
		return mv;
	}

	/**
	 * 查看订单详情
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "goOrderDetails")
	public ModelAndView goOrderDetails(Integer id) {

		PhOrder ph = phOrderService.selectByOrderId(id);
		ModelAndView mv = new ModelAndView("puhui/order/orderDetail.html");
		mv.addObject("ph", ph);
		return mv;
	}

	/**
	 * 获取统计数据
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-27
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getStatistics", method = RequestMethod.POST)
	public Map<String, Object> getStatistics(PhOrder ph) {
		// 查询统计
		Map<String, Object> statistics = phOrderService.selectStatistics(ph);
		return statistics;

	}

	/**
	 * 查询订单列表
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-27
	 * @param request
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderList", method = RequestMethod.POST)
	public Map<String, Object> getOrderList(HttpServletRequest request,
			PhOrder ph) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 获取前端过来的参数
		Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数
																		// 每次加1
		Integer iDisplayStart = Integer.valueOf(request
				.getParameter("iDisplayStart"));// 起始
		Integer iDisplayLength = Integer.valueOf(request
				.getParameter("iDisplayLength"));// 每页显示的size

		List<PhOrder> list = phOrderService.selectListByOrder(ph,
				iDisplayStart, iDisplayLength);

		PageInfo<PhOrder> pageInfo = new PageInfo<>(list);
		// 为操作次数加1
		int initEcho = sEcho + 1;
		map.put("sEcho", initEcho);
		map.put("iTotalRecords", pageInfo.getTotal());
		map.put("iTotalDisplayRecords", pageInfo.getTotal());
		map.put("aData", pageInfo.getList());
		return map;

	}

	/**
	 * 更新结算状态
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateSettle", method = RequestMethod.POST)
	public Map<String, Object> updateSettle(Integer id, String settleSemarks) {

		Map<String, Object> resutl = new HashMap<>();

		try {

			Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
			Session session = currentUser.getSession();
			LoginInfoDto user = (LoginInfoDto) session
					.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

			PhOrder phOrder = new PhOrder();
			phOrder.setId(id);
			phOrder.setSettleUserId(user.getId());
			phOrder.setSettleUserName(user.getUserName());
			phOrder.setSettleTime(new Date());
			phOrder.setSettleSemarks(settleSemarks);

			phOrderService.updateSettleById(phOrder);
			resutl.put("code", ResponseCode.success.getCode());
			resutl.put("msg", "更新成功");

		} catch (Exception e) {
			e.printStackTrace();
			resutl.put("code", ResponseCode.fail.getCode());
			resutl.put("msg", "更新失败");
		}

		return resutl;

	}

	/**
	 * 批量更新结算状态
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-3-1
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "batchUpdateSettleByIds", method = RequestMethod.POST)
	public Map<String, String> batchUpdateSettleByIds(String ids) {

		Map<String, String> result = new HashMap<>();

		String[] idsTmp = ids.split(",");

		Map<String, Object> map = new HashMap<>();

		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		LoginInfoDto user = (LoginInfoDto) session
				.getAttribute(SessionCons.LOGIN_USER_SESSION);// 当前的操作用户

		map.put("settleUserId", user.getId());
		map.put("settleUserName", user.getUserName());
		map.put("settleTime", new Date());
		map.put("settleSemarks", "批量结算");
		map.put("ids", idsTmp);

		try {
			phOrderService.batchUpdateSettleByIds(map);
			result.put("code", "10000");
			result.put("msg", "更新成功");

		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "-10000");
			result.put("msg", "更新失败");
		}

		return result;

	}

	/**
	 * 导出excel
	 *
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-28
	 * @param ids
	 * @param ph
	 * @param response
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(String orderIds, PhOrder ph,
			HttpServletRequest request, HttpServletResponse response) {

		List<PhOrder> list = new ArrayList<>();

		// 统计数据列表
		List<Map<String, String>> statisticsList = new ArrayList<>();

		// 三个统计用的MAP
		Map<String, String> oneMap = new LinkedHashMap<>();
		Map<String, String> twoMap = new LinkedHashMap<>();
		Map<String, String> threeMap = new LinkedHashMap<>();

		Map<String, Object> statistics;

		String searchType = request.getParameter("searchType");
		String searchTitle = request.getParameter("searchTitle");

		switch (searchType) {
		case "1":
			oneMap.put("检索字段", "商家名称");
			ph.setMerchantName(searchTitle);
			break;
		case "2":
			oneMap.put("检索字段", "商家ID");
			ph.setMerchantId(Integer.valueOf(searchTitle));
			break;
		case "3":
			oneMap.put("检索字段", "买单用户名");
			ph.setCustomerName(searchTitle);
			break;
		case "4":
			oneMap.put("检索字段", "用户ID");
			ph.setCustomerId(Integer.valueOf(searchTitle));
			break;
		}

		oneMap.put("检索关键词", searchTitle);

		// 有选择的ID,就导出指定ID的数据

		if (StringUtils.isNotEmpty(orderIds)) {

			String[] ids = orderIds.split(",");
			for (String id : ids) {
				PhOrder p = phOrderService.selectByOrderId(Integer.valueOf(id));
				list.add(p);
			}

			statistics = phOrderService.selectStatisticsByIds(ids);

		} else {

			list = phOrderService.selectListByOrderNoPage(ph);

			// 查询统计
			statistics = phOrderService.selectStatistics(ph);

		}


		if(list.size()==0){
			return;
		}



		oneMap.put(
				"支付日期",
				(ph.getStartDate() != null ? DateUtils.getTime(ph
						.getStartDate()) : "")
						+ "至"
						+ (ph.getEndDate() != null ? DateUtils.getTime(ph
								.getEndDate()) : ""));
		// 添加第一行的map
		statisticsList.add(oneMap);

		if (ph.getIssettle() != null) {
			if (ph.getIssettle() == 0) {
				twoMap.put("结算状态", "未结算");
			} else {
				twoMap.put("结算状态", "已结算");
			}
		} else {
			twoMap.put("结算状态", "");
		}

		if (ph.getPayWay() != null) {
			if (ph.getPayWay() == 0) {
				twoMap.put("支付方式", "微信支付");
			} else {
				twoMap.put("支付方式", "支付宝支付");
			}
		} else {
			twoMap.put("支付方式", "");
		}

		if (ph.getProvince() != null) {
			AreaModel am = iAreaService.selectByKey(ph.getProvince());
			twoMap.put("省份", am.getAreaName());
		} else {
			twoMap.put("省份", "");
		}

		if (ph.getCity() != null) {
			AreaModel am = iAreaService.selectByKey(ph.getCity());
			twoMap.put("城市", am.getAreaName());
		} else {
			twoMap.put("城市", "");
		}

		if (ph.getCounty() != null) {
			AreaModel am = iAreaService.selectByKey(ph.getCounty());
			twoMap.put("区/县", am.getAreaName());
		} else {
			twoMap.put("区/县", "");
		}

		// 添加第2行的map
		statisticsList.add(twoMap);

		threeMap.put("买单数", statistics.get("orderTotal").toString());
		threeMap.put("商家数", statistics.get("merchantTotal").toString());
		threeMap.put("支付金额统计(元)", statistics.get("payMoneyTotal").toString());
		threeMap.put("已结算买单数", statistics.get("settleOrder").toString());
		threeMap.put("已结算金额统计(元)", statistics.get("settlePayMoney").toString());
		threeMap.put("表格导出时间", DateUtils.getTime());
		// 添加第3行的map
		statisticsList.add(threeMap);

		// 列名
		String[] rowName = { "序号", "订单号", "支付时间", "消费原价(元)", "优惠金额(元)",
				"支付金额(元)", "商家名称", "省份", "城市", "区/县", "导出时间", "结算状态" };

		List<Object[]> tmp = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {

			PhOrder phTmp = list.get(i);

			String payTime = null;
			String Issettle = null;
			String province = null;
			String city = null;
			String county = null;
			String exportTime = null;

			// 转换时间格式
			if (phTmp.getPayTime() != null) {
				payTime = DateUtils.getTime(phTmp.getPayTime());
			}

			// 转换结算显示
			if (phTmp.getIssettle() != null) {

				if (phTmp.getIssettle() == 0) {
					Issettle = "未结算";
				} else {
					Issettle = "已结算";
				}
			}

			// 转换省市区
			if (phTmp.getProvince() != null) {
				AreaModel am = iAreaService.selectByKey(phTmp.getProvince());
				province = am.getAreaName();
			}

			if (phTmp.getCity() != null) {
				AreaModel am = iAreaService.selectByKey(phTmp.getCity());
				city = am.getAreaName();
			}

			if (phTmp.getCounty() != null) {
				AreaModel am = iAreaService.selectByKey(phTmp.getCounty());
				county = am.getAreaName();
			}

			// 设置导出时间
			phTmp.setExportTime(new Date());
			// 更新导出时间
			phOrderService.updateExportTime(phTmp);

			// 转换时间格式
			if (phTmp.getExportTime() != null) {
				exportTime = DateUtils.getTime(phTmp.getExportTime());
			}

			// 添加excel列数据
			Object[] obj = { i + 1, phTmp.getOrderNum(), payTime,
					phTmp.getOriginalPrice(), phTmp.getDiscountMoney(),
					phTmp.getPayMoney(), phTmp.getMerchantName(), province,
					city, county, exportTime, Issettle };
			tmp.add(obj);

		}

		ExportExcel ex = new ExportExcel("订单", rowName, tmp, statisticsList);

		try {
			HSSFWorkbook workbook = ex.export();

			String fileName = "Excel-"
					+ String.valueOf(System.currentTimeMillis()).substring(4,
							13) + ".xls";
			String headStr = "attachment; filename=\"" + fileName + "\"";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			OutputStream out = response.getOutputStream();
			workbook.write(out);

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

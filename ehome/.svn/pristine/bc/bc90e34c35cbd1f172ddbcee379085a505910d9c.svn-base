package com.ehome.cloud.app.puhui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.puhui.model.PhOrder;
import com.ehome.cloud.puhui.service.IPhOrderService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;

@Controller
@RequestMapping(value = "/app/phOrder/*")
public class AppPhOrderController extends BaseController {

	@Resource(name = "phOrderService")
	private IPhOrderService phOrderService;

	/**
	 * 获取订单列表
	 * 
	 * @author kokJuis
	 * @version 1.0
	 * @date 2017-2-24
	 * @param userId
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPhOrderList", method = RequestMethod.POST)
	public Map<String, Object> getPhOrderList(Integer userId,
			Integer iDisplayStart, Integer iDisplayLength) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			PhOrder phOrder = new PhOrder();
			phOrder.setCustomerId(userId);
			List<PhOrder> list = phOrderService.selectListByOrderApp(phOrder,
					iDisplayStart, iDisplayLength);

			// 循环获取图片路径
			for (PhOrder phOrder2 : list) {
				String imgUrl = phOrderService.selectImgByMerchantId(phOrder2
						.getMerchantId());
				phOrder2.setImgUrl(imgUrl);
			}

			result.put("status", ResponseCode.success.getCode());
			result.put("message", ResponseCode.success.getMsg());
			result.put("data", list);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", ResponseCode.fail.getCode());
			result.put("message", ResponseCode.fail.getMsg());
		}

		return result;
	}
}

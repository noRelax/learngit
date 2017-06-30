package com.ehome.cloud.app.advertise;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ehome.cloud.sys.model.Advertise;
import com.ehome.cloud.sys.service.IAdvertiseService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.NumberUtils;

/**
 * 广告接口
 * @Title:AppAdvertiseController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月14日 下午6:57:58
 * @version:
 */
@Controller
@RequestMapping(value = "/app/advertise")
public class AppAdvertiseController extends BaseController {

	@Resource
	private IAdvertiseService advertiseService;

	@RequestMapping(value = "/getAdvertise", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getAdvertise(NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer city,
			@RequestParam(required = false, defaultValue = "") Integer area,
			HttpServletResponse response) throws Exception {
		Condition condition = new Condition(Advertise.class);
		Criteria criteria = condition.createCriteria();
		if (!NumberUtils.isNull(city) && !NumberUtils.eqZero(city)) {
			criteria.andEqualTo("city", city);
		}
		if (!NumberUtils.isNull(area) && !NumberUtils.eqZero(area)) {
			criteria.andEqualTo("area", area);
		}
		condition.orderBy("createTime").desc();
		List<Advertise> advertiseList = advertiseService
				.selectByCondition(condition);
		if (CollectionUtils.isNotEmpty(advertiseList)) {
			if (advertiseList.size() > 3) {
				advertiseList = advertiseList.subList(0, 3);
			}
			return new AjaxResult(
					String.valueOf(ResponseCode.success.getCode()),
					ResponseCode.success.getMsg(), advertiseList);
		} else {
			return new AjaxResult(
					String.valueOf(ResponseCode.success.getCode()),
					ResponseCode.success.getMsg(), Collections.EMPTY_LIST);
		}
	}
}

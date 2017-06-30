/**
 * @Project:ZGHome
 * @FileName:AppClassify.java
 */
package com.ehome.cloud.app.puhui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.puhui.model.ClassifyModel;
import com.ehome.cloud.puhui.service.IClassifyService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.TokenUtil;

/**
 * @Title:AppClassify
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月28日
 * @version:
 */
@Controller
@RequestMapping(value="/app/puhui/class")
public class AppClassify extends BaseController {
	
	@Resource
	private IClassifyService iClassifyService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/getClassList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getClassList(
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token
			){
		logger.info("获取普惠商城分类列表");
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}

		try{
			List<ClassifyModel> list = iClassifyService.selectAll();
			map.put("status", ResponseCode.success.getCode());
			map.put("message", ResponseCode.success.getMsg());
			map.put("datas", list);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", ResponseCode.fail.getCode());
			map.put("message", ResponseCode.fail.getMsg());
		}
		return map;
	}
}

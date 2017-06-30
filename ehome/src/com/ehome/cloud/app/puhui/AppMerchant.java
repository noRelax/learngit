/**
 * @Project:ZGHome
 * @FileName:AppMerchant.java
 */
package com.ehome.cloud.app.puhui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.cloud.puhui.service.IMerchantService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.TokenUtil;

/**
 * @Title:AppMerchant
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月28日
 * @version:
 */
@Controller
@RequestMapping(value="/app/puhui/merchant")
public class AppMerchant extends BaseController {
	
	@Resource
	private IMerchantService iMerchantService;
	
	/**
	 * 获取商家列表
	 * @return
	 */
	@RequestMapping(value="/getMerchantList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchantList(
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "1") String start,
			@RequestParam(required = false, defaultValue = "10") String limit,
			@RequestParam(required = false, defaultValue = "") String sortType,
			@RequestParam(required = false, defaultValue = "") String lat,
			@RequestParam(required = false, defaultValue = "") String lng,
			@RequestParam(required = false, defaultValue = "0") String classId,
			@RequestParam(required = false, defaultValue = "0") String isMember
			){
		logger.info("获取普惠商城列表");
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		
		
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("lng", lng.trim());
		conditionMap.put("lat", lat.trim());
		conditionMap.put("start", Integer.parseInt(start.trim()));
		conditionMap.put("limit", Integer.parseInt(limit.trim()));
		conditionMap.put("classId", Integer.parseInt(classId.trim()));
		conditionMap.put("sortType", sortType);
		conditionMap.put("keyword", keyword);
		conditionMap.put("isMember", isMember);
		
		try{
			List<Map<String, Object>> list = null;
				//距离
			list = iMerchantService.getListByPosition(conditionMap);
			
			int orderCount = 0;
			String[] picIds;
			for (Map<String, Object> mc : list){
				orderCount = iMerchantService.getOrderCountByMcId((int)mc.get("id"));
				mc.put("orderCount", (int)mc.get("orderCount") + orderCount);
				
				picIds = mc.get("picIds").toString().split(",");
				if(picIds.length > 0){
					mc.put("picUrl", iMerchantService.getMcPicByPicid(Integer.parseInt(picIds[0])));
				}else{
					mc.put("picUrl", "");
				}
			}
			
			
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
	
	
	/**
	 * 获取商家详情
	 * @return
	 */
	@RequestMapping(value="/getMerchant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMerchant(
			@RequestParam(required = false, defaultValue = "") String time,
			@RequestParam(required = false, defaultValue = "") String apptype,
			@RequestParam(required = false, defaultValue = "") String token,
			@RequestParam(required = false, defaultValue = "") String id,
			HttpServletResponse response
			){
		logger.info("获取普惠商城详情");
		response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		Map<String, Object> map = new HashMap<String, Object>();
		if(!TokenUtil.validateToken("", Long.parseLong(time.trim()), apptype.trim(), token.trim())){
			map.put("status", ResponseCode.invalidtoken.getCode());
			map.put("message", ResponseCode.invalidtoken.getMsg());
			return map;
		}
		
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		try{
			MerchantModel mm = iMerchantService.selectByKey(Integer.parseInt(id.trim()));
			
			if(mm != null){
				List<Map<String, Object>> picList = iMerchantService.getPicsByIds(mm.getPicIds().split(","));
				
				modelMap.put("id", mm.getId());
				modelMap.put("name", mm.getName());
				modelMap.put("intro", mm.getIntro());
				modelMap.put("normalDiscount", mm.getNormalDiscount());
				modelMap.put("memberDiscount", mm.getMemberDiscount());
				modelMap.put("address", mm.getAddress());
				modelMap.put("phone", mm.getPhone());
				modelMap.put("labels", mm.getLabels());
				
				//营业时间
				String[] btime = mm.getBusinessTime().split(" ");
				String businessTime = "";
				if(mm.getBusinessTime().indexOf("周一 周二 周三 周四 周五 周六 周日") > -1){
					businessTime = "每周一~周日, 每天"+ btime[btime.length-2] + "-" + btime[btime.length-1];
				}else if(mm.getBusinessTime().indexOf("周一 周二 周三 周四 周五 周六") > -1){
					businessTime = "每周一~周六, 每天"+ btime[btime.length-2] + "-" + btime[btime.length-1];
				}else if(mm.getBusinessTime().indexOf("周一 周二 周三 周四 周五") > -1){
					businessTime = "每周一~周五, 每天"+ btime[btime.length-2] + "-" + btime[btime.length-1];
				}else{
					for(int i=0; i<btime.length-2; i++){
						if(!btime[i].equals("")){
							businessTime += btime[i] + "、";
						}
					}
					businessTime = businessTime.substring(0, businessTime.length()-1);
					businessTime = businessTime + ", 每天"+ btime[btime.length-2] + "-" + btime[btime.length-1];
				}
				modelMap.put("businessTime", businessTime);
				if(mm.getIscheck() == 0){
					modelMap.put("starttime", "");
					modelMap.put("endtime", "");
				}else{
					SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					modelMap.put("starttime", sdf.format(mm.getStarttime()));
					modelMap.put("endtime", sdf.format(mm.getEndtime()));
				}
				modelMap.put("pics", picList);
				modelMap.put("orderCount", mm.getInitialValue() + iMerchantService.getOrderCountByMcId(mm.getId()));
				modelMap.put("longitude", mm.getLongitude());
				modelMap.put("latitude", mm.getLatitude());
				modelMap.put("isCheck", mm.getIscheck());
			}else{
				throw new Exception();
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(modelMap);
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

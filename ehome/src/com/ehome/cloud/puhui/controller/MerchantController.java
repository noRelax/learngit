/**
 * @Project:ZGHome
 * @FileName:MerchantController.java
 */
package com.ehome.cloud.puhui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.cloud.puhui.service.IMerchantService;
import com.ehome.cloud.sys.model.UploadFile;
import com.ehome.cloud.sys.service.IUploadFileService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.BeanToMapUtil;
import com.github.pagehelper.PageInfo;

/**
 * 商家处理
 * 
 * @Title:MerchantController
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月22日
 * @version:
 */
@Controller
@RequestMapping(value = "/puhui/merchant")
public class MerchantController extends BaseController {
	
	@Resource
	private IMerchantService iMerchantService;
	@Resource
	private IUploadFileService iUploadFileService;

	/**
	 * 商家列表页面
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String listPage(){
		return "/puhui/merchant/list.html";
	}
	
	/**
	 * 获取商家列表
	 * @param name
	 * @param province
	 * @param city
	 * @param county
	 * @param sEcho
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/querylist")
	@ResponseBody
	public AjaxResult querylist(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String province,
			@RequestParam(required = false, defaultValue = "") String city,
			@RequestParam(required = false, defaultValue = "") String county,
			@RequestParam(required = false, defaultValue = "") String classifyId,
			@RequestParam(required = false, defaultValue = "") String starttime,
			@RequestParam(required = false, defaultValue = "") String endtime,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows
			){
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("province", province);
		map.put("city", city);
		map.put("county", county);
		map.put("classifyId", classifyId);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		
		List<Map<String,Object>> merchantsList = iMerchantService.selectPageByConditionMap(map, page, rows);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(merchantsList);
		Pagination<Map<String,Object>> pagination = new Pagination<Map<String,Object>>();
		pagination.setsEcho(sEcho);
		pagination.setData(pageInfo.getList());
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());

		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}
	
	/**
	 * 新增商家页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(){
		return "/puhui/merchant/add.html";
	}
	
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value="/addmerchant")
	@ResponseBody
	public AjaxResult addMerchant(
			@RequestParam(required = false, defaultValue = "") String id,
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") String province,
			@RequestParam(required = false, defaultValue = "") String city,
			@RequestParam(required = false, defaultValue = "") String county,
			@RequestParam(required = false, defaultValue = "") String address,
			@RequestParam(required = false, defaultValue = "") String longitude,
			@RequestParam(required = false, defaultValue = "") String latitude,
			@RequestParam(required = false, defaultValue = "") String phone,
			@RequestParam(required = false, defaultValue = "") String mon,
			@RequestParam(required = false, defaultValue = "") String tues,
			@RequestParam(required = false, defaultValue = "") String wed,
			@RequestParam(required = false, defaultValue = "") String thur,
			@RequestParam(required = false, defaultValue = "") String fri,
			@RequestParam(required = false, defaultValue = "") String sat,
			@RequestParam(required = false, defaultValue = "") String sun,
			@RequestParam(required = false, defaultValue = "") String bstime,
			@RequestParam(required = false, defaultValue = "") String betime,
			@RequestParam(required = false, defaultValue = "10") String normalDiscount,
			@RequestParam(required = false, defaultValue = "10") String memberDiscount,
			@RequestParam(required = false, defaultValue = "0") String initialValue,
			@RequestParam(required = false, defaultValue = "") String classifyId,
			@RequestParam(required = false, defaultValue = "") String keywords,
			@RequestParam(required = false, defaultValue = "") String labels,
			@RequestParam(required = false, defaultValue = "") String editorValue,
			@RequestParam(required = false, defaultValue = "") String linkman,
			@RequestParam(required = false, defaultValue = "") String linkmanPhone,
			@RequestParam(required = false, defaultValue = "") String settlementName,
			@RequestParam(required = false, defaultValue = "") String settlementAccount,
			@RequestParam(required = false, defaultValue = "") String settlementHolder,
			@RequestParam(required = false, defaultValue = "") String smsNumber1,
			@RequestParam(required = false, defaultValue = "") String smsNumber2,
			@RequestParam(required = false, defaultValue = "") String smsNumber3,
			@RequestParam(required = false, defaultValue = "") String mailSite1,
			@RequestParam(required = false, defaultValue = "") String mailSite2,
			@RequestParam(required = false, defaultValue = "") String mailSite3,
			@RequestParam(required = false, defaultValue = "") String isrecommend,
			@RequestParam(required = false, defaultValue = "") String recommendNum,
			@RequestParam(required = false, defaultValue = "") String ischeck,
			@RequestParam(required = false, defaultValue = "") String picIds,
			@RequestParam(required = false, defaultValue = "") String picDescs,
			@RequestParam(required = false, defaultValue = "") String starttime,
			@RequestParam(required = false, defaultValue = "") String endtime){
		
		MerchantModel merchantModel = new MerchantModel();
		try {
			merchantModel.setName(name);
			merchantModel.setProvince(Integer.parseInt(province));
			merchantModel.setCity(Integer.parseInt(city));
			merchantModel.setCounty(Integer.parseInt(county));
			merchantModel.setAddress(address);
			merchantModel.setLongitude(longitude);
			merchantModel.setLatitude(latitude);
			merchantModel.setPhone(phone);
			String businessTime = mon + " " + tues + " " + wed + " " + thur + " " + fri  + " " + sat + " " + sun + " " + bstime + " " + betime;
			merchantModel.setBusinessTime(businessTime);
			merchantModel.setNormalDiscount(normalDiscount);
			merchantModel.setMemberDiscount(memberDiscount);
			merchantModel.setInitialValue(Integer.parseInt(initialValue));
			merchantModel.setClassifyId(Integer.parseInt(classifyId));
			merchantModel.setKeywords(keywords);
			merchantModel.setLabels(labels.trim());
			merchantModel.setIntro(editorValue);
			merchantModel.setLinkman(linkman);
			merchantModel.setLinkmanPhone(linkmanPhone);
			merchantModel.setSettlementName(settlementName);
			merchantModel.setSettlementAccount(settlementAccount);
			merchantModel.setSettlementHolder(settlementHolder);
			merchantModel.setSmsNumber(smsNumber1+","+smsNumber2+","+smsNumber3);
			merchantModel.setMailSite(mailSite1+","+mailSite2+","+mailSite3);
			merchantModel.setIsrecommend(Integer.parseInt(isrecommend));
			merchantModel.setRecommendNum(Integer.parseInt(recommendNum));
			merchantModel.setIscheck(Integer.parseInt(ischeck));
			merchantModel.setPicIds(picIds);
		
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			if(!starttime.equals("")) merchantModel.setStarttime(sdf.parse(starttime));
			if(!endtime.equals("")) merchantModel.setEndtime(sdf.parse(endtime));
			
			merchantModel.setStatus(1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int count = 0;
		try{
			String[] spicIds = picIds.split(",");
			String[] spicDescs = picDescs.split(",");//前台多加一个空格保证每个都有描述，去除空格保存
			UploadFile uf = null;
			for(int i=0; i<spicIds.length; i++){
				if(!spicIds[i].trim().equals("")){
					uf = iUploadFileService.selectByKey(Integer.parseInt(spicIds[i]));
					uf.setContent(spicDescs[i].trim());
					iUploadFileService.updateByKey(uf);
				}
			}
			
			if(id.equals(""))
				count = iMerchantService.saveNotNull(merchantModel);
			else{
				merchantModel.setId(Integer.parseInt(id));
				count = iMerchantService.updateByKey(merchantModel);
			}
			
			if(count > 0){
				return new AjaxResult(ResponseCode.success.getCode(), "", "");
			}else{
				return new AjaxResult(ResponseCode.fail.getCode(), "", "");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
		
	}
	
	/**
	 * 编辑商家页面
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "") String id){
		ModelAndView mv = new ModelAndView("/puhui/merchant/edit.html");
		MerchantModel mm = iMerchantService.selectByKey(Integer.parseInt(id));
		String tempintro = StringUtils.replace(mm.getIntro(), "'", "\\'");
		mm.setIntro(tempintro);
		if(mm.getNormalDiscount().trim().equals("10")) mm.setNormalDiscount("") ;
		if(mm.getMemberDiscount().trim().equals("10")) mm.setMemberDiscount(""); ;
		Map<String, Object> mmMap = BeanToMapUtil.beanToMap(mm);
		String[] sArray ;
		sArray = mm.getSmsNumber().split(",");
		for(int i = 0; i< sArray.length; i++){
			mmMap.put("smsNumber"+(i+1), sArray[i]);
		}
		sArray = mm.getMailSite().split(",");
		for(int i = 0; i< sArray.length; i++){
			mmMap.put("mailSite"+(i+1), sArray[i]);
		}
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		if(mmMap.get("starttime") != null) mmMap.put("starttime", sdf.format((Date)mmMap.get("starttime")));
		if(mmMap.get("endtime") != null) mmMap.put("endtime", sdf.format((Date)mmMap.get("endtime")));
		
		List<Map<String, Object>> picList = iMerchantService.getPicsByIds(mm.getPicIds().split(","));
		mv.addObject("mm", mmMap);
		mv.addObject("picList", JSONArray.toJSONString(picList));
		return mv;
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxResult delete(@RequestParam(required = false, defaultValue = "") String id){
		
		int count = 0;
		if(!id.equals("")){
			MerchantModel mm = iMerchantService.selectByKey(Integer.parseInt(id.trim()));
			String[] picids = mm.getPicIds().split(",");
			int picid = 0;
			for(String tmps : picids){
				if(!tmps.equals(""))
					picid = Integer.parseInt(tmps);
					deleteFile(picid);
			}
			count = iMerchantService.deleteByKey(Integer.parseInt(id.trim()));
		}
		
		if(count > 0){
			return new AjaxResult(ResponseCode.success.getCode(), "", "");
		}else{
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
		
	}
	
	/**
	 * 新增商家页面
	 * @return
	 */
	@RequestMapping(value = "/setTypePage")
	public String setTypePage(){
		return "/puhui/merchant/setTypePage.html";
	}
	
	/**
	 * 设置商家类型
	 * @return
	 */
	@RequestMapping(value = "/setType")
	@ResponseBody
	public AjaxResult setType(
			@RequestParam(required = false, defaultValue = "") String mmids,
			@RequestParam(required = false, defaultValue = "") String isRecommend
			){
		String[] ids = mmids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(!isRecommend.equals("")){
				map.put("ids", ids);
				map.put("isRecommend", isRecommend);
				iMerchantService.setTypeByIds(map);
			}
			
			return new AjaxResult(ResponseCode.success.getCode(), "", "");
		}catch(Exception e){
			e.printStackTrace();
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
	}
	
	public int deleteFile(int id){			
		UploadFile uf = null;
		uf = iUploadFileService.selectByKey(id);
		uf.setFlag(-1);
		return iUploadFileService.updateByKey(uf);
	}
	
	/**
	 * 删除图片
	 * @return
	 */
	@RequestMapping(value="/deletePic")
	@ResponseBody
	public AjaxResult deletePic(
			@RequestParam(required = false, defaultValue = "") String picid,
			@RequestParam(required = false, defaultValue = "") String id
			){
		
		try{
			if(!picid.equals("")){
				deleteFile(Integer.parseInt(picid.trim()));
			}
			if(!id.equals("")){
				MerchantModel mm = iMerchantService.selectByKey(Integer.parseInt(id.trim()));
				mm.setPicIds(mm.getPicIds().replaceAll(picid+",", ""));
				iMerchantService.updateByKey(mm);
			}
			
			return new AjaxResult(ResponseCode.success.getCode(), "", "");
		}catch(Exception e){
			e.printStackTrace();
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
		
	}
	
	/**
	 * 打开地图页面
	 * @return
	 */
	@RequestMapping(value = "/openMap")
	public String openMap(){
		return "/puhui/merchant/openMap.html";
	}
	
	/**
	 * 打开文件上传页面
	 * @return
	 */
	@RequestMapping(value = "/toFileLayer")
	public String toFileLayer(){
		return "/puhui/merchant/fileLayer.html";
	}
	
	/**
	 * 打开商家推荐值页面
	 * @return
	 */
	@RequestMapping(value = "/toRecommendLayer")
	public String toRecommendLayer(){
		return "/puhui/merchant/recommendLayer.html";
	}
	
	
	/**
	 * 获取商家推荐列表
	 * @param sEcho
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/recommendlist")
	@ResponseBody
	public AjaxResult recommendlist(
			@RequestParam(required = false, defaultValue = "") String classifyId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows
			){
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classifyId", classifyId);
		
		List<Map<String,Object>> merchantsList = iMerchantService.selectPageForRecommendlist(map, page, rows);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(merchantsList);
		Pagination<Map<String,Object>> pagination = new Pagination<Map<String,Object>>();
		pagination.setsEcho(sEcho);
		pagination.setData(pageInfo.getList());
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}
}

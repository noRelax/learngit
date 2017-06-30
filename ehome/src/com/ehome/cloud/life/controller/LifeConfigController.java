package com.ehome.cloud.life.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ehome.cloud.common.controller.CommonController;
import com.ehome.cloud.life.model.AreaModel;
import com.ehome.cloud.life.model.LifeCityModel;
import com.ehome.cloud.life.model.LifeConfigModel;
import com.ehome.cloud.life.service.ILifeConfigService;
import com.ehome.cloud.sys.service.IUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.StringUtils;
import com.github.pagehelper.PageInfo;

/**
 * 服务管理控制类入口
 * 
 * @Title:LifeConfigController
 * @Description:TODO
 * @author:TCR
 * @date:2017年2月17日 上午11:33:15
 * @version:
 */
@Controller
@RequestMapping(value = "/life")
public class LifeConfigController extends CommonController {

	@Resource
	private ILifeConfigService lifeConfigService;

	@Resource
	private IUserService userService;

	/**
	 * 生活服务首页
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list")
	@RequiresUser
	@RequiresPermissions("life:mgr:config")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new BaseModelAndView("/life/lifeconfig/list.html",
				request);
		return view;
	}

	@RequestMapping(value = "/querylist", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("life:mgr:config")
	@ResponseBody
	public AjaxResult queryLifelList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String lifeName,
			@RequestParam(required = false, defaultValue = "") String provinceId,
			@RequestParam(required = false, defaultValue = "") String cityId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<LifeConfigModel> lifeConfigList = lifeConfigService
				.queryLifeConfigList(lifeName, provinceId, cityId, page, rows);
		DictoryCodeUtils.renderList(lifeConfigList);
		PageInfo<LifeConfigModel> pageInfo = new PageInfo<>(lifeConfigList);
		Pagination<LifeConfigModel> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 修改服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit-life-page", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("life:mgr:edit")
	public String editLifePage(Model model, NativeWebRequest request) {
		Integer lifeId = NumberUtils.toInt(request.getParameter("lifeId"));
		LifeConfigModel lifeConfigModel = lifeConfigService.queryById(lifeId);
		List<LifeConfigModel> lifeCityModel = lifeConfigService
				.queryByCityId(lifeId);
		model.addAttribute("lifeId", lifeId);
		model.addAttribute("unloadId", lifeConfigModel.getUnloadId());
		model.addAttribute("lifeConfigModel", lifeConfigModel);
		model.addAttribute("lifeCityModel", JSON.toJSON(lifeCityModel));
		return "/life/lifeconfig/edit.html";
	}

	/**
	 * 新增服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-life-page", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("life:mgr:add")
	public String addLifePage(Model model, NativeWebRequest request) {
		LifeConfigModel maxSort = lifeConfigService.queryMaxSort();
		if (maxSort == null) {
			maxSort = new LifeConfigModel();
			maxSort.setSort(50);
			model.addAttribute("maxSort", maxSort);
		} else {
			model.addAttribute("maxSort", maxSort);
		}
		return "/life/lifeconfig/add.html";
	}

	/**
	 * 保存生活服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addLife", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult addLife(Model model, NativeWebRequest request,
			MultipartFile filed,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") String lifeName,
			@RequestParam(required = false, defaultValue = "") String file,
			@RequestParam(required = false, defaultValue = "") String icon,
			@RequestParam(required = false, defaultValue = "") String url,
			@RequestParam(required = false, defaultValue = "") Integer isIndex,
			@RequestParam(required = false, defaultValue = "") String lifeDept,
			@RequestParam(required = false, defaultValue = "") String remark,
			@RequestParam(required = false, defaultValue = "") Integer sort,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") Integer cityId,
			@RequestParam(required = false, defaultValue = "") String json,
			@RequestParam(required = false, defaultValue = "") Integer unloadId)
			throws BusinessException {
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			LifeConfigModel lifeConfigModel = lifeConfigService.queryById(id);
			// List<LifeConfigModel> lifeCityModel =
			// lifeConfigService.queryByCityId(id);
			if (lifeConfigModel != null) {
				lifeConfigModel.setLifeName(lifeName);
				lifeConfigModel.setIcon(icon);
				lifeConfigModel.setIsIndex(isIndex);
				lifeConfigModel.setLifeDept(lifeDept);
				lifeConfigModel.setSort(sort);
				lifeConfigModel.setStatus(status);
				lifeConfigModel.setUrl(url);
				lifeConfigModel.setRemark(remark);
				lifeConfigModel.setUnloadId(unloadId);
				// lifeCityModel.setCityId(cityId);
				// lifeCityModel.set(index, element)
				lifeConfigService.updateLife(lifeConfigModel);
				// List<Integer> idList= new ArrayList<>(Arrays.asList(list));
				// if(idList !=null){
				// lifeConfigService.deleteCityId(id);
				// lifeConfigService.insertCity(idList, id,provinceId);
				// }
				//
				if (StringUtils.isNotBlank(json)) {
					// JSONObject jsonObj =JSONObject.parseObject(json);
					JSONArray ja = JSON.parseArray(json);
					List<LifeConfigModel> lifeCityId = lifeConfigService
							.queryByCityId(id);
					if (lifeCityId != null) {
						lifeConfigService.deleteCityId(id);
					}
					for (Object obj : ja) {
						JSONObject js = JSONObject.parseObject(obj.toString());
						LifeConfigModel lifeCityModel = new LifeConfigModel();
						if (null != js) {
							Integer pid = js.getInteger("pid");
							String pname = js.getString("pname");
							lifeCityModel.setProvinceId(pid);
							lifeCityModel.setProvinceName(pname);
							lifeCityModel.setLifeConfigId(id);
							JSONArray children = JSON.parseArray(js
									.getString("children"));
							if (children != null) {
								for (Object chi : children) {
									JSONObject cjs = JSONObject.parseObject(chi
											.toString());
									Integer cid = cjs.getInteger("cid");
									String cname = cjs.getString("cname");
									lifeCityModel.setCityId(cid);
									lifeCityModel.setCityName(cname);
									lifeConfigService
											.insertsCity(lifeCityModel);
								}
							}
						}
					}
				}
			}
		} else {
			LifeConfigModel lifeConfigModel = new LifeConfigModel();
			// String icon = lifeConfigService.queryIcon();
			lifeConfigModel.setLifeName(lifeName);
			lifeConfigModel.setIcon(icon);
			lifeConfigModel.setIsIndex(isIndex);
			lifeConfigModel.setLifeDept(lifeDept);
			lifeConfigModel.setSort(sort);
			lifeConfigModel.setStatus(status);
			lifeConfigModel.setUrl(url);
			lifeConfigModel.setRemark(remark);
			lifeConfigModel.setUnloadId(unloadId);
			Integer lifeCityId = lifeConfigService.insertLife(lifeConfigModel);
			//
			// List<Integer> idList= new ArrayList<>(Arrays.asList(list));
			// lifeConfigService.insertCity(idList, lifeCityId,provinceId);
			if (StringUtils.isNotBlank(json)) {
				// JSONObject jsonObj =JSONObject.parseObject(json);
				JSONArray ja = JSON.parseArray(json);
				for (Object obj : ja) {
					JSONObject js = JSONObject.parseObject(obj.toString());
					LifeConfigModel lifeCityModel = new LifeConfigModel();
					if (null != js) {
						Integer pid = js.getInteger("pid");
						String pname = js.getString("pname");
						lifeCityModel.setProvinceId(pid);
						lifeCityModel.setProvinceName(pname);
						lifeCityModel.setLifeConfigId(lifeCityId);
						JSONArray children = JSON.parseArray(js
								.getString("children"));
						if (children != null) {
							for (Object chi : children) {
								JSONObject cjs = JSONObject.parseObject(chi
										.toString());
								Integer cid = cjs.getInteger("cid");
								String cname = cjs.getString("cname");
								lifeCityModel.setCityId(cid);
								lifeCityModel.setCityName(cname);
								lifeConfigService.insertsCity(lifeCityModel);
							}
						}
					}
				}
			}
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * _ 删除生活服务列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	// @RequiresPermissions("sys:user:add")
	public AjaxResult delete(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer unloadId)
			throws BusinessException {
		List<LifeConfigModel> lifeId = lifeConfigService.queryByLifeId(id);
		if (lifeId.size() != 0) {
			lifeConfigService.deleteLife(id);
			lifeConfigService.deleteLifeCityId(id);
			for (int i = 0; i < lifeId.size(); i++) {
				List<LifeConfigModel> parentUnloadId = lifeConfigService
						.queryByParentId(lifeId.get(i).getParentId());
				for (int j = 0; j < parentUnloadId.size(); j++) {
					LifeConfigModel unloadIds = new LifeConfigModel();
					unloadIds = parentUnloadId.get(j);
					lifeConfigService.deleteParUnloadId(unloadIds);
				}
			}
			List<LifeConfigModel> lifeIds = lifeConfigService
					.queryByPLifeId(id);
			for (int k = 0; k < lifeIds.size(); k++) {
				lifeConfigService.deleteLocalId(lifeIds.get(0).getParentId());
			}
		}
		if (unloadId != null) {
			lifeConfigService.deleteUnloadId(unloadId);
		}
		if (lifeId.size() == 0) {
			LifeConfigModel idd = lifeConfigService.queryByIds(id);
			lifeConfigService.deletelocalUnloadId(idd.getUnloadId());
			lifeConfigService.deleteLife(id);
			lifeConfigService.deleteLifeCityId(id);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	@RequestMapping(value = "/locallist", method = RequestMethod.GET)
	@RequiresUser
	public String locallist(Model model, NativeWebRequest request) {
		Integer localId = NumberUtils.toInt(request.getParameter("localId"));
		model.addAttribute("localId", localId);
		return "/life/lifeconfig/locallist.html";
	}

	@RequestMapping(value = "/querylocallist", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult queryLocalList(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer localId,
			@RequestParam(required = false, defaultValue = "") String lifeName,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<LifeConfigModel> localList = lifeConfigService.querylocalList(
				lifeName, localId, page, rows);
		DictoryCodeUtils.renderList(localList);
		PageInfo<LifeConfigModel> pageInfo = new PageInfo<>(localList);
		Pagination<LifeConfigModel> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 新增目标页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "add-local-page", method = RequestMethod.GET)
	@RequiresUser
	public String addlocalPage(Model model, NativeWebRequest request) {
		Integer lifeId = NumberUtils.toInt(request.getParameter("lifeId"));
		model.addAttribute("lifeId", lifeId);
		LifeConfigModel maxSort = lifeConfigService.queryLocalMaxSort(lifeId);
		if (maxSort == null) {
			maxSort = new LifeConfigModel();
			maxSort.setSort(50);
			model.addAttribute("maxSort", maxSort);
		} else {
			model.addAttribute("maxSort", maxSort);
		}
		return "/life/lifeconfig/localadd.html";
	}

	/**
	 * 保存目标页
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addlocal", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult addlocal(
			Model model,
			NativeWebRequest request,
			MultipartFile file,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer parentId,
			@RequestParam(required = false, defaultValue = "") String lifeName,
			@RequestParam(required = false, defaultValue = "") String icon,
			@RequestParam(required = false, defaultValue = "") String url,
			@RequestParam(required = false, defaultValue = "") Integer sort,
			@RequestParam(required = false, defaultValue = "") Integer status,
			@RequestParam(required = false, defaultValue = "") Integer unloadId)
			throws BusinessException {
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			LifeConfigModel lifeConfigModel = lifeConfigService.queryById(id);
			if (lifeConfigModel != null) {
				lifeConfigModel.setLifeName(lifeName);
				lifeConfigModel.setIcon(icon);
				lifeConfigModel.setSort(sort);
				lifeConfigModel.setStatus(status);
				lifeConfigModel.setUrl(url);
				lifeConfigModel.setUnloadId(unloadId);
				lifeConfigService.updateLocal(lifeConfigModel);
			}
		} else {
			LifeConfigModel lifeConfigModel = new LifeConfigModel();
			lifeConfigModel.setParentId(parentId);
			lifeConfigModel.setLifeName(lifeName);
			lifeConfigModel.setIcon(icon);
			lifeConfigModel.setSort(sort);
			lifeConfigModel.setStatus(status);
			lifeConfigModel.setUrl(url);
			lifeConfigModel.setUnloadId(unloadId);
			lifeConfigService.insertLocal(lifeConfigModel);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 修改目标页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit-local-page", method = RequestMethod.GET)
	@RequiresUser
	public String editlocalPage(Model model, NativeWebRequest request) {
		Integer localId = NumberUtils.toInt(request.getParameter("localId"));
		//Integer lifeId = NumberUtils.toInt(request.getParameter("lifeId"));
		LifeConfigModel localModel = lifeConfigService.queryLocalById(localId);
		model.addAttribute("localId", localId);
		model.addAttribute("localModel", localModel);
		return "/life/lifeconfig/localedit.html";
	}

	/**
	 * 数据追踪首页
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/datalist")
	@RequiresUser
	public ModelAndView datalist(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new BaseModelAndView(
				"/life/lifeconfig/datalist.html", request);
		return view;
	}

	@RequestMapping(value = "/querydatalist", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult querydataList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String startTime,
			@RequestParam(required = false, defaultValue = "") String endTime,
			@RequestParam(required = false, defaultValue = "") String lifeName,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<Integer> dataList = lifeConfigService.querydataList(lifeName,
				startTime, endTime, page, rows);
		DictoryCodeUtils.renderList(dataList);
		PageInfo<Integer> pageInfo = new PageInfo<>(dataList);
		Pagination<Integer> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 获取省市列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	// @RequiresPermissions("sys:user:add")
	public AjaxResult getArea(Model model, NativeWebRequest request)
			throws BusinessException {
		List<AreaModel> areaList = lifeConfigService.queryArea();
		return new AjaxResult(ResponseCode.success.getCode(), "", areaList);
	}

	/**
	 * 获取所有省
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProvince")
	@ResponseBody
	public Map<String, Object> getProvince() {
		Map<String, Object> map = new HashMap<>();
		List<LifeCityModel> list = lifeConfigService.selectByArea();
		map.put("data", list);
		return map;
	}

	/**
	 * 根据pid获取地区
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getByPid")
	@ResponseBody
	public Map<String, Object> getByPid(
			@RequestParam(required = false, defaultValue = "") String provinceId) {
		Map<String, Object> map = new HashMap<>();
		List<LifeCityModel> list = lifeConfigService.selectByCity(provinceId);
		map.put("data", list);
		return map;
	}

	/**
	 * 获取市名称列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAreaName", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	// @RequiresPermissions("sys:user:add")
	public AjaxResult getAreaName(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String areaName)
			throws BusinessException {
		List<AreaModel> areaList = lifeConfigService.queryAreaName(areaName);
		if (CollectionUtils.isNotEmpty(areaList)) {
			for (AreaModel area : areaList) {
				if (area.getLevel().equals(1)) {
					area.setPid(1);
				} else {
					area.setPid(0);
				}
			}
		}
		return new AjaxResult(ResponseCode.success.getCode(), "", areaList);
	}
}

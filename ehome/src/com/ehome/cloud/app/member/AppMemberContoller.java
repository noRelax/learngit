package com.ehome.cloud.app.member;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.app.marry.service.IAppMarryService;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.cloud.member.model.Member;
import com.ehome.cloud.member.service.IAppMemberService;
import com.ehome.cloud.sys.model.AppUserModel;
import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.MapUtils;
import com.ehome.push.PushUtil;

/**
 * 会员管理
 * 
 * @Title:AppMemberContoller
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月21日
 * @version 1.0,2017年2月21日
 * @{tags
 */
@Controller
@RequestMapping(value = "/app/member")
public class AppMemberContoller extends BaseController {

//	private final static Logger logger = LoggerFactory
//			.getLogger(AppMemberContoller.class);
	// @Autowired
	// private SecurityManager securityManager;
	//
	@Resource
	private IAppMemberService appMemberService;
	@Resource
    private IAppUserService appUserService;
	@Resource
    private IAppMarryService appMarryService;

	/**
	 * 查询基层工会
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBaseUnion", method = RequestMethod.POST)
	public void getBaseUnion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");

		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();

		try {
			List<Map<String, Object>> list = null;
			list = appMemberService.queryBaseUnion(map);
			Map<String, Object> baseUnion = new HashMap<>();
			baseUnion.put("name", "baseUnion");
			baseUnion.put("list", list);
			dataList.add(baseUnion);
		} catch (Exception e) {
			e.printStackTrace();

		}
		response.getWriter().print(
				JsonUtil.appResposeMoreListJson(status, message, dataList));
	}

	/**
	 * 申请入会
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public void apply(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// Boolean assertTest = false;
		Member member = new Member();
		Map<String, Object> map = new HashMap<>();

		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();

		try {
			// map = decode(map);
			// XXX 校验必填信息
			/**
			 * 1. 首先判断会员表有没过申请会员 如果没 直接新增会员表和会员审批 2. 如果曾经已有会员 则更新会员表 新增审批表
			 */
			map = MapUtils.toCamelCaseMap(map);
			member = (Member) MapUtils.convertMap(Member.class, map);

			Member memberTemp = new Member(member.getAppUserId());
			memberTemp = appMemberService.selectOne(memberTemp);
			// 操作记录表
			map.put("to_basic_union_id", map.get("basic_union_id"));

			String hukou = MapUtils.getString(map, "hukou", null);
			String[] tempHukou = hukou.split("-");
			if (tempHukou.length == 2) {
				map.put("province_name", tempHukou[0]);
				map.put("city_name", tempHukou[1]);
			}
			// 查询省市id
			List<Map<String, Object>> provinceCity = appMemberService
					.queryProvinceCityByname(map);
			if (CollectionUtils.isNotEmpty(provinceCity)
					&& provinceCity.size() == 2) {
				map.put("province",
						Integer.parseInt(provinceCity.get(0).get("id")
								.toString()));
				map.put("city",
						Integer.parseInt(provinceCity.get(1).get("id")
								.toString()));
			}
			// 查询上级工会
			Map<String, Object> base_union_id = new HashMap<>();
			base_union_id.put("id", map.get("basic_union_id"));
			map.put("upper_union_id", getUpperUnionId(base_union_id));

			if (null == memberTemp) {
				Member memberByIdcard = new Member();
				memberByIdcard.setIdCard(MapUtils.getString(map, "id_card",
						null));
				List<Member> members = appMemberService.select(memberByIdcard);
				if (members != null && members.size() > 0) {
					status = ResponseCode.idcard_exist.getCode();
					message = ResponseCode.idcard_exist.getMsg();
					response.getWriter().print(
							JsonUtil.appResposeSingeMapJson(status, message,
									null));
					return;
				} else {
					appMemberService.insertApply(map);
					//同步身份证
					AppUserModel appUserModel = new AppUserModel();
	                appUserModel.setId(member.getAppUserId());
	                appUserModel.setIsMember(0);
	                appUserModel.setIdCard(MapUtils.getString(map, "id_card",null));
	                appUserModel.setSex(MapUtils.getInteger(map, "sex",null));
	                appUserService.updateNotNull(appUserModel);
				}
			} else {
				map.put("from_basic_union_id", member.getBasicUnionId());//
				map.put("id", memberTemp.getId());
				map.put("member_id", memberTemp.getId());
				appMemberService.updateApply(map);
				//同步身份证
				AppUserModel appUserModel = new AppUserModel();
				appUserModel.setId(member.getAppUserId());
				appUserModel.setIsMember(0);
                appUserModel.setIdCard(MapUtils.getString(map, "id_card",null));
                appUserModel.setSex(MapUtils.getInteger(map, "sex",null));

				appUserService.updateNotNull(appUserModel);
			}
			
			//同步婚恋的婚恋状况
			MarryMemberModel marryMemberModelTemp = new MarryMemberModel();
			marryMemberModelTemp.setAppUserId(member.getAppUserId());
			MarryMemberModel  marryMemberModel = appMarryService.selectOne(marryMemberModelTemp);
	        if(marryMemberModel!=null){
	            marryMemberModel.setMaritalStatus(member.getMaritalStatus());
	            marryMemberModel.setSex(member.getSex());
	            appMarryService.updateNotNull(marryMemberModel);
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.error.getCode();
			message = ResponseCode.error.getMsg();
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(status, message, null));
	}

	@RequestMapping(value = "/getApplyInfo", method = RequestMethod.POST)
	public void getApplyInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 跨域头
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
//		response.addHeader("Access-Control-Allow-Headers",
//				"X-Requested-With, Content-Type,device,token");
//		response.addHeader("Access-Control-Allow-Credentials", "true");
//		response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();

		try {
			// map = decode(map);
			map = appMemberService.queryApplyInfoById(MapUtils.getInteger(map,
					"app_user_id", -1));

		} catch (Exception e) {
			e.printStackTrace();

		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(status, message, map));
	}

	// 根据基层工会查询上级工会
	public Integer getUpperUnionId(Map<String, Object> map) {
		Map<String, Object> pidInfo = appMemberService.queryOrgainPId(map);
		pidInfo = appMemberService.queryOrgian(pidInfo);
		if (pidInfo != null
				&& pidInfo.get("id") != null
				&& pidInfo.get("orgain_type") != null
				&& (0 == Integer
						.parseInt(pidInfo.get("orgain_type").toString()))) {
			return Integer.parseInt(pidInfo.get("id").toString());
		} else if (null == pidInfo || null == pidInfo.get("id")
				|| "".equals(pidInfo.get("id"))) {
			return null;
		} else {
			return getUpperUnionId(pidInfo);
		}

	}
	
	/**
	 * 推送的测试方法  type值： 1 只推送短信 2只推送个推(通知栏) 3 只推送个推(透传) 4 推送短信和个推(通知) 5推送短信和个推(透传)
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception 
	  * http://localhost:8080/ehome/app/member/testPush?mobile=15011834279&userId=1234&content=neirong&title=titleqeqwe&text=qweq&type=1
	 */
	@RequestMapping(value = "/testPush", method = RequestMethod.GET)
	public void  testPush(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<>();
		String result ="";
		map = this.getPageData();
		List<String> users = new ArrayList<>();
		String[] datas;

		switch (Integer.parseInt(map.get("type").toString())) {
		case 1:
			map.put("smsTemplateId", "95200");
			map.put("mobile", MapUtils.getString(map, "mobile", ""));
			datas = new String[]{"023122","10"};
			map.put("datas", datas);
		    result = PushUtil.push(1, map);
			System.out.println("testresult"+result);
			break;
		case 2:
			users = new ArrayList<>();
			users.add(MapUtils.getString(map, "userId", "1234"));
			map.put("users", users);
			map.put("content", MapUtils.getString(map, "content", ""));//传递的内容
			map.put("title",  MapUtils.getString(map, "title", ""));
			map.put("text",MapUtils.getString(map, "text", ""));
			
			result = PushUtil.push(2, map);
			System.out.println("testresult"+result);
			break;
		case 3:
			//个推(透传)
			users = new ArrayList<>();
			users.add(MapUtils.getString(map, "userId", "1234"));
			map.put("users", users);
//			map.put("title",  MapUtils.getString(map, "title", ""));
//			map.put("text",MapUtils.getString(map, "text", ""));
			JSONObject obj = new JSONObject();
			obj.put("auditStatus", MapUtils.getString(map, "auditStatus", ""));//传递的内容
			obj.put("subject", MapUtils.getString(map, "subject", ""));//传递的内容
			obj.put("data", MapUtils.getString(map, "data", ""));//传递的内容
			obj.put("type", 5);
			map.put("content", obj.toString());//传递的内容

			result = PushUtil.push(3, map);
			System.out.println("testresult"+result);
			break;
		case 4:
			//短信和个推（通知）
			datas = new String[]{"023122","10"};
			map.put("smsTemplateId", "95200");
			map.put("mobile", MapUtils.getString(map, "mobile", ""));
			map.put("datas", datas);
			
			users = new ArrayList<>();
			users.add(MapUtils.getString(map, "userId", "1234"));
			map.put("users", users);
			map.put("content", MapUtils.getString(map, "content", ""));//传递的内容
			map.put("title",  MapUtils.getString(map, "title", ""));
			map.put("text",MapUtils.getString(map, "text", ""));
			
			result = PushUtil.push(4, map);
			System.out.println("testresult"+result);
			break;
		case 5:
			//短信和个推(透传)
			datas = new String[]{"023122","10"};
			map.put("smsTemplateId", "95200");
			map.put("mobile", MapUtils.getString(map, "mobile", ""));
			map.put("datas", datas);
			
			users = new ArrayList<>();
			users.add(MapUtils.getString(map, "userId", "1234"));
			map.put("users", users);
			map.put("content", MapUtils.getString(map, "content", ""));//传递的内容
			map.put("title",  MapUtils.getString(map, "title", ""));
			map.put("text",MapUtils.getString(map, "text", ""));
			
			result = PushUtil.push(5, map);
			System.out.println("testresult"+result);
			break;

		default:
			break;
		}
		PrintWriter out = response.getWriter();
		out.print(result.replaceAll("result=ok","推送成功").replaceAll("result=NOTarget", "未绑定别名="+MapUtils.getString(map, "userId", "1234")));
		
		
	}
	
	
	@RequestMapping(value = "/testMarryPush", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> testMarryPush(@RequestParam(required = false, defaultValue = "") Integer userId,
            @RequestParam(required = false, defaultValue = "") Integer type,
            @RequestParam(required = false, defaultValue = "") String content){
	   
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<String> users = new ArrayList<>();
        users.add(userId.toString());
        resultMap.put("users", users);
        JSONObject obj = new JSONObject();
        obj.put("subject", "");
        obj.put("data", content);
        obj.put("type", type);
        resultMap.put("content", obj.toString());//传递的内容 根据业务不同 传递的内容也不同
        PushUtil.push(3, resultMap);
	    return resultMap;
	}
}

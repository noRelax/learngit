package com.ehome.cloud.app.marry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.app.marry.dto.AppMarryCommentDto;
import com.ehome.cloud.app.marry.dto.AppMarryPhotoDto;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.cloud.marry.service.IAppMarryLoveService;
import com.ehome.cloud.marry.service.IAppMarryMemmberService;
import com.ehome.cloud.marry.service.IAppMarryPhotoService;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.member.service.IMemberService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.model.AjaxResult;

/**
 * @Title: AppMarryMemmberController
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月18日 下午5:23:59
 * @version
 */

@Controller
@RequestMapping(value = "/app/marry/member")
public class AppMarryMemberController extends BaseController {

    @Resource
    private IAppMarryMemmberService appMarryMemmberService;

    @Resource
    private IGoldCoinService goldCoinService;

    @Resource
    private IAppMarryLoveService appMarryLoveService;

    @Resource
    private IMemberService memberService;

    @Resource
    private IAppMarryPhotoService appMarryPhotoService;

    /**
     * 我的 信息补全
     * 
     * @param appUserId
     * @return
     */
    @RequestMapping(value = "/myInfoPage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getMyInfoPage(@RequestParam(required = false, defaultValue = "") Integer appUserId) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Integer> myLove = appMarryLoveService.queryMyLove(appUserId);
            List<Integer> loveMe = appMarryLoveService.queryLoveMe(appUserId);
            map.put("goldCoinsNum", Integer.valueOf(goldCoinService.getGoldCoinTotalNum(appUserId)));
            map.put("myLoveNum", myLove == null ? 0 : myLove.size());
            map.put("loveMeNum", loveMe == null ? 0 : loveMe.size());
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), map);
        }

        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), map);
    }

    /**
     * 婚恋首页照片查找
     * 
     * @param appUserId
     * @param marryDemand
     * @param page
     * @param sourceDevice
     * @return
     */
    @RequestMapping(value = "/selectRequireToIndex", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult selectRequireToIndex(@RequestParam(required = false, defaultValue = "") Integer appUserId,
            @RequestParam(required = false, defaultValue = "10") Integer marryDemand,
            @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "") Integer sourceDevice) {
        Map<String, Object> map = null;
        try {
            MarryMemberModel entity = new MarryMemberModel();
            entity.setAppUserId(appUserId);
            MarryMemberModel memberModel = appMarryMemmberService.selectOne(entity);
            if (memberModel != null) {
                if (marryDemand > 0) {
                    if (memberModel.getMarryDemand() == null || memberModel.getMarryDemand() == 0
                            || memberModel.getMarryDemand().intValue() != marryDemand.intValue()) {
                        memberModel.setMarryDemand(marryDemand);
                        appMarryMemmberService.updateByKey(memberModel);
                    }
                }
                map = appMarryMemmberService.queryIndexPhoto(appUserId, memberModel.getMarryDemand(), page, sourceDevice);
            }else{
                return new AjaxResult(ResponseCode.error.getCode(), "该用户没有进入过婚恋模块  ", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), "");
        }
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), map);
    }

    @RequestMapping(value = "/selectIndexPhotoById", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult selectIndexPhotoById(@RequestParam(required = false, defaultValue = "") Integer appUserId,
            @RequestParam(required = false, defaultValue = "") Integer photoId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        AppMarryPhotoDto appMarryPhotoDto = null;
        List<AppMarryCommentDto> list = null;
        try {
            appMarryPhotoDto = appMarryPhotoService.getPhotoDetail(appUserId, photoId);
            list = appMarryPhotoService.getComment(photoId, 1, 2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(list)) {
            appMarryPhotoDto.setAppMarryCommentDtoList(list);
        }
        resultMap.put("appMarryPhotoDto", appMarryPhotoDto);
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), resultMap);
    }

}

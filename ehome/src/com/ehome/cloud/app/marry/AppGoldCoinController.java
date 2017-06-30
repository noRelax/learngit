package com.ehome.cloud.app.marry;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.marry.dto.GoldCoinDto;
import com.ehome.cloud.marry.model.GoldCoinRulesModel;
import com.ehome.cloud.marry.service.IGoldCoinRulesService;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.redis.JedisUtils;

/**
 * 金币相关操作接口
 * 
 * @Title: AppGoldCoinController
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年4月14日 上午10:17:55
 * @version
 */
@Controller
@RequestMapping(value = "/app/marry/goldCoin")
public class AppGoldCoinController extends BaseController {

    @Resource
    public IGoldCoinService goldCoinService;

    @Resource
    public IGoldCoinRulesService goldCoinRulesService;

    /**
     * 分页获取用户金币明细
     * 
     * @param appUserId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getGoldCoinDetail", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getGoldCoinDetail(@RequestParam(required = false, defaultValue = "") Integer appUserId,
            @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "20") Integer rows) {

        if (logger.isDebugEnabled()) {
            logger.debug("获取金币明细");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<GoldCoinDto> goldCoinDtoList = null;
        try {
            goldCoinDtoList = goldCoinService.selectPageByAppUserId(appUserId, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), "");
        }
        DictoryCodeUtils.renderList(goldCoinDtoList);
        resultMap.put("deatailList", goldCoinDtoList);
        resultMap.put("totalNum", goldCoinService.getGoldCoinTotalNum(appUserId));
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), resultMap);
    }

    /**
     * 获取金币规则
     *
     * @return
     */
    @RequestMapping(value = "/getGoldCoinRules", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getGoldCoinRules() {
        if (logger.isDebugEnabled()) {
            logger.debug("获取金币规则");
        }
        List<GoldCoinRulesModel> GoldCoinRulesModelList = null;
        try {
            GoldCoinRulesModelList = goldCoinRulesService.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.fail.getCode(), ResponseCode.fail.getMsg(), Collections.EMPTY_LIST);
        }
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), GoldCoinRulesModelList);
    }

    @RequestMapping(value = "/isFirstChat", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult isFirstChat(@RequestParam(required = false, defaultValue = "") String fromUserId,
            @RequestParam(required = false, defaultValue = "") String toUserId) {
        if (logger.isDebugEnabled()) {
            logger.debug("判断是否第一次聊天");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap = goldCoinService.chatAtherExpendGoldCoins(fromUserId, toUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), resultMap);
        }
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), resultMap);
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult chat(@RequestParam(required = false, defaultValue = "") String fromUserId,
            @RequestParam(required = false, defaultValue = "") String toUserId) {
        if (logger.isDebugEnabled()) {
            logger.debug("主动私聊");
        }
        Integer number = 0;
        try {
            //婚恋统计相互喜欢且成功私聊用户 第一次私聊时记录
            if (JedisUtils.exist("mutual")) {
                Set<String> mutualSet = JedisUtils.zRange("mutual", 0, -1);
                if (CollectionUtils.isNotEmpty(mutualSet)) {
                    if (mutualSet.contains(fromUserId + "-" + toUserId) || mutualSet.contains(toUserId + "-" + fromUserId)) {
                        JedisUtils.zAdd(
                                "marry:mutual:privateChat",
                                new Date().getTime(),
                                (NumberUtils.toInt(fromUserId) + NumberUtils.toInt(toUserId)) + "");
                    }
                }
            }
            number = goldCoinService.saveGoldCoinModel(9, 2, Integer.valueOf(fromUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(ResponseCode.error.getCode(), ResponseCode.error.getMsg(), number);
        }
        return new AjaxResult(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), number);
    }

}

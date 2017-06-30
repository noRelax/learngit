/**
 * @Project:ZGHome
 * @FileName:AppUpvote.java
 */
package com.ehome.cloud.app.guangchang;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.guangchang.model.ReportModel;
import com.ehome.cloud.guangchang.service.IReportService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.TokenUtil;

/**
 * 广场匿名聊举报客户端处理
 * 
 * @Title:AppReport
 * @author:张宗奎
 * @date:2017年6月8日
 * @version:
 */
@Controller
@RequestMapping(value = "/app/guangchang/report")
public class AppReport extends BaseController {

    @Resource
    private IReportService iReportService;

    /**
     * 匿名聊举报
     * 
     * @param type 举报类型：话题1，匿名聊2
     * @param discussionId
     *            匿名聊id
     * @param time
     * @param apptype
     * @param token
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object report(@RequestParam int type, @RequestParam int discussionId, @RequestParam Long time, @RequestParam String apptype,
            @RequestParam String token) {
        Map<String, String> resultMap = new HashMap<String, String>();
        if (!TokenUtil.validateToken("", time, apptype, token)) {
            resultMap.put("status", ResponseCode.invalidtoken.getCode() + "");
            resultMap.put("message", ResponseCode.invalidtoken.getMsg());
            return resultMap;
        }

        ReportModel reportModel = new ReportModel();
        reportModel.setType(type);
        reportModel.setDiscussionId(discussionId);
        reportModel.setReportTime(new Date());
        try {
            if (iReportService.save(reportModel) > 0) {
                resultMap.put("status", ResponseCode.success.getCode() + "");
                resultMap.put("message", ResponseCode.success.getMsg());
            } else {
                resultMap.put("status", ResponseCode.error.getCode() + "");
                resultMap.put("message", ResponseCode.error.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", ResponseCode.error.getCode() + "");
            resultMap.put("message", ResponseCode.error.getMsg());
        }
        return resultMap;
    }
}

package com.ehome.cloud.ankang.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehome.cloud.ankang.service.IAnKangCupService;
import com.ehome.core.frame.BaseController;

/**
 * @Title:AnKangCupController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月16日 下午5:04:16
 * @version:
 */
@Controller
@RequestMapping(value = "/ankang")
public class AnKangCupController extends BaseController {
	
	@Resource
	private IAnKangCupService anKangCupService;

}

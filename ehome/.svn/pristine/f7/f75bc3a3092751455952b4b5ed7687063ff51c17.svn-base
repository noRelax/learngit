package com.ehome.cloud.app.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.sys.dto.DictionaryDto;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.EntityUtils;

/**
 * 数据字典App调用
 * 
 * @Title:AppDictionaryController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月11日 上午10:25:13
 * @version:
 */
@Controller
@RequestMapping(value = "/app/dictionary")
public class AppDictionaryController extends BaseController {

	@Resource
	private IDictionaryService dictionaryService;

	@RequestMapping(value = "/queryCode", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AjaxResult queryByCode(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String code)
			throws BusinessException {
		if (StringUtils.isNotBlank(code)) {
			if (code.indexOf(",") != -1) {
				String[] codes = code.split(",");
				List<DictionaryDto> dictList = dictionaryService
						.getByCodeList(Arrays.asList(codes));
				Map<String, List<DictionaryDto>> dictMap = new HashMap<>();
				if (CollectionUtils.isNotEmpty(dictList)) {
					dictMap = dictList.stream().collect(
							Collectors.groupingBy(DictionaryDto::getTypeCode));
				}
				return new AjaxResult(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), JSON.toJSON(dictMap));
			} else {
				List<Dictionary> dictList = dictionaryService.queryByCode(code);
				List<DictionaryDto> dtoList = EntityUtils.entityConvertDto(
						dictList, DictionaryDto.class);
				Map<String, List<DictionaryDto>> dictMap = new HashMap<>();
				dictMap.put(code, dtoList);
				return new AjaxResult(ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), dictMap);
			}
		} else {
			List<DictionaryDto> dictList = dictionaryService
					.getByCodeList(new ArrayList<String>());
			Map<String, List<DictionaryDto>> dictMap = new HashMap<>();
			if (CollectionUtils.isNotEmpty(dictList)) {
				dictMap = dictList.stream().collect(
						Collectors.groupingBy(DictionaryDto::getTypeCode));
			}
			return new AjaxResult(ResponseCode.success.getCode(),
					ResponseCode.success.getMsg(), JSON.toJSON(dictMap));
		}
	}

}

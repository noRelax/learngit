package com.ehome.cloud.app.help;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.ehome.cloud.app.help.dto.ResponseHelpExplainDto;
import com.ehome.cloud.help.model.HelpRightExplainModel;
import com.ehome.cloud.help.service.IHelpRightExplainService;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.NumberUtils;

/**
 * 帮扶维权说明接口
 * 
 * @Title: AppHelpRightExplainController
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月29日 下午7:40:20
 * @version
 */
@Controller
@RequestMapping(value = "/app/help/helpRightExplain")
public class AppHelpRightExplainController extends BaseController {

	private final static Logger logger = LoggerFactory
			.getLogger(AppHelpRightExplainController.class);

	@Resource
	private IHelpRightExplainService helpRightExplainService;

	@Resource
	private IDictionaryService dictionaryService;

	@RequestMapping(value = "/getExplain", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult myApplyList(
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer explainType,
			@RequestParam(required = false, defaultValue = "") Integer provinceId,
			@RequestParam(required = false, defaultValue = "") Integer cityId,
			@RequestParam(required = false, defaultValue = "") Integer right_or_help_type)
			throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("获取帮扶维权说明");
		}
		
		String rightOrHelpType = null;
		
		ResponseHelpExplainDto dto = new ResponseHelpExplainDto();

		if (!NumberUtils.isNull(explainType) && NumberUtils.neZero(explainType)) {

			Condition condition = new Condition(HelpRightExplainModel.class);
			Criteria criteria = condition.createCriteria();
			criteria.andEqualTo("explainType", explainType);

			if (!NumberUtils.isNull(provinceId)
					&& NumberUtils.neZero(provinceId)) {
				criteria.andEqualTo("provinceId", provinceId);
			}

			if (!NumberUtils.isNull(cityId) && NumberUtils.neZero(cityId)) {
				criteria.andEqualTo("cityId", cityId);
			}
			
			List<Dictionary> ExplainTypeList = dictionaryService.queryByCode("CODE_EXPLAIN_TYPE");
			
			// 匹配说明类型
			for (Dictionary dictionary : ExplainTypeList) {
				if(Integer.parseInt(dictionary.getDictKey()) == explainType){
					List<Dictionary> rightOrHelpList = dictionaryService.queryByCode(dictionary.getDictValue());
					//匹配子频道类型
					for (Dictionary dictionary2 : rightOrHelpList) {
						if(right_or_help_type.intValue() == Integer.parseInt(dictionary2.getDictKey())){
							rightOrHelpType = dictionary2.getDictValue();//获取子频道名称
						}
					}
					
				}
			}

			if (StringUtils.isNotBlank(rightOrHelpType)) {
				criteria.andEqualTo("rightOrHelpType", rightOrHelpType);
			}
			List<HelpRightExplainModel> list = helpRightExplainService
					.selectByCondition(condition);
			if (CollectionUtils.isNotEmpty(list)) {
				dto.setContent(list.get(0).getContent());
				dto.setServiceButton(list.get(0).getServiceButtunType());
			}else{
				return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
						ResponseCode.success.getMsg(), null);
			}
		}
		return new AjaxResult(String.valueOf(ResponseCode.success.getCode()),
				ResponseCode.success.getMsg(), dto);
	}
}

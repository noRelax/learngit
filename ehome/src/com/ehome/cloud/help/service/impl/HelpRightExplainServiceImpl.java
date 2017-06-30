package com.ehome.cloud.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.help.dto.HelpRightExplainDto;
import com.ehome.cloud.help.mapper.HelpRightExplainMapper;
import com.ehome.cloud.help.model.HelpRightExplainModel;
import com.ehome.cloud.help.service.IHelpRightExplainService;
import com.ehome.core.frame.BaseService;
import com.github.pagehelper.PageHelper;

/**
 * @Title: HelpRightExplainServiceImpl.java
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月28日 上午11:54:08
 * @version
 */
@Service("helpRightExplainService")
public class HelpRightExplainServiceImpl extends BaseService<HelpRightExplainModel>
		implements IHelpRightExplainService {
	
	@Resource
	private HelpRightExplainMapper helpRightExplainMapper;

	@Override
	public List<HelpRightExplainDto> queryForList(HelpRightExplainDto explainDto,
			Integer page, Integer rows) {
		PageHelper.startPage(page, rows, true);
		
		return helpRightExplainMapper.queryForList(explainDto);
	}
}

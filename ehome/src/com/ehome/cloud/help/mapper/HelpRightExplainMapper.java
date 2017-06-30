package com.ehome.cloud.help.mapper;

import java.util.List;

import com.ehome.cloud.help.dto.HelpRightExplainDto;
import com.ehome.cloud.help.model.HelpRightExplainModel;
import com.ehome.core.frame.MyMapper;

/**
 * @Title: HelpRightExplainMapper
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年3月28日 上午11:51:08 
 * @version 
 */
public interface HelpRightExplainMapper extends MyMapper<HelpRightExplainModel>{

	public List<HelpRightExplainDto> queryForList(HelpRightExplainDto explainDto);
	
}

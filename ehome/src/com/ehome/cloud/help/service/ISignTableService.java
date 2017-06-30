package com.ehome.cloud.help.service;

import java.util.List;

import com.ehome.cloud.help.dto.SignTableDto;
import com.ehome.cloud.help.model.SignTableModel;
import com.ehome.core.frame.IService;

/**
 * @Title: ISignTableService 
 * @Description: TODO 
 * @author hl@diandianwifi.com   
 * @date 2017年3月22日 上午11:52:37 
 * @version 
 */
public interface ISignTableService extends IService<SignTableModel>{
	
	List<SignTableDto> queryPage(int page, int rows);
}

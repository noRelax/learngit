/**
 * @Project:ZGHome
 * @FileName:ClassifyServiceImpl.java
 */
package com.ehome.cloud.puhui.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.puhui.mapper.ClassifyMapper;
import com.ehome.cloud.puhui.model.ClassifyModel;
import com.ehome.cloud.puhui.service.IClassifyService;
import com.ehome.core.frame.BaseService;
import com.github.pagehelper.PageHelper;

/**
 * @Title:ClassifyServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月28日
 * @version:
 */
@Service
public class ClassifyServiceImpl extends BaseService<ClassifyModel> implements IClassifyService {

	@Resource
	private ClassifyMapper classifyMapper;
	
	@Override
	public List<Map<String, Object>> selectPageList(Map<String, Object> map, int page, int rows) {
		PageHelper.startPage(page, rows);
		return classifyMapper.selectPageList(map);
	}

}

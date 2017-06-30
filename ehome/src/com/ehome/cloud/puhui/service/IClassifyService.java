/**
 * @Project:ZGHome
 * @FileName:IClassifyService.java
 */
package com.ehome.cloud.puhui.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.puhui.model.ClassifyModel;
import com.ehome.core.frame.IService;

/**
 * @Title:IClassifyService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月28日
 * @version:
 */
public interface IClassifyService extends IService<ClassifyModel> {
	List<Map<String,Object>> selectPageList(Map<String,Object> map, int page, int rows);
}

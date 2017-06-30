/**
 * @Project:ZGHome
 * @FileName:CommonService.java
 */
package com.ehome.cloud.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ehome.cloud.common.dao.DaoSupport;
import com.ehome.core.util.StringUtils;

/**
 * @Title:CommonService
 * @Description:TODO
 * @author:ddl
 * @date:2016年12月19日
 * @version:
 */
@Service("commonService")
public class CommonService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	private  final Logger logger = LoggerFactory.getLogger(this.getClass().getName());  

	
	/**
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public List columnsList(String table) throws Exception {
		Map m = new HashMap();
		m.put("table", table);
		String Str = "CommonMapper.columnsList";
		
		return (List) dao.findForList(Str, m);
	}

	
	public List list(Map m) throws Exception {
		String mapper = StringUtils.getString(m, "mapper", "");
		String Str = "CommonMapper.list";
		if (!mapper.equals("")) {
			Str = mapper + "Mapper.list";
		}		
		int page = StringUtils.getInt(m, "page",1);
		int pageSize = StringUtils.getInt(m, "pagesize",10);
		String fields = StringUtils.getString(m, "fields"," * ");
		m.put("start", (page-1)*pageSize);
		m.put("pagesize", pageSize);	
		m.put("fields",fields);
		return (List) dao.findForList(Str, m);
	}

	/**
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public int insert(Map params) throws Exception {
		String mapper = StringUtils.getString(params, "mapper","");
		String Str = "CommonMapper.insert";
		if (!mapper.equals("")) {
			Str = mapper + "Mapper.insert";
		}
		int i = (int) dao.save(Str, params);
		return i;
	}

	/**
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public int insertReturnId(Map params) throws Exception {
		String mapper = StringUtils.getString(params, "mapper","");
		String Str = "CommonMapper.insertReturnId";
		if (!mapper.equals("")) {
			Str = mapper + "Mapper.insertReturnId";
		}
		int i = (int) dao.save(Str, params);
		return i;
	}

	
	/**
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public int update(Map params) throws Exception {
		String mapper = StringUtils.getString(params, "mapper","");
		String Str = "CommonMapper.update";
		if (!mapper.equals("")) {
			Str = mapper + "Mapper.update";
		}
		int i = (int) dao.save(Str, params);
		return i;
	}

	/**
	 * 批量删除或者单个删除
	 * 
	 * @param params 
	 * ids 如：  1,3,5 或者 1
	 * @return
	 * @throws Exception
	 */
	public int deleteByBatchId(Map params) throws Exception{
		
		String mapper = StringUtils.getString(params, "mapper","");
		String Str = "CommonMapper.deleteByBatchId";
		if (!mapper.equals("")) {
			Str = mapper + "Mapper.deleteByBatchId";
		}
		int i = (int) dao.save(Str, params);
		return i;
	}
	
	
	/**
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public Map insertTest(Map params,String mapper) throws Exception {
		List l = null;
		Map a = new HashMap();
		a.put("test", 1);
		int i = (int) dao.save("CommonMapper.insertObj", a);
		a.put("test", 2);
		i = (int) dao.save("CommonMapper.insertObj", a);
		a.put("test", "11111111111111111111111");
		i = (int) dao.save("CommonMapper.insertObj", a);
		logger.info("测试事务");
		return null;
	}
}

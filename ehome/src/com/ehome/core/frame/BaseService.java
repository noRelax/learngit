package com.ehome.core.frame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Condition;

/**
 * 常用的方法都有了
 * 
 * @author hsu
 * 
 * @param <T>
 */
public class BaseService<T> implements IService<T> {

	@Autowired
	protected MyMapper<T> mapper;

	public MyMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<T> select(T entity) {
		return mapper.select(entity);
	}

	@Override
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public int save(T entity) {
		return mapper.insert(entity);
	}

	@Override
	public int saveNotNull(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	public int insertList4Mysql(List<T> entityList) {
		return mapper.insertList(entityList);
	}

	@Override
	public int deleteByKey(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int updateByKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	public int updateNotNull(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> selectByCondition(Condition condition) {
		return mapper.selectByExample(condition);
	}

	@Override
	public int deleteByCondition(Condition condition) {
		return mapper.deleteByCondition(condition);
	}

	// @Override
	// public List<T> selectByIds(String ids) {
	// return mapper.selectByIds(ids);
	// }
	//
	// @Override
	// public int deleteByIds(String ids) {
	// return mapper.deleteByIds(ids);
	// }

	@Override
	public List<T> selectPage(int pageNum, int pageSize) {
		return selectPage(pageNum, pageSize, true);
	}

	@Override
	public List<T> selectPage(int pageNum, int pageSize, boolean count) {
		PageHelper.startPage(pageNum, pageSize, count);
		return mapper.select(null);
	}

	@Override
	public List<T> selectPageByCondition(Condition condition, int pageNum,
			int pageSize) {
		return selectPageByCondition(condition, pageNum, pageSize, true);
	}

	@Override
	public List<T> selectPageByCondition(Condition condition, int pageNum,
			int pageSize, boolean count) {
		PageHelper.startPage(pageNum, pageSize, count);
		return selectByCondition(condition);
	}

}

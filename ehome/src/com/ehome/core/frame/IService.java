package com.ehome.core.frame;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Condition;

/**
 * 常用接口
 * 
 * @author hsu
 *
 * @param <T>
 */
@Service
public interface IService<T> {

	/**
	 * 根据主键字段进行查询
	 * 
	 * @param key
	 * @return
	 */
	T selectByKey(Object key);

	/**
	 * 根据实体中的属性值进行查询
	 * 
	 * @param entity
	 * @return
	 */
	List<T> select(T entity);

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常
	 * 
	 * @param entity
	 * @return
	 */
	T selectOne(T entity);

	/**
	 * 查询全部结果
	 * 
	 * @return
	 */
	List<T> selectAll();

	/**
	 * 保存一个实体,null的属性也会保存，不会使用数据库默认值
	 * 
	 * @param entity
	 * @return
	 */
	int save(T entity);

	/**
	 * 保存一个实体，忽略null,使用数据库默认值
	 * 
	 * @param entity
	 * @return
	 */
	int saveNotNull(T entity);

	/**
	 * 批量插入，限制实体包含id属性并且必须为自增列
	 * 
	 * @param entityList
	 * @return
	 */
	int insertList4Mysql(List<T> entityList);

	/**
	 * 根据主键字段进行删除
	 * 
	 * @param key
	 * @return
	 */
	int deleteByKey(Object key);

	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 * 
	 * @param entity
	 * @return
	 */
	int updateByKey(T entity);

	/**
	 * 根据主键更新属性不为null的值
	 * 
	 * @param entity
	 * @return
	 */
	int updateNotNull(T entity);

	/**
	 * 根据Condition条件进行查询
	 * 
	 * @param condition
	 * @return
	 */
	List<T> selectByCondition(Condition condition);

	/**
	 * 根据Condition条件进行删除
	 * 
	 * @param condition
	 * @return
	 */
	int deleteByCondition(Condition condition);

	/**
	 * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
	 * 
	 * @param ids
	 * @return
	 */
	// List<T> selectByIds(String ids);

	/**
	 * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
	 * 
	 * @param ids
	 * @return
	 */
	// int deleteByIds(String ids);

	/**
	 * 根据pageNum和pageSize分页查询,带分页信息，可以通过 new PageInfo<T>(List<T>) 当list使用,
	 *   需要先执行 SELECT count(0)
	 *   当条数超出的时候返回最后一页的信息，生成sql：
	 *     select xx from table   LIMIT  (pageNum - 1 )* pageSize，pageSize 
	 *   pageNum 和 pageSize 由  SELECT count(0)  设置
	 *   
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	List<T> selectPage(int pageNum, int pageSize);

	/**
	 * 根据pageNum和pageSize分页查询
	 *   当count=true时候 等同于 selectPage(int pageNum, int pageSize)
	 *   当false时候直接生成sql：
	 *      select xx from table   LIMIT  (pageNum - 1 )* pageSize，pageSize
	 * 
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页条数
	 * @param count 
	 *     是否进行分页查询
	 * @return
	 */
	List<T> selectPage(int pageNum, int pageSize, boolean count);

	/**
	 * 根据pageNum和pageSize分页查询,带分页信息，可以通过 new PageInfo<T>(List<T>) 当list使用
	 * 
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<T> selectPageByCondition(Condition condition, int pageNum, int pageSize);

	/**
	 * 根据condition、pageNum和pageSize分页查询
	 * 
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @param 是否进行分页查询
	 * @return
	 */
	List<T> selectPageByCondition(Condition condition, int pageNum,
			int pageSize, boolean count);
}

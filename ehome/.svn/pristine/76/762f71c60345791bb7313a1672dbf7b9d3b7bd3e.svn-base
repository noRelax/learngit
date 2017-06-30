package com.ehome.core.frame;

import java.util.List;
import java.util.Map;

/**
 * 直接调用sql执行
 * @author hsu
 *
 */
public interface SqlMapper {

    
    /**
     * sql with insert
     * @param sql
     * @param vallue
     * @return
     */
    public long save(String sql, Object vallue); 
    
    /**
     * sql with update
     * @param sql
     * @param vallue
     * @return
     */
    public long update(String sql, Object vallue); 
    
    /**
     * sql with delete 
     * @param sql
     * @param vallue
     * @return
     */
    public long delete(String sql, Object vallue); 
    
    /** 
     * 执行增删改sql语句 
     *  
     * @param sql sql语句
     * @param value 参数值 
     * @return 
     */  
//    public long executeSql(String sql, Object vallue);  
      
    /** 
     * 根据sql语句查询单条数据 
     *  
     * @param sql sql语句
     * @param value 参数值 
     * @return 
     */  
    public Map<String, Object> findOne(String sql, Object vallue);  
      
    /** 
     * 根据sql语句查询多条数据 
     *  
     * @param sql sql语句 
     * @param value 参数值
     * @return 
     */  
    public List<Map<String, Object>> findMany(String sql, Object vallue);  
      
    /** 
     * 根据sql语句查询条数 
     *  
     * @param sql sql语句 
     * @param value 参数值 
     * @return 
     */  
    public long findCount(String sql, Object vallue);  
      
      
    
}

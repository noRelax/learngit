package com.ehome.core.interceptor;

public interface ISqlLog {
    
    /**
     * 
     * @param cost 花费时间，单位sql
     * @param sql_id 对应的mybatis id
     * @param sql 执行的sql内容
     */
    void save(long cost, String sql_id, String sql);
}

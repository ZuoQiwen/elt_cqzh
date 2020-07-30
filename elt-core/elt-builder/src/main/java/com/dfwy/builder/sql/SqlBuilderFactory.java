package com.dfwy.builder.sql;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SqlBuilderFactory {
    @Autowired
    Map<String, SqlBuilder> sqlBuilders = new ConcurrentHashMap<>();
    @Resource
    private DruidDataSource druidDataSource;

    public SqlBuilder getSqlBuilder(){
        Assert.notNull(druidDataSource.getDbType(),"elt database can not be empty");
        SqlBuilder  sqlBuilder = sqlBuilders.get(druidDataSource.getDbType().toUpperCase());
        if(sqlBuilder==null){
            throw new RuntimeException("elt database error,expect mysql oracle");
        }
        return sqlBuilder;
    }


}

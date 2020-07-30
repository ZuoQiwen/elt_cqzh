package com.dfwy.builder.config;


import com.dfwy.builder.filter.FilterHelper;
import com.dfwy.builder.sql.SqlBuilderFactory;
import com.dfwy.builder.transform.original.OriginalTransformHelper;
import com.dfwy.database.configer.BuilderConfiger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EltBuilderHelper {
    /**
     * elt数据清洗规则，启动时加载  可以通过、/elt/reload 进行数据刷新
     */
    @Autowired
    private ELTConfiger eltConfiger;
    /**
     * sql构造工具类
     */
    @Autowired
    private SqlBuilderFactory sqlBuilderFactory;
    /**
     *  原始数据标准表数据入库配置，该数据在yml文件中读取
     */
    @Autowired
    private BuilderConfiger dataSourceConfiger;

    /**
     * 表级别数据过滤工具
     */
    @Autowired
    private FilterHelper filterHelper;
    @Autowired
    private OriginalTransformHelper originalTransformHelper;


    public ELTConfiger getEltConfiger() {
        return eltConfiger;
    }

    public SqlBuilderFactory getSqlBuilderFactory() {
        return sqlBuilderFactory;
    }

    public BuilderConfiger getDataSourceConfiger() {
        return dataSourceConfiger;
    }

    public FilterHelper getFilterHelper() {
        return filterHelper;
    }

    public OriginalTransformHelper getOriginalTransformHelper() {
        return originalTransformHelper;
    }
}

//package com.dfwy.web.framework.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DfwyDataSourceConfig {
//
//    @Bean(name = "druidDataSource")
//    @Primary
//    @ConfigurationProperties(prefix="spring.datasource.druid")
//    public DruidDataSource druidDataSource() {
//        DruidDataSource druidDataSource = new DfwyDataSource();
//        return druidDataSource;
//    }
//}

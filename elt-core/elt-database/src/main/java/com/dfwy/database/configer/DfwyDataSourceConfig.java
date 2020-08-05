//package com.dfwy.database.configer;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowire;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
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

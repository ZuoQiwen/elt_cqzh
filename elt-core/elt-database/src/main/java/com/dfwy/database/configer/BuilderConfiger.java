package com.dfwy.database.configer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 *@className DataSourceConfiger
 *@description : elt数据源配置
 *@author zuoqiwen 
 *@date 2020/6/5 17:32
 *@version V1.0
 **/

@Configuration
@ConfigurationProperties("elt")
@Slf4j
@Data
public class BuilderConfiger {
    /**
     * 原始表数据源配置
     */
    private ELTDatabaseConfig original =  new ELTDatabaseConfig();
    /**
     * 标准表数据源配置
     */
    private ELTDatabaseConfig standard =  new ELTDatabaseConfig();


    @Data
    public static class ELTDatabaseConfig{
        /**
         * 是否入库
         */
        public boolean saveTodatabase;
        /**
         * 单次入库行数
         */
        public int singleSavecount;
        /**
         * 是否异步  默认是false：同步，true：异步
         */
        public boolean async;
    }
}

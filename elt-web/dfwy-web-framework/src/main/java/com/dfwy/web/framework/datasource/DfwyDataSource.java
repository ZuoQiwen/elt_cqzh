//package com.dfwy.web.framework.datasource;
//
//import com.alibaba.druid.filter.config.ConfigTools;
//import com.alibaba.druid.pool.DruidDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//@Slf4j
//public class DfwyDataSource extends DruidDataSource {
//    private String key="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAILV+9ZinXED57mbNVc9fVgPbghp0Gl1uSqY6aqYzjoMyIn0k9uqqig1eAvAeeLkcO9D8SkWiE2z5+osy+gQQAMCAwEAAQ==";
//    @Override
//    public void setPassword(String password){
//
//        if(StringUtils.isNotBlank(password)){
//            try{
//                this.password =  ConfigTools.decrypt(key,password);
//            }catch (Exception e){
//                log.error("数据库密码解密出错，{"+password+"}");
//                throw new RuntimeException("数据库密码解密失败！", e);
//            }
//        }
//    }
//}

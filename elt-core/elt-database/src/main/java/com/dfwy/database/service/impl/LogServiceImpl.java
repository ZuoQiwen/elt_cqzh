package com.dfwy.database.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.dfwy.common.configer.Snowflake;
import com.dfwy.common.domain.OperatinLog;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.database.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: 异步日志记录
 * date: 2020/1/20 14:03
 *
 * @author zuoqiwen
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {
    @Resource
    private ELTMapper originalMapper;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private DruidDataSource druidDataSource;
    @Autowired
    private Snowflake snowflake;

    @SneakyThrows
    @Override
    public void insertLog(OperatinLog operatinLog, Object request, Object response) {
        operatinLog.setRequest(formatRequest(request))
                .setResponse(objectMapper.writeValueAsString(response))
                .setId(snowflake.nextId());
        String db = druidDataSource.getDbType();
        if(ELTConstant.MYSQL.equalsIgnoreCase(db)){
            originalMapper.saveLogMysql(operatinLog);
        }else if(ELTConstant.ORACLE.equalsIgnoreCase(db)){
            originalMapper.saveLogOracle(operatinLog);
        }else{
            log.error("expect dbtype mysql/oracle but"+db);
        }
    }
    private String formatRequest(Object request) throws JsonProcessingException {
        if(request!=null){
            if(request instanceof String){
                return ((String) request).replace("\"","");
            }else{
                return objectMapper.writeValueAsString(request);
            }
        }
        return null;
    }
}
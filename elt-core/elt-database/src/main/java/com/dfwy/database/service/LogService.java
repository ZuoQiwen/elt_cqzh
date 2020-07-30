package com.dfwy.database.service;

import com.dfwy.common.domain.OperatinLog;
import org.springframework.scheduling.annotation.Async;

/**
 * Description:
 * date: 2020/1/20 14:03
 *
 * @author zuoqiwen
 */
public interface LogService {
    /**
     * 异步插入日志记录
     * @param operatinLog
     * @param
     */
    @Async
    void insertLog(OperatinLog operatinLog, Object request, Object respnse);
}

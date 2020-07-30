package com.dfwy.database.service;

import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface BuildDataService {
    /**
     * 原始表数据入库
     * @param taxData
     * @return
     */
    void saveOriginalData(List<String> originalSql);
    /**
     * 原始表异步数据入库
     * @param taxData
     * @return
     */
    @Async
    void saveOriginalAsyncData(List<String> originalSql);
    /**
     * 标准表数据入库
     * @param taxData
     * @return
     */
    void saveStandardData(List<String> standardSql);
    /**
     * 标准表异步数据入库
     * @param taxData
     * @return
     */
    @Async
    void saveStandardAsyncData(List<String> standardSql);
}

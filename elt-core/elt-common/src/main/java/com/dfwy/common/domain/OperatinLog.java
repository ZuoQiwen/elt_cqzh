package com.dfwy.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Description:
 * date: 2020/1/19 18:23
 *
 * @author zuoqiwen
 */
@Data
@Accessors(chain = true)
public class OperatinLog {
    private String api;
    private String request;
    private String response;
    /**
     * 0 请求成功  1请求失败
     */
    private int status;
    private Long id;
    private long costTime;
    private String businessId;



}

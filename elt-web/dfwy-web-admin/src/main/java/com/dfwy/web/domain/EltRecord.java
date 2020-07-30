package com.dfwy.web.domain;


import com.dfwy.web.common.annotation.Excel;
import com.dfwy.web.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class EltRecord extends BaseEntity {
    private String id;
    @Excel(name = "接口名称")
    private String api;
    private String request;
    private String response;
    @Excel(name = "请求状态", readConverterExp = "0=正常,1=失败")
    private Integer status;
    @Excel(name = "请求时间")
    private String time;
    @Excel(name = "消耗时间")
    private long costtime;

}

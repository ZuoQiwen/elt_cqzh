package com.dfwy.web.domain;

import com.dfwy.web.common.annotation.Excel;
import com.dfwy.web.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.UUID;

/**
 *@className TaxDataRequest
 *@description : 税务数据请求报文解析
 *@author zuoqiwen
 *@date 2020/7/20 17:41
 *@version V1.0
 **/
@Data
public class TaxDataRequest extends BaseEntity {
    @Excel(name = "合作平台")
    private String COP_PLT;
    @Excel(name = "区域码")
    private String NTB_AREA;
    @Excel(name = "国税局编码")
    private String NTB_CODE;
    @Excel(name = "纳税人识别号")
    private String TXP_RGT;
    @Excel(name = "纳税人名称")
    private String TXP_NAME;
    @Excel(name = "授权编码")
    private String NTB_AUTH;
    @Excel(name = "法人姓名")
    private String LP_NAME;
    @Excel(name = "法人证件类型")
    private String LP_TYPE;
    @Excel(name = "法人证件号码")
    private String LP_NUM;
    @Excel(name = "查询时间")
    private String QRY_TIME;
    @Excel(name = "预留字段1")
    private String RES01;
    @Excel(name = "预留字段2")
    private String RES02;
    @Excel(name = "预留字段3")
    private String RES03;
    @Excel(name = "预留字段4")
    private String RES04;
    @Excel(name = "预留字段5")
    private String RES05;
    private String ID;

}

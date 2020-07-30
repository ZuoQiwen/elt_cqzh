package com.dfwy.common.domain.taxdata;

import com.dfwy.common.domain.CQBOCData;
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
public class TaxDataRequest {
    private String COP_PLT;
    private String NTB_AREA;
    private String NTB_CODE;
    private String TXP_RGT;
    private String TXP_NAME;
    private String NTB_AUTH;
    private String LP_NAME;
    private String LP_TYPE;
    private String LP_NUM;
    private String QRY_TIME;
    private String RES01;
    private String RES02;
    private String RES03;
    private String RES04;
    private String RES05;
    private String ID;

    public TaxDataRequest(){
        this.ID = UUID.randomUUID().toString().replace("_","");
    }


    public CQBOCData getData(){
        return new CQBOCData().setCPBM("QED-YSD")
                .setNSRSBH(this.getTXP_RGT())
                .setYHBM("SYHD_BNAKOFCHINA")
                .setYHXDXH(this.ID);
    }
}

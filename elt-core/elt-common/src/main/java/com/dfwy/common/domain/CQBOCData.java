package com.dfwy.common.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *@className CQTaxData
 *@description : 重庆数据请求参数
 *@author zuoqiwen 
 *@date 2020/6/18 10:50
 *@version V1.0
 **/
@Data
@Accessors(chain = true)
public class CQBOCData implements ELTOperation{
    @JsonProperty("NSRSBH")
    private String NSRSBH;
    @JsonProperty("YHBM")
    private String YHBM;
    @JsonProperty("YHXDXH")
    private String YHXDXH;
    @JsonProperty("CPBM")
    private String CPBM;


    @Override
    public String getBusinessId() {
        return this.YHXDXH;
    }

    @Override
    public String getNsrsbh() {
        return this.NSRSBH;
    }

}

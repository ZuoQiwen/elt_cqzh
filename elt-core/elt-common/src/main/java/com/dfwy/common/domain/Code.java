package com.dfwy.common.domain;

import lombok.Data;

/**
 *@className DM
 *@description :码值转换表
 *@author zuoqiwen
 *@date 2020/6/10 10:27
 *@version V1.0
 **/
@Data
public class Code {
    /**
     * 标准表
     */
    private String bzb;
    /**
     * 码值
     */
    private String mz;
    /**
     * 码值名称
     */
    private String mzmc;

}

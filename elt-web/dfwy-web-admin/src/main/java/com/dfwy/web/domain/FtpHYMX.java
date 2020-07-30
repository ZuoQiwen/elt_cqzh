package com.dfwy.web.domain;

import com.dfwy.web.common.annotation.Excel;
import com.dfwy.web.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *@className FtpHYMX
 *@description : 按行业统计
 *@author zuoqiwen
 *@date 2020/7/14 14:20
 *@version V1.0
 **/
@Data
@Accessors(chain = true)
public class FtpHYMX extends BaseEntity {
    /**
     * 行业代码
     */
    @Excel(name = "行业代码")
    private String hydm;
    /**
     * 产品代码
     */
    @Excel(name = "产品代码")
    private String cpdm;
    /**
     * 授信户数
     */
    @Excel(name = "授信户数")
    private String sxhs;
    /**
     * 授信金额
     */
    @Excel(name = "授信金额")
    private String sxje;
    /**
     * 有贷户数
     */
    @Excel(name = "有贷户数")
    private String ydhs;
    /**
     * 贷款余额
     */
    @Excel(name = "贷款余额")
    private String dkye;
    /**
     * 累投笔数
     */
    @Excel(name = "累投笔数")
    private String ltbs;
    /**
     * 累计投放金额
     */
    @Excel(name = "累计投放金额")
    private String ltje;
}

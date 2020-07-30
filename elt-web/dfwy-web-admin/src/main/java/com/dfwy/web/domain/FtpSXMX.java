package com.dfwy.web.domain;

import com.dfwy.web.common.annotation.Excel;
import com.dfwy.web.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className SXMX
 * @description : 授信明细
 * @date 2020/7/14 14:06   字段顺序不可变
 **/
@Data
@Accessors(chain = true)
public class FtpSXMX extends BaseEntity {
    /**
     * 产品代码
     */
    @Excel(name = "产品代码")
    private String cpdm;
    /**
     * 纳税人名称
     */
    @Excel(name = "纳税人名称")
    private String nsrmc;
    /**
     * 纳税人名称
     */
    @Excel(name = "纳税人名称")
    private String nsrsbh;
    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String htbh;
    /**
     * 授信起始日期
     */
    @Excel(name = "授信起始日期")
    private String shqsrq;
    /**
     * 授信终止日期
     */
    @Excel(name = "授信终止日期")
    private String shzzrq;
    /**
     * 是否贷款（Y/N
     */
    @Excel(name = "是否贷款（Y/N）")
    private String sfdk;
}

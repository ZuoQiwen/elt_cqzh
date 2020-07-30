package com.dfwy.common.domain.ftp;

import com.dfwy.common.annotation.SftpOrder;
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
public class FtpSXMX {
    /**
     * 产品代码
     */
    @SftpOrder(1)
    private String cpdm;
    /**
     * 纳税人名称
     */
    @SftpOrder(2)
    private String nsrmc;
    /**
     * 纳税人名称
     */
    @SftpOrder(3)
    private String nsrsbh;
    /**
     * 纳税人名称
     */
    @SftpOrder(4)
    private String htbh;
    /**
     * 授信起始日期
     */
    @SftpOrder(5)
    private String shqsrq;
    /**
     * 授信终止日期
     */
    @SftpOrder(6)
    private String shzzrq;
    /**
     * 是否贷款（Y/N
     */
    @SftpOrder(7)
    private String sfdk;
}

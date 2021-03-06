package com.dfwy.common.domain.ftp;

import com.dfwy.common.annotation.SftpOrder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className FtpNSXYDJMX
 * @description : 纳税信用评级
 * @date 2020/7/14 14:20
 **/
@Data
@Accessors(chain = true)
public class FtpNSXYDJMX {
    /**
     * 纳税信用等级
     */
    @SftpOrder(1)
    private String nsxypj;
    /**
     * 产品代码
     */
    @SftpOrder(2)
    private String cpdm;
    /**
     * 授信户数
     */
    @SftpOrder(3)
    private String sxhs;
    /**
     * 授信金额
     */
    @SftpOrder(4)
    private String sxje;
    /**
     *有贷户数
     */
    @SftpOrder(5)
    private String ydhs;
    /**
     * 贷款余额
     */
    @SftpOrder(6)
    private String dkye;
    /**
     * 累投笔数
     */
    @SftpOrder(7)
    private String ltbs;
    /**
     * 累计投放金额
     */
    @SftpOrder(8)
    private String ltje;
}

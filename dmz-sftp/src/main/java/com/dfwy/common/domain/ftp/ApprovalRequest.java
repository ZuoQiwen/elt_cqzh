package com.dfwy.common.domain.ftp;

import lombok.Data;

import java.util.UUID;

@Data
public class ApprovalRequest {
    /**
     * 合作平台
     */
    private String COP_PLT;
    /**
     * 区域码
     */
    private String NTB_AREA;
    /**
     * 国税局编码
     */
    private String COP_CODE;
    /**
     * 纳税人识别号
     */
    private String TXP_RGT;
    /**
     * 纳税人名称
     */
    private String TXP_NAME;
    /**
     * 申请ID
     */
    private String OFSMID;
    /**
     * 贷款申请阶段
     */
    private String TRANSTAGE;
    /**
     * 贷款申请状态
     */
    private String TRAN_RESULT;
    /**
     * 授权失败原因
     */
    private String FAILMSG;
    /**
     * 预授信额度
     */
    private String PREAUTHAMTMAX;
    /**
     * 申请日期
     */
    private String APLTM;
    /**
     * 申请贷款金额
     */
    private String APLFINCAMT;
    /**
     * 批复额度
     */
    private String LML;
    /**
     * 批复日期
     */
    private String APPRODATE;
    /**
     * 授信金额
     */
    private String PREAUTHAMT;
    /**
     * 授信合同编号
     */
    private String CNTID;
    /**
     * 货币种类
     */
    private String CURCODE;
    /**
     * 授信开始日期
     */
    private String PREAUTHSTARTDATE;
    /**
     * 授信到期日期
     */
    private String PREAUTHDUEDATE;
    /**
     * 授信对象类型
     */
    private String OBJTYPE;
    /**
     * 银行放款账号
     */
    private String TOACCTNO;
    /**
     * 账号性质
     */
    private String ACNOTYPE;
    /**
     * 还款方式
     */
    private String PAYMETHOD;
    /**
     * 签约日期
     */
    private String SIGNTIME;
    /**
     * 授信到期日期
     */
    private String LNDUEDTD;
    /**
     * 是否首次提款
     */


    private String FIRSTDRAEFLG;
    /**
     * 首日提款日期
     */
    private String FIRSTDRAEDATE;
    /**
     * 贷款结清日期
     */
    private String SETDOWNDATE;
    /**
     * 企业提款余额
     */
    private String DRAWABLEAMT;
    /**
     * 每笔提款对应贷款账户
     */
    private String ACCTNO;
    /**
     * 每笔提款日期
     */
    private String DRAWDATE;
    /**
     * 每笔提款金额
     */
    private String DEAWAMT;
    /**
     * 累计提款金额
     */
    private String ASUMDRAWATMT;

    private String id;

    public ApprovalRequest(){
        this.id = UUID.randomUUID().toString().replace("-","");
    }

}

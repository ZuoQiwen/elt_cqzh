package com.dfwy.common.domain.ftp;

import com.dfwy.common.domain.ELTOperation;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className CSPA
 * @description :  中行数据文件推送至税局
 * @date 2020/7/13 17:05
 **/
@Data
public class ApprovalResult implements ELTOperation {
    private String businessId;
    private String date;
    private List<ApprovalRequest> list;

    public ApprovalResult(List<ApprovalRequest> list){
        this.list = list;
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.businessId  = UUID.randomUUID().toString().replace("-","");
    }
    private ApprovalResult(){

    }
    @Override
    public String getNsrsbh() {
        return null;
    }
}

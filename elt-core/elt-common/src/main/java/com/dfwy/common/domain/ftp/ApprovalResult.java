package com.dfwy.common.domain.ftp;

import com.dfwy.common.domain.ELTOperation;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @SneakyThrows
    public ApprovalResult(Map<String,String> map){
        this.list = new ArrayList<>();
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.businessId  = UUID.randomUUID().toString().replace("-","");
        ApprovalRequest item = new ApprovalRequest();
        BeanUtils.populate(item,map);
        this.list.add(item);
    }
    private ApprovalResult(){

    }
    @Override
    public String getNsrsbh() {
        return null;
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Map<String,String> map =  new HashMap<>();
        map.put("COP_PLT","1");
        map.put("NTB_AREA","2");
        ApprovalRequest item = new ApprovalRequest();
        BeanUtils.populate(item,map);
        System.out.println(item);
    }
}

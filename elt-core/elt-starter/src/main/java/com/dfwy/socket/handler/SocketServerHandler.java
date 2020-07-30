package com.dfwy.socket.handler;

import com.dfwy.common.domain.ftp.ApprovalResult;
import com.dfwy.common.domain.taxdata.TaxDataRequest;
import com.dfwy.common.utils.Result;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.BuildService;
import com.dfwy.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className SocketServerHandler
 * @description : socket服务处理
 * @date 2020/7/20 14:53
 **/
@Slf4j
@Component
public class SocketServerHandler {
    @Autowired
    private BuildService buildService;
    @Autowired
    private SftpService sftpService;
    @Autowired
    private ELTMapper eltMapper;

    /**
     * @param []
     * @return java.lang.String
     * @description : 业务逻辑处理，此处包含sftp文件推送和税务数据获取
     * @author zuoqiwen
     * @date 2020/7/20 14:42
     */
    public String handler(String str) {

        return "1213";
    }

    /**
     * @param []
     * @return java.lang.String
     * @description :  税务数据处理
     * @author zuoqiwen
     * @date 2020/7/20 14:51
     */
    private String taxDataHandler(TaxDataRequest taxDataRequest) {
        Result result = null;
        try {
            eltMapper.insertIntoTaxDataRequest(taxDataRequest);
            result = buildService.build(taxDataRequest.getData());
        } catch (Exception e) {
            result = Result.fail("税务数据处理失败");
        }
        return result.isSuccess() ?
                TaxDataResponse.success((Map<String, List<Map<String, String>>>) result.getData()) :
                TaxDataResponse.failed(result.getMsg());
    }

    /**
     * @param [approvalResult]
     * @return java.lang.String
     * @description :  sftp处理
     * @author zuoqiwen
     * @date 2020/7/20 14:51
     */
    private String sftpHandler(ApprovalResult approvalResult) {
        try {
            eltMapper.insertIntoApprovalResult(approvalResult);
            return SftpResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return SftpResponse.fail("数据存储失败");
        }
    }
}

package com.dfwy.socket.handler;

import com.dfwy.common.domain.ftp.ApprovalResult;
import com.dfwy.common.domain.taxdata.TaxDataRequest;
import com.dfwy.common.utils.Result;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.BuildService;
import com.dfwy.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
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
    public String handler(String str) throws JSONException {
        Map<String,Object> map = XML.toJSONObject(str).toMap();
        if(map.containsKey("CSPA05")){
            return taxDataHandler(new TaxDataRequest((((Map<String, Map<String, String>>) map.get("CSPA05")).get("REP_MSG"))));
        }else if(map.containsKey("CSPA02")){
            return sftpHandler(new ApprovalResult((((Map<String, Map<String, String>>) map.get("CSPA02")).get("REP_MSG"))));
        }else{
            log.error("报文解析异常");
            return "";
        }
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

    public static void main(String[] args) {
        String xml="<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<CSPA05>\n" +
                "\t<REP_HEAD>\t\n" +
                "\t</REP_HEAD>\n" +
                "\t<REP_MSG>\n" +
                "\t\n" +
                "\t</REP_MSG>\n" +
                "</CSPA05>";
        Map<String,Object> map = XML.toJSONObject(xml).toMap();
        Object object  = map.get("CSPA05");
        System.out.println(object);
        System.out.println((((Map<String, Map<String, String>>) map.get("CSPA05")).get("REP_MSG")));
    }

}

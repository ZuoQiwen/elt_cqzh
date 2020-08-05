package com.dfwy.httpserver;

import com.dfwy.common.domain.ftp.FtpTask;
import com.dfwy.common.utils.Result;
import com.dfwy.sftp.Sftp;
import com.dfwy.sftp.SftpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * @ClassName SftpHttpServer
 * @Description  sftp服务
 * @Author zuoqiwen
 * @Date 2020/7/29 10:06 
 * @Version 1.0
 **/
public class SftpHttpServer implements HttpHandler {
    // private Logger log = LoggerFactory.getLogger(SftpHttpServer.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Result result = null;
        try {
            FtpTask ftpTask = SftpHttpUtils.getRequestBody(httpExchange);
            boolean falg=SftpHttpUtils.isEffective(ftpTask);
            if(falg){
                new SftpUtils(ftpTask).put();
                result =  Result.success();
            }else{
                result =  Result.fail("参数无效");
            }
        } catch (Exception e) {
            //log.error("sftp文件上传失败", e);
            result =  Result.fail(e.getMessage());
        }
        SftpHttpUtils.writeResponse(httpExchange,result);
    }
}

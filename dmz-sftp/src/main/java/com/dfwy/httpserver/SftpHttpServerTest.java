package com.dfwy.httpserver;

import com.dfwy.common.utils.Result;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * @ClassName SftpHttpServerTest
 * @Description  sftp服务测试
 * @Author zuoqiwen
 * @Date 2020/7/29 10:05 
 * @Version 1.0
 **/
public class SftpHttpServerTest  implements HttpHandler {
    private Logger log = LoggerFactory.getLogger(SftpHttpServer.class);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SftpHttpUtils.writeResponse(httpExchange,Result.success("测试"));
    }
}

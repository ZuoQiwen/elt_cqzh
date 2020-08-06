package com.dfwy.httpserver;

import com.dfwy.common.utils.Result;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;


import java.io.IOException;
/**
 * @ClassName SftpHttpServerTest
 * @Description  sftp服务测试
 * @Author zuoqiwen
 * @Date 2020/7/29 10:05 
 * @Version 1.0
 **/
public class SftpHttpServerTest  implements HttpHandler {

    @SneakyThrows
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println(Thread.currentThread().getName()+"start");
        Thread.sleep(5000);
        SftpHttpUtils.writeResponse(httpExchange, Result.success("测试"));
    }
}

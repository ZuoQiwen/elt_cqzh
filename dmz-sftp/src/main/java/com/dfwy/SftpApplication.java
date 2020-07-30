package com.dfwy;


import com.dfwy.httpserver.SftpHttpServer;
import com.dfwy.httpserver.SftpHttpServerTest;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;


public class SftpApplication {
    private static Logger logger = LoggerFactory.getLogger(SftpApplication.class);
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(35168), 10);
            server.createContext("/sftp",new SftpHttpServer());
            server.createContext("/test",new SftpHttpServerTest());
            server.start();
            logger.info("sftp服务启动成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

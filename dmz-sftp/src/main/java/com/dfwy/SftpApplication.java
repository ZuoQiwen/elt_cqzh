package com.dfwy;


import com.dfwy.httpserver.SftpHttpServer;
import com.dfwy.httpserver.SftpHttpServerTest;
import com.dfwy.httpserver.ForwardServer;
import com.sun.net.httpserver.HttpServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;


public class SftpApplication {
    //private static Logger logger = LoggerFactory.getLogger(SftpApplication.class);
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(35168), 100);
            ExecutorService executorService = new ThreadPoolExecutor(10,200,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
            server.setExecutor(executorService);
            server.createContext("/sftp",new SftpHttpServer());
            server.createContext("/test",new SftpHttpServerTest());
            server.createContext("/transform",new ForwardServer());
            server.start();
            //logger.info("sftp server started");
            System.out.println("sftp server started");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

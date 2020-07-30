package com.dfwy;

import com.alibaba.druid.pool.DruidDataSource;
import com.dfwy.socket.netty.NettyServer;
import com.dfwy.socket.netty.SocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAsync
@MapperScan({"com.dfwy.*.mapper"})
@EnableScheduling
public class EltApplication  implements CommandLineRunner {
    @Value("${cqzh.socket.port}")
    private int port;

    public static void main(String[] args) {
        System.getProperties().setProperty("oracle.jdbc.J2EE13Compliant", "true");
        SpringApplication.run(EltApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new NettyServer(port).start();
    }
}

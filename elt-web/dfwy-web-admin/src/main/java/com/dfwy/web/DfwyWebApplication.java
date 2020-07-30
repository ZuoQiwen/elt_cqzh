package com.dfwy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author dfwy
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DfwyWebApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(DfwyWebApplication.class, args);
        System.out.println("dfwy web started");
    }
}
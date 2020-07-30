//package com.dfwy;
//
//import com.dfwy.common.annotation.SftpOrder;
//import com.dfwy.common.domain.ftp.*;
//import com.dfwy.sftp.SftpUtils;
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@WebAppConfiguration
//@Slf4j
//public class SftpUtilsTest {
//    @Autowired
//    private SftpUtils sftpUtils;
//    @Test
//    public void getLinesTest() throws IllegalAccessException {
//        List<FtpSXMX> list =  new ArrayList<>();
//        FtpSXMX sxmx = new FtpSXMX().setCpdm("1").setHtbh("2").setNsrmc("3");
//        list.add(sxmx);
//        list.add(new FtpSXMX().setCpdm("2").setHtbh("3").setNsrmc("3"));
//        System.out.println(new SftpUtils().getContent(list));
//    }
//
//
//    @Test
//    public void put(){
//        List<FtpSXMX> list =  new ArrayList<>();
//        FtpSXMX sxmx = new FtpSXMX().setCpdm("1").setHtbh("2").setNsrmc("3");
//        list.add(sxmx);
//
//        DmzFtp dmzFtp = new DmzFtp("20201000");
//        List<FtpHYMX> list1 = new ArrayList<>();
//        List<FtpZGSWJMX> list2 = new ArrayList<>();
//        List<FtpNSXYDJMX> list3 = new ArrayList<>();
//
//        FtpZGSWJMX ftpZGSWJMX = new FtpZGSWJMX().setCpdm("1").setDkye("2").setLtje("3")
//                .setLtbs("4").setSxhs("5");
//
//
//        FtpHYMX ftpHYMX = new FtpHYMX();
//        ftpHYMX.setCpdm("1").setDkye("2").setHydm("3").setLtje("4").setLtbs("5").setYdhs("6");
//
//        FtpNSXYDJMX ftpNSXYDJMX = new FtpNSXYDJMX();
//        ftpNSXYDJMX.setCpdm("1").setDkye("2").setLtje("3").setNsxypj("4");
//        list1.add(ftpHYMX);
//        list2.add(ftpZGSWJMX);
//        list3.add(ftpNSXYDJMX);
//        dmzFtp.setDate("20200715").setFtpHYMX(list1).setFtpNSXYDJMX(list3).setFtpSXMX(list).setFtpZGSWJMX(list2);
//
//        sftpUtils.put(dmzFtp);
//
//
//
//
//    }
//    @Test
//    public void fileTest() throws IOException {
//        String filePath = "/sftp";
//        filePath = System.getProperty("os.name").toLowerCase().contains("windows") ? "C://" + filePath :
//                "/opt" + filePath;
//        File file = new File(filePath,"12121212.txt");
//        if(!file.exists()){
//            file.createNewFile();
//        }
//    }
//
//
//    @SneakyThrows
//    @Test
//    public void put1(){
//        JSch jsch = new JSch();
//        Session session = jsch.getSession("root", "122.51.93.180", 55555);
//        session.setPassword("joy458751760");
//        Properties config = new Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//        session.setTimeout(3000);
//        session.connect();
//        Channel channel = session.openChannel("sftp");
//        channel.connect();
//        ChannelSftp channelSftp =  (ChannelSftp) channel;
//        channelSftp.cd("/home/sftp");
//        channelSftp.put(new FileInputStream(new File("C:/sftp/NSXYDJ_CQBOC20200715.txt")),
//                "NSXYDJ_CQBOC20200715.txt");
//
//    }
//    @Test
//    public void test(){
//        System.out.println(Stream.of(FtpZGSWJMX.class.getDeclaredFields())
//                .filter(x->x.getAnnotation(SftpOrder.class)!=null)
//                .sorted((Field field1,Field field2)->{
//                    return ((SftpOrder)field1.getAnnotation(SftpOrder.class)).value()
//                            -((SftpOrder)field2.getAnnotation(SftpOrder.class)).value();
//                }).collect(Collectors.toList()));
//    }
//}

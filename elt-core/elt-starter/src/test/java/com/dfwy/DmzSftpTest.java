package com.dfwy;

import com.dfwy.common.domain.ftp.*;
import com.dfwy.common.utils.Result;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class DmzSftpTest {
    public static void main(String[] args) {
        List<FtpSXMX> list =  new ArrayList<>();
        FtpSXMX sxmx = new FtpSXMX().setCpdm("1").setHtbh("2").setNsrmc("3");
        list.add(sxmx);

        DmzFtp dmzFtp = new DmzFtp("20201000");
        List<FtpHYMX> list1 = new ArrayList<>();
        List<FtpZGSWJMX> list2 = new ArrayList<>();
        List<FtpNSXYDJMX> list3 = new ArrayList<>();

        FtpZGSWJMX ftpZGSWJMX = new FtpZGSWJMX().setCpdm("1").setDkye("2").setLtje("3")
                .setLtbs("4").setSxhs("5");


        FtpHYMX ftpHYMX = new FtpHYMX();
        ftpHYMX.setCpdm("1").setDkye("2").setHydm("3").setLtje("4").setLtbs("5").setYdhs("6");

        FtpNSXYDJMX ftpNSXYDJMX = new FtpNSXYDJMX();
        ftpNSXYDJMX.setCpdm("1").setDkye("2").setLtje("3").setNsxypj("4");
        list1.add(ftpHYMX);
        list2.add(ftpZGSWJMX);
        list3.add(ftpNSXYDJMX);

        dmzFtp.setDate("20200805").setFtpHYMX(list1).setFtpNSXYDJMX(list3).setFtpSXMX(list).setFtpZGSWJMX(list2);
//        JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
//        client.setAddress("http://localhost:18082/sftp");
//        client.setServiceClass(com.dfwy.webservice.SftpService.class);
//        com.dfwy.webservice.SftpService sftpService = (com.dfwy.webservice.SftpService) client.create();
        SftpParam sftpParam = new SftpParam().setUserName("root").setPassword("joy458751760")
                .setIp("122.51.93.180").setPort(55555).setTimeout(3000).setYhbm("CQZGYH").setSrc("/home/sftp/file");
       // sftpService.sftp(new FtpTask().setDmzFtp(dmzFtp).setSftpParam(sftpParam));

        System.out.println(new RestTemplate().postForEntity("http://localhost:35168/sftp",new FtpTask().setDmzFtp(dmzFtp).setSftpParam(sftpParam), Result.class).getBody());
    }
}

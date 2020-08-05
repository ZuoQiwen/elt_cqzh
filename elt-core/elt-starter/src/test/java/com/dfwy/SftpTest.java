package com.dfwy;

import com.dfwy.common.domain.ftp.*;
import com.dfwy.common.utils.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SftpTest {
//    @Autowired
//    private RestTemplate restTemplate;
    @Test
    public void test(){
        DmzFtp dmzFtp = new DmzFtp("2020-10-11");
        List<FtpSXMX> ftpSXMXList = new ArrayList<>();
        ftpSXMXList.add(new FtpSXMX().setCpdm("1").setHtbh("2"));
        List<FtpHYMX> hymxList = new ArrayList<>();
        hymxList.add(new FtpHYMX().setDkye("2").setLtje("3"));
        List<FtpNSXYDJMX> nsxydjmxList = new ArrayList<>();
        nsxydjmxList.add(new FtpNSXYDJMX().setDkye("2aa").setCpdm("ddd"));
        List<FtpZGSWJMX> zgswjmxes = new ArrayList<>();
        zgswjmxes.add(new FtpZGSWJMX().setCpdm("sss").setCpdm("ss"));
        dmzFtp.setFtpHYMX(hymxList).setFtpZGSWJMX(zgswjmxes).setFtpNSXYDJMX(nsxydjmxList).setFtpSXMX(ftpSXMXList);
        System.out.println(new RestTemplate().postForEntity("http://127.0.0.1:35168/sftp",dmzFtp, Result.class).getBody());

    }
}

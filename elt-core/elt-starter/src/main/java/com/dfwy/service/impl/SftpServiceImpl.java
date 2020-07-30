package com.dfwy.service.impl;

import com.dfwy.common.annotation.ClearSession;
import com.dfwy.common.annotation.Log;
import com.dfwy.common.domain.ftp.ApprovalResult;
import com.dfwy.common.domain.ftp.DmzFtp;
import com.dfwy.common.domain.ftp.FtpTask;
import com.dfwy.common.utils.Result;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.config.CqzhConfiger;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Service
@Slf4j
public class SftpServiceImpl implements SftpService {
    @Autowired
    private CqzhConfiger cqzhConfiger;
    @Autowired
    private ELTMapper eltMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Log(api = "CSPA220001融资申请审批结果推送接口")
    public Result sftpPutFile(String date) {
        DmzFtp dmzFtp = new DmzFtp(date);
        dmzFtp.setFtpSXMX(eltMapper.selectSxmx(date));
        dmzFtp.setFtpNSXYDJMX(eltMapper.selectNsxydj(date));
        dmzFtp.setFtpHYMX(eltMapper.selectHymx(date));
        dmzFtp.setFtpZGSWJMX(eltMapper.selectZgswj(date));
        return restTemplate.postForEntity(cqzhConfiger.getDmzSftpUrl()+"/sftp",
                new FtpTask().setDmzFtp(dmzFtp).setSftpParam(cqzhConfiger.getSftpParam()),Result.class).getBody();
    }
}

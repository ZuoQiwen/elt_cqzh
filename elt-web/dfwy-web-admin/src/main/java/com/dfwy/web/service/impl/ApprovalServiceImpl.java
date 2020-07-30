package com.dfwy.web.service.impl;

import com.dfwy.web.domain.FtpHYMX;
import com.dfwy.web.domain.FtpNSXYDJMX;
import com.dfwy.web.domain.FtpSXMX;
import com.dfwy.web.domain.FtpZGSWJMX;
import com.dfwy.web.mapper.ApprovalMapper;
import com.dfwy.web.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalMapper approvalMapper;

    @Override
    public List<FtpHYMX> hyList(FtpHYMX ftpHYMX) {
        return approvalMapper.hyList(ftpHYMX);
    }

    @Override
    public List<FtpNSXYDJMX> xydjList(FtpNSXYDJMX ftpNSXYDJMX) {
        return approvalMapper.xydjList(ftpNSXYDJMX);
    }

    @Override
    public List<FtpZGSWJMX> zgswjgList(FtpZGSWJMX ftpZGSWJMX) {
        return approvalMapper.zgswjgList(ftpZGSWJMX);
    }

    @Override
    public List<FtpSXMX> sxmxList(FtpSXMX ftpSXMX) {
        return approvalMapper.sxmxList(ftpSXMX);
    }
}

package com.dfwy.web.service;

import com.dfwy.web.domain.FtpHYMX;
import com.dfwy.web.domain.FtpNSXYDJMX;
import com.dfwy.web.domain.FtpSXMX;
import com.dfwy.web.domain.FtpZGSWJMX;

import java.util.List;

public interface ApprovalService {
    List<FtpHYMX> hyList(FtpHYMX ftpHYMX);

    List<FtpNSXYDJMX> xydjList(FtpNSXYDJMX ftpNSXYDJMX);

    List<FtpZGSWJMX> zgswjgList(FtpZGSWJMX ftpZGSWJMX);

    List<FtpSXMX> sxmxList(FtpSXMX ftpSXMX);
}

package com.dfwy.web.mapper;

import com.dfwy.web.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalMapper {
    List<FtpHYMX> hyList(FtpHYMX ftpHYMX);

    List<FtpNSXYDJMX> xydjList(FtpNSXYDJMX ftpNSXYDJMX);

    List<FtpZGSWJMX> zgswjgList(FtpZGSWJMX ftpZGSWJMX);

    List<FtpSXMX> sxmxList(FtpSXMX ftpSXMX);
}

package com.dfwy.service;

import com.dfwy.common.domain.ftp.ApprovalResult;
import com.dfwy.common.utils.Result;


public interface SftpService {
    Result sftpPutFile(String date);
}

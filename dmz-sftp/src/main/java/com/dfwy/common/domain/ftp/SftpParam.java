package com.dfwy.common.domain.ftp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SftpParam {
    private String userName;
    private String password;
    private String ip;
    private Integer port;
    private Integer timeout;
    private String yhbm;
    private String src;
}

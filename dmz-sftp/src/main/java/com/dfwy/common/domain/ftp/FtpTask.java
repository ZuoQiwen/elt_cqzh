package com.dfwy.common.domain.ftp;

import com.dfwy.common.utils.CqzhEndecryptUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class FtpTask {
    /**
     * 加密结果
     */
    private String key;
    /**
     * 未加密id
     */
    private String id;
    private SftpParam sftpParam;
    private DmzFtp dmzFtp;

    public FtpTask(){
        this.id = UUID.randomUUID().toString().replace("_","");
        this.key = CqzhEndecryptUtils.encrypt(id);
    }
}

package com.dfwy.common.domain.ftp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DmzFtp {
    private List<FtpSXMX> ftpSXMX;
    private List<FtpHYMX> ftpHYMX;
    private List<FtpNSXYDJMX> ftpNSXYDJMX;
    private List<FtpZGSWJMX> ftpZGSWJMX;
    private String date;

    public DmzFtp(String date){
        this.date = date;
    }
    public DmzFtp(){

    }






}

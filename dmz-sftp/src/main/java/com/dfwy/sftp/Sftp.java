package com.dfwy.sftp;

import com.dfwy.common.domain.ftp.SftpParam;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
public class Sftp {
    private Logger log = LoggerFactory.getLogger(Sftp.class);

    private SftpParam sftpParam;

    private ChannelSftp channelSftp;
    private Session session;

    public Sftp(SftpParam sftpParam) {
        this.sftpParam = sftpParam;
    }

    public void put(String src, String dest){
        File file = new File(dest);
        this.getChannelSftp();
        try {
            if(this.channelSftp.ls(src) == null){
                this.channelSftp.mkdir(src);
            }
            this.channelSftp.cd(src);
            this.channelSftp.put(new FileInputStream(file),file.getName());
        } catch (SftpException e) {
            log.error("推送文件失败：src:"+src+",dest:"+dest,e);
        } catch (FileNotFoundException e) {
            log.error("推送文件失败,文件不存在:"+dest,e);
        }
    }
    public void put(String src,String fileName, InputStream inputStream){
        this.getChannelSftp();
        try {
            if(this.channelSftp.ls(src) == null){
                this.channelSftp.mkdir(src);
            }
            this.channelSftp.cd(src);
            this.channelSftp.put(inputStream,fileName);
        } catch (SftpException e) {
            log.error("推送文件失败：src:"+src+",dest:"+fileName,e);
        }
    }
    public void close(){
        if(this.channelSftp !=null){
            this.channelSftp.disconnect();
        }
        if(this.session !=null){
            this.session.disconnect();
        }
    }

    public void getChannelSftp(){
        if(this.channelSftp!=null){
            return;
        }
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpParam.getUserName(), sftpParam.getIp(), sftpParam.getPort());
            session.setPassword(sftpParam.getPassword());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(sftpParam.getTimeout());
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            this.channelSftp =  (ChannelSftp) channel;
        } catch (JSchException e) {
            log.error("sftp初始化错误",e);
        }
    }
}

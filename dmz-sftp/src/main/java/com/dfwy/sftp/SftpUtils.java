package com.dfwy.sftp;


import com.dfwy.common.annotation.SftpOrder;
import com.dfwy.common.domain.ftp.DmzFtp;
import com.dfwy.common.domain.ftp.FtpTask;
import com.dfwy.common.domain.ftp.SftpParam;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.utils.SM9Util;
import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SftpUtils {
    //private Logger log = LoggerFactory.getLogger(SftpUtils.class);

    private FtpTask ftpTask;

    private static final String DELIMITER = "|";
    private static final String LINE_DELIMITER = "\r\n";

    private SftpUtils() {
    }

    public SftpUtils(FtpTask ftpTask) {
        this.ftpTask = ftpTask;
    }

    public void put() {
        DmzFtp dmzFtp = ftpTask.getDmzFtp();
        SftpParam sftpParam = ftpTask.getSftpParam();
//        log.info("ftp文件上传：SXMX[{}]ZGSWJDM[{}]HYDM[{}]NSXYDJ[{}]" ,
//                size(dmzFtp.getFtpSXMX()),
//                size(dmzFtp.getFtpZGSWJMX()),
//                size(dmzFtp.getFtpHYMX()),
//                size(dmzFtp.getFtpNSXYDJMX()));
        Sftp sftp = new Sftp(sftpParam);
        String fileName = sftpParam.getYhbm() + dmzFtp.getDate();
        sftp.put(sftpParam.getSrc(), "SXMX_" + fileName, createInputStream(getContent(dmzFtp.getFtpSXMX())));
        sftp.put(sftpParam.getSrc(), "ZGSWJDM_" + fileName, createInputStream(getContent(dmzFtp.getFtpZGSWJMX())));
        sftp.put(sftpParam.getSrc(), "HYDM_" + fileName, createInputStream(getContent(dmzFtp.getFtpHYMX())));
        sftp.put(sftpParam.getSrc(), "NSXYDJ_" + fileName, createInputStream(getContent(dmzFtp.getFtpNSXYDJMX())));
        sftp.close();
     //   log.info("ftp文件上传成功");
    }

    private String size(List list){
        if(CollectionUtils.isEmpty(list)){
            return "0";
        }else{
            return String.valueOf(list.size());
        }
    }
    private InputStream createInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    public String getContent(List<? extends Object> list) {
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            Object object = list.get(0);
            //Field[] fields = object.getClass().getDeclaredFields();
            //jdk 获取getDeclaredFields的字段顺序并不保证和代码中上下顺序一致，故在此进行排序处理
            List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
            Collections.sort(fields, new Comparator<Field>() {
                @Override
                public int compare(Field field1, Field field2) {
                    return ((SftpOrder) field1.getAnnotation(SftpOrder.class)).value()
                            - ((SftpOrder) field2.getAnnotation(SftpOrder.class)).value();
                }
            });
            try {
                for (Object o : list) {
                    StringBuffer line = new StringBuffer();
                    for (Field field : fields) {
                        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        line.append(StringUtil.getText(field.get(o))).append(DELIMITER);
                    }
                    sb.append(line.length() > DELIMITER.length() ? line.substring(0, line.length() - DELIMITER.length()) :
                            line.toString()).append(LINE_DELIMITER);
                }
                String result = sb.length() > LINE_DELIMITER.length() ? sb.substring(0, sb.length() - LINE_DELIMITER.length()) : sb.toString();
                //log.info("获取文件内容成功：" + (list.size()>0?list.get(0).getClass().getSimpleName():""));
                return SM9Util.encrypt(result);
            } catch (Exception e) {
                //log.info("获取文件内容失败：list：{},文本：{}", list, sb.toString());
                throw new RuntimeException("获取文件数据异常", e);
            }
        }
        return "";
    }
}

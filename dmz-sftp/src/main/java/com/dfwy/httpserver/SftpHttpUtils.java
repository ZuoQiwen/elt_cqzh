package com.dfwy.httpserver;

import com.dfwy.common.domain.ftp.FtpTask;
import com.dfwy.common.utils.CqzhEndecryptUtils;
import com.dfwy.common.utils.Result;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.sftp.Sftp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SftpHttpUtils {
    //private static Logger log = LoggerFactory.getLogger(Sftp.class);
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static FtpTask getRequestBody(HttpExchange httpExchange) {
        try {
            String content = IOUtils.toString(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            return StringUtil.isEmpty(content) ? null : objectMapper.readValue(content, FtpTask.class);
        } catch (IOException e) {
            // log.error("读取请求数据异常", e);
        }
        return null;
    }

    public static boolean isEffective(FtpTask ftpTask) {
        return ftpTask != null && !StringUtil.isEmpty(ftpTask.getKey()) && ftpTask.getKey().equals(CqzhEndecryptUtils.encrypt(ftpTask.getId()));

    }

    public static void writeResponse(HttpExchange httpExchange, Result result) throws IOException {
        String response = objectMapper.writeValueAsString(result);
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        httpExchange.sendResponseHeaders(200, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        httpExchange.close();
    }
}

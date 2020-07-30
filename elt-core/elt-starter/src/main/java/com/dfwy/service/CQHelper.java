package com.dfwy.service;

import com.dfwy.common.configer.RestTemplateConfig;
import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.config.CqzhConfiger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.LinkedList;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className CQHelper
 * @description : 重庆税务获取工具类
 * @date 2020/7/16 13:37
 **/
@Component
@ConfigurationProperties("cqzh")
@Slf4j
@Data
public class CQHelper {
    @Autowired
    private CqzhConfiger cqzhConfiger;

    @Autowired
    private RestTemplateConfig restTemplateConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @description :  token不在缓存  修改为每次获取
     * @author zuoqiwen
     * @date 2020/7/27 15:25
     * @param []
     * @return java.lang.String
     */
    public String getTooken() {
        CqzhConfiger.CqzhParam  token = cqzhConfiger.getTokenParam();
        StringBuffer sb = new StringBuffer();
        sb.append(token.getUrl()).append("?").
                append("ServiceID").append("=").append(token.getServiceID()).append("&")
                .append("SourceSysID").append("=").append(token.getSourceSysID());
        log.info("从税务申请token的url为" + sb.toString() + "请求数据为" + token.toString());
        try {
            TokenResult result = restTemplate.postForEntity(sb.toString(), token, TokenResult.class).getBody();
            log.info("[获取TOKEN成功]："+result);
            if (result != null && StringUtil.isNotEmpty(result.getToken())) {
                return result.getToken();
            } else {
                String errorMessage = "获取token数据结果中token为空，中断流程";
                log.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "获取token数据异常";
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }
    /**
     * @param cqbocData
     * @return java.lang.String
     * @description :  获取税务数据
     * @author zuoqiwen
     * @date 2020/7/16 14:48
     */
    public String getData(CQBOCData cqbocData) throws IOException {
        CqzhConfiger.CqzhParam  taxData = cqzhConfiger.getTaxDataParam();
        StringBuffer sb = new StringBuffer();
        if (taxData == null) {
            String errorMessage = "税务查询接口信息没有配置，请检查application.yml文件cqzh参数";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        sb.append(taxData.getUrl()).append("?").
                append("ServiceID").append("=").append(taxData.getServiceID()).append("&")
                .append("SourceSysID").append("=").append(taxData.getSourceSysID());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", getTooken());
        HttpEntity<CQBOCData> httpEntity = new HttpEntity<>(cqbocData, httpHeaders);
        String result = null;
        try {
            result = restTemplate.postForEntity(sb.toString(), httpEntity, String.class).getBody();
            log.info("[查询税务数据成功]："+result.length());
        } catch (Exception e) {
            log.error("查询税务数据发生异常,url{},参数{}",sb.toString(),cqbocData , e);
            throw new RuntimeException("查询税务数据发生异常" + e.getMessage());
        }
        return result;
    }

    @Data
    @Accessors(chain = true)
    public class CqTooken {
        private String tooken;
        private long expireTime;
    }



    @Data
    public static class TokenResult {
        private String code;
        private String message;
        private long expire;
        private String token;
    }


    public String post(String url, String authorization, String json) {
        try {
            HttpHeaders headers = new HttpHeaders();
            LinkedList<MediaType> list = new LinkedList<>();
            list.add(MediaType.ALL);
            headers.setAccept(list);
            headers.setConnection("Keep-Alive");
            headers.set("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authorization);
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("查询税务数据发生异常" + e);
            throw new RuntimeException("查询税务数据发生异常" + e.getMessage());
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(new CQBOCData().setCPBM("1")));
    }
}

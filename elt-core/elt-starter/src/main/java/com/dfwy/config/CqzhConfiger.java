package com.dfwy.config;


import com.dfwy.common.domain.SystemConfig;
import com.dfwy.common.domain.ftp.SftpParam;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.CQHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
@Data
@Slf4j
public class CqzhConfiger {
    @Autowired
    private ELTMapper eltMapper;
    private Map<String, String> cache = new ConcurrentHashMap<>();

    private SftpParam sftpParam;
    private CqzhParam taxDataParam;
    private CqzhParam tokenParam;

    private String dmzSftpUrl ;

    @PostConstruct
    public void init() {
        List<SystemConfig> list = eltMapper.selectSystemConfiger();
        cache = list.stream().collect(Collectors.toMap(SystemConfig::getConfigkey, SystemConfig::getConfigValue, (a, b) -> a));
        check();
        this.sftpParam = new SftpParam().setIp(getStringValue(CqzhContant.CQZH_SFTP_IP))
                .setUserName(getStringValue(CqzhContant.CQZH_SFTP_NAME))
                .setPort(getIntegerValue(CqzhContant.CQZH_SFTP_PORT))
                .setYhbm(getStringValue(CqzhContant.CQZH_SFTP_YHBM))
                .setSrc(getStringValue(CqzhContant.CQZH_SFTP_SRC))
                .setTimeout(getIntegerValue(CqzhContant.CQZH_SFTP_SESSION_TIMEOUT))
                .setPassword(getStringValue(CqzhContant.CQZH_SFTP_PASSWORD));

        this.tokenParam = new CqzhParam().setId(getStringValue(CqzhContant.CQZH_TOKEN_ID))
                .setPassword(getStringValue(CqzhContant.CQZH_TOKEN_PASSWORD))
                .setUrl(getStringValue(CqzhContant.CQZH_TOKEN_URL))
                .setServiceID(getStringValue(CqzhContant.CQZH_TOKEN_SERVICEID))
                .setSourceSysID(getStringValue(CqzhContant.CQZH_TOKEN_SOURCESYSID));
        this.taxDataParam = new CqzhParam().setUrl(getStringValue(CqzhContant.CQZH_TAXDATA_URL))
                .setServiceID(getStringValue(CqzhContant.CQZH_TAXDATA_SERVICEID))
                .setSourceSysID(getStringValue(CqzhContant.CQZH_TAXDATA_SOURCESYSID));
        this.dmzSftpUrl = getStringValue(CqzhContant.CQZH_DMZ_SFTP_URL);
        log.info("系统参数配置加载成功");

    }

    private void check() {
        try {
            for (Field declaredField : CqzhContant.class.getDeclaredFields()) {
                declaredField.setAccessible(true);
                String key = (String) declaredField.get(CqzhContant.class);
                if (StringUtil.isEmpty(cache.get(key))) {
                    log.error("{}配置信息为空", key);
                    throw new RuntimeException("系统配置异常");
                }
            }
        } catch (IllegalAccessException e) {
            log.error("配置信息校验异常");
        }
    }

    public String getStringValue(String key) {
        return cache.get(key);
    }

    public int getIntegerValue(String key) {
        return Integer.parseInt(cache.get(key));
    }

    @Data
    @Accessors(chain = true)
    public static class CqzhParam {
        @JsonIgnore
        private String url;
        @JsonIgnore
        private String serviceID;
        @JsonIgnore
        private String sourceSysID;
        private String id;
        private String password;
    }

    public static void main(String[] args) throws IllegalAccessException {
        System.out.println(Arrays.asList(CqzhContant.class.getDeclaredFields()));
        for (Field declaredField : CqzhContant.class.getDeclaredFields()) {
            declaredField.setAccessible(true);
            System.out.println(declaredField.get(CqzhContant.class));

        }
    }

}

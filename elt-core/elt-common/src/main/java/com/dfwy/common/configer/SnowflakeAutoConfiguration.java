package com.dfwy.common.configer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * Description:
 * date: 2020/2/19 14:30
 *
 * @author zuoqiwen
 */
@Configuration
@Slf4j
public class SnowflakeAutoConfiguration {
    @Value("${snowflake.workerId:1}")
    private int workerId;
    @Value("${snowflake.datacenterId:1}")
    private int datacenterId;

    @Bean
    Snowflake snowflake() throws UnknownHostException {
        Snowflake snowflake = new Snowflake(workerId, datacenterId);
        return snowflake;
    }
}

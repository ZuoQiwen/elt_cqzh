package com.dfwy.common.configer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Description:
 * date: 2020/2/19 14:48
 *
 * @author zuoqiwen
 */
@Configuration
@Data
public class RestTemplateConfig {
    @Value("${elt.interface.readTimeout:3000}")
    private int readTimeout ;
    @Value("${elt.interface.connectTimeout:3000}")
    private int connectTimeout;
    @Value("${elt.interface.writeTimeout:3000}")
    private int writeTimeout;
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        RestTemplate restTemplate= new RestTemplate(factory);
        // 支持中文编码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;

    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);//单位为ms
        factory.setConnectTimeout(connectTimeout);//单位为ms
        return factory;
    }


}

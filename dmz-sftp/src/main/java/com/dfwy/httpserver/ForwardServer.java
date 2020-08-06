package com.dfwy.httpserver;

import com.dfwy.common.utils.StringUtil;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @ClassName TransformServer
 * @Description  elt http税务数据请求转发
 * @Author zuoqiwen
 * @Date 2020/8/6 9:08
 * @Version 1.0
 **/
public class ForwardServer implements HttpHandler {
    private RestTemplate restTemplate ;

    private final String TAXDATA_URL="forward_url=";

    private void init() {
        if(restTemplate ==null){
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(30000);
            factory.setConnectTimeout(30000);
            restTemplate =  new RestTemplate(factory);
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        }
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            init();
            String requestParam = httpExchange.getRequestURI().getRawQuery();
            if(StringUtil.isEmpty(requestParam)){
                throw new RuntimeException("转发地址错误");
            }
            String url = URLDecoder.decode(requestParam.substring(requestParam.indexOf(TAXDATA_URL)+TAXDATA_URL.length()),"UTF-8");
            String content = IOUtils.toString(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            Headers headers = httpExchange.getRequestHeaders();
            HttpHeaders httpHeaders = new HttpHeaders();
            for (Map.Entry<String, List<String>> item : headers.entrySet()) {
                httpHeaders.addAll(item.getKey(),item.getValue());
            }
            HttpEntity<String> entity = new HttpEntity<>(content,httpHeaders);
            String result = restTemplate.postForEntity(url,entity,String.class).getBody();
            SftpHttpUtils.writeResponse(httpExchange, result);
        } catch (Exception  e) {
            //模拟税局错误代码
            Map<String,String> error = new HashMap<>();
            error.put("ResponseCode","88");
            error.put("ResponseMsg","前置取数异常");
            SftpHttpUtils.writeResponse(httpExchange, error);
        }
    }


}

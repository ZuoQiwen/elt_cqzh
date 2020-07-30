//package com.dfwy.common.configer;
//
//import com.dfwy.common.expection.JsonSerializationException;
//import com.dfwy.common.expection.RemoteAccessException;
//import com.dfwy.common.utils.StringUtil;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class OkHttpUtil {
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private RestTemplateConfig restTemplateConfig;
//
//    public <T> T postForEntry(String url, Object object, Class<T> responseType, Map<String, String> headers) {
//        Assert.notNull(url, "请求地址不能为空");
//
//        String jsonBody = null;
//        try {
//            jsonBody = object == null ? "" : objectMapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new JsonSerializationException(object.toString());
//        }
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .connectTimeout(restTemplateConfig.getConnectTimeout(), TimeUnit.MILLISECONDS)
//                .writeTimeout(restTemplateConfig.getWriteTimeout(), TimeUnit.MILLISECONDS)
//                .readTimeout(restTemplateConfig.getReadTimeout(), TimeUnit.MILLISECONDS)
//                .build();
//        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), jsonBody);
//        Request.Builder requestBuilder = new Request.Builder()
//                .url(url);
//        if (!CollectionUtils.isEmpty(headers)) {
//            headers.forEach((k, v) -> requestBuilder.addHeader(k, v));
//        }
//        Request request = requestBuilder.post(body).build();
//        try (Response response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                String result = response.body() != null ? response.body().toString() : null;
//                return StringUtil.isEmpty(result) && responseType != null && responseType != Void.class ?
//                        null : objectMapper.readValue(result, responseType);
//            } else {
//                //服务端报错
//                throw new RuntimeException(String.format("[HTTP POSTJSON 请求失败]：url:%s\r\n content-type:%s \r\n " +
//                                "header:%s\r\n body:%s", url,
//                        request.body().contentType(),
//                        request.headers(),
//                        jsonBody));
//            }
//        }catch (RuntimeException e){
//            log.error("HTTP请求服务器异常:"+e.getMessage());
//            throw new RemoteAccessException("HTTP请求服务器异常", e);
//        }
//        catch (Exception e) {
//            String message = e.getMessage() +"\r\n"+String.format("[HTTP POSTJSON 请求失败]：url:%s\r\n content-type:%s \r\n header:%s\r\n body:%s", url,
//                    request.body().contentType(),
//                    request.headers(),
//                    jsonBody);
//            log.error("HTTP远程调用失败:"+message);
//            throw new RemoteAccessException("HTTP远程调用失败", e);
//        }
//    }
//
//    public <T> T postForEntry(String url, Object object, Class<T> responseType) {
//        return postForEntry(url, object, responseType, null);
//    }
//
//    public <T> T postForEntry(String url, Object object) {
//        return postForEntry(url, object, null, null);
//    }
//
//    public static void main(String[] args) {
//        OkHttpUtil okHttpUtil = new OkHttpUtil();
//        okHttpUtil.restTemplateConfig = new RestTemplateConfig();
//        okHttpUtil.objectMapper = new ObjectMapper();
//        okHttpUtil.restTemplateConfig.setConnectTimeout(1000);
//        okHttpUtil.restTemplateConfig.setReadTimeout(1000);
//        okHttpUtil.restTemplateConfig.setWriteTimeout(1000);
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "12121212");
//        headers.put("sss", "ccc");
//        System.out.println(okHttpUtil.postForEntry("http://localhost:8082/elt?aaa=11", okHttpUtil.restTemplateConfig, String.class,
//                headers));
//    }
//}

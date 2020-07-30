//package com.dfwy.common.util;
//
//import org.junit.Test;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.web.client.RestTemplate;
//
//public class HttpTest {
//    @Test
//    public void restTemplateTest(){
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization","1212");
//        HttpEntity<String> httpEntity = new HttpEntity<String>("asdadsadsadsadsadsadsadsadsadasdsadsad",httpHeaders);
//        new RestTemplate().postForEntity("http://127.0.0.1:9999/test1?a=1&b=2",
//                httpEntity,
//                String.class,"c=4","d=99");
//    }
//}

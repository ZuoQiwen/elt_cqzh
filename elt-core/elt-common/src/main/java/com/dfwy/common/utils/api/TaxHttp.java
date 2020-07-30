package com.dfwy.common.utils.api;

import com.dfwy.common.utils.ELTConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component(ELTConstant.HTTP)
public class TaxHttp implements TaxRequest{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TaxRequestFactory taxRequestFactory;
    @Override
    public String execute(Object object) {
        TaxRequestFactory.Request request = taxRequestFactory.taxDataRequest;
        if(ELTConstant.POST.equalsIgnoreCase(request.getHttpType())){
            return restTemplate.postForEntity(request.getUrl(),object,String.class).getBody();
        }else if(ELTConstant.GET.equalsIgnoreCase(request.getHttpType())){
            return restTemplate.getForEntity(request.getUrl(),String.class).getBody();
        }else{
            throw new RuntimeException("http type config error,expect post/get but"+request.getHttpType());
        }
    }
}

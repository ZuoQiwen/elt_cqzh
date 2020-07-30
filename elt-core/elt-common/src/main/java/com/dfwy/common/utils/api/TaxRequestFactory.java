package com.dfwy.common.utils.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConfigurationProperties("elt")
public class TaxRequestFactory {
    @Autowired
    Map<String, TaxRequest> taxRequests = new ConcurrentHashMap<>();
    public Request taxDataRequest =  new Request();

    public Request getTaxDataRequest() {
        return taxDataRequest;
    }

    public void setTaxDataRequest(Request taxDataRequest) {
        this.taxDataRequest = taxDataRequest;
    }


    public TaxRequest getTaxRequest(){
        Assert.notNull(taxDataRequest.getType(),"taxdata resquest type can not be null");
        TaxRequest taxRequest =  taxRequests.get(taxDataRequest.getType().toUpperCase());
        if(taxRequest==null){
            throw new RuntimeException("taxdata resquest type config error ,expect http/webservice ,but"+taxDataRequest.getType());
        }
        return taxRequest;
    }

    @Data
    public static class Request{
        public String type;
        public String url;
        public String webserviceMethod;
        public String httpType;
    }
}

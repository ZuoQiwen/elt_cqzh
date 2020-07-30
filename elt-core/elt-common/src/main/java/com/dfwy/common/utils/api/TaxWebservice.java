package com.dfwy.common.utils.api;

import com.dfwy.common.utils.ELTConstant;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(ELTConstant.WEBSERVICE)
public class TaxWebservice implements TaxRequest{
    @Autowired
    private TaxRequestFactory taxRequestFactory;
    @Override
    public String execute(Object object) {
        TaxRequestFactory.Request request = taxRequestFactory.taxDataRequest;

        //避免cxf线程中创建client时classloader递归内存溢出
        ThreadLocal<ClassLoader> threadLocal = new ThreadLocal<>();
        threadLocal.set(Thread.currentThread().getContextClassLoader());
        String ret = null;
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(request.getUrl());
            Object[] result = client.invoke(request.getWebserviceMethod(),new Object[]{object});
            ret = (String) result[0] ;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ClassLoaderUtils.setThreadContextClassloader(threadLocal.get());
        }
        return ret;
    }
}

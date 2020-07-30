package com.dfwy.builder.transform.standard;

import com.dfwy.builder.transform.StandardTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StandardTransformHelper {
    @Autowired
    private Map<String, StandardTransform> map = new ConcurrentHashMap<>();

    public void transform(Map<String, List<Map<String,String>>> data){
        if(!CollectionUtils.isEmpty(data)){
            map.forEach((x,y)->{
                y.transfrom(data.get(x));
            });
        }
    }
}

package com.dfwy.builder.transform.original;

import com.dfwy.builder.transform.OriginalTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OriginalTransformHelper {
    @Autowired
    private Map<String, OriginalTransform> map = new ConcurrentHashMap<>();


    public void transform(Map<String, List<Map<String,String>>> data, String key){
        if(map.containsKey(key)){
            map.get(key).transform(data.get(key));
        }
    }
}

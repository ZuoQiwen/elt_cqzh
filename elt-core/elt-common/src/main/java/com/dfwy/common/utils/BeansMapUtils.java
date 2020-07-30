package com.dfwy.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *@className BeansUtils
 *@description :
 *@author zuoqiwen 
 *@date 2020/7/2 18:59
 *@version V1.0
 **/
public class BeansMapUtils {
    /**
     * @description : listmap 复制
     * @author zuoqiwen
     * @date 2020/7/2 18:59
     * @param [list] 
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public static List<Map<String,String>> copy(List<Map<String,String>> list){
        if(!CollectionUtils.isEmpty(list)){
            List<Map<String,String>> target =  new ArrayList<>();
            list.forEach(x->{
                Map<String,String> item = new HashMap<>();
                item.putAll(x);
                target.add(item);
            });
            return target;
        }
        return list;
    }
    public static Map<String,String> copy(Map<String,String> map){
        if(!CollectionUtils.isEmpty(map)){
            Map<String,String> item = new HashMap<>();
            item.putAll(map);
            return item;
        }
        return map;
    }
 }

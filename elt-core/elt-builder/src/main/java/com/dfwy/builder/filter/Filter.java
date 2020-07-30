package com.dfwy.builder.filter;


/**
 *@className Filter
 *@description : 原始表数据过滤
 *@author zuoqiwen 
 *@date 2020/6/17 13:44
 *@version V1.0
 **/
public interface Filter {
    /**
     * @description : 规则过滤
     * @author zuoqiwen
     * @date 2020/6/17 15:57
     * @param [ value 数据置，compare比较值]
     * @return boolean
     */
    boolean filter(String value , Object compare);
}

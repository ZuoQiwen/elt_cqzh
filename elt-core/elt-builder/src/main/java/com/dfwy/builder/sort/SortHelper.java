package com.dfwy.builder.sort;


import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 *@className SortHelper
 *@description : 排序工具类
 *@author zuoqiwen
 *@date 2020/7/24 10:16
 *@version V1.0
 **/
@Slf4j
public class SortHelper {

    public static void sort(List<Map<String,String>> table,String rules){
        if(CollectionUtils.isEmpty(table)){
            return;
        }
        Assert.notNull(rules, "排序规则不能为空");
        List<SortRule> ruleList = init(rules);
        TableSortHandler.sort(table,ruleList);
    }

    private static List<SortRule> init(String rules) {
        List<SortRule> ruleList = new ArrayList<>();
        String [] sortList = rules.split(ELTConstant.COMMA);
        log.info("[排序规则初始化]排序规则："+ Arrays.asList(sortList));
        String  item =null;
        for (int i = sortList.length - 1; i >= 0; i--) {
            String [] sort = split(sortList[i]);
            item = StringUtil.isEmpty(sort[1])?item:sort[1];
            ruleList.add(new SortRule().setItem(sort[0]).setRule(item));
        }
        Collections.reverse(ruleList);
        log.info("[排序规则初始化]结束："+ruleList);
        return ruleList;
    }
    /**
     * @description :  排序规则进行切分
     * @author zuoqiwen
     * @date 2020/7/24 10:34
     * @param [str] 
     * @return java.lang.String[]
     */
    private static String [] split(String str){
        Assert.notNull(str, "排序表达式不能为空");
        String [] array = new String[2];
        if(str.endsWith(ELTConstant.SORT_ASC)){
            array[0] = StringUtil.trim(str.substring(0,str.lastIndexOf(ELTConstant.SORT_ASC)));
            array[1] = ELTConstant.SORT_ASC;
        }else if(str.endsWith(ELTConstant.SORT_DESC)){
            array[0] = StringUtil.trim(str.substring(0,str.lastIndexOf(ELTConstant.SORT_DESC)));
            array[1] = ELTConstant.SORT_DESC;
        }else{
            array[0] = str;
            array[1] = null;
        }
        return array;
    }
}

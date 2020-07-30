package com.dfwy.builder.sort;

import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.ELTUtils;
import com.dfwy.common.utils.StringUtil;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *@className TableSortHandler
 *@description : 原始表排序
 *@author zuoqiwen
 *@date 2020/7/24 10:44
 *@version V1.0
 **/
public class TableSortHandler {

    public static void sort(List<Map<String,String>> table,List<SortRule> rules){
        Assert.notEmpty(rules, "排序规则不能为空");
        table.sort((Map<String, String> var1, Map<String, String> var2)->{
            int i=0;
            while(i<rules.size()) {
                String item = rules.get(i).getItem();
                int compareResult = ELTUtils.compare(var1.get(item), var2.get(item));
                if(compareResult==0) {
                    i++;
                }else {
                    return ELTConstant.SORT_DESC.equals(rules.get(i).getRule())?-compareResult:compareResult;
                }
            }
            return 0;
        });
    }
}

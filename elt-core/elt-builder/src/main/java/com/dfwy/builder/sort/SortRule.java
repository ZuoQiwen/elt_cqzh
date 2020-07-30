package com.dfwy.builder.sort;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *@className SortRule
 *@description : 排序规则
 *@author zuoqiwen
 *@date 2020/7/24 10:08
 *@version V1.0
 **/
@Data
@Accessors(chain = true)
public class SortRule {
    private String item;
    private String rule;





}

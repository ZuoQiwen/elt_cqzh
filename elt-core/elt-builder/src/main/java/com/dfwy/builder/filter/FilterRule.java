package com.dfwy.builder.filter;

import com.dfwy.common.utils.ELTConstant;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className FilterRule
 * @description : 过滤规则
 * @date 2020/6/17 15:53
 **/
@Data
public class FilterRule {
    /**
     * 过滤规则的值
     */
    private Object value;
    /**
     * 过滤规则
     */
    private String rule;
    /**
     * 过滤字段
     */
    private String field;

    public FilterRule(String field, String value, String rule) {
        if (ELTConstant.FILTER_NOT_IN.equals(rule) || ELTConstant.FILTER_IN.equals(rule)) {
            this.value = Arrays.stream(value.replace("(","").replace(")","").split(","))
                    .collect(Collectors.toSet());
        } else {
            this.value = value;
        }
        this.rule = rule;
        this.field = field;
    }

    private FilterRule() {
    }
}

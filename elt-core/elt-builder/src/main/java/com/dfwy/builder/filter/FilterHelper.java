package com.dfwy.builder.filter;

import com.dfwy.common.utils.ELTConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

@Component
public class FilterHelper {
    /**
     * 规则定义  此处的规则中子集需要放在后边，即in需要放到not in后边，
     * 有匹配到后进行下一次数据匹配，否则会造成匹配规则错误
     */
    private final String[] FILTER_RULES =
            new String[]{
                    ELTConstant.FILTER_NOT_EQUALS, ELTConstant.FILTER_EQUALS,
                    ELTConstant.FILTER_NOT_IN, ELTConstant.FILTER_IN,
                    ELTConstant.FILTER_GREATER_THAN, ELTConstant.FILTER_LESS_THAN,
                    ELTConstant.FILTER_IS_NOT_NULL, ELTConstant.FILTER_IS_NULL
            };

    @Autowired
    private Map<String, Filter> filters = new HashMap<>();

    /**
     * @param [rule 过滤规则, item]
     * @return boolean
     * @description : 表级别数据过滤，解析过滤规则，进行匹配，返回匹配结果
     * @author zuoqiwen
     * @date 2020/6/17 16:12
     */
    public boolean filter(List<FilterRule> rules, Map<String, String> item) {
        for (FilterRule rule : rules) {
            boolean flag = filters.get(rule.getRule()).filter(item.get(rule.getField()), rule.getValue());
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param [rule]
     * @return java.util.List<com.dfwy.builder.filter.FilterRule>
     * @description : 初始化过滤规则
     * @author zuoqiwen
     * @date 2020/6/17 16:29
     */
    public List<FilterRule> init(String rules) {
        Assert.notNull(rules, "filter rule can not be null");
        List<FilterRule> ruleList = new ArrayList<>();
        for (String rule : rules.trim().replace(" ", "").toUpperCase().split(ELTConstant.FILTER_AND)) {
            loop:
            for (String item : FILTER_RULES) {
                if (rule.indexOf(item) > -1) {
                    String[] array = rule.split(item);
                    ruleList.add(new FilterRule(array[0], array.length > 1 ? array[1] : "", item));
                    break loop;
                }
            }
        }
        return ruleList;
    }

}

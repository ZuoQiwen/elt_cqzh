package com.dfwy.builder.filter;


import com.dfwy.common.utils.ELTConstant;

import java.util.Arrays;

public class FilterHelperTest  {

    public static void main(String[] args) {
        String rule = " field1=22 and field2>2 and field3<4 and filed4 not in (1,2,3,4) and filed5 in (222,sd) and " +
                "field6 is null and field7 is not null and field8 !=222";
        System.out.println(Arrays.asList(rule.trim().replace(" ","").toUpperCase().split(ELTConstant.FILTER_AND)));
        System.out.println(new FilterHelper().init(rule));
    }
}
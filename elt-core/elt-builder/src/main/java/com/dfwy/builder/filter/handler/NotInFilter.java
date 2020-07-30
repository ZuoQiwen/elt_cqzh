package com.dfwy.builder.filter.handler;

import com.dfwy.builder.filter.Filter;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component(ELTConstant.FILTER_NOT_IN)
public class NotInFilter implements Filter {
    @Override
    public boolean filter(String value , Object compare) {
        return !((Set<String>)compare).contains(value);
    }
}

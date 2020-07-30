package com.dfwy.builder.filter.handler;

import com.dfwy.builder.filter.Filter;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.stereotype.Component;

@Component(ELTConstant.FILTER_IS_NULL)
public class IsNullFilter implements Filter {
    @Override
    public boolean filter(String value , Object compare) {
        return value==null;
    }
}

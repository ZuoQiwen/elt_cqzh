package com.dfwy.builder.filter.handler;

import com.dfwy.builder.filter.Filter;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.stereotype.Component;

@Component(ELTConstant.FILTER_IS_NOT_NULL)
public class IsNotNullFilter implements Filter {
    @Override
    public boolean filter(String value , Object compare) {
        return value!=null;
    }
}

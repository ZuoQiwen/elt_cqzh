package com.dfwy.builder.filter.handler;

import com.dfwy.builder.filter.Filter;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.ELTUtils;
import org.springframework.stereotype.Component;

@Component(ELTConstant.FILTER_GREATER_THAN)
public class GreaterFilter implements Filter {
    @Override
    public boolean filter(String value , Object compare) {
        return ELTUtils.compare(value,String.valueOf(compare))>0;
    }
}

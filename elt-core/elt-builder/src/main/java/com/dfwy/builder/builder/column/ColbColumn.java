package com.dfwy.builder.builder.column;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.stereotype.Component;


@Component(ELTConstant.COLUMN_COLB)
public class ColbColumn implements Column {
    @Override
    public String build(ELTColumn eltColumn, String value) {
        return value;
    }
}

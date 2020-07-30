package com.dfwy.builder.builder.column;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(ELTConstant.COLUMN_VARCHAR2)
public class VARCHAR2Column implements Column {
    @Autowired
    private DateColumn dateColumn;
    @Override
    public String build(ELTColumn eltColumn, String value) {
        return dateColumn.build(eltColumn,value);
    }
}

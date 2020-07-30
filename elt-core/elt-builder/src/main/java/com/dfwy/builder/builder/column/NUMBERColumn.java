package com.dfwy.builder.builder.column;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(ELTConstant.COLUMN_NUMBER)
public class NUMBERColumn implements Column {
    @Override
    public String build(ELTColumn eltColumn, String value) {

        if(!StringUtil.isEmpty(value)){
            Pattern patten = Pattern.compile(ELTConstant.NUMBER_PATTEN);
            Matcher matcher = patten.matcher(eltColumn.getBzbzdlx());
            if(matcher.matches()){
                //保留小数%.2f
               return String.format("%."+matcher.group(2)+"f",Double.parseDouble(value));
            }
        }
        return ELTConstant.ZERO;

    }


}

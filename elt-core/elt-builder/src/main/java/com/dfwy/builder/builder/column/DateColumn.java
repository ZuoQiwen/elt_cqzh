package com.dfwy.builder.builder.column;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
@Component(ELTConstant.COLUMN_DATE)
public class DateColumn implements Column {
    @Override
    public String build(ELTColumn eltColumn, String value) {
        if(!StringUtil.isEmpty(eltColumn.getYsbrqgs()) && !StringUtil.isEmpty(eltColumn.getBzbrqgs())
            && !eltColumn.getYsbrqgs().equals(eltColumn.getBzbrqgs()) && !StringUtil.isEmpty(value)){
            //原始表日期格式和标准表日期格式不一样  进行日格式转换
            try {
                return new SimpleDateFormat(eltColumn.getBzbrqgs()).format(new SimpleDateFormat(eltColumn.getYsbrqgs()).parse(value));
            } catch (ParseException e) {
                log.error("日期格式转换错误",eltColumn.toString()+",value :"+value);
                e.printStackTrace();
            }
        }
        return value;
    }
}

package com.dfwy.builder.builder.column;

import com.dfwy.common.utils.ELTConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ColumnFactory {
    @Autowired
    Map<String, Column> columns = new ConcurrentHashMap<>();
    public  Column getColumn(String type){
        if(type.startsWith(ELTConstant.COLUMN_NUMBER)){
            type = ELTConstant.COLUMN_NUMBER;
        }else if(type.startsWith(ELTConstant.COLUMN_VARCHAR2)||type.startsWith(ELTConstant.COLUMN_VARCHAR)){
            type = ELTConstant.COLUMN_VARCHAR2;
        }
        Column column =  columns.get(type);
        if(column==null){
            column = columns.get(ELTConstant.COLUMN_VARCHAR2);
            log.info("not cofigered column type default as "+type);
        }
        return column;
    }
}

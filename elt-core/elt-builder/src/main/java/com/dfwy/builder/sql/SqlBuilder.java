package com.dfwy.builder.sql;

import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@className SqlBuilder
 *@description : 批量入库sql生成
 *@author zuoqiwen 
 *@date 2020/6/11 15:50
 *@version V1.0
 **/
public interface SqlBuilder {
    /**
     * @description : 
     * @author zuoqiwen
     * @date 2020/6/11 16:02
     * @param [data 入库数据, eltTableConfig 字段配置项, singleSavecount 单次入库数量, type 类型  原始数据入库，标准表入库]
     * @return java.util.List<java.lang.String>
     */
    List<String> sql(Map<String, List<Map<String, String>>> data,
                     int singleSavecount,int type,String businessId);

    /**
     * @description :  当前表是否还有下一行
     * @author zuoqiwen
     * @date 2020/6/12 11:48
     * @param [rowCount, size]
     * @return boolean
     */
    default public boolean hasNextOne(int rowCount, int size){
        return rowCount<size;
    }


    default public String tablePrefix(int type){
        return type== ELTConstant.ORIGINAL?ELTConstant.ORIGINAL_TABLE_PREFIX:ELTConstant.STANDARD_TABLE_PREFIX;
    }


    default public String tableFields(Set<String> fields){
        StringBuffer sb = new StringBuffer();
        sb.append(ELTConstant.ID).append(",").append(ELTConstant.BUSINESSID).append(",")
            .append(StringUtil.join(fields, ","));
        return sb.toString().toUpperCase();
    }

}

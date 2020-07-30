package com.dfwy.builder.sql.oracle;

import com.dfwy.builder.config.ELTConfiger;
import com.dfwy.builder.sql.SqlBuilder;
import com.dfwy.common.configer.Snowflake;
import com.dfwy.common.utils.ClobUtils;
import com.dfwy.common.utils.ELTConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className OracleSqlBuilder
 * @description : oracle入库   需要额外对clob 进行处理
 * @date 2020/6/11 15:58
 *
 * DECLARE
 *         CLOB_FIELD1_1 CLOB := #{field1};
 *         CLOB_FIELD1_2 CLOB := #{field2};
 *           BEGIN
 *             insert into table(field1,field2)
 *             values(
 *                 CLOB_FIELD1_1,
 *                 CLOB_FIELD1_2
 *             );
 *         END;
 **/
@Component(ELTConstant.ORACLE)
@Slf4j
public class OracleSqlBuilder implements SqlBuilder {
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private ELTConfiger eltConfiger;

    @Override
    public List<String> sql(Map<String, List<Map<String, String>>> origalData,
                            int singleSavecount, int type,String businessId) {
        List<String> sqlList = new ArrayList<>();
        //当前已经转换的条数
        int current = 0;
        StringBuffer sb = new StringBuffer();
        StringBuffer declare = new StringBuffer();
        Set<String> clobFields = new HashSet<>();
        for (Map.Entry<String, List<Map<String, String>>> data : origalData.entrySet()) {
            //当前表的所有数据
            List<Map<String, String>> tables = data.getValue();
            if (!CollectionUtils.isEmpty(tables)) {
                String tableFields = tableFields(tables.get(0).keySet());
                sb.append("INSERT INTO ")/*.append(tablePrefix(type))*/.append(data.getKey().toUpperCase());
                clobFields = getClobFields(eltConfiger, data, type);
                //每一行数据
                int rowCount = 0;
                for (Map<String, String> row : tables) {
                    StringBuffer rowBuffer = new StringBuffer("");
                    rowBuffer.append("(")
                            .append(snowflake.nextId()).append(",")
                            .append("'").append(businessId).append("'");
                    //一行数据的值
                    for (Map.Entry<String, String> item : row.entrySet()) {
                        if(!CollectionUtils.isEmpty(clobFields) && clobFields.contains(item.getKey().toUpperCase())){
                            //clob字段
                            StringBuffer name = new StringBuffer();
                            name.append("CLOB_").append(item.getKey().toUpperCase()).append("_").append(current);

                            declare.append(name).append(" ").append(ELTConstant.COLUMN_COLB)
                                    .append(" ").append(":").append("=")
                                    .append(ClobUtils.setStrToClob(String.valueOf(item.getValue()))).append(";");
                            rowBuffer.append(name).append(",");
                        }else{
                            rowBuffer.append("'").append(String.valueOf(item.getValue())).append("'").append(",");
                        }
                    }
                    sb.append(rowBuffer.substring(0, rowBuffer.length() - 1)).append(")");
                    rowCount++;
                    if (++current == singleSavecount) {
                        sb.append(";");
                        current = 0;
                        sqlList.add(sqlEnd(sb, declare));
                        // 满足条数要求后需要重新初始化，但是需要判断是否当前数据为当前表格最后一条数据
                        sb = hasNextOne(rowCount,tables.size()) ? sqlInit(data, tableFields,type) : new StringBuffer("");
                        declare = new StringBuffer();
                    } else {
                        sb.append(hasNextOne(rowCount,tables.size())?",":";");
                    }
                }
            }
        }
        sqlList.add(sqlEnd(sb, declare));
        return sqlList;
    }

    /**
     * @param [sb, declare]
     * @return java.lang.String
     * @description : 含有clobsql结尾
     * @author zuoqiwen
     * @date 2020/6/12 10:12
     */
    public String sqlEnd(StringBuffer sb, StringBuffer declare) {
        return declare.length()!=0 ?
                declare.insert(0,"DECLARE ").append(" BEGIN ").append(sb.toString()).append(" END;").toString() :
                sb.toString();
    }

    /**
     * @param [data, tableFields]
     * @return java.lang.StringBuffer
     * @description : sql字符串拼接初始化
     * @author zuoqiwen
     * @date 2020/6/12 10:12
     */
    public StringBuffer sqlInit(Map.Entry<String, List<Map<String, String>>> data, String tableFields,int type) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ")/*.append(tablePrefix(type))*/.append(" ").append(data.getKey().toUpperCase());
        sb.append("(").append(tableFields).append(")").append("VALUES");
        return sb;
    }

    /**
     * @param [eltTableConfig, data,type 原始表/标准表]
     * @return java.util.Set<java.lang.String>
     * @description :  根据配置关系表获取当前表的clob字段
     * @author zuoqiwen
     * @date 2020/6/12 9:23
     */

    public Set<String> getClobFields(ELTConfiger eltConfiger, Map.Entry<String,
            List<Map<String, String>>> data, int type) {
        return eltConfiger.getClobColumn(data.getKey().toUpperCase(), type);

    }
}

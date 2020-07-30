package com.dfwy.builder.sql.mysql;

import com.dfwy.builder.sql.SqlBuilder;
import com.dfwy.common.configer.Snowflake;
import com.dfwy.common.utils.ELTConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zuoqiwen
 * @version V1.0
 * <p>
 * 输出sql类似 INSERT INTO TEST (id) VALUES(1),(2),(3),(4);
 * <p>
 * 把所有数据按照 singleSavecount 进行分组
 * @className MysqlSqlBuilder
 * @description :mysql数据入库    mysql数据不需要做额外处理，直接进行拼接返回
 * @date 2020/6/11 15:57
 **/
@Component(ELTConstant.MYSQL)
public class MysqlSqlBuilder implements SqlBuilder {
    @Autowired
    private Snowflake snowflake;


    public List<String> sql(Map<String, List<Map<String, String>>> origalData, int singleSavecount, int type,String businessId) {

        List<String> sqlList = new ArrayList<>();
        //当前已经转换的条数
        int current = 0;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, List<Map<String, String>>> data : origalData.entrySet()) {
            //当前表的所有数据
            List<Map<String, String>> tables = data.getValue();
            if (!CollectionUtils.isEmpty(tables)) {
                String tableFields = tableFields(tables.get(0).keySet());
                sb.append("INSERT INTO ")/*.append(tablePrefix(type))*/.append(data.getKey().trim().toUpperCase());
                sb.append("(").append(tableFields).append(")").append("VALUES");
                //每一行数据
                int rowCount = 0;
                for (Map<String, String> row : tables) {
                    StringBuffer rowBuffer = new StringBuffer("");
                    rowBuffer.append("(")
                            .append(snowflake.nextId()).append(",")
                            .append("'").append(businessId).append("'").append(",");
                    //一行数据的值
                    for (Map.Entry<String, String> item : row.entrySet()) {
                        rowBuffer.append("'").append(String.valueOf(item.getValue())).append("'").append(",");
                    }
                    sb.append(rowBuffer.substring(0, rowBuffer.length() - 1)).append(")");
                    rowCount++;
                    if (++current == singleSavecount) {
                        sb.append(";");
                        current = 0;
                        sqlList.add(sb.toString());
                        //不是当前表的最后一条数据
                        if (rowCount != tables.size()) {
                            sb = new StringBuffer("INSERT INTO ")/*.append(tablePrefix(type))*/.append(data.getKey().toUpperCase())
                                    .append("(").append(tableFields).append(")").append("VALUES");
                        } else {
                            sb = new StringBuffer("");
                        }
                    } else {
                        sb.append(hasNextOne(rowCount, tables.size()) ? "," : ";");

                    }
                }
            }
        }
        sqlList.add(sb.toString());
        return sqlList;
    }
}

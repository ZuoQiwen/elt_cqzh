package com.dfwy.builder.builder.column;

import com.dfwy.builder.operation.EvalOperationUtils;
import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.ELTUtils;
import com.dfwy.common.utils.StringUtil;
import org.springframework.util.CollectionUtils;
import java.util.Map;
public interface Column {


    /**
     * 字段映射处理
     * @param eltColumn  原始表字段映射关系
     * @param value  原始表字段值
     * @return
     */
    String build(ELTColumn eltColumn, String value);

    /**
     * 字段值映射
     * @param eltColumn
     * @param value
     * @return
     */
    default String execute(ELTColumn eltColumn,Map<String,String> code, Map<String, String> map){
        return build(eltColumn,valueBuild(eltColumn,code, map));
    }

    /**
     * 行数据处理
     * @param eltColumn
     * @param value
     * @return
     */
    default String valueBuild(ELTColumn eltColumn,Map<String,String> code, Map<String, String> map) {
        String value = EvalOperationUtils.eval(eltColumn,map);
        if(!StringUtil.isEmpty(value)){
            //是否清除逗号
            if(ELTUtils.isTrue(eltColumn.getSfscdh()) && value.endsWith(ELTConstant.COMMA)){
                value = !StringUtil.isEmpty(value)?value.substring(0,value.length()-1):value;
            }
            //是否清除百分号
            if(ELTUtils.isTrue(eltColumn.getSfqcbfh()) && value.endsWith(ELTConstant.PERCENT)){
                value = !StringUtil.isEmpty(value)?value.substring(0,value.length()-1):value;
            }
            //是否转换万元
            if(ELTUtils.isTrue(eltColumn.getSfzhwy()) &&ELTUtils.biggerThan(value,ELTConstant.WANYUAN)){
                value = !StringUtil.isEmpty(value)?String.valueOf(Double.parseDouble(value)/10000):value;
            }
            //代码转换表
            if(ELTUtils.isTrue(eltColumn.getSfdmzh()) && !StringUtil.isEmpty(eltColumn.getDmzhb()) && !CollectionUtils.isEmpty(code)){
                value = code.getOrDefault(value.trim(),"");
            }
        }else{
            //为空默认值设置
            return StringUtil.isEmpty(eltColumn.getMrz())?value:eltColumn.getMrz();
        }
        return value;
    }
}

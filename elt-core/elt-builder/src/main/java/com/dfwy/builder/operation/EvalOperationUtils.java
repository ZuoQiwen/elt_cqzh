package com.dfwy.builder.operation;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.MathUtil;
import com.dfwy.common.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

@Slf4j
public class EvalOperationUtils {
    /**
     * 计算值为无线  至为空
     */
    public static final String INFINITY = "Infinity";

    /**
     * @param [eltColumn, map]
     * @return java.lang.String
     * @description :  表达式计算
     * @author zuoqiwen
     * @date 2020/7/15 15:31
     */
    public static String eval(ELTColumn eltColumn, Map<String, String> map) {
        //原始表字段不为空  并且原始表中没有该字段判断当前为表达式
        String ysbzd = eltColumn.getYsbzd();
        if (!StringUtil.isEmpty(ysbzd) && !map.containsKey(ysbzd)) {
            log.info("当前节点参数为表达式，原始表{}【{}】表达式：{}", eltColumn.getBzb(), eltColumn.getBzbzd(), eltColumn.getYsbzd());
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            map.forEach((x, y) -> {
                if (ysbzd.contains(x)) {
                    engine.put(x, eltColumn.getBzbzdlx().startsWith(ELTConstant.COLUMN_NUMBER) ?
                            MathUtil.parseDouble(y) : y);
                }
            });
            String value = null;
            try {
                value = String.valueOf(engine.eval(ysbzd));
                log.info("节点参数赋值表达式：{}，赋值{}，计算结果：{}" ,
                        new ObjectMapper().writeValueAsString(engine.getBindings(ScriptContext.ENGINE_SCOPE)),ysbzd,value );
            } catch (ScriptException | JsonProcessingException e) {
                log.error("eval计算表达式异常"+eltColumn);
            }
            return INFINITY.equals(value) ? null : value;
        }
        return map.get(ysbzd);
    }

    public static void main(String[] args) throws ScriptException, JsonProcessingException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.put("a", 1);
        engine.put("b", 0);
        System.out.println(engine.eval("a/b"));
        System.out.println(engine.eval("if(a>b){'0'}"));

    }
}

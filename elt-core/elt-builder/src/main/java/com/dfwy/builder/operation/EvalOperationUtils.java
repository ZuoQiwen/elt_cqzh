package com.dfwy.builder.operation;

import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.MathUtil;
import com.dfwy.common.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EvalOperationUtils {
    /**
     * 计算值为无线  至为空
     */
    public static final String INFINITY = "Infinity";

    private static Map<String, Expression> compiledScripts = new ConcurrentHashMap();


    /**
     * @param [eltColumn, map]
     * @return java.lang.String
     * @description :  表达式计算
     * @author zuoqiwen
     * @date 2020/7/15 15:31
     */
    public static String eval(ELTColumn eltColumn, Map<String, String> map){
        //原始表字段不为空  并且原始表中没有该字段判断当前为表达式
        String ysbzd = eltColumn.getYsbzd();
        if (!StringUtil.isEmpty(ysbzd) && !map.containsKey(ysbzd)) {
            log.debug("当前节点参数为表达式，原始表{}【{}】表达式：{}", eltColumn.getBzb(), eltColumn.getBzbzd(), eltColumn.getYsbzd());
            String value = null;
            try {
                Map param = new HashMap();
                map.forEach((x, y) -> {
                    if (ysbzd.contains(x)) {
                        param.put(x, eltColumn.getBzbzdlx().startsWith(ELTConstant.COLUMN_NUMBER) ?
                                MathUtil.parseDouble(y) : y);
                    }
                });
                value = String.valueOf(getCompiledExpression(ysbzd).execute(param));
                log.debug("节点参数赋值表达式：{}，赋值{}，计算结果：{}" ,ysbzd,param,value );
            } catch (ScriptException e) {
                log.error("eval计算表达式异常"+eltColumn);
            }
            return INFINITY.equals(value) ? null : value;
        }
        return map.get(ysbzd);
    }

    public static Expression getCompiledExpression(String ysbzd) throws ScriptException {
        if(compiledScripts.containsKey(ysbzd)){
            return compiledScripts.get(ysbzd);
        }else{
            Expression expression = AviatorEvaluator.compile(ysbzd);
            compiledScripts.put(ysbzd,expression);
            return expression;
        }
    }
    public static void main(String[] args) throws ScriptException, JsonProcessingException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.put("a", 1);
        engine.put("b", 2);
        engine.put("NSRZGLX", "一般纳税人121");
        engine.put("TZBL", 12);

        System.out.println(engine.eval("a/b"));
        System.out.println(engine.eval("if(NSRZGLX.indexOf('一般纳税人')>0){'2'}else{ '1'}"));
        System.out.println(engine.eval("TZBL*100+'%'"));
        Map map = new HashMap<>();
        map.put("a","一般纳税人121");
        map.put("b",Double.parseDouble("1"));
        map.put("c",2);

        System.out.println(AviatorEvaluator.compile("if(string.indexOf(a,'一般纳税人')>0){'2'}else{ '1'}").execute(map));
        System.out.println(AviatorEvaluator.compile("b/c").execute(map));

    }
}

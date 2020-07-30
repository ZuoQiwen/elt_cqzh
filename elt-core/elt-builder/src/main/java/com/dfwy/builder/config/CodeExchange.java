package com.dfwy.builder.config;

import com.dfwy.common.domain.Code;
import com.dfwy.common.utils.ELTConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zuoqiwen
 * @version V1.0
 * @className DMExchange
 * @description : 代码转换表
 * @date 2020/6/10 10:46
 **/
@Slf4j
public class CodeExchange {
    private Map<String, Map<Integer, Map<String, String>>> exchange = new ConcurrentHashMap();


    public CodeExchange(List<Code> dms) {
        if (!CollectionUtils.isEmpty(dms)) {
            //数据字典表根据标准表进行分组
            Map<String, List<Code>> group = dms.stream().collect(Collectors.groupingBy(Code::getBzb));
            group.forEach((x, y) -> {
                Map<Integer, Map<String, String>> item = new HashMap<>();
                Map<String, String> itemCode2Desption = y.stream()
                        .collect(Collectors.toMap(Code::getMzmc, Code::getMz, (o1, o2) -> o1));
                Map<String, String> itemDesption2Code = y.stream()
                        .collect(Collectors.toMap(Code::getMz, Code::getMzmc, (o1, o2) -> o1));
                item.put(ELTConstant.DESPTION2CODE, itemDesption2Code);
                item.put(ELTConstant.CODE2DESPTION, itemCode2Desption);
                exchange.put(x, item);
            });
        }
    }

    public Map<Integer, Map<String, String>> getCodeExchage(String tableName) {
        Assert.notNull(tableName);
        return exchange.get(tableName);
    }
    public Map<String, Map<Integer, Map<String, String>>> getCodeExchage() {
        return exchange;
    }
}

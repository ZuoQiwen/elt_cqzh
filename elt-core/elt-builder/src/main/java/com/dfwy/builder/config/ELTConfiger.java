package com.dfwy.builder.config;

import com.dfwy.common.domain.Code;
import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.domain.ELTTable;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.database.mapper.ELTMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@Data
public class ELTConfiger {

    @Autowired
    private  ELTMapper eltlMapper;
    @Autowired
    private  ObjectMapper objectMapper;

    /**
     * key  报文节点,表配置
     */
    private Map<String, ELTTable> eltTable = new ConcurrentHashMap();
    /**
     * 原始表和报文节点转换
     */
    private Map<String, String> ysbBwjdExchageMap = new ConcurrentHashMap();
    /**
     * 代码转换表
     */
    private CodeExchange exchange = null;


    private Map<String,List<ELTColumn>> clobCllumn;
    /**
     * 数据节点初始化
     */
    @SneakyThrows
    @PostConstruct
    public void init() {
        List<ELTTable> tables = eltlMapper.selectAllELTTables();
        List<ELTColumn> columns = eltlMapper.selectAllColumns();
        //原始表报文节点映射关系，如果数据是从报文中读取的，需要该映射关系进行转换
        ysbBwjdExchageMap =
                tables.stream().filter(x->!StringUtil.isEmpty(x.getBwjd())).collect(Collectors.toMap(ELTTable::getBwjd,
                        ELTTable::getYsb,(v1,v2)->v2));
        log.info("elt ysbBwjdExchageMap init success :\n" + objectMapper.writeValueAsString(ysbBwjdExchageMap));

        //根据标准表进行分组 key为标准表  value为标准表对应的字段映射
        Map<String, List<ELTColumn>> columnGroup =
                columns.stream().filter(x->!StringUtil.isEmpty(x.getYsb())).collect(Collectors.groupingBy(ELTColumn::getBzb));
        eltTable = tables.stream().filter(x->!StringUtil.isEmpty(x.getBzb())&&!StringUtil.isEmpty(x.getYsb())).map(x -> {
            List<ELTColumn> columnList = columnGroup.get(x.getBzb());
            if(!CollectionUtils.isEmpty(columnList)){
                //一个标准表可能有多个原始表映射
                x.setYsb(columnList.stream().map(y->y.getYsb()).collect(Collectors.toSet()).stream().collect(Collectors.joining(",")));
                x.setEltColumnList(columnList);
            }
            return x;
            //此处根据bzb进行tomap  当一个标准表对应多个原始表时同样不影响结果(value是重复的，可以取任意一个)
        }).collect(Collectors.toMap(ELTTable::getBzb, Function.identity(),(o1,o2)->o1));

        log.info("elt configer init success :\n" + objectMapper.writeValueAsString(eltTable));
        List<Code> dms = eltlMapper.selectAllCodes();

        exchange = new CodeExchange(dms);
        log.info("code exchange init success :\n" + objectMapper.writeValueAsString(exchange.getCodeExchage()));
        tables = null;
        columns = null;
        dms = null;
    }

    /**
     * 获取映射表关系
     * @param tableName
     * @return
     */
    public ELTTable getELTTable(String tableName) {
        Assert.notNull(tableName, "tableName can not be empty");
        ELTTable table = eltTable.get(tableName);
        if (Objects.isNull(table)) {
            log.error("table init error ：" + tableName + ", please add configeration in elt_table and reload configeration");
        }
        return table;
    }

    /**
     * 获取当前表的代码转换表
     * @param eltColumn
     * @return
     */
    public  Map<Integer,Map<String,String>> getCodeExchange(String tableName) {
        Assert.notNull(tableName, "tableName can not be empty");
        return exchange.getCodeExchage(tableName);
    }

    public Set<String> getClobColumn(String name,int type){
        if(ELTConstant.ORIGINAL == type){
            Map<String,List<ELTColumn>>  clobCllumn =
                    eltTable.values().stream().map(x->x.getEltColumnList()).flatMap(x->x.stream()).
                            filter(x-> ELTConstant.COLUMN_COLB.equalsIgnoreCase(x.getYsbzdlx())).collect(Collectors.groupingBy(ELTColumn::getYsb));
            return clobCllumn.get(name).stream().map(x->x.getYsbzd()).collect(Collectors.toSet());
        }else if(ELTConstant.STANDARD == type){
            Map<String,List<ELTColumn>>  clobCllumn =
                    eltTable.values().stream().map(x->x.getEltColumnList()).flatMap(x->x.stream()).
                            filter(x-> ELTConstant.COLUMN_COLB.equalsIgnoreCase(x.getBzbzdlx())).collect(Collectors.groupingBy(ELTColumn::getBzb));
            return clobCllumn.get(name).stream().map(x->x.getBzb()).collect(Collectors.toSet());
        }else{
            throw new RuntimeException("save database type error, expect 0,1,but "+type);
        }
    }
}

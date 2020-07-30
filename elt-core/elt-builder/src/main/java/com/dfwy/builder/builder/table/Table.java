package com.dfwy.builder.builder.table;

import com.dfwy.builder.builder.ELTBuilder;
import com.dfwy.builder.builder.column.Column;
import com.dfwy.builder.builder.column.ColumnFactory;
import com.dfwy.builder.config.EltBuilderHelper;
import com.dfwy.builder.exception.ELTTableMergeException;
import com.dfwy.builder.filter.FilterHelper;
import com.dfwy.builder.filter.FilterRule;
import com.dfwy.builder.sort.SortHelper;
import com.dfwy.common.domain.ELTColumn;
import com.dfwy.common.domain.ELTTable;
import com.dfwy.common.utils.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

/**
 * @ClassName Table
 * @Description 原始数据单表处理
 * @Author zuoqiwen
 * @Date 2020/6/4 23:03
 * @Since 1.8
 * @Version 1.0
 */
@Slf4j
public class Table {
    /**
     * 原始数据单表
     */
    private List<Map<String, String>> original;
    /**
     * 标准表数据单表
     */
    private List<Map<String, String>> standard;
    /**
     * 映射表
     */
    private ELTTable item;
    /**
     * 代码表
     */
    private Map<Integer, Map<String, String>> exchange;
    private EltBuilderHelper eltBuilderHelper;

    public Table(ELTTable item, Map<Integer, Map<String, String>> exchange,
                 Map<String, List<Map<String, String>>> original,
                 EltBuilderHelper eltBuilderHelper) {
        /**
         *  当前标准表是否是由单个原始表映射而成的，如果是由多个表组成则需要在此处进行关联
         *  类似left join 此处只考虑单节点数据的关联，即有仅有一个原始表的数据行数可以大于1
         */
        this.item = item;
        this.exchange = exchange;
        this.eltBuilderHelper = eltBuilderHelper;
        if (!item.isSingleNode()) {
            this.original = BeansMapUtils.copy(originalData(original,item.getYsb()));
        } else {
            this.original = merge(item, original);
        }

    }

    public List<Map<String,String>> originalData(Map<String, List<Map<String, String>>> original,String ysb){
        List<Map<String, String>> data = original.getOrDefault(ysb,new ArrayList<>());
        eltBuilderHelper.getOriginalTransformHelper().transform(original,ysb);
        return data;
    }
    /**
     * @description : 一个标准表对应多个原始表时  多个表数据进行合并处理
     * 获取数据量最多的表，依次添加其他表的数据
     * @author zuoqiwen
     * @date 2020/6/16 18:53
     * @param [item, original]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    private List<Map<String, String>> merge(ELTTable item, Map<String, List<Map<String, String>>> original) {
        String[] ysbs = item.getYsb().split(",");
        int count = 0;
        //数据最大项
        int index = 0;
        for (int i = 0; i < ysbs.length; i++) {
            int currentSize = original.getOrDefault(ysbs[i], new ArrayList<>()).size();
            if (currentSize > 1) {
                count++;
                index = i;
            }
        }
        if (count > 1) {
            //多个节点数据项大于1，无法正常匹配
            String errorMessage = "存在一个标注表节点配置多个原始表节点，并且原始表数据节点数量都大于1";
            log.error(errorMessage);
            throw new ELTTableMergeException(errorMessage);
        }
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        //最长的数据
        List<Map<String, String>> before = originalData(original,ysbs[index]);
        for (Map<String, String> current : before) {
            Map<String, String> map = new HashMap<>();
            /**
             * 此处需要进行原始数据拷贝，直接merge会造成源数据污染，影响原始数据入库
             */
            map.putAll(current);
            for (int i = 0; i < ysbs.length; i++) {
                if (i == index) {
                    continue;
                }
                List<Map<String, String>> after = original.get(ysbs[i]);
                if (!CollectionUtils.isEmpty(after) && after.size() == 1) {
                    map.putAll(after.get(0));
                }
            }
            result.add(map);
        }
        return result;

    }
    /**
     * @description : 表级别数据处理
     * @author zuoqiwen
     * @date 2020/6/17 14:38
     * @param []
     * @return com.dfwy.builder.builder.table.Table
     */
    public Table build() {
        //表级别数据过滤
        if (ELTUtils.isTrue(this.item.getSfgl()) && !StringUtil.isEmpty(item.getGltj())) {
            FilterHelper filterHelper = this.eltBuilderHelper.getFilterHelper();
            List<FilterRule> rules = filterHelper.init(this.item.getGltj());
            log.debug("[FILTER]表级别数据处理:" + item.getGltj()+", 过滤规则："+rules);
            this.original = original.stream().filter(x->filterHelper.filter(rules,x)).collect(Collectors.toList());
        }
        if (ELTUtils.isTrue(this.item.getSfqc()) && !StringUtil.isEmpty(item.getQczd())) {
            log.debug("[DISTINCT]表级别数据处理:" + item.getQczd());
            this.original = ELTUtils.distinct(original, item.getQczd().split(","));
        }
        if(ELTUtils.isTrue(this.item.getSfpx()) && !StringUtil.isEmpty(item.getPxzd())){
            log.debug("[ORDER]表级别数据处理:" + item.getPxzd());
            SortHelper.sort(this.original,item.getPxzd());
        }
        return this;
    }

    /**
     * 单表数据处理，返回标准表结构化数据
     *
     * @return
     */
    public List<Map<String, String>> execute() {
        ColumnFactory columnFactory = SpringUtils.getBean(ColumnFactory.class);
        standard = new ArrayList<>();
        for (Map<String, String> map : original) {
            Map<String, String> resultColumn = new HashMap<>();
            if (!CollectionUtils.isEmpty(item.getEltColumnList())) {
                for (ELTColumn eltColumn : item.getEltColumnList()) {
                    Column column = columnFactory.getColumn(eltColumn.getBzbzdlx());
                    String value = column.execute(eltColumn, exchange == null ? null : exchange.get(eltColumn.getSfdmzh()),
                            map);
                    resultColumn.put(eltColumn.getBzbzd(), value);
                }
                standard.add(resultColumn);
            }

        }
        return standard;
    }

    /**
     * 根据原始表获取原始数据，判断是否属于字段组合并进行组装
     *
     * @param map
     * @param eltColumn
     * @return
     */
    private String value(Map<String, String> map, ELTColumn eltColumn) {
        String ysbzd = eltColumn.getYsbzd();
        if (!StringUtil.isEmpty(ysbzd)) {
            StringBuffer sb = new StringBuffer();
            if (ysbzd.indexOf(ELTConstant.FIELD_SEPARATOR) > 0) {
                for (String s : ysbzd.split(ELTConstant.FIELD_SEPARATOR)) {
                    sb.append(map.getOrDefault(s, ""))/*.append(ELTConstant.FIELD_SEPARATOR)*/;
                }
                return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString();
            } else {
                return map.getOrDefault(ysbzd, "");
            }
        }
        return "";
    }


    public List<Map<String, String>> sort(List<Map<String, String>> data) {
        Collections.sort(data, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return 0;
            }
        });
        return data;
    }
}

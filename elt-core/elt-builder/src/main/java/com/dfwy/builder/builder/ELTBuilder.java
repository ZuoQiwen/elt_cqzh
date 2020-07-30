package com.dfwy.builder.builder;

import com.dfwy.builder.builder.table.Table;
import com.dfwy.builder.config.ELTConfiger;
import com.dfwy.builder.config.EltBuilderHelper;
import com.dfwy.builder.exception.ELTTableMergeException;
import com.dfwy.builder.sql.SqlBuilder;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.SpringUtils;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.database.configer.BuilderConfiger;
import com.dfwy.database.service.BuildDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/* *
 * @ClassName ELTBuilder
 * @Description elt数据处理
 * @Author zuoqiwen
 * @Date 2020/6/4 22:42
 * @Since 1.8
 * @Version 1.0
 */
@Slf4j
public class ELTBuilder {
    /**
     * 原始数据
     */
    private Map<String, List<Map<String, String>>> origalData;

    /**
     * 标准表数据
     */
    private Map<String, List<Map<String, String>>> buildedData = new LinkedHashMap<>();
    /**
     * 数据是否处理
     */
    private boolean builded;
    private String businessId;
    private EltBuilderHelper eltBuilderHelper;


    public ELTBuilder(Map<String, List<Map<String, String>>> data, String businessId,
                      EltBuilderHelper eltBuilderHelper) {
        this.origalData = data;
        this.businessId = businessId;
        this.eltBuilderHelper = eltBuilderHelper;
    }

    /**
     * @param
     * @return void
     * @description : 数据处理入口
     * @author zuoqiwen
     * @date 2020/6/15 10:51
     */
    public void build() {
        //原始数据入库
        saveOriginalData();
        //数据处理 生成标准数据
        buildData();
        //标准表入库
        saveStandardData();
    }

    /**
     * @param
     * @return java.util.Map<java.lang.String, java.util.List < java.util.Map < java.lang.String, java.lang.String>>>
     * @description : 数据处理入口
     * @author zuoqiwen
     * @date 2020/6/01 15:57
     */
    public Map<String, List<Map<String, String>>> buildData() {
        if (builded) {
            return buildedData;
        }
        ELTConfiger eltConfiger = this.eltBuilderHelper.getEltConfiger();
        eltConfiger.getEltTable().forEach((bzb, item) -> {
            //获取代码转换表的代码表
            if (!Objects.isNull(item) && !StringUtil.isEmpty(item.getBzb())) {
                Map<Integer, Map<String, String>> exchange = eltConfiger.getCodeExchange(item.getBzb());
                Table table = new Table(item, exchange, origalData,this.eltBuilderHelper).build();
                List<Map<String, String>> values = table.execute();
                buildedData.put(item.getBzb(), values);
            }
        });
        builded = true;
        return buildedData;
    }

    /**
     * @param [before, values]
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @description :  一个标准表对应多个数据节点
     * @author zuoqiwen
     * @date 2020/6/15 17:32
     */
    private List<Map<String, String>> merge(List<Map<String, String>> before, List<Map<String, String>> values) {
        if (CollectionUtils.isEmpty(before)) {
            return values;
        }
        if (CollectionUtils.isEmpty(values)) {
            return before;
        }
        if (before.size() > 1 && values.size() > 1) {
            throw new ELTTableMergeException("存在一个标注表节点配置多个原始表节点，并且原始表数据节点数量都大于1");
        }
        if (before.size() >= values.size()) {
            before.forEach(x -> x.putAll(values.get(0)));
            return before;
        }
        if (before.size() < values.size()) {
            values.forEach(x -> x.putAll(before.get(0)));
            return values;
        }
        //不会走到这里
        return before;
    }

    /**
     * @param []
     * @return java.util.List<java.lang.String>
     * @description : 获取原始表入库sql，每次执行同一个表的sql，每次执行数量受数据库限制
     * @author zuoqiwen
     * @date 2020/6/10 15:58
     */
    private List<String> getOriginalSql() {
        return getSql(ELTConstant.ORIGINAL);
    }

    /**
     * @param []
     * @return java.util.List<java.lang.String>
     * @description : 获取标准表入库sql，每次执行同一个表的sql，每次执行数量受数据库限制
     * @author zuoqiwen
     * @date 2020/6/10 15:58
     */
    private List<String> getStandardSql() {
        if (!builded) {
            buildData();
        }
        return getSql(ELTConstant.STANDARD);
    }

    private List<String> getSql(int type) {
        SqlBuilder sqlBuilder = this.eltBuilderHelper.getSqlBuilderFactory().getSqlBuilder();
        List<String> sql =
                sqlBuilder.sql(ELTConstant.ORIGINAL == type ? origalData : buildedData,
                        ELTConstant.ORIGINAL == type ?
                                eltBuilderHelper.getDataSourceConfiger().getOriginal().getSingleSavecount()
                                : eltBuilderHelper.getDataSourceConfiger().getStandard().getSingleSavecount(),
                        type, businessId);
        return sql;
    }

    /**
     * @param []
     * @return void
     * @description : 原始表数据入库
     * @author zuoqiwen
     * @date 2020/6/11 15:33
     */
    public void saveOriginalData() {
        BuilderConfiger dataSourceConfiger = this.eltBuilderHelper.getDataSourceConfiger();
        if (!dataSourceConfiger.getOriginal().saveTodatabase) {
            return;
        }
        List<String> sql = getOriginalSql();
        if (CollectionUtils.isEmpty(sql)) {
            return;
        }
        BuildDataService buildService = SpringUtils.getBean(BuildDataService.class);
        if (dataSourceConfiger.getOriginal().async) {
            buildService.saveOriginalAsyncData(sql);
        } else {
            buildService.saveOriginalData(sql);
        }
    }

    /**
     * @param []
     * @return void
     * @description : 标准表数据入库
     * @author zuoqiwen
     * @date 2020/6/11 15:34
     */
    public void saveStandardData() {
        BuilderConfiger dataSourceConfiger = this.eltBuilderHelper.getDataSourceConfiger();

        if (!dataSourceConfiger.getStandard().saveTodatabase) {
            return;
        }
        List<String> sql = getStandardSql();
        if (CollectionUtils.isEmpty(sql)) {
            return;
        }
        BuildDataService buildService = SpringUtils.getBean(BuildDataService.class);
        if (dataSourceConfiger.getStandard().async) {
            buildService.saveStandardAsyncData(sql);
        } else {
            buildService.saveStandardData(sql);
        }
    }
}

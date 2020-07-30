package com.dfwy.common.domain;

import com.dfwy.common.utils.ELTConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* *
 * @ClassName ELTTable
 * @Description 数据清洗表
 * @Author zuoqiwen
 * @Date 2020/6/2 23:03
 * @Since 1.8
 * @Version 1.0
 */
@Data
@Slf4j
public class ELTTable {
    /**
     * 原始表
     */
    private String ysb;
    /**
     * 报文几点名称
     */
    private String bwjd;
    /**
     * 是否去重
     */
    private int    sfqc;
    /**
     * 去重字段
     */
    private String qczd;
    /**
     * 是否过滤
     */
    private int    sfgl;
    /**
     * 过滤条件
     */
    private String gltj;
    /**
     * 是否排序
     */
    private int    sfpx;
    /**
     * 排序类型  0ASC,1DESC
     */
    private int    pxlx;
    /**
     * 排序字段
     */
    private String pxzd;
    /**
     * 标准表
     */
    private String bzb;
    /**
     * 字段映射表
     */
    private List<ELTColumn> eltColumnList ;

    /**
     * @description : 获取当前表的clob字段
     * @author zuoqiwen
     * @date 2020/6/12 9:23
     * @param []
     * @return java.util.Set<java.lang.String>
     */
    public Set<String> getClobFields(int type) {
        if(CollectionUtils.isEmpty(eltColumnList)){
            return null;
        }
        if(ELTConstant.ORIGINAL == type){
            return eltColumnList.stream().filter(x-> ELTConstant.COLUMN_COLB.equalsIgnoreCase(x.getYsbzdlx()))
                    .map(x->x.getYsbzd()).collect(Collectors.toSet());
        }else if(ELTConstant.STANDARD == type){
            return eltColumnList.stream().filter(x-> ELTConstant.COLUMN_COLB.equalsIgnoreCase(x.getBzbzdlx()))
                    .map(x->x.getBzbzd()).collect(Collectors.toSet());
        }else{
            throw new RuntimeException("save database type error, expect 0,1,but "+type);
        }
    }
    /**
     * @description : 是否是单节点
     * @author zuoqiwen
     * @date 2020/6/15 18:46
     * @param []
     * @return boolean
     */
    public boolean isSingleNode(){
        return this.ysb.indexOf(",")>-1;
    }

}

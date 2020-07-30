package com.dfwy.common.domain;

import lombok.Data;

import javax.script.ScriptEngine;

/* *
 * @ClassName ELTColumn
 * @Description  数据表字段映射
 * @Author zuoqiwen
 * @Date 2020/6/2 23:07
 * @Since 1.8
 * @Version 1.0
 */
@Data
public class ELTColumn {
    /**
     * 标准表
     */
    private String bzb;
    /**
     * 标准表字段
     */
    private String bzbzd;
    /**
     * 标准表字段注释
     */
    private String bzbzdzs;
    /**
     * 标准表字段类型
     */
    private String bzbzdlx;
    /**
     * 原始表
     */
    private String ysb;
    /**
     * 原始表字段
     */
    private String ysbzd;
    /**
     * 原始表字段注释
     */
    private String ysbzdzs;
    /**
     * 原始表字段类型
     */
    private String ysbzdlx;
    /**
     * 是否代码转换   0 不转换  1描述转码值 2码值转描述
     */
    private int    sfdmzh;
    /**
     * 代码转换表
     */
    private String dmzhb;
    /**
     * 默认值
     */
    private String mrz;
    /**
     * 日期格式
     */
    private String ysbrqgs;
    /**
     * 是否可为空
     */
    private int    sfkwk;
    /**
     *是否清空格
     */
    private int    sfqkg;
    /**
     *是否清逗号
     */
    private int    sfscdh;
    /**
     *是否清除百分号
     */
    private int    sfqcbfh;
    /**
     * 是否转换万元
     */
    private int    sfzhwy;

    /**
     * 标准表日期格式
     */
    private String bzbrqgs;
    /**
     * 代码转换字段
     */
    private String DMZHZD;

}

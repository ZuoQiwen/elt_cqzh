package com.dfwy.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
@Slf4j
public class ELTUtils {
    /**
     * 在ELT项目中 1表示true  0false
     * @param i
     * @return
     */
    public static boolean isTrue(int i){
        return 0!=i;
    }

    public static boolean biggerThan(String i,int j){
        try {
            return StringUtil.isEmpty(i)?false:Integer.parseInt(i)>j;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * @description : 根据已知字段进行去重
     * @author zuoqiwen
     * @date 2020/6/12 11:55
     * @param [list, keys] 
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public static List<Map<String,String>> distinct(List<Map<String,String>> list, final String [] keys){
        //1.创建set集合以及比较条件
        Set<Map<String,String>> result = new TreeSet<Map<String,String>>(
                (Map<String, String> var1, Map<String,String> var2)->  {
                    StringBuffer compare1 = new StringBuffer();
                    StringBuffer compare2 = new StringBuffer();
                    for (String key : keys) {
                        compare1.append(var1.get(key.trim().toUpperCase())).append("-");
                        compare2.append(var2.get(key.trim().toUpperCase())).append("-");
                    }
                    return compare1.toString().compareTo(compare2.toString());
                }
        );
        //3.去重:只保留第一条有效记录
        result.addAll(list);
        return new ArrayList<Map<String,String>>(result);

    }
    /**
     * 字符串比较，如果长度不一样则长度补全在进行比较
     * @param first
     * @param second
     */
    public static int compare(String first,String second) {
        //1.一种为空的情况
        if(first==null && second!=null) {
            return -1;
        }
        if(first!=null && second==null) {
            return 1;
        }
        if(MathUtil.isNumber(first) && MathUtil.isNumber(second)){
           return (int)(Double.parseDouble(first)-Double.parseDouble(second));
        }
        //2 都不为空
        if(first!=null && second!=null) {
            if(first.length()>second.length()) {
                second = StringUtils.leftPad(second,first.length(),"0");
            }
            if(first.length()<second.length()) {
                first = StringUtils.leftPad(first,second.length(),"0");
            }
            return first.compareTo(second);
        }
        return 0;
    }
    /**
     * 字段过滤
     * @param result
     * @param xmlEmentnew
     * @param flag false只保留结果集中的有效字段，根据filed保留所有字段没有则为空
     * @return
     */
    public static List<Map<String, String>> filter(List<Map<String, String>> result, String[] fileds) {
        List<Map<String, String>> filter = new ArrayList<>();
        for (Map<String, String> map : result) {
            Map<String, String> current = new HashMap<>();
            for (String key : fileds) {
                if(map.containsKey(key)) {
                    String value = map.get(key);
                    current.put(key, value!=null?value:"");
                }
            }
            filter.add(current);
        }
        return filter;
    }

    public static void addELTOperationParam(String businessId){
        ServletUtils.getRequest().getSession().setAttribute(ELTConstant.BUSINESSID,businessId);
    }
    public static String getELTOperationParam(){
        try {
            return (String)ServletUtils.getRequest().getSession().getAttribute(ELTConstant.BUSINESSID);
        } catch (Exception e) {
            //异步任务获取requestd对象已经销毁
            return null;
        }
    }
    public static void cleanELTOperationParam(){
        ServletUtils.getRequest().getSession().setAttribute(ELTConstant.BUSINESSID,null);
    }
    /**
     * @description : 计算百分比   返回字符串类型数据  不包含百分号
     * @author zuoqiwen
     * @date 2020/6/16 9:56
     * @param [i]
     * @return java.lang.String
     */
    public static String percent(double i) {
        return String.format("%.2f",i*100);
    }

    /**
     * @description : 数组转为字符串  保留两位小数
     * @author zuoqiwen
     * @date 2020/6/16 9:57
     * @param [i] 
     * @return java.lang.String
     */
    public static String numberFormat(double i){
        return String.format("%.2f",i);
    }
    /**
     * @description : 标准差
     * @author zuoqiwen
     * @date 2020/6/16 9:58
     * @param [list]   sqrt((xi-mean)^2/n)
     * @return java.lang.String
     */
    public static double std(double [] data){
        if(data!=null){
            double sum = Arrays.stream(data).sum();
            double mean = sum/data.length;
            double dVar=0;
            for (Double aDouble : data) {
                dVar+=(aDouble-mean)*(aDouble-mean);
            }
            return Math.sqrt(dVar/data.length);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(compare("0.1","0.01"));
        System.out.println(compare("11","2"));

    }
}

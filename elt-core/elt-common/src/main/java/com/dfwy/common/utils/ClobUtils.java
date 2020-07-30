package com.dfwy.common.utils;

/**
 * Description:
 * date: 2020/4/1 13:59
 *
 * @author zuoqiwen
 */
public class ClobUtils {
    private static final int CLOB_JOIN_LENGTH=2000;

    public static String setStrToClob(String str){
        if(StringUtil.isEmpty(str)){
            return "";
        }
        if(str.length()<=CLOB_JOIN_LENGTH){
            return str;
        }
        StringBuffer sb = new StringBuffer();
        while(str.length()>CLOB_JOIN_LENGTH){
            sb.append(str.substring(0, CLOB_JOIN_LENGTH)).append("||");
            str = str.substring(CLOB_JOIN_LENGTH);
        }
        sb.append(str);
        return sb.toString();
    }
}

package com.dfwy.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

/**
 * Description: <br/>
 * date: 2019/11/5 15:15<br/>
 *
 * @author zuoqiwen<br />
 * @since JDK 1.8
 */
public class StringUtil extends StringUtils {
    public static String getText(Object o) {
        if (o != null) {
            return o.toString().trim();
        } else {
            return "";
        }
    }
    public static  String firstLetterToLowerCase(String string){
        if(string.length()>0){
            char[] chars = string.toCharArray();
            chars[0] = (char) (chars[0]+32);
            return String.valueOf(chars);
        }
        return string.toLowerCase();
    }

}

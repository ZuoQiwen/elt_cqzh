package com.dfwy.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 * date: 2020/5/19 18:04
 *
 * @author zuoqiwen
 */
public class SocketUtils {
    private static final String CHARSET_NAME="GBK";
    public static String fixBytesLengthFormat(String xmlStr) {
        int a = 0;
        try {
            a = xmlStr.getBytes(CHARSET_NAME).length;
            return StringUtils.leftPad(String.valueOf(a),FixLengthDecoder.HEADER_LENGTH,"0")+xmlStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xmlStr;
    }

    public static int getLengthFromString(String str) {
        int indexOfChar = 0;
        boolean flag = true;
        while (flag) {
            if (str.charAt(indexOfChar) == '0') {
                indexOfChar++;
            } else {
                flag = false;
            }
        }
        return Integer.parseInt(str.substring(indexOfChar, FixLengthDecoder.HEADER_LENGTH));
    }
    public static String current(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public static ByteBuf response(String xml) throws UnsupportedEncodingException {
        return Unpooled.copiedBuffer(fixBytesLengthFormat(xml).getBytes(CHARSET_NAME));
    }
}

package com.dfwy.common.utils;


import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 *@className 重庆中行sftp有效性校验
 *@description :
 *@author zuoqiwen 
 *@date 2020/7/28 11:13
 *@version V1.0
 **/
public class CqzhEndecryptUtils {

    private static final String salt = "87771c98f6fa47aaa9c9f044805edd41";
    private static final int HASHITERATIONS = 3;

    private static final String KEY_SYFFER="cqzh_dmzs_ftp_xmapp01";

    /**
     * @description :  key加密
     * @author zuoqiwen
     * @date 2020/7/28 11:19
     * @param [key] 
     * @return java.lang.String
     */
    public static  String encrypt(String key){
        //组合md5Password,迭代，对密码进行加密
        return  new Md5Hash(key,key+salt,HASHITERATIONS).toBase64();
    } 
    

    
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(encrypt(UUID.randomUUID().toString().replace("-","")));
    } 
}

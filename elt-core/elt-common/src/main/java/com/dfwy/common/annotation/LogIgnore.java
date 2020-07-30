package com.dfwy.common.annotation;

import java.lang.annotation.*;

/**
 *@className LogIgnore
 *@description : 日志记录时忽略参数 仅适用于第一个参数
 *@author zuoqiwen 
 *@date 2020/6/16 14:58
 *@version V1.0
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogIgnore {
}

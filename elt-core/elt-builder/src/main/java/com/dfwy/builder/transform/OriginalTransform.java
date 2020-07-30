package com.dfwy.builder.transform;

import java.util.List;
import java.util.Map;
/**
 * @ClassName OriginalTransform
 * @Description  原始表数据转换
 * @Author zuoqiwen
 * @Date 2020/7/30 14:29
 * @Version 1.0
 **/
public interface OriginalTransform {
    void transform(List<Map<String,String>> data);

}

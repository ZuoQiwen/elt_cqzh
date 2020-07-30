package com.dfwy.builder.transform.standard;

import com.dfwy.builder.transform.OriginalTransform;
import com.dfwy.builder.transform.StandardTransform;
import com.dfwy.common.utils.BeansMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Zcfzb
 * @Description  资产负债表
 * 负债和所有者权益项目名称、负债和所有者权益项目-期初、负债和所有者权益项目-期末映射至资产项目名称、资产-期初、资产-期末
 * 一行数据变为两行其他数据不变
 * @Author zuoqiwen
 * @Date 2020/7/30 14:40 
 * @Version 1.0
 **/
@Component("test")
@Slf4j
public class Test implements StandardTransform {

    @Override
    public void transfrom(List<Map<String, String>> data) {

    }
}

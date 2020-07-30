package com.dfwy.service;

import com.dfwy.common.domain.CQBOCData;

import java.util.List;
import java.util.Map;

public interface TaxDataService {
    /**
     * 获取税务数据   需要根据自己业务逻辑实现
     * @return
     */
    String taxData(CQBOCData taxData);

    /**
     * 税务数据报文解析    根据实际业务报文解析，该方法需要自己重新实现
     * @return
     */
    Map<String, List<Map<String, String>>> format(String xml);
}

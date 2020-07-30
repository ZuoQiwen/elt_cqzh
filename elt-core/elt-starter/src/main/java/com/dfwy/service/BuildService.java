package com.dfwy.service;

import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.utils.Result;

import java.util.List;
import java.util.Map;


public interface BuildService {
    /**
     * 处理处理接口
     * @return
     */
    Result build(CQBOCData cqTaxData);

    void saveNsrxx(Map<String, List<Map<String, String>>>data);

}

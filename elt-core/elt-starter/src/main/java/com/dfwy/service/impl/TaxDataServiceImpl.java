package com.dfwy.service.impl;


import com.dfwy.builder.config.ELTConfiger;
import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.utils.ELTConstant;
import com.dfwy.common.utils.api.TaxRequestFactory;
import com.dfwy.controller.TaxDataTestController;
import com.dfwy.service.CQHelper;
import com.dfwy.service.TaxDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Data
public class TaxDataServiceImpl implements TaxDataService {
    @Autowired
    private TaxRequestFactory taxRequestFactory;
    @Autowired
    private ELTConfiger eltConfiger;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CQHelper cqHelper;
    @Value("${cqzh.dev}")
    private int status;


    @SneakyThrows
    @Override
   // @Log(api = "2-获取数据")
    public String taxData(CQBOCData taxData) {
        if(status==0){
            String bwPath = System.getProperty("os.name").toLowerCase().startsWith("win")?"C://xmapp01/bw/":"/xmapp01/bw/";
            //读取本地报文文件
            return FileUtils.readFileToString(new File(bwPath+taxData.getNsrsbh()+".txt"), "utf-8");
        }else{
            return cqHelper.getData(taxData);
        }
    }

    @SneakyThrows
    @Override
    //@Log(api = "3-报文格式转换")
    public Map<String, List<Map<String, String>>> format(String taxData) {
        if (StringUtils.isEmpty(taxData)){
            String errorMessage = "税务获取数据为空，请检查数据是否正常";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap jsonMap = mapper.readValue(taxData, HashMap.class);
        if (ELTConstant.SUCCESS.equals(jsonMap.get(ELTConstant.CODE))) {
            jsonMap.remove(ELTConstant.CODE);
            jsonMap.remove(ELTConstant.MSG);
            return uppper(jsonMap);
        } else {
            String errorMessage = "从税务获取数据返回值是失败，错误码：" + jsonMap.get(ELTConstant.CODE) + "，错误描述：" + jsonMap.get(ELTConstant.MSG);
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }


    /**
     * @param [jsonMap]
     * @return java.util.Map<java.lang.String, java.util.List < java.util.Map < java.lang.String, java.lang.String>>>
     * @description :  报文结构转换为大写
     * @author zuoqiwen
     * @date 2020/6/18 10:51
     */
    private Map<String, List<Map<String, String>>> uppper(HashMap<String, List<Map<String, String>>> jsonMap) {
        if (CollectionUtils.isEmpty(jsonMap)) {
            String errorMessage = "税务数据去掉code和msg字段之后没有数据字段，请检查税务返回数据是否正常";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        Map<String, String> ysbBwjdExchage = eltConfiger.getYsbBwjdExchageMap();

        Map<String, List<Map<String, String>>> result = new HashMap<>();
        for (Map.Entry<String, List<Map<String, String>>> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            List<Map<String, String>> value = entry.getValue();
            List<Map<String, String>> valueFormat = new ArrayList<>();
            if (!CollectionUtils.isEmpty(value)) {
                for (Map<String, String> map : value) {
                    valueFormat.add(upperMap(map));
                }
            }
            result.put(ysbBwjdExchage.getOrDefault(key.toUpperCase(),key.toUpperCase()), valueFormat);
        }
        return result;
    }

    private Map<String, String> upperMap(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return map;
        }
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> item : map.entrySet()) {
            if (!"swordrownum".equalsIgnoreCase(item.getKey())) {
                result.put(item.getKey().trim().toUpperCase(), "null".equals(String.valueOf(item.getValue())) ?
                        "" : String.valueOf(item.getValue()));
            }
        }
        return result;
    }
}

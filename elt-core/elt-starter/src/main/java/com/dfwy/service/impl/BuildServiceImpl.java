package com.dfwy.service.impl;

import com.dfwy.builder.builder.ELTBuilder;
import com.dfwy.builder.config.EltBuilderHelper;
import com.dfwy.common.annotation.ClearSession;
import com.dfwy.common.annotation.Log;
import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.domain.Nsrxx;
import com.dfwy.common.utils.Result;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.BuildService;
import com.dfwy.service.TaxDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BuildServiceImpl implements BuildService {

    @Autowired
    private TaxDataService taxDataService;
    @Autowired
    private EltBuilderHelper eltHelper;
    @Autowired
    private ELTMapper eltMapper;

    @Override
    @Log(api = "CSPA050004国税数据查询")
    @ClearSession
    public Result build(CQBOCData cqTaxData) {
        //获取结构化数据
        Map<String, List<Map<String, String>>> data = taxDataService.format(taxDataService.taxData(cqTaxData));
        //基本信息入库
        saveNsrxx(data);
        //数据清洗
        ELTBuilder builder = new ELTBuilder(data, cqTaxData.getBusinessId(),eltHelper);
        builder.build();
        //标准数据并转发
        Map<String, List<Map<String, String>>> standardData = builder.buildData();
        //计算指标并返回
        return Result.success(standardData);
    }

    @Override
    public void saveNsrxx(Map<String, List<Map<String, String>>>data) {
        List<Map<String,String>> nsrjbxxList = data.get("NSRJBXX");
        List<Map<String,String>> xydjList = data.get("NSXYDJ");

        if(!CollectionUtils.isEmpty(nsrjbxxList)){
            Map<String,String> nsrjbxx = nsrjbxxList.get(0);
            String xydj = null;
            if(!CollectionUtils.isEmpty(xydjList)){
                xydjList.stream().sorted((Map<String, String> map1,Map<String, String> map2)-> StringUtil.compare(map1.get("PJSJ"),map2.get("PJSJ")));
                xydj = xydjList.get(0).get("XYDJ");
            }
            eltMapper.saveNsrxx(new Nsrxx().setNsrsbh(nsrjbxx.get("NSRSBH"))
                    .setHydm(nsrjbxx.get("HYDM"))
                    .setXydj(xydj)
                    .setZgswjgdm(nsrjbxx.get("ZGSWJGDM")));
        }else{
            String errorMessage = "原始税务数据内容异常：NSRJBXX节点为空";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }


}

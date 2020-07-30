package com.dfwy.database.service.impl;

import com.dfwy.common.annotation.LogIgnore;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.database.service.BuildDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class BuildDataServiceImpl implements BuildDataService {
    @Autowired
    private ELTMapper eltMapper;

    @Override
    //@Log(api = "4-原始表数据入库")
    @Transactional(rollbackFor = Exception.class)
    public void saveOriginalData(@LogIgnore List<String> originalSql) {
        if (!CollectionUtils.isEmpty(originalSql)) {
            originalSql.forEach(x -> eltMapper.save(x));
        }
    }

    @Override
   //@Log(api = "4-原始表异步数据入库")
    @Transactional(rollbackFor = Exception.class)
    public void saveOriginalAsyncData(@LogIgnore List<String> originalSql) {
        if (!CollectionUtils.isEmpty(originalSql)) {
            originalSql.forEach(x -> eltMapper.save(x));
        }
    }

    @Override
   //@Log(api = "5-标准表数据入库")
    @Transactional(rollbackFor = Exception.class)
    public void saveStandardData(@LogIgnore List<String> standardSql) {
        if (!CollectionUtils.isEmpty(standardSql)) {
            standardSql.forEach(x -> eltMapper.save(x));
        }
    }

    @Override
    //@Log(api = "5-标准表异步数据入库")
    @Transactional(rollbackFor = Exception.class)
    public void saveStandardAsyncData(@LogIgnore List<String> standardSql) {
        if (!CollectionUtils.isEmpty(standardSql)) {
            standardSql.forEach(x -> eltMapper.save(x));
        }
    }
}

package com.dfwy.web.service.impl;

import com.dfwy.web.domain.EltRecord;
import com.dfwy.web.domain.TaxDataRequest;
import com.dfwy.web.mapper.EltRecordMapper;
import com.dfwy.web.service.EltRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EltRecordServiceImpl implements EltRecordService {
    @Autowired
    private EltRecordMapper eltRecordMapper;
    @Override
    public List<EltRecord> selectEltRecordList(EltRecord eltRecord) {
        return eltRecordMapper.selectEltRecordList(eltRecord);
    }

    @Override
    public List<TaxDataRequest> selectTaxDataRequestList(TaxDataRequest taxDataRequest) {
        return eltRecordMapper.selectTaxDataRequestList(taxDataRequest);
    }
}

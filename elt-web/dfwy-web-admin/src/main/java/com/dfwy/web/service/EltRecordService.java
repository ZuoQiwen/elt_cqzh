package com.dfwy.web.service;

import com.dfwy.web.domain.EltRecord;
import com.dfwy.web.domain.TaxDataRequest;

import java.util.List;

public interface EltRecordService {
    List<EltRecord> selectEltRecordList(EltRecord eltRecord);

    List<TaxDataRequest> selectTaxDataRequestList(TaxDataRequest taxDataRequest);
}

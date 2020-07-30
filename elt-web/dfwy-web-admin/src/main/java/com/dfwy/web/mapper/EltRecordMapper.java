package com.dfwy.web.mapper;

import com.dfwy.web.domain.EltRecord;
import com.dfwy.web.domain.TaxDataRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EltRecordMapper {
    List<EltRecord> selectEltRecordList(EltRecord eltRecord);

    List<TaxDataRequest> selectTaxDataRequestList(TaxDataRequest taxDataRequest);
}

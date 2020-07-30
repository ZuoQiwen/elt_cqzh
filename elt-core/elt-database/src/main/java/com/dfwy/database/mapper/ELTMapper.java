package com.dfwy.database.mapper;

import com.dfwy.common.domain.*;
import com.dfwy.common.domain.ftp.*;
import com.dfwy.common.domain.taxdata.TaxDataRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *@className OriginalMapper
 *@description : 原始表入库mapper
 *@author zuoqiwen
 *@date 2020/6/5 17:33
 *@version V1.0
 **/
@Component
public interface ELTMapper {
    /**
     * @description : 原始表入库
     * @author zuoqiwen
     * @date 2020/6/5 17:38
     * @param sql
     * @return void
     */
    void save(@Param("sql") String sql);

    void saveLogMysql(OperatinLog operatinLog);
    void saveLogOracle(OperatinLog operatinLog);
    /**
     * @description : 查询原始表标准表映射关系
     * @author zuoqiwen
     * @date 2020/6/5 17:38
     * @param
     * @return java.util.List<com.dfwy.common.domain.ELTTable>
     */
    List<ELTTable> selectAllELTTables();
    /**
     * @description :查询字段映射关系
     * @author zuoqiwen
     * @date 2020/6/5 17:38
     * @param
     * @return java.util.List<com.dfwy.common.domain.ELTColumn>
     */
    List<ELTColumn> selectAllColumns();

    /**
     * @description : 代码表查询
     * @author zuoqiwen
     * @date 2020/6/10 15:33
     * @param []
     * @return java.util.List<com.dfwy.common.domain.Code>
     */
    List<Code> selectAllCodes();

    List<String> selectIndicators();

    void saveNsrxx(Nsrxx nsrxx);



    void  insertIntoApprovalResult(ApprovalResult approvalResult);

    List<FtpSXMX> selectSxmx(String date);

    String selectEltDate(String id);

    List<FtpNSXYDJMX> selectNsxydj(String date);

    List<FtpHYMX> selectHymx(String date);

    List<FtpZGSWJMX> selectZgswj(String date);

    void insertIntoTaxDataRequest(TaxDataRequest taxDataRequest);

    List<SystemConfig> selectSystemConfiger();

}

//package com.dfwy;
//
//import com.dfwy.builder.sql.SqlBuilder;
//import com.dfwy.builder.sql.mysql.MysqlSqlBuilder;
//import com.dfwy.builder.sql.oracle.OracleSqlBuilder;
//import com.dfwy.common.domain.CQBOCData;
//import com.dfwy.common.domain.ELTColumn;
//import com.dfwy.common.domain.ELTTable;
//import com.dfwy.common.utils.ELTConstant;
//import com.dfwy.service.impl.TaxDataServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ELTTest {
//
//    public void taxData(){
//
//    }
//    @SneakyThrows
//    public void  mysql(){
//        TaxDataServiceImpl test =  new TaxDataServiceImpl();
//        CQBOCData cqTaxData = new CQBOCData();
//        String json = test.taxData(cqTaxData);
//        //System.out.println(test.format(json));
//        MysqlSqlBuilder builder = new MysqlSqlBuilder();
//        List<String> sql = builder.sql(test.format(json),15,1,"");
//        System.out.println(new ObjectMapper().writeValueAsString(sql));
//    }
//    @SneakyThrows
//
//    public void  oracle(){
//        TaxDataServiceImpl test =  new TaxDataServiceImpl();
//        CQBOCData cqTaxData = new CQBOCData();
//        String json = test.taxData(cqTaxData);
//        SqlBuilder oracle = new OracleSqlBuilder();
//        Map<String, ELTTable> eltTableConfig = new HashMap<>();
//        ELTTable eltTable = new ELTTable();
//        eltTable.setYsb("NSRJBXX");
//        List<ELTColumn> columns = new ArrayList<>();
//        ELTColumn column = new ELTColumn();
//        column.setYsbzdlx(ELTConstant.COLUMN_COLB);
//        column.setYsbzd("ZGSWJMC");
//        ELTColumn column1 = new ELTColumn();
//        column1.setYsbzdlx(ELTConstant.COLUMN_COLB);
//        column1.setYsbzd("NSRZT");
//        columns.add(column);
//        columns.add(column1);
//       // eltTable.setEltColumnList(columns);
//        eltTableConfig.put("NSRJBXX",eltTable);
//        List<String> oracleSql =oracle.sql(test.format(json),15,0,"");
//        System.out.println(new ObjectMapper().writeValueAsString(oracleSql));
//
//    }
//}

package com.dfwy.web.controller;

import com.dfwy.web.common.annotation.Log;
import com.dfwy.web.common.core.controller.BaseController;
import com.dfwy.web.common.core.domain.AjaxResult;
import com.dfwy.web.common.core.page.TableDataInfo;
import com.dfwy.web.common.enums.BusinessType;
import com.dfwy.web.common.utils.Result;
import com.dfwy.web.common.utils.poi.ExcelUtil;
import com.dfwy.web.domain.EltRecord;
import com.dfwy.web.domain.TaxDataRequest;
import com.dfwy.web.service.EltRecordService;
import com.dfwy.web.system.domain.SysMenu;
import com.dfwy.web.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/elt/record")
@Slf4j
public class EltController extends BaseController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${elt.url}")
    private String eltUrl;

    @Autowired
    private EltRecordService eltRecordService;
    @RequiresPermissions("elt:record:approval")
    @RequestMapping("/approval")
    public String elt(){
        return "elt/approval";
    }

    @RequestMapping("/approval/list")
    @ResponseBody
    public TableDataInfo approvalList(EltRecord eltRecord){
        startPage();
        return getDataTable(eltRecordService.selectEltRecordList(eltRecord));
    }

    @Log(title = "融资管理", businessType = BusinessType.EXPORT)
    //@RequiresPermissions("elt:record:approval:export")
    @PostMapping("/approval/export")
    @ResponseBody
    public AjaxResult approvalExport(EltRecord eltRecord)
    {
        List<EltRecord> list = eltRecordService.selectEltRecordList(eltRecord);
        ExcelUtil<EltRecord> util = new ExcelUtil<EltRecord>(EltRecord.class);
        return util.exportExcel(list, "融资管理");
    }
    @RequiresPermissions("elt:record:taxData")
    @RequestMapping("/taxData")
    public String taxData(){
        return "elt/taxData";
    }

    @RequestMapping("/taxData/list")
    @ResponseBody
    public TableDataInfo list(TaxDataRequest taxDataRequest){
        startPage();
        return getDataTable(eltRecordService.selectTaxDataRequestList(taxDataRequest));
    }
    @Log(title = "税务数据取数", businessType = BusinessType.EXPORT)
    @PostMapping("/taxData/export")
    @ResponseBody
    public AjaxResult taxDataExport(TaxDataRequest taxDataRequest)
    {
        List<TaxDataRequest> list = eltRecordService.selectTaxDataRequestList(taxDataRequest);
        ExcelUtil<TaxDataRequest> util = new ExcelUtil<TaxDataRequest>(TaxDataRequest.class);
        return util.exportExcel(list, "税务数据取数管理");
    }

    @RequestMapping("/reexecute")
    @ResponseBody
    public Result reExecute(String id)
    {
        return restTemplate.postForEntity(eltUrl,id,Result.class).getBody();
    }

}

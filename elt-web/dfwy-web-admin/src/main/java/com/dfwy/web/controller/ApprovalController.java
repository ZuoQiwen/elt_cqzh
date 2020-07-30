package com.dfwy.web.controller;

import com.dfwy.web.common.annotation.Log;
import com.dfwy.web.common.core.controller.BaseController;
import com.dfwy.web.common.core.domain.AjaxResult;
import com.dfwy.web.common.core.page.TableDataInfo;
import com.dfwy.web.common.enums.BusinessType;
import com.dfwy.web.common.utils.poi.ExcelUtil;
import com.dfwy.web.domain.*;
import com.dfwy.web.service.ApprovalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName ApprovalController
 * @Description 融资审批结果
 * @Author zuoqiwen
 * @Date 2020/7/29 16:21
 * @Version 1.0
 **/
@Controller
@RequestMapping("/elt/approval")
public class ApprovalController extends BaseController {

    @Autowired
    private ApprovalService approvalService;

    @RequestMapping("/hy")
    @RequiresPermissions("elt:approval:hy")
    public String hy() {
        return "elt/hy";
    }

    @RequestMapping("/hyList")
    @ResponseBody
    public TableDataInfo hyList(FtpHYMX ftpHYMX) {
        return getDataTable(approvalService.hyList(ftpHYMX));
    }

    @Log(title = "融资审批行业统计", businessType = BusinessType.EXPORT)
    @PostMapping("/hy/export")
    @ResponseBody
    public AjaxResult hyExport(FtpHYMX ftpHYMX)
    {
        List<FtpHYMX> list = approvalService.hyList(ftpHYMX);
        ExcelUtil<FtpHYMX> util = new ExcelUtil<FtpHYMX>(FtpHYMX.class);
        return util.exportExcel(list, "融资审批行业统计");
    }

    @RequestMapping("/xydj")
    @RequiresPermissions("elt:approval:xydj")
    public String xydj() {
        return "elt/xydj";
    }

    @RequestMapping("/xydjList")
    @ResponseBody
    public TableDataInfo xydjList(FtpNSXYDJMX ftpNSXYDJMX) {
        return getDataTable(approvalService.xydjList(ftpNSXYDJMX));
    }
    @Log(title = "融资审批信用等级统计", businessType = BusinessType.EXPORT)
    @PostMapping("/xydj/export")
    @ResponseBody
    public AjaxResult xydjExport(FtpNSXYDJMX ftpNSXYDJMX)
    {
        List<FtpNSXYDJMX> list = approvalService.xydjList(ftpNSXYDJMX);
        ExcelUtil<FtpNSXYDJMX> util = new ExcelUtil<>(FtpNSXYDJMX.class);
        return util.exportExcel(list, "融资审批信用等级统计");
    }

    @RequestMapping("/zgswjg")
    @RequiresPermissions("elt:approval:zgswjg")
    public String zgswjg() {
        return "elt/zgswjg";
    }

    @RequestMapping("/zgswjgList")
    @ResponseBody
    public TableDataInfo zgswjgList(FtpZGSWJMX ftpZGSWJMX) {
        return getDataTable(approvalService.zgswjgList(ftpZGSWJMX));
    }
    @Log(title = "融资审批主管税务机关统计", businessType = BusinessType.EXPORT)
    @PostMapping("/zgswjg/export")
    @ResponseBody
    public AjaxResult zgswjgExport(FtpZGSWJMX ftpZGSWJMX)
    {
        List<FtpZGSWJMX> list = approvalService.zgswjgList(ftpZGSWJMX);
        ExcelUtil<FtpZGSWJMX> util = new ExcelUtil<>(FtpZGSWJMX.class);
        return util.exportExcel(list, "融资审批主管税务机关统计");
    }

    @RequestMapping("/sxmx")
    @RequiresPermissions("elt:approval:sxmx")
    public String sxmx() {
        return "elt/sxmx";
    }

    @RequestMapping("/sxmxList")
    @ResponseBody
    public TableDataInfo sxmxList(FtpSXMX ftpSXMX) {
        return getDataTable(approvalService.sxmxList(ftpSXMX));
    }


    @Log(title = "融资审批授信明细统计", businessType = BusinessType.EXPORT)
    @PostMapping("/sxmx/export")
    @ResponseBody
    public AjaxResult sxmxExport(FtpSXMX ftpSXMX)
    {
        List<FtpSXMX> list = approvalService.sxmxList(ftpSXMX);
        ExcelUtil<FtpSXMX> util = new ExcelUtil<>(FtpSXMX.class);
        return util.exportExcel(list, "融资审批授信明细统计");
    }
}

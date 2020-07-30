package com.dfwy.controller;

import com.dfwy.common.domain.CQBOCData;
import com.dfwy.service.TaxDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 *@className ELTController
 *@description : 重庆税务数据挡板
 *@author zuoqiwen
 *@date 2020/6/12 14:39
 *@version V1.0
 **/
@RestController
@Slf4j
public class TaxDataTestController {
    @Autowired
    private TaxDataService taxDataService;

    @RequestMapping("/restOriginal")
    public String taxData(@RequestBody CQBOCData cqTaxData) throws Exception {
        log.info("税务数据请求，参数"+cqTaxData);
        String result = FileUtils.readFileToString(new File(TaxDataTestController.class.getClassLoader()
                .getResource("doc/taxData.json").toURI().getPath()), "utf-8");
        return result;
    }

    @RequestMapping("/test")
    public String test(CQBOCData cqTaxData) throws Exception {

        return taxDataService.taxData(cqTaxData);
    }
    @RequestMapping("/test1")
    public String test1(String a, String b, @RequestBody String c, HttpServletRequest request) throws Exception {
        System.out.println(request.getHeader("Authorization"));
        return "success";
    }
}

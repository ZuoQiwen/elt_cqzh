package com.dfwy.controller;

import com.dfwy.builder.config.ELTConfiger;
import com.dfwy.common.domain.CQBOCData;
import com.dfwy.common.utils.Result;
import com.dfwy.common.utils.StringUtil;
import com.dfwy.database.mapper.ELTMapper;
import com.dfwy.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dfwy.service.BuildService;

import java.time.LocalDate;

@RestController
@RequestMapping("/elt")
@Slf4j
public class ELTController {

    @Autowired
    private ELTConfiger eltConfiger;
    @Autowired
    private BuildService buildService;
    @Autowired
    private SftpService sftpService;
    @Autowired
    private ELTMapper eltMapper;
    /**
     * @description : 数据配置项重新加载
     * @author zuoqiwen
     * @date 2020/6/18 10:50
     * @param [] 
     * @return void
     */
    @RequestMapping("/reload")
    public void refresh(){
        eltConfiger.init();
        log.info("eltConfiger reload:"+eltConfiger);
    }
    /**
     * @description : 数据清洗
     * @author zuoqiwen
     * @date 2020/6/18 10:50
     * @param [cqTaxData]
     * @return com.dfwy.common.utils.Result
     */
    @RequestMapping("/build")
    public Result build(CQBOCData cqTaxData){
        return buildService.build(cqTaxData);
    }


    @PostMapping("/reexecute")
    public Result reexecute(@RequestBody String id){
        return sftpService.sftpPutFile(eltMapper.selectEltDate(id));
    }
}

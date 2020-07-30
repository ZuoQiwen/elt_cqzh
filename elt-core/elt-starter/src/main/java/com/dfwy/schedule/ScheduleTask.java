package com.dfwy.schedule;

import com.dfwy.common.utils.Result;
import com.dfwy.service.SftpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ScheduleTask {
    @Autowired
    private SftpService sftpService;

    //每天23点触发
    //@Scheduled(cron = "0 0 23 * * ?")
    @Scheduled(cron = "0 03 15 * * ?")
    public void sftp() {
        log.info("[SFTP定时任务]开始");
        int i = 0;
        Result result = Result.fail("");
        String date = LocalDate.now().toString();
        //失败后重发
        while (i < 3 && !result.isSuccess()) {
            result = sftpService.sftpPutFile(date);
            if(result.isSuccess()){
                log.info("[SFTP定时任务{}]成功",date);
                break;
            }
            i++;
            log.error("[SFTP定时任务{}][{}]失败，{}",date,i, result.getMsg());
        }
    }


}

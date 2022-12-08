package com.my.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
//@EnableScheduling
public class MyScheduleConfig {

//    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleTask(){
        System.out.println("5秒中执行的定时任务~~");
    }
}

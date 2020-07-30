//package com.dfwy.common.thread;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * 线程池配置TaskExecutor，利用@Aysnc实现异步方法，EnableAsync开启该功能
// *
// * @author ruoyi
// **/
//@Configuration
//public class ThreadPoolConfig
//{
//    /**
//     * 核心线程池大小
//     */
//    private int corePoolSize = 10;
//
//    /**
//     * 最大可创建的线程数
//     */
//    private int maxPoolSize = 50;
//
//    /**
//     * 队列最大长度
//     */
//    private int queueCapacity = 1000;
//
//    /**
//     * 线程池维护线程所允许的空闲时间
//     */
//    private int keepAliveSeconds = 300;
//
//    @Bean
//    public TaskExecutor taskExecutor()
//    {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setCorePoolSize(corePoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setKeepAliveSeconds(keepAliveSeconds);
//        // 线程池对拒绝任务(无线程可用)的处理策略
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.setThreadNamePrefix("taskExecutor-");
//        return executor;
//    }
//
//
//
//
//}

package org.arch.alarm.biz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


@Configuration
public class ThreadPoolConfig {
    /**
     * 企业微信通知线程池
     *
     * @return
     */
    @Bean("workWechatNotifyExecutor")
    public ThreadPoolTaskExecutor workWechatNotifyExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setKeepAliveSeconds(1);
        threadPoolTaskExecutor.setMaxPoolSize(2);
        threadPoolTaskExecutor.setQueueCapacity(10);
        // 发送企业微信消息 发不了忽略就行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        return threadPoolTaskExecutor;
    }
}

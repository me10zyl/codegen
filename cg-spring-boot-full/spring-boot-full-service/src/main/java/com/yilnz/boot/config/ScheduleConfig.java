package com.yilnz.boot.config;

import com.yilnz.boot.util.DistributeLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@EnableAsync
public class ScheduleConfig {

    @Bean(destroyMethod = "release")
    @Scope(scopeName = "prototype")
    public DistributeLock distributeLock(){
        return new DistributeLock();
    }
}

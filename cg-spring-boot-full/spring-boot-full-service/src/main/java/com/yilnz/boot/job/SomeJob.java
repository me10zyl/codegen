package com.yilnz.boot.job;


import com.yilnz.boot.util.DistributeLock;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Uncomment to use job
 */
@Component
public class SomeJob {

    private Logger logger = LoggerFactory.getLogger(SomeJob.class);

    @Autowired
    private DistributeLock lock;

    @Autowired
    private Scheduler scheduler;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final String lockPath = "/com/yilnz/boot/full/somejob";

    @Scheduled(fixedDelay = 5000)
    public void reportCurrentTime() {
        try {
            if(lock.acquire(lockPath)) {
                logger.info("The time is now {}", dateFormat.format(new Date()));
            }
        } catch (Exception e) {
            logger.error("zk DistributeLock error", e);
        }
    }

    @PostConstruct
    public void testAddQuartZ(){
        final JobDetail jobDetail = JobBuilder.newJob(SomeJobQuartz.class)
                .withIdentity(new JobKey("myTest", "testGroup")).build();
        final SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
        try {
            scheduler.clear();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            logger.error("add job error", e);
        }

    }
}

package com.yilnz.boot.job;


import com.yilnz.boot.util.DistributeLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Uncomment to use job
 */
//@Component
public class SomeJob {

    private Logger logger = LoggerFactory.getLogger(SomeJob.class);

    @Autowired
    private DistributeLock lock;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final String lockPath = "/com/yilnz/boot/full/somejob";

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        try {
            if(lock.acquire(lockPath)) {
                logger.info("The time is now {}", dateFormat.format(new Date()));
            }
        } catch (Exception e) {
            logger.error("zk DistributeLock error", e);
        }
    }
}

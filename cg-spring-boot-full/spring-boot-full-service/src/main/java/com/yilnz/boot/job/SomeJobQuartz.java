package com.yilnz.boot.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeJobQuartz implements Job {

    private Logger logger = LoggerFactory.getLogger(SomeJobQuartz.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("quertz executed!!! {}", this);
    }
}

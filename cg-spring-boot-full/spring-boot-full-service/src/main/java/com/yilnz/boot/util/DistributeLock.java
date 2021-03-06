package com.yilnz.boot.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class DistributeLock {

    @Autowired
    private CuratorClientHolder curatorClientHolder;

    private static final Logger logger = LoggerFactory.getLogger(DistributeLock.class);
    private InterProcessMutex interProcessMutex;
    private Map<String, InterProcessMutex> mutexMap = new ConcurrentHashMap<String, InterProcessMutex>();

    private InterProcessMutex getMutex(String lockPath){
        if (interProcessMutex != null) {
            return interProcessMutex;
        }
        final InterProcessMutex interProcessMutex = new InterProcessMutex(curatorClientHolder.getClient(), lockPath);
        this.interProcessMutex = interProcessMutex;
        return interProcessMutex;
    }

    public boolean acquire(String lockPath) throws Exception {
        return getMutex(lockPath).acquire(0, TimeUnit.SECONDS);
    }

    public void release(){
        try {
            if (this.interProcessMutex != null) {
                this.interProcessMutex.release();
            }
        } catch (Exception e) {
            logger.error("mutex release error", e);
        }
    }
}

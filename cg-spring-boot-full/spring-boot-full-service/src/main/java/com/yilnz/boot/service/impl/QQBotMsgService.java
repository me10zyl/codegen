package com.yilnz.boot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yilnz.boot.dao.QQBotMsgMapper;
import com.yilnz.boot.entity.QQBotMsg;
import com.yilnz.boot.entity.QQBotMsgExample;
import com.yilnz.boot.entity.json.QQBotMsgQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"qqbot"})
public class QQBotMsgService {

    private final static Logger logger = LoggerFactory.getLogger(QQBotMsgService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private QQBotMsgMapper qqBotMsgMapper;

    @Cacheable(key = "targetClass + methodName + #p0")
    public PageInfo<QQBotMsg> list(QQBotMsgQuery qqBotMsgQuery) {
        if (qqBotMsgQuery.getPage() == null || qqBotMsgQuery.getPage() <= 1) {
            qqBotMsgQuery.setPage(1);
        }
        if (!StringUtils.isEmpty(qqBotMsgQuery.getStartEndTime())) {
            final String[] split = qqBotMsgQuery.getStartEndTime().split("~");
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                qqBotMsgQuery.setStartTime(sdf.parse(split[0].trim()));
                qqBotMsgQuery.setEndTime(sdf.parse(split[1].trim()));
            } catch (ParseException e) {
                logger.error("parse time error", e);
            }
        }
        final QQBotMsgExample qqBotMsgExample = new QQBotMsgExample();
        final QQBotMsgExample.Criteria criteria = qqBotMsgExample.createCriteria();
        if (!StringUtils.isEmpty(qqBotMsgQuery.getQqGroup())) {
            criteria.andGroupIdLike("%" + qqBotMsgQuery.getQqGroup() + "%");
        }
        if (!StringUtils.isEmpty(qqBotMsgQuery.getSenderQQ())) {
            criteria.andSenderQqLike("%" + qqBotMsgQuery.getSenderQQ() + "%");
        }
        if (!StringUtils.isEmpty(qqBotMsgQuery.getMessage())) {
            criteria.andMessageLike("%" + qqBotMsgQuery.getMessage() + "%");
        }
        if (qqBotMsgQuery.getStartTime() != null && qqBotMsgQuery.getEndTime() != null) {
            criteria.andTimeBetween(qqBotMsgQuery.getStartTime(), qqBotMsgQuery.getEndTime());
        } else if (qqBotMsgQuery.getStartTime() != null) {
            criteria.andTimeGreaterThanOrEqualTo(qqBotMsgQuery.getStartTime());
        } else if (qqBotMsgQuery.getEndTime() != null) {
            criteria.andTimeLessThanOrEqualTo(qqBotMsgQuery.getEndTime());
        }
        qqBotMsgExample.setOrderByClause("time desc");
        PageHelper.startPage(qqBotMsgQuery.getPage(), 20);
        final List<QQBotMsg> qqBotMsgs = qqBotMsgMapper.selectByExample(qqBotMsgExample);
        return new PageInfo<QQBotMsg>(qqBotMsgs);
    }
}

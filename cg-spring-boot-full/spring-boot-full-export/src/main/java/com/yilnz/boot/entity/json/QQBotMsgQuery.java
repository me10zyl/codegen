package com.yilnz.boot.entity.json;

import lombok.Data;

import java.util.Date;

@Data
public class QQBotMsgQuery {
    private String qqGroup;
    private Integer page;
    private String message;
    private Date startTime;
    private Date endTime;
    private String senderQQ;
    private String startEndTime;
}

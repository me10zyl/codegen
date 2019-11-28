package com.yilnz.boot.dao;

import com.yilnz.boot.entity.QQBotMsg;
import com.yilnz.boot.entity.QQBotMsgExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QQBotMsgMapper {
    int countByExample(QQBotMsgExample example);

    int deleteByExample(QQBotMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QQBotMsg record);

    int insertSelective(QQBotMsg record);

    List<QQBotMsg> selectByExample(QQBotMsgExample example);

    QQBotMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QQBotMsg record, @Param("example") QQBotMsgExample example);

    int updateByExample(@Param("record") QQBotMsg record, @Param("example") QQBotMsgExample example);

    int updateByPrimaryKeySelective(QQBotMsg record);

    int updateByPrimaryKey(QQBotMsg record);
}
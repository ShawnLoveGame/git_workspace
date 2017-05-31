/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-25 Created
 */
package com.he.im.dao;

import com.he.im.model.profire.OfHistory;
import com.he.im.model.profire.OfHistoryExample;
import com.he.im.model.profire.OfHistoryKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfHistoryMapper {
    int countByExample(OfHistoryExample example);

    int deleteByExample(OfHistoryExample example);

    int deleteByPrimaryKey(OfHistoryKey key);

    int insert(OfHistory record);

    int insertSelective(OfHistory record);

    List<OfHistory> selectByExample(OfHistoryExample example);

    OfHistory selectByPrimaryKey(OfHistoryKey key);

    int updateByExampleSelective(@Param("record") OfHistory record, @Param("example") OfHistoryExample example);

    int updateByExample(@Param("record") OfHistory record, @Param("example") OfHistoryExample example);

    int updateByPrimaryKeySelective(OfHistory record);

    int updateByPrimaryKey(OfHistory record);

    List<OfHistory> selectHistroyChatByUser(String toAcount);

    List<String> selectCurrentMsgUser(String userName);
}
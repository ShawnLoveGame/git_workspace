/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-01-05 Created
 */
package com.he.exam.dao.log;

import com.he.exam.model.log.BackOperateLog;

public interface BackOperateLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BackOperateLog record);

    int insertSelective(BackOperateLog record);

    BackOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BackOperateLog record);

    int updateByPrimaryKey(BackOperateLog record);
}
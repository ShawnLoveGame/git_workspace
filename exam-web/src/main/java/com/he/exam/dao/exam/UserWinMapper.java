/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-21 Created
 */
package com.he.exam.dao.exam;

import com.he.exam.model.exam.UserWin;
import com.he.exam.model.exam.UserWinExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWinMapper {
    int countByExample(UserWinExample example);

    int deleteByExample(UserWinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserWin record);

    int insertSelective(UserWin record);

    List<UserWin> selectByExample(UserWinExample example);

    UserWin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserWin record, @Param("example") UserWinExample example);

    int updateByExample(@Param("record") UserWin record, @Param("example") UserWinExample example);

    int updateByPrimaryKeySelective(UserWin record);

    int updateByPrimaryKey(UserWin record);

    void batchInsertUserWin(List<UserWin> userWins);

}
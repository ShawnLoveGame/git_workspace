/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-29 Created
 */
package com.he.exam.webapp.dao.exam;

import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.exam.ExamUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamUserMapper {
    int countByExample(ExamUserExample example);

    int deleteByExample(ExamUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExamUser record);

    int insertSelective(ExamUser record);

    List<ExamUser> selectByExample(ExamUserExample example);

    ExamUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExamUser record, @Param("example") ExamUserExample example);

    int updateByExample(@Param("record") ExamUser record, @Param("example") ExamUserExample example);

    int updateByPrimaryKeySelective(ExamUser record);

    int updateByPrimaryKey(ExamUser record);
}
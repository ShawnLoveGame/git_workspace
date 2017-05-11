/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.dao.exam;

import com.he.exam.model.exam.Department;
import com.he.exam.model.exam.ExamUserAnswer;
import com.he.exam.model.exam.ExamUserAnswerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamUserAnswerMapper {
    int countByExample(ExamUserAnswerExample example);

    int deleteByExample(ExamUserAnswerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExamUserAnswer record);

    int insertSelective(ExamUserAnswer record);

    List<ExamUserAnswer> selectByExample(ExamUserAnswerExample example);

    ExamUserAnswer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExamUserAnswer record, @Param("example") ExamUserAnswerExample example);

    int updateByExample(@Param("record") ExamUserAnswer record, @Param("example") ExamUserAnswerExample example);

    int updateByPrimaryKeySelective(ExamUserAnswer record);

    int updateByPrimaryKey(ExamUserAnswer record);

    int countUserAnswer();

    List<Department> findUserDepartmentOrder();

    List<ExamUserAnswer> findUserExamAnswerListByTime(@Param("startTime") String startTime);
}
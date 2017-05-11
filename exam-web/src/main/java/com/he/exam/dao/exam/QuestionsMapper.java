/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.dao.exam;

import com.he.exam.model.exam.Questions;
import com.he.exam.model.exam.QuestionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionsMapper {
    int countByExample(QuestionsExample example);

    int deleteByExample(QuestionsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Questions record);

    int insertSelective(Questions record);

    List<Questions> selectByExample(QuestionsExample example);

    Questions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Questions record, @Param("example") QuestionsExample example);

    int updateByExample(@Param("record") Questions record, @Param("example") QuestionsExample example);

    int updateByPrimaryKeySelective(Questions record);

    int updateByPrimaryKey(Questions record);

    void batchInsertQuestion(List<Questions> qlist);

    void batchDeleteQuestion(List<Long> ids);
}
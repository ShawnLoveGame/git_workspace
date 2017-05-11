/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-24 Created
 */
package com.he.exam.dao.exam;

import com.he.exam.model.exam.QuestionLibrary;
import com.he.exam.model.exam.QuestionLibraryExample;
import com.he.exam.model.exam.Questions;
import com.he.exam.model.exam.dto.QuestionLibraryDTO;
import com.he.exam.model.exam.dto.QuestionsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionLibraryMapper {
    int countByExample(QuestionLibraryExample example);

    int deleteByExample(QuestionLibraryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QuestionLibrary record);

    int insertSelective(QuestionLibrary record);

    List<QuestionLibrary> selectByExample(QuestionLibraryExample example);

    QuestionLibrary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuestionLibrary record, @Param("example") QuestionLibraryExample example);

    int updateByExample(@Param("record") QuestionLibrary record, @Param("example") QuestionLibraryExample example);

    int updateByPrimaryKeySelective(QuestionLibrary record);

    int updateByPrimaryKey(QuestionLibrary record);

    List<QuestionLibrary> findLibraryWithPg(QuestionLibraryDTO dto);

    int countFindLibraryWithPg(QuestionLibraryDTO dto);

    List<Questions> findQuestionWithPg(QuestionsDTO dto);
}
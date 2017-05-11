/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.webapp.dao.exam;

import com.he.exam.webapp.model.exam.ExamDetail;
import com.he.exam.webapp.model.exam.ExamDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamDetailMapper {
    int countByExample(ExamDetailExample example);

    int deleteByExample(ExamDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExamDetail record);

    int insertSelective(ExamDetail record);

    List<ExamDetail> selectByExample(ExamDetailExample example);

    ExamDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExamDetail record, @Param("example") ExamDetailExample example);

    int updateByExample(@Param("record") ExamDetail record, @Param("example") ExamDetailExample example);

    int updateByPrimaryKeySelective(ExamDetail record);

    int updateByPrimaryKey(ExamDetail record);
}
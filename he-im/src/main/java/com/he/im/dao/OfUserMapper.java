/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-24 Created
 */
package com.he.im.dao;

import com.he.im.model.profire.OfUser;
import com.he.im.model.profire.OfUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OfUserMapper {
    int countByExample(OfUserExample example);

    int deleteByExample(OfUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OfUser record);

    int insertSelective(OfUser record);

    List<OfUser> selectByExample(OfUserExample example);

    OfUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OfUser record, @Param("example") OfUserExample example);

    int updateByExample(@Param("record") OfUser record, @Param("example") OfUserExample example);

    int updateByPrimaryKeySelective(OfUser record);

    int updateByPrimaryKey(OfUser record);
}
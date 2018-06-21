package com.ai.smart.bottom.common.mapper;

import com.ai.smart.bottom.common.model.ComSection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>ComSectionMapper 类<br>
 * <b>日期：</b> 2018-05-07 10:22:10 <br>
 */
public interface ComSectionMapper {

    /**
     * 主键查询数据
     */
    ComSection selectByPrimaryKey(String sectionName);

    /**
     * 新增数据
     */
    int insert(ComSection record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(ComSection record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(ComSection record);

    ComSection findByModel(ComSection record);

}

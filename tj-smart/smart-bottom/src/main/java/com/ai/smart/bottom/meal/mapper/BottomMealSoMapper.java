package com.ai.smart.bottom.meal.mapper;

import com.ai.smart.bottom.meal.model.BottomMealSo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMealSoMapper 类<br>
 * <b>日期：</b> 2018-05-15 10:35:48 <br>
 */
public interface BottomMealSoMapper {

    /**
     * 主键查询数据
     */
    BottomMealSo selectByPrimaryKey(Long id);

    /**
     * 新增数据
     */
    int insert(BottomMealSo record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomMealSo record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomMealSo record);

    int updateStatusById(Long id);

}

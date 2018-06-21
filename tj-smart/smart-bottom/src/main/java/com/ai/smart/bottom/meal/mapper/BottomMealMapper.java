package com.ai.smart.bottom.meal.mapper;

import com.ai.smart.bottom.meal.model.BottomMeal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMealMapper 类<br>
 * <b>日期：</b> 2018-05-14 18:08:06 <br>
 */
public interface BottomMealMapper {

    /**
     * 主键查询数据
     */
    BottomMeal selectByPrimaryKey(Long id);

    /**
     * 新增数据
     */
    int insert(BottomMeal record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomMeal record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomMeal record);

    List<BottomMeal> findMealByOnline();

    BottomMeal findMealByNcode(String ncode);

    List<BottomMeal> findSubMealByMealId(Long parentId);

}

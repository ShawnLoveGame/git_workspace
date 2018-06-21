package com.ai.smart.bottom.meal.service;

import com.ai.smart.bottom.meal.mapper.BottomMealMapper;
import com.ai.smart.bottom.meal.model.BottomMeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MealService {

    @Autowired
    private BottomMealMapper bottomMealMapper;



    /**
     * 查询有效的套餐商品
     * @return
     */
    public List<BottomMeal> fingMealsByOnline(){
        List<BottomMeal> mealByOnline = bottomMealMapper.findMealByOnline();
        return mealByOnline;
    }

    public BottomMeal findMealByNcode(String ncode){
        return bottomMealMapper.findMealByNcode(ncode);
    }

    /**
     * 根据id查询主套餐信息
     * @param id
     * @return
     */
    public BottomMeal findMealById(Long id){
        return bottomMealMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询对应的子套餐列表
     * @param parentId
     * @return
     */
    public List<BottomMeal> findSubMealByMealId(Long parentId){
        return bottomMealMapper.findSubMealByMealId(parentId);
    }


}

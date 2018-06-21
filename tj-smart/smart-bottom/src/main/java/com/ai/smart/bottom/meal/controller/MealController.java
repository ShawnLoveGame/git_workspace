package com.ai.smart.bottom.meal.controller;

import com.ai.smart.bottom.base.BaseController;
import com.ai.smart.bottom.meal.service.MealService;
import com.ai.smart.common.base.GlobalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/meal",description = "套餐信息接口")
@RequestMapping("/meal")
@RestController
public class MealController extends BaseController{

    @Autowired
    private MealService mealService;

    @ApiOperation(notes = "/fingMealsByOnline",value = "查询有效的套餐列表")
    @RequestMapping(value = "/fingMealsByOnline",method = RequestMethod.POST)
    public GlobalResponse fingMealsByOnline(){
        return GlobalResponse.success(mealService.fingMealsByOnline());
    }

    @ApiOperation(notes = "/findSubMealByMealId",value = "查询子套餐列表")
    @RequestMapping(value = "/findSubMealByMealId",method = RequestMethod.POST)
    public GlobalResponse findSubMealByMealId(Long mealId){
        return GlobalResponse.success(mealService.findSubMealByMealId(mealId));
    }

}

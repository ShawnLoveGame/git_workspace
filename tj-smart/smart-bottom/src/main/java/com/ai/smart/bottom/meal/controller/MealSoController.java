package com.ai.smart.bottom.meal.controller;

import com.ai.smart.bottom.base.BaseController;
import com.ai.smart.bottom.filter.LoginAuth;
import com.ai.smart.bottom.meal.service.MealSoService;
import com.ai.smart.common.base.GlobalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/mealSo",description = "套餐办理接口")
@RequestMapping("/mealSo")
@RestController
public class MealSoController extends BaseController{

    @Autowired
    private MealSoService mealSoService;

    @LoginAuth
    @ApiOperation(notes = "/createMealSo",value = "办理套餐业务")
    @RequestMapping(value = "/createMealSo",method = RequestMethod.POST)
    public GlobalResponse createMealSo(String ncode){
        return mealSoService.createMealSo(ncode,getLoginUser());
    }

    @LoginAuth
    @ApiOperation(notes = "/getMiguSo",value = "咪咕流量包领取")
    @RequestMapping(value = "/getMiguSo",method = RequestMethod.POST)
    public GlobalResponse getMiguSo(){
        return mealSoService.getMiguSo(getLoginUser());
    }

}

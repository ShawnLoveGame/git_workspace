package com.ai.smart.third.confiscation.controller;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.third.confiscation.service.HtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/meal",description = "套餐服务api")
@RequestMapping("/meal")
@RestController
public class MealController {

    @Autowired
    private HtcService htcService;

    @ApiOperation(notes = "/addMealByNcode",value = "新增业务")
    @RequestMapping(value = "/addMealByNcode",method = RequestMethod.POST)
    public GlobalResponse addMealByNcode(String serialNum,String mealCode,String efftype){
        return htcService.addMealByNcode(serialNum,mealCode,efftype);
    }
    @ApiOperation(notes = "/changeMeal",value = "业务转移")
    @RequestMapping(value = "/changeMeal",method = RequestMethod.POST)
    public  GlobalResponse changeMeal(String serialNum,String mealCode,String efftype,String cparam){
        return htcService.changeMeal(serialNum,mealCode,efftype,cparam);
    }

    @ApiOperation(notes = "/checkZzMutex",value = "增值业务互斥检查")
    @RequestMapping(value = "/checkZzMutex",method = RequestMethod.POST)
    public GlobalResponse checkZzMutex(String serialNum,String ncode,String efftype){
        return htcService.checkZzMutex(serialNum,ncode,efftype);
    }

    @ApiOperation(notes = "/checkIsMealOnMonth",value = "当月是否办理过主体产品变更")
    @RequestMapping(value = "/checkIsMealOnMonth",method = RequestMethod.POST)
    public GlobalResponse  checkIsMealOnMonth(String serialNum){
        return htcService.checkIsMealOnMonth(serialNum);
    }

    @ApiOperation(notes = "/findCurrentMeal",value = "获取当前手机号的资费信息")
    @RequestMapping(value = "/findCurrentMeal",method = RequestMethod.POST)
    public GlobalResponse findCurrentMeal(String serialNum){
        return htcService.findCurrentMeal(serialNum);
    }
}

package com.ai.smart.third.confiscation.controller;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.third.confiscation.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(value = "/recharge",description = "缴费服务api")
@RequestMapping("/recharge")
@RestController
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @ApiOperation(notes = "/rechargeOnline",value = "用户手机充值缴费")
    @RequestMapping(value = "/rechargeOnline",method = RequestMethod.POST)
    public GlobalResponse rechargeOnline(String serialnum, BigDecimal amount){
        return rechargeService.rechargeOnline(serialnum,amount);
    }

    @ApiOperation(notes = "/largessFee",value = "赠费上账")
    @RequestMapping(value = "/largessFee",method = RequestMethod.POST)
    public GlobalResponse largessFee(String formNum,String serialNum, BigDecimal amount,String subjectId,
                                     String notes){
        return rechargeService.largessFee(formNum,serialNum,amount,subjectId,notes);
    }
}

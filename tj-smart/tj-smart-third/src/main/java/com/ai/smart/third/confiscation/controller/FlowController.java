package com.ai.smart.third.confiscation.controller;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.third.confiscation.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/flow",description = "流量服务api")
@RequestMapping("/flow")
@RestController
public class FlowController {

    @Autowired
    private FlowService flowService;

    @ApiOperation(notes = "/handlePackage",value = "流量包办理")
    @RequestMapping(value = "/handlePackage",method = RequestMethod.POST)
    public GlobalResponse handlePackage(String serialNum,String packageCode,String effectType){
        return flowService.handlePackage(serialNum,packageCode,effectType);
    }

    @ApiOperation(notes = "/unsubscribePackage",value = "流量包退订")
    @RequestMapping(value = "/unsubscribePackage",method = RequestMethod.POST)
    public GlobalResponse unsubscribePackage(String serialNum,String packageCode){
        return flowService.unsubscribePackage(serialNum,packageCode);
    }


    @ApiOperation(notes = "/findMyPackage",value = "根据手机号码查询订购的流量包")
    @RequestMapping(value = "/findMyPackage",method = RequestMethod.POST)
    public GlobalResponse findMyPackage(String serialNum){
        return flowService.findMyPackage(serialNum);
    }
}

package com.ai.smart.third.confiscation.controller;


import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.third.confiscation.service.HtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/htc",description = "三户服务api")
@RequestMapping("/htc")
@RestController
public class HtcController {

    @Autowired
    private HtcService htcService;

    @ApiOperation(notes = "/findHtcBySerialNum", value = "根据手机号码查询三户接口")
    @RequestMapping(value = "/findHtcBySerialNum", method = RequestMethod.POST)
    public GlobalResponse findHtcBySerialNum(String serialNum) {
        return htcService.findHtcBySerialNum(serialNum);
    }

    @ApiOperation(notes = "/checkIsQcyd", value = "校验是否为重庆移动号码")
    @RequestMapping(value = "/checkIsQcyd", method = RequestMethod.POST)
    public GlobalResponse checkIsQcyd(String serialNum){
        return htcService.checkIsQcyd(serialNum);
    }

    @ApiOperation(notes = "/findTelFare", value = "话费查询")
    @RequestMapping(value = "/findTelFare", method = RequestMethod.POST)
    public GlobalResponse findTelFare(String serialNum) {
        return htcService.findTelFare(serialNum);
    }

    @ApiOperation(notes = "/findMargin", value = "余量查询")
    @RequestMapping(value = "/findMargin", method = RequestMethod.POST)
    public GlobalResponse findMargin(String serialNum, String month) {
        return htcService.findMargin(serialNum, month);
    }

    @ApiOperation(notes = "/findPaymentRecByMonth", value = "按月份查询缴费记录")
    @RequestMapping(value = "/findPaymentRecByMonth", method = RequestMethod.POST)
    public GlobalResponse findPaymentRecByMonth(String serialNum, String month) {
            return htcService.findPaymentRecByMonth(serialNum, month);
    }

    @ApiOperation(notes = "/findBelongs",value = "校验是否重庆移动")
    @RequestMapping(value = "/findBelongs",method = RequestMethod.POST)
    public GlobalResponse findBelong(String serialNum){
        return htcService.checkIsQcyd(serialNum);
    }

    @ApiOperation(notes = "/findBillByMonth",value = "按月份查询账单记录")
    @RequestMapping(value = "/findBillByMonth",method = RequestMethod.POST)
    public GlobalResponse findBillByMonth(String serialNum,String month){
        return htcService.findBillByMonth(serialNum,month);
    }

    @ApiOperation(notes = "/findPoings",value = "积分查询")
    @RequestMapping(value = "/findPoings",method = RequestMethod.POST)
    public GlobalResponse findPoings(String serialNum){
        return htcService.findPoings(serialNum);
    }

    @ApiOperation(notes = "/queryUserCenterBasicInfo",value = "查询用户中心三项基本信息")
    @RequestMapping(value = "/queryUserCenterBasicInfo",method = RequestMethod.POST)
    public GlobalResponse queryUserCenterBasicInfo(String serialNum,String month){
        return htcService.queryUserCenterBasicInfo(serialNum,month);
    }

    @ApiOperation(notes="/queryBusinessOpenedAndPackageUsage",value = "业务开通和套餐使用状态查询")
    @RequestMapping(value = "queryBusinessOpenedAndPackageUsage",method = RequestMethod.POST)
    public GlobalResponse queryBusinessOpenedAndPackageUsage(String serialNum,String flag,Integer qryNextMonth,String privid){
        return htcService.queryBusinessOpenedAndPackageUsage(serialNum,flag,qryNextMonth,privid);
    }


}

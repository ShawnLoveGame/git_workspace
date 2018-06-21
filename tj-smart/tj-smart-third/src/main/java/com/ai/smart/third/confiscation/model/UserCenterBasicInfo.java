package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class UserCenterBasicInfo {

    /*
    用户中心三项基本信息实体
     */

    private double monthConsume;//本月消费
    private double remainingCharge;//当前话费余额
    private String usableIntegral;//可用积分

    public UserCenterBasicInfo(){

    }

    public UserCenterBasicInfo(double monthConsume,double remainingCharge,String usableIntegral){
        this.monthConsume = monthConsume;
        this.remainingCharge = remainingCharge;
        this.usableIntegral = usableIntegral;
    }

}

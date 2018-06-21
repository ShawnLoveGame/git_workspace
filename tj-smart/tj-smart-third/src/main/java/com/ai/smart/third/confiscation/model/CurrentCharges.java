package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class CurrentCharges {

    /*
    当前余额查询结果实体
     */

    private double leftmoney;//账户余额

    public CurrentCharges(){

    }

    public CurrentCharges(double leftmoney){
        this.leftmoney = leftmoney;
    }

}

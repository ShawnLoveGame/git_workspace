package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class AccountInfo {

    /*
    账户查询结果实体
     */

    private String cycle;//账期
    private double tatalefee;//当月费用
    private double present;//当月赠送话费
    private double charge;//当月赠送话费


    public AccountInfo(){

    }

    public AccountInfo(String cycle,double tatalefee,double present,double charge){
        this.cycle = cycle;
        this.tatalefee = tatalefee;
        this.present = present;
        this.charge = charge;
    }
}

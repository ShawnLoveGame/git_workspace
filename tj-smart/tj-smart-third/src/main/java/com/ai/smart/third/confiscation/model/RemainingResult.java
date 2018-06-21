package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class RemainingResult {

    /*
    查询剩余信息的结果集
     */
    private String privid;//优惠编码
    private String name;//累积量名称
    private int type;//累积量类型：1- GSM秒数2- 短信条数3- 彩信条数
                     // 4- GPRS流量(单位:B)5- 费用6- 彩铃条数
    private double total;//累积量总量
    private double used;//累积量总量已用
    private double remain;//累积量总量剩余
    private String priname;//优惠名称
    private int tfflag;//流量统计标志：1：定向不限流量2：定向限量统付
                       // 3：通用不限流量4：通用限量统付 其它传null
    private String bbossprodname;//流量统付产品，传名称，其他传null

    public RemainingResult(){

    }

    public RemainingResult(String privid,String name,int type,double total,
                           double used,double remain,String priname,int tfflag,
                           String bbossprodname){
        this.privid = privid;
        this.name = name;
        this.type = type;
        this.total = total;
        this.used = used;
        this.remain = remain;
        this.priname = priname;
        this.tfflag = tfflag;
        this.bbossprodname = bbossprodname;
    }

}

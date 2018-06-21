package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 缴费记录查询  100012
 */
@Data
public class PaymentVo {
    private  String detailinfo;
    private  String row;//结果集行标志
    private  String itemname;//业务名称
    private  String recdate;//操作时间 格式yyyy-mm-dd hh24:mi:ss
    private  int recfee;//缴费金额	单位:分
    private  String status;//状态中文说明	正常或回退
    private  String recopid;//受理工号(渠道工号)	办理缴费业务的操作员工号

}

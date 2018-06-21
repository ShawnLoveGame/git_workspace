package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 缴费记录查询
 */
@Data
public class HistoryFeeVo {
    private String DETAILINFO;
    private String ROW;//标志返回结果集合中的一条记录,可以返回多条
    private String ITEMNAME;//业务名称
    private String RECDATE;//操作时间 格式yyyy-mm-dd hh24:mi:ss
    private String RECFEE;//缴费金额 单位:分
    private String STATUS;//状态中文说明 正常或回退
    private String RECOPID;//受理工号(渠道工号) 办理缴费业务的操作员工号

}

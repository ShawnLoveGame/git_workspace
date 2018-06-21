package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 余量查询
 */
@Data
public class LeftAmountVo {
    private String DETAILINFO;
    private String ROW;//	结果集行标志
    private String PRIVID;//
    private String  NAME;//	累计量名称
    private String  TYPE;//1- GSM秒数2- 短信条数3- 彩信条数4- GPRS流量(单位:B)5- 费用6- 彩铃条数

    private String  TOTAL;//赠送总量 针对不限流量套餐，此字段请展现为“不限”
    private String  USED;//已经使用
    private String  REMAIN;//剩余量
    private String  PRINAME;//优惠名称
    private String  TFFLAG;//流量统付标记1：定向不限流量2：定向限量统付3：通用不限流量4：通用限量统付 其它传空

    private String BBOSSPRODNAME;//客户产品     针对流量统付传产品名称，其它传空



}

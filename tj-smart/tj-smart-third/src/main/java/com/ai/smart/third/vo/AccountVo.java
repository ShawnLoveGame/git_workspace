package com.ai.smart.third.vo;

import lombok.Data;

@Data
public class AccountVo {
    private  String CYCLE;// 查询月份
    private  String TATLEFEE;//当月费用
    private  String PRESENT;//当月赠送话费
    private  String CHARGE;//	当月已交费
}

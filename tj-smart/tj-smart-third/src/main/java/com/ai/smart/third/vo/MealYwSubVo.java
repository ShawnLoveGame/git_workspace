package com.ai.smart.third.vo;

import lombok.Data;

@Data
public class MealYwSubVo {

    //套餐名称
    private String NAME;

    private String SDATE;

    private String EDATE;

    //编码ID
    private String ITEMID;

    //费用标准:xx元/月
    //
    //xx元/天
    //
    //xx元/月按月分摊
    //
    //按附加属性(此类型费用返回为空)
    //
    //xx元/一次性
    //
    //无固定费(此类型返回为空)
    private String FEEDESC;

    //服务绑定标识 :1:绑定不能单独取消
    //0:可以单独取消
    private String serviceflag;

    //受理时间
    private String RECDATE;
}

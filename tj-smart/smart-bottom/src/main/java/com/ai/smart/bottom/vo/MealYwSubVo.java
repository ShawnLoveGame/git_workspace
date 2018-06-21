package com.ai.smart.bottom.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.Data;

@Data
public class MealYwSubVo extends PropertyNamingStrategy {

    //套餐名称
    @JsonProperty("NAME")
    private String NAME;
    @JsonProperty("SDATE")
    private String SDATE;
    @JsonProperty("EDATE")
    private String EDATE;

    //编码ID
    @JsonProperty("ITEMID")
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
    @JsonProperty("FEEDESC")
    private String FEEDESC;

    //服务绑定标识 :1:绑定不能单独取消
    //0:可以单独取消
    @JsonProperty("serviceflag")
    private String serviceflag;

    //受理时间
    @JsonProperty("RECDATE")
    private String RECDATE;
}

package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 最新余额查询
 */
@Data
public class Uip100041 {

    @JsonProperty(value = "X_RESULTCODE")
    private String x_resultcode;

    @JsonProperty(value = "X_RESULTINFO")
    private String x_resultinfo;

    @ApiModelProperty(notes = "当前余额")
    @JsonProperty(value = "LEFTMONEY")
    private String leftmoney;
}

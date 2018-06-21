package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 帐户信息
 */
@Data
public class Uip100008 {

    @JsonProperty(value = "X_RESULTCODE")
    private String x_resultcode;

    @JsonProperty(value = "X_RESULTINFO")
    private String x_resultinfo;

    @JsonProperty(value = "CYCLE")
    @ApiModelProperty(notes = "查询月份(帐期)YYYYMMDD")
    private String cycle;

    @JsonProperty(value = "TATLEFEE")
    @ApiModelProperty(notes = "当月费用")
    private String tatlefee;

    @JsonProperty(value = "PRESENT")
    @ApiModelProperty(notes = "当月赠送话费")
    private String present;

    @JsonProperty(value = "CHARGE")
    @ApiModelProperty(notes = "当月已交费")
    private String charge;


}

package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Uip100213 {

    @ApiModelProperty(notes = "结果集")
    @JsonProperty(value = "RESULT")
    private  String result;
}

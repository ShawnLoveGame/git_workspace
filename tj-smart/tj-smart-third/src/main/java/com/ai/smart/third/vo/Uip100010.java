package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Uip100010 {

    @JsonAlias({"x_recordnum","X_RECORDNUM"})
    private String x_recordnum;

    @JsonAlias({"availscore","AVAILSCORE"})
    @ApiModelProperty(notes = "可用积分")
    private String availscore;

    @JsonAlias({"presentscore","PRESENTSCORE"})
    @ApiModelProperty(notes = "赠送积分")
    private String presentscore;

    @JsonAlias({"x_resultcode","X_RESULTCODE"})
    private String x_resultcode;

    @JsonAlias({"x_resultinfo","X_RESULTINFO"})
    private String x_resultinfo;

}

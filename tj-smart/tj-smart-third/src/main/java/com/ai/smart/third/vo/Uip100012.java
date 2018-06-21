package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 缴费记录查询
 */
@Data
public class Uip100012 {

    @ApiModelProperty(notes = "记录数目")
    @JsonAlias({"x_recordnum","X_RECORDNUM"})
    private String x_recordnum;

    @JsonAlias({"status","STATUS"})
    @ApiModelProperty(notes = "正常或回退")
    private String status;

    @JsonAlias({"recdate","RECDATE"})
    @ApiModelProperty(notes = "操作时间")
    private String recdate;

    @JsonAlias({"x_resultcode","X_RESULTCODE"})
    private String x_resultcode;

    @JsonAlias({"recopid","RECOPID"})
    @ApiModelProperty(notes = "受理工号(渠道工号)")
    private String recopid;

    @JsonAlias({"x_resultinfo","X_RESULTINFO"})
    private String x_resultinfo;

    @JsonAlias({"itemname","ITEMNAME"})
    @ApiModelProperty(notes = "业务名称")
    private String itemname;

    @JsonAlias({"recfee","RECFEE"})
    @ApiModelProperty(notes = "缴费金额单位:分")
    private String recfee;
}

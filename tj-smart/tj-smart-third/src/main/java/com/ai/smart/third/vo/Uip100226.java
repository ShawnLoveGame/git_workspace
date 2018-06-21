package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Uip100226 {

    @ApiModelProperty(notes = "电话号码")
    @JsonAlias({"telnum","TELNUM"})
    private  String  telnum; // 电话号码

    @JsonAlias({"retflag","RETFLAG"})
    @ApiModelProperty(notes = "0:校验通过1:存在互斥 以上两种情况RETCODE返回码为100，其它错误时如产品不可用等错误信息在RETCODE和RETMSG节点中描述    当retflag=0时，以下的互斥相关节点无返回值")
    private  String retflag;//

    @JsonAlias({"mtxid","MTXID"})
    @ApiModelProperty(notes = "互斥业务ID")
    private  String    mtxid;//

    @JsonAlias({"mtxname","MTXNAME"})
    @ApiModelProperty(notes = "互斥业务名称")
    private  String  mtxname;//互斥业务名称

    @JsonAlias({"mtxstartdate","MTXSTARTDATE"})
    @ApiModelProperty(notes = "互斥业务开始时间")
    private  String mtxstartdate;//MTXSTARTDATE

    @JsonAlias({"mtxenddate","MTXENDDATE"})
    @ApiModelProperty(notes = "互斥业务结束时间")
    private  String mtxenddate;//互斥业务结束时间
}

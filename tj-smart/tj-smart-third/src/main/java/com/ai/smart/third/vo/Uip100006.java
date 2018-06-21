package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Uip100006 {

    @ApiModelProperty(notes = "优惠列表数量")
    @JsonAlias({"pricount","PRICOUNT"})
    private String  pricount;

    @ApiModelProperty(notes = "服务列表数量")
    @JsonAlias({"srvcount","SRVCOUNT"})
    private String srvcount;

    @ApiModelProperty(notes = "描述")
    @JsonAlias({"detailinfo","DETAILINFO"})
    private String detailinfo;

    @ApiModelProperty(notes = "结果集行标志")
    @JsonAlias({"row","ROW"})
    private String row;

    @ApiModelProperty(notes = "套餐名称")
    @JsonAlias({"name","NAME"})
    private String name;

    @ApiModelProperty(notes = "生效时间")
    @JsonAlias({"sdate","SDATE"})
    private String sdate;

    @ApiModelProperty(notes = "失效时间")
    @JsonAlias({"edate","EDATE"})
    private String edate;

    @ApiModelProperty(notes = "编码ID")
    @JsonAlias({"itemid","ITEMID"})
    private String itemid;

    @ApiModelProperty(notes = "费用标准")
    @JsonAlias({"feedesc","FEEDESC"})
    private String feedesc;

    @ApiModelProperty(notes = "服务绑定标识 1:绑定不能单独取消 0:可以单独取消")
    @JsonAlias({"serviceflag","SERVICEFLAG"})
    private String serviceflag;

    @ApiModelProperty(notes = "办理日期")
    @JsonAlias({"recdate","RECDATE"})
    private String recdate;

}

package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 100004接口返回对象
 */
@Data
public class Uip100004 {

    @JsonAlias({"custmgr","CUSTMGR"})
    @ApiModelProperty(notes = "客户经理")
    private String custmgr;

    @ApiModelProperty(notes = "主品牌编码")
    @JsonAlias({"productgroupid","PRODUCTGROUPID"})
    private String productgroupid;

    @ApiModelProperty(notes = "开卡时间")
    @JsonAlias({"startdate","STARTDATE"})
    private String startdate;

    @ApiModelProperty(notes = "用户卡归属地")
    @JsonAlias({"simorgid","SIMORGID"})
    private String simorgid;

    @ApiModelProperty(notes = "Vip卡号")
    @JsonAlias({"vipid","VIPID"})
    private String vipid;

   @JsonAlias({"custmgrname","CUSTMGRNAME"})
    @ApiModelProperty(notes = "重要客户的个人客户经理名称")
    private String custmgrname;

 @JsonAlias({"netage","NETAGE"})
    @ApiModelProperty(notes = "入网时间")
    private String netage;

 @JsonAlias({"subsgroupid","SUBSGROUPID"})
    @ApiModelProperty(notes = "二级品牌名称")
    private String subsgroupid;

   @JsonAlias({"custmgrphone","CUSTMGRPHONE"})
    @ApiModelProperty(notes = "重要客户的个人客户经理联系电话")
    private String custmgrphone;

    @JsonAlias({"userid","USERID"})
    @ApiModelProperty(notes = "用户id")
    private String userid;

   @JsonAlias({"username","USERNAME"})
    @ApiModelProperty(notes = "用户姓名")
    private String username;

    @JsonAlias({"grplinkman","GRPLINKMAN"})
    @ApiModelProperty(notes = "是否重要客户集团联系人1:是 0:否")
    private String grplinkman;

    @JsonAlias({"x_resultcode","X_RESULTCODE"})
    @ApiModelProperty(notes = "返回编码")
    private String x_resultcode;

    @JsonAlias({"birthday","BIRTHDAY"})
    @ApiModelProperty(notes = "生日")
    private String birthday;

    @JsonAlias({"x_resultinfo","X_RESULTINFO"})
    @ApiModelProperty(notes = "结果信息")
    private String x_resultinfo;

    @JsonAlias({"grpkeyman","GRPKEYMAN"})
    @ApiModelProperty(notes = "是否重要客户集团关键人")
    private String grpkeyman;

     @JsonAlias({"vipenddate","VIPENDDATE"})
     @ApiModelProperty(notes = "Vip卡终止日期")
    private String vipenddate;

 @JsonAlias({"x_recordnum","X_RECORDNUM"})
    @ApiModelProperty(notes = "记录数目")
    private String x_recordnum;

    @ApiModelProperty(notes = "用户前3个月平均消费")
    @JsonAlias({"arpu","ARPU"})
    private String arpu;

    @ApiModelProperty(notes = "重要客户标识")
    @JsonAlias({"important","IMPORTANT"})
    private String important;

    @ApiModelProperty(notes = "主优惠中文说明")
    @JsonAlias({"mainprivname","MAINPRIVNAME"})
    private String mainprivname;

    @ApiModelProperty(notes = "主品牌中文说明")
    @JsonAlias({"productgroupname","PRODUCTGROUPNAME"})
    private String productgroupname;

    @ApiModelProperty(notes = "重要客户的集团客户经理名称")
    @JsonAlias({"grpcustmgrname","GRPCUSTMGRNAME"})
    private String grpcustmgrname;

    @ApiModelProperty(notes = "证件号码")
    @JsonAlias({"certid","CERTID"})
    private String certid;

    @ApiModelProperty(notes = "重要客户的集团客户经理号码")
    @JsonAlias({"grpcustmgrphone","GRPCUSTMGRPHONE"})
    private String grpcustmgrphone;

   @JsonAlias({"communityattr","COMMUNITYATTR"})
   private String communityattr;

    @ApiModelProperty(notes = "主优惠编码")
    @JsonAlias({"mainprivid","MAINPRIVID"})
    private String mainprivid;

}

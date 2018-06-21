package com.ai.smart.third.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

@Data
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class Uip100007 {
    @ApiModelProperty(notes = "")
    @JsonAlias({"detailinfo"})
    private String detailinfo;

    @ApiModelProperty(notes = "结果集行标志")
    @JsonAlias({"row","ROW"})
    private String row;//	结果集行标志

    @ApiModelProperty(notes = "")
    @JsonAlias({"privid","PRIVID"})
    private String privid;//

    @ApiModelProperty(notes = "累计量名称")
    @JsonAlias({"name","NAME"})
    private String  name;//	累计量名称

    @ApiModelProperty(notes = "1- GSM秒数2- 短信条数3- 彩信条数4- GPRS流量(单位:B)5- 费用6- 彩铃条数")
    @JsonAlias({"type","TYPE"})
    private String  type;//1- GSM秒数2- 短信条数3- 彩信条数4- GPRS流量(单位:B)5- 费用6- 彩铃条数

    @ApiModelProperty(notes = "赠送总量 针对不限流量套餐，此字段请展现为“不限”")
    @JsonAlias({"total","TOTAL"})
    private String  total;//赠送总量 针对不限流量套餐，此字段请展现为“不限”

    @ApiModelProperty(notes = "已经使用")
    @JsonAlias({"used","USED"})
    private String  used;//已经使用

    @ApiModelProperty(notes = "剩余量")
    @JsonAlias({"remain","REMAIN"})
    private String  remain;//剩余量

    @ApiModelProperty(notes = "优惠名称")
    @JsonAlias({"priname","PRINAME"})
    private String  priname;//优惠名称

    @ApiModelProperty(notes = "流量统付标记1：定向不限流量2：定向限量统付3：通用不限流量4：通用限量统付 其它传空")
    @JsonAlias({"tfflag","TFFLAG"})
    private String  tfflag;//流量统付标记1：定向不限流量2：定向限量统付3：通用不限流量4：通用限量统付 其它传空

    @ApiModelProperty(notes = "客户产品     针对流量统付传产品名称，其它传空")
    @JsonAlias({"bbossprodname","BBOSSPRODNAME"})
    private String  bbossprodname ;//客户产品     针对流量统付传产品名称，其它传空

}

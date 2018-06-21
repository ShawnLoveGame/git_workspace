package com.ai.smart.third.confiscation.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MyPackageVo {
    private String packageName;//流量包名

    private int  packageSize;//流量包大小

    private BigDecimal packagePrice;//价格

    private Date endTime;//有效截至日期

    private int isUse ;//是否有效1：有效 0：失效

    private int LastPackage;//剩余流量

     private  String packageCode;//流量包编码




}

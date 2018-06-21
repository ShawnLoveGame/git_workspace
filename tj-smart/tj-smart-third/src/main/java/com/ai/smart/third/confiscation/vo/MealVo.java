package com.ai.smart.third.confiscation.vo;

import lombok.Data;

import javax.annotation.security.DenyAll;

@Data
public class MealVo {


    /**
     * 套餐id
     */
    private String productId;
    /**
     * 用户网别： 1:2G,2:3G,3:固定电话,4:宽带（ADSL）,5:宽带（LAN）,6:小灵通,7:WLAN业务,8:融合,9:集团,10:上网卡,11:4G,12:身份证宽带绑定
     */
    private Integer netType;

    /**
     * 套餐
     */
    private String productName;

    /**
     * 付费类型 0：不区分 1：预付费  2：后付费
     */
    private Integer payType;


}

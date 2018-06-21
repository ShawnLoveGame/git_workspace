package com.ai.smart.third.confiscation.model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {

    private String username;//用户名
    private String certid;//证件号码;
    private String startDate;//开卡时间
    private String productName;//主产品名称
    private String productId;//主产品id
    private String productGroupId;//主产品编码
    private String productGroupName;//主产品中文名
    private String prePayType;//付费类型
    private List<String> privilege;//优惠名称列表
    private List<Integer> privilegeId;//优惠ID列表
    private String vipTypeName;//vip级别中文说明
    private String vipTypeId;//vip级别ID
    private String vipId;//vip卡号
    private double minFee;//最低消费（单位为分）
    private String subsGroupId;//二级品牌名称
    private String simOrgId;//用户卡归属地
    private String customer;//客户经理
    private String vipEndDate;//vip终止日期
    private Double arpu;//用户前三个月平均消费

}

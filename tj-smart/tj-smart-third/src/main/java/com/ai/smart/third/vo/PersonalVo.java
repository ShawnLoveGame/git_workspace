package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 个人信息查询
 */
@Data
public class PersonalVo {
    private String USERNAME;//用户名称
    private String CERTID;//CERTID	1		证件号码
    private String STARTDATE;//STARTDATE	1		开卡时间
    private String PRODUCTNAME;//PRODUCTNAME	1		主产品名称
    private String PRODUCTID;//PRODUCTID	1		主产品ID
    private String PRODUCTGROUPID;//PRODUCTGROUPID	1		主品牌编码
    private String PRODUCTGROUPNAME;//PRODUCTGROUPNAME	1		主品牌中文说明
    private String PREPAYTYPE;//PREPAYTYPE	1		付费类型
    private String PRIVILEGE;//PRIVILEGE	1		优惠名称列表
    private String PRIVILEGEID;//PRIVILEGEID	1		优惠ID列表
    private String VIPTYPENAME;//VIPTYPENAME	1		客户级别中文说明
    private String VIPTYPEID;//VIPTYPEID	1		客户级别ID
    private String VIPID;//VIPID	1		Vip卡号
    private String MINFEE;//MINFEE	1		最低消费金额
    private String SUBSGROUPID;//SUBSGROUPID	1		二级品牌名称
    private String SIMORGID;//SIMORGID	1		用户卡归属地
    private String CUSTMGR;//CUSTMGR	1		客户经理
    private String VIPENDDATE;//VIPENDDATE	1		Vip卡终止日期
    private String ARPU;//ARPU	1		用户前3个月平均消费
    private String MAINPRIVID;//MAINPRIVID	1		主优惠编码
    private String MAINPRIVNAME;//MAINPRIVNAME	1		主优惠中文说明
    private String IMPORTANT;//IMPORTANT	1		重要客户标识
    private String GRPCUSTMGRNAME;//GRPCUSTMGRNAME	1		重要客户的集团客户经理名称
    private String GRPCUSTMGRPHONE;//GRPCUSTMGRPHONE	1		重要客户的集团客户经理联系电话
    private String GRPKEYMAN;//GRPKEYMAN	1		是否重要客户集团关键人
    private String GRPLINKMAN;//GRPLINKMAN	1		是否重要客户集团联系人
    private String CUSTMGRNAME;//重要客户的个人客户经理名称
    private String CUSTMGRPHONE;//重要客户的个人客户经理联系电话
    private String CERTTYPE;//证件类型
    private String ZGDFLAG;//ZGDFLAG		1		中高端用户标识
    private String G3FLAG;//G3FLAG		1		G3用户标识
    private String FAMILYADDR;//FAMILYADDR	1		家庭住址
    private String TELNUM;//TELNUM	1		联系电话
    private String NOTES;//NOTES	1		其它信息
    private String MAIL;//MAIL	1		邮政编码
    private String USERID;//USERID	1		用户ID
    private String BIRTHDAY;//BIRTHDAY	?	F4	生日
    private String GPRSAVG;//GPRSAVG	1	V9	前3个月平均GPRS流量
    private String ISFAMILY;//ISFAMILY	1	F1	是否开通家庭账户
    private String ACCTID;//ACCTID	1	V32	帐户编号
    private String TSMAINPRIVID;//TSMAINPRIVID	?	V32	特殊主优惠编码
    private String TSMAINPRIVNAME;//TSMAINPRIVNAME	?	V32	特殊主优惠名称
}

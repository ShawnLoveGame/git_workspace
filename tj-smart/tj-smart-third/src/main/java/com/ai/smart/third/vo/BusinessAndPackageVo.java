package com.ai.smart.third.vo;

import lombok.Data;

@Data
public class BusinessAndPackageVo {
    private String PRICOUNT;//优惠列表数量
    private String SRVCOUNT;//服务列表数量
    private String ROW;//结果行标志
    private String DETAILINFO;
    private String NAME;//套餐名称
    private String SDATE;//生效时间
    private String EDATE;//失效时间
    private String ITEMID;//编码ID
    private String FEEDESC;//费用标准
    private String serviceflag;//1:绑定不能单独取消 0:可以单独取消
    private String RECDATE;//受理时间
}

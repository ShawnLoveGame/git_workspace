package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 产品受理接口返回VO  接口:100045
 */

@Data
public class ProAcVo {
    private String CURID;//当月已存在的优惠ID
    private String CURNAME;//当月已存在的优惠名称
    private String NEXTID;//下月有效的优惠ID
    private String NEXTNAME;//下月有效的优惠名称
    private String CUROUTPARAM;//指定优惠当月附加参数列表
    private String NEXTOUTPARAM;//指定优惠下月附加参数列表
}

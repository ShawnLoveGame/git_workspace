package com.ai.smart.third.vo;

import lombok.Data;

/**
 * 产品受理业务校验
 */
@Data
public class ProductVo {
    private String DOCMENT;//0：无档案 1：有档案
    private String ACCORDANT;//本月是否已办理过资费变更业务 1未办理  0已办理
    private String DETAILINFO;//
    private String ROW;//结果集行标志 标志返回结果集合中的一条记录,可以返回多条
    private String PRODID;//产品ID
    private String PRODNAME;//产品名称
    private String STARTDATE;//开始时间
}

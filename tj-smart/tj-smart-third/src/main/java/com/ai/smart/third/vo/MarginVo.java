package com.ai.smart.third.vo;

import lombok.Data;

import java.util.List;

/**
 * 余量查询返回实体  100007
 */
@Data
public class MarginVo {

    private Double sumToal;
    private Double sumRemain;
    private Double sumUsed;
    private List<Uip100007> list;

}

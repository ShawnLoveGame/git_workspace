package com.ai.smart.third.vo;

import lombok.Data;

import java.util.List;

@Data
public class MealYwVo {

    //优惠列表数量
    private String PRICOUNT;

    //服务列表数量
    private String SRVCOUNT;

    private List<MealYwSubVo> DETAILINFO;
}

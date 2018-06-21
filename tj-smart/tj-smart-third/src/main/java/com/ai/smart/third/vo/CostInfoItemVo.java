package com.ai.smart.third.vo;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class CostInfoItemVo {

    private String parent;

    private Map<String,String> subitem = Maps.newHashMap();
}

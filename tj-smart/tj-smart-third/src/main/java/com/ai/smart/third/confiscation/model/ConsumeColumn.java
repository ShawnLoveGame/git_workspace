package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class ConsumeColumn {

    private String columnName;//栏目名
    private double consumeAmount;//消费金额
    private boolean isParentColumn;//是否是父栏目

    public ConsumeColumn(String columnName,double consumeAmount,boolean isParentColumn){
        this.columnName = columnName;
        this.consumeAmount = consumeAmount;
        this.isParentColumn = isParentColumn;
    }
}

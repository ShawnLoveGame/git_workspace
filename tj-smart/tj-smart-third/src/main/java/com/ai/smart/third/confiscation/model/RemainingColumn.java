package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class RemainingColumn {

    private String columnName;//栏目名
    private double totalAmount;//总量
    private double remainingAmount;//已用量
    private boolean isFatherColumn;//是否是父栏目
    private boolean havaTotalAmount;//是否有总量

    public RemainingColumn(String columnName,double totalAmount,
                           double remainingAmount,boolean isFatherColumn,boolean havaTotalAmount){
        this.columnName = columnName;
        this.totalAmount = totalAmount;
        this.remainingAmount = remainingAmount;
        this.isFatherColumn = isFatherColumn;
        this.havaTotalAmount = havaTotalAmount;
    }

    public RemainingColumn(String columnName,double remainingAmount,
                           boolean isFatherColumn,boolean havaTotalAmount){
        this.columnName = columnName;
        this.remainingAmount = remainingAmount;
        this.isFatherColumn = isFatherColumn;
        this.havaTotalAmount = havaTotalAmount;
    }
}

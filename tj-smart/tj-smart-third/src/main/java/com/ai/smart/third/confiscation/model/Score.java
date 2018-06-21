package com.ai.smart.third.confiscation.model;

import lombok.Data;

@Data
public class Score {

    /*
    积分查询结果实体
     */

    private int availScore;//可用积分
    private int presentScore;//赠送积分

    public Score(){

    }

    public Score(int availScore,int presentScore){
        this.availScore = availScore;
        this.presentScore = presentScore;
    }

}

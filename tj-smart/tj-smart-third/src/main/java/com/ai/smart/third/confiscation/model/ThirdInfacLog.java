package com.ai.smart.third.confiscation.model;

import lombok.Data;

import java.sql.Timestamp;


/*
*日志模型
*/

@Data
public class ThirdInfacLog {

    private Long id;
    private String methodName;//调用的方法名
    private String serialNum;//参数  绑定的手机号码
    private String remark;//调用的方法的备注
    private String returnContent;//返回的报文
    private Timestamp createTime;//创建时间  方法调用的时间

    public ThirdInfacLog(String methodName,String serialNum,
                         String remark,String returnContent,Timestamp createTime){

        this.methodName = methodName;
        this.serialNum = serialNum;
        this.remark = remark;
        this.returnContent = returnContent;
        this.createTime = createTime;
    }
}

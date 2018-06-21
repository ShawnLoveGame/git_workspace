package com.ai.smart.third.vo;

import lombok.Data;

@Data
public class ZzMutexVo {

    private  String  TELNUM; // 电话号码
    private  String RETFLAG;//0:校验通过1:存在互斥 以上两种情况RETCODE返回码为100，其它错误时如产品不可用等错误信息在RETCODE和RETMSG节点中描述    当retflag=0时，以下的互斥相关节点无返回值
    private  String    MTXID;//互斥业务ID
    private  String  MTXNAME;//互斥业务名称
    private  String MTXSTARTDATE;//MTXSTARTDATE
    private  String MTXENDDATE;//互斥业务结束时间

}

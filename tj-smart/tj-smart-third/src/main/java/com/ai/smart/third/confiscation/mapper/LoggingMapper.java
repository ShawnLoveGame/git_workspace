package com.ai.smart.third.confiscation.mapper;


import com.ai.smart.third.confiscation.model.ThirdInfacLog;


/*
*日志mapper接口
*/
public interface LoggingMapper {

    /*
    *写入日志
    */
    int WriteLog(ThirdInfacLog log);
}

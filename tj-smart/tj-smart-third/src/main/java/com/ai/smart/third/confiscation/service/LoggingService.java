package com.ai.smart.third.confiscation.service;

import com.ai.smart.third.confiscation.mapper.LoggingMapper;
import com.ai.smart.third.confiscation.model.ThirdInfacLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/*
*日志记录服务
*/

@Slf4j
@Service
public class LoggingService {

    @Autowired
    private LoggingMapper loggingMapper;

    /*
    *写入日志
    * 日志内容：调用的方法名，手机号码，备注，返回结果，创建时间
    */
    public void writeLogInfo(String serialNum,String remark,
                              Object returnContent){
        try{
            //获取当前调用的方法名
            String methodName = new Throwable().getStackTrace()[1].getMethodName();
            //获取当前时间
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            //创建日志实体
//            ThirdInfacLog log = ;
            //写入日志
            String returnContentString;
            returnContentString = (returnContent == null ? "null" : returnContent.toString());
            loggingMapper.WriteLog(new ThirdInfacLog(methodName,serialNum,remark,returnContentString,nowTime));
        }catch(Exception e){
            e.printStackTrace();
        }




    }

}

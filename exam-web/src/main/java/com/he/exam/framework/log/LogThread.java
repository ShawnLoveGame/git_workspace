package com.he.exam.framework.log;

import com.he.exam.dao.log.BackOperateLogMapper;
import com.he.exam.model.log.BackOperateLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by he on 2017/1/5.
 */
public class LogThread extends Thread {

    private Log log = LogFactory.getLog(getClass());

    private BackOperateLog backLog ;
    private BackOperateLogMapper backOperateLogMapper;

    public LogThread(BackOperateLog backLog, BackOperateLogMapper backOperateLogMapper) {
        this.backLog = backLog;
        this.backOperateLogMapper = backOperateLogMapper;
    }

    @Override
    public void run() {
        try{
            backOperateLogMapper.insert(backLog);
        }catch (Exception e){
            log.error("日志插入异常" ,e);
        }
    }
}

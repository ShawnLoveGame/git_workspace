package com.he.exam.service.monitor;

import org.quartz.SchedulerException;

/**
 * Created by he on 2017/1/10.
 */
public class SchedulerFactroyBeanWithoutDestroy extends org.springframework.scheduling.quartz.SchedulerFactoryBean {

    @Override
    public void destroy() throws SchedulerException {
        super.destroy();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

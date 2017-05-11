package com.he.exam.task;

import com.he.exam.service.user.UserService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * Created by he on 2017/4/21.
 */
@DisallowConcurrentExecution
public class DailyWinTask implements Job {

    @Resource private UserService userService;
    public void execute(JobExecutionContext context) throws JobExecutionException {

        userService.calculateDailyWin();
    }
}

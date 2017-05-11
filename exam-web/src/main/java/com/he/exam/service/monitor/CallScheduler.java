package com.he.exam.service.monitor;

import com.he.exam.model.exam.ScheduleJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;


public class CallScheduler implements ApplicationListener<ContextRefreshedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				Scheduler scheduler = schedulerFactoryBean.getScheduler();
				// 这里获取任务信息数据   初始化启动 定时任务
				List<ScheduleJob> jobList = scheduleJobService.findScheduleJobsByDTOForTask();
				for (ScheduleJob job : jobList) {
					logger.error("当前定时器为："+job.getJobName());
					doScheduler(job,scheduler);
				}
			} catch (Exception e) {
				logger.error("定时器初始化异常："+e.getMessage());
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doScheduler(ScheduleJob job,Scheduler scheduler){
		try {
			if(scheduler == null){
				scheduler = schedulerFactoryBean.getScheduler();
			}
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				Class clazz = Class.forName(job.getJobClass());
				JobBuilder jb= JobBuilder.newJob(clazz);
				jb.withIdentity(job.getJobName(),job.getJobGroup());
				jb.withDescription(job.getJobDesc());
				JobDetail jobDetail = jb.build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(),job.getJobGroup()).withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
			updateScheduler(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateScheduler(ScheduleJob job) throws SchedulerException{
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		//0 1 2 停用
        if(job.getJobStatus() == 0){
		    scheduler.pauseTrigger(triggerKey);
        }
        // 启用
        if(job.getJobStatus() == 1){
		    scheduler.resumeTrigger(triggerKey);
        }
        //2删除
        if(job.getJobStatus() == 2){
		    scheduler.deleteJob(jobKey);
        }
        // 立即运行 scheduler.triggerJob(jobKey);
        if(job.getJobStatus() == 3){
        	scheduler.triggerJob(jobKey);
        }
	}
}

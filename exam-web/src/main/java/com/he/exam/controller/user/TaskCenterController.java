package com.he.exam.controller.user;

import com.he.exam.model.exam.ScheduleJob;
import com.he.exam.service.monitor.ScheduleJobService;
import com.he.exam.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 
 * 定时器控制器
 * 
 * @author daizy
 *
 */
@Controller
public class TaskCenterController {
	
	@Resource
	private ScheduleJobService scheduleJobService;
	
	/**
	 * @desc int smsSendLog page
	 * @return
	 */
	@RequestMapping("/job/initScheduleJobPage")
	public String initScheduleJobPage(){
		return "user/taskCenter";
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping("/job/findScheduleJobsPgByDTO")
	@ResponseBody
	public Pagination<ScheduleJob> findScheduleJobsByDTO(){
		return scheduleJobService.findScheduleJobsByDTO();
	}
	

	@RequestMapping("/job/updateScheduleJob")
	@ResponseBody
	public void updateScheduleJob(ScheduleJob job){
		 scheduleJobService.updateScheduleJob(job);
	}
	


}

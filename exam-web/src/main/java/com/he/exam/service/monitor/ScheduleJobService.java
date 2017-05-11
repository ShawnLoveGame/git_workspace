package com.he.exam.service.monitor;

import com.he.exam.dao.exam.ScheduleJobMapper;
import com.he.exam.model.exam.ScheduleJob;
import com.he.exam.model.exam.ScheduleJobExample;
import com.he.exam.util.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("scheduleJobService")
public class ScheduleJobService {
	
	@Resource
	private ScheduleJobMapper scheduleJobMapper;
	@Resource private CallScheduler callScheduler;


	public List<ScheduleJob> findScheduleJobsByDTOForTask() {

		ScheduleJobExample scheduleJobExample  = new ScheduleJobExample();
		return scheduleJobMapper.selectByExample(scheduleJobExample);
	}

	public Pagination<ScheduleJob> findScheduleJobsByDTO() {
		Pagination<ScheduleJob> pg = new Pagination<ScheduleJob>();
		ScheduleJobExample example = new ScheduleJobExample();
		List<ScheduleJob> jobList = scheduleJobMapper.selectByExample(example);
		pg.setRows(jobList);
		pg.setTotal(jobList.size());
		return pg;
	}
	public void updateScheduleJob(ScheduleJob job) {
		try {
			if(job.getJobStatus() != 3){
				scheduleJobMapper.updateByPrimaryKeySelective(job);
			}
			ScheduleJob jobtmp = scheduleJobMapper.selectByPrimaryKey(job.getId());
			jobtmp.setJobStatus(job.getJobStatus());
			callScheduler.updateScheduler(jobtmp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

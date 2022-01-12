package com.test.project.config;
/*package com.test.project.ui.tiles;

import static org.quartz.JobBuilder.newJob;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BatchController  {
    @Autowired
    private Scheduler scheduler;
    
    @PostConstruct
    public void start() {

        //JobDetail aggreReqJobDetail = buildJobDetail(ScheduleService.class, "testJob", "test", new HashMap());
        try {
            //scheduler.scheduleJob(aggreReqJobDetail, buildJobTrigger("0/10 * * * * ?")); // 매 10초 ( 12시1분10초 -> 12시1분20초 )
            //scheduler.scheduleJob(aggreReqJobDetail, buildJobTrigger("0 0 0/1 * * ?")); // 매 1시 ( 12시 -> 1시 -> 2시 )
            
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).withIdentity(name, group)
                .usingJobData(jobDataMap)
                .build();
    }
}
*/
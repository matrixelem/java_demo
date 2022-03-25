package com.example.java_demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class QuartzDemo {
    public static void main(String[] args) {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(RawBatchInsertJob.class)
                    .withIdentity("ckJob", "ckGroup").build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("ckBatchInsertTrigger", "ckBatchInsertGroup")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)//每隔1s执行一次
                            .repeatForever()).build();//一直执行

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("create job error", e);
        }
    }

    static class RawBatchInsertJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext)  {
            JobDetail detail = jobExecutionContext.getJobDetail();
           log.info("开始执行");
        }
    }
}

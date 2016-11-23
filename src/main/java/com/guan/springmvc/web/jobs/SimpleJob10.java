package com.guan.springmvc.web.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

/**
 * Created by guanxianxiao on 2016/11/22.
 */
public class SimpleJob10 implements Job{

    private static Logger _log = LoggerFactory.getLogger(SimpleJob10.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        _log.info("Executing job: " + jobKey + " executing at " + new Date() + ", fired by: " + jobExecutionContext.getTrigger().getKey());

        if(jobExecutionContext.getMergedJobDataMap().size() > 0) {
            Set<String> keys = jobExecutionContext.getMergedJobDataMap().keySet();
            for(String key: keys) {
                String val = jobExecutionContext.getMergedJobDataMap().getString(key);
                _log.info(" - jobDataMap entry: " + key + " = " + val);
            }
        }

        jobExecutionContext.setResult("hello");
    }
}

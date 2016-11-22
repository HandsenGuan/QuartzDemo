package com.springmvc.jobs;

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
public class SimpleJob2 implements Job{

    private static Logger _log = LoggerFactory.getLogger(SimpleJob2.class);

    public SimpleJob2() {
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        _log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
    }
}

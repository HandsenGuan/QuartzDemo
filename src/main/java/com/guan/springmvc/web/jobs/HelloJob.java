package com.guan.springmvc.web.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/22.
 */
public class HelloJob implements Job
{

    private static Logger _log = LoggerFactory.getLogger(HelloJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("Hello, Quartz! - executing its JOB at "+
                new Date() + " by " + jobExecutionContext.getTrigger().getDescription());

        _log.info("Hello, Quartz! - executing its JOB at "+
                new Date() + " by " + jobExecutionContext.getTrigger().getDescription());

    }

}

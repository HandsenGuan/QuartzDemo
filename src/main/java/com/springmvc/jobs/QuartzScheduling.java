package com.springmvc.jobs;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by guanxianxiao on 2016/11/22.
 */
public class QuartzScheduling  {
    Logger log = LoggerFactory.getLogger(QuartzScheduling.class);

    public static void main (String[] args){
        QuartzScheduling quartzScheduling = new QuartzScheduling();

        //实例1
//        quartzScheduling.runExample1();

        //实例2
        try {
            quartzScheduling.runExample2();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public void runExample1(){
        try {

            log.info("------- Initializing ----------------------");
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();

            log.info("------- Initialization Complete -----------");

            Date runTime = evenMinuteDate(new Date());

            log.info("------- Scheduling Job  -------------------");
            JobDetail job = newJob(HelloJob.class).withIdentity("job1","group1").build();

            Trigger trigger = newTrigger().withIdentity("job1","group1")
                    .startAt(runTime)
                    .build();

            sched.scheduleJob(job,trigger);

            log.info(job.getKey() + " will run at: " + runTime);


            sched.start();
            log.info("------- Started Scheduler -----------------");

            log.info("------- Waiting 5 seconds... -------------");
            try {
                // wait 65 seconds to show job
                Thread.sleep(5L * 1000L);
                // executing...
            } catch (Exception e) {
                //
            }
            log.info("------- Shutting Down ---------------------");
            sched.shutdown(true);
            log.info("------- Shutdown Complete -----------------");

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void runExample2() throws SchedulerException {
        log.info("------- Initializing -------------------");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");

        // jobs can be scheduled before sched.start() has been called

        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 will only fire once at date/time "ts"
        JobDetail job = newJob(SimpleJob10.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();

        // schedule it to run!
        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job2 will only fire once at date/time "ts"
        job = newJob(SimpleJob10.class).withIdentity("job2", "group1").build();

        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger2", "group1").startAt(startTime).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job3 will run 11 times (run once and repeat 10 more times)
        // job3 will repeat every 10 seconds
        job = newJob(SimpleJob10.class).withIdentity("job3", "group1").build();

        trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(5)).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // the same job (job3) will be scheduled by a another trigger
        // this time will only repeat twice at a 70 second interval

        trigger = newTrigger().withIdentity("trigger3", "group2").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2)).forJob(job).build();

        ft = sched.scheduleJob(trigger);
        log.info(job.getKey() + " will [also] run at: " + ft + " and repeat: " + trigger.getRepeatCount()
                + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job4 will run 6 times (run once and repeat 5 more times)
        // job4 will repeat every 10 seconds
        job = newJob(SimpleJob10.class).withIdentity("job4", "group1").build();

        trigger = newTrigger().withIdentity("trigger4", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(5)).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job5 will run once, five minutes in the future
        job = newJob(SimpleJob10.class).withIdentity("job5", "group1").build();

        trigger = (SimpleTrigger) newTrigger().withIdentity("trigger5", "group1")
                .startAt(futureDate(5, DateBuilder.IntervalUnit.SECOND)).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // job6 will run indefinitely, every 40 seconds
        job = newJob(SimpleJob10.class).withIdentity("job6", "group1").build();

        trigger = newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();

        log.info("------- Started Scheduler -----------------");

        // jobs can also be scheduled after start() has been called...
        // job7 will repeat 20 times, repeat every five minutes
        job = newJob(SimpleJob10.class).withIdentity("job7", "group1").build();

        trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(20)).build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // jobs can be fired directly... (rather than waiting for a trigger)
        job = newJob(SimpleJob10.class).withIdentity("job8", "group1").storeDurably().build();

        sched.addJob(job, true);

        log.info("'Manually' triggering job8...");
        sched.triggerJob(jobKey("job8", "group1"));

        log.info("------- Waiting 30 seconds... --------------");

        try {
            // wait 33 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // jobs can be re-scheduled...
        // job 7 will run immediately and repeat 10 times for every second
        log.info("------- Rescheduling... --------------------");
        trigger = newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(20)).build();

        ft = sched.rescheduleJob(trigger.getKey(), trigger);
        log.info("job7 rescheduled to run at: " + ft);

        log.info("------- Waiting five minutes... ------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public void runExample10() throws SchedulerException {

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = null;

        try {
            sched = sf.getScheduler();
        } catch (NoClassDefFoundError e) {
            log.error(" Unable to load a class - most likely you do not have jta.jar on the classpath. If not present in the examples/lib folder, please " +
                    "add it there for this sample to run.", e);
            return;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        log.info("------- Initialization Complete -----------");

        log.info("------- (Not Scheduling any Jobs - relying on XML definitions --");

        log.info("------- Starting Scheduler ----------------");

        sched.start();

        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting five minutes... -----------");

        try {
            Thread.sleep(5L * 1000L);
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }


}

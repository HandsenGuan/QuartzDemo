package com.guan.springmvc.web.jobs;

import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author guanxianxiao
 * @Date 2016/11/24 9:38
 */
public class MutiThreadTimeTaskDemo {

    public void execute() {
        System.out.println("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread start");
                try {
                    System.out.println("。。。。");
                    Thread.sleep(8L * 1000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        System.out.println("end job");
//        StdJDBCDelegate
    }



}

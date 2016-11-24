package com.guan.springmvc.web.jobs;

import com.guan.springmvc.core.util.ApplicationUtils;
import com.guan.springmvc.web.model.User;
import com.guan.springmvc.web.service.UserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/22.
 */
public class TimedTask {

    @Resource
    private UserService userService;

    /**
     * 业务逻辑处理
     */
    public void execute() {

        // 执行业务逻辑
        System.out.println("定时任务开始[date:"+new Date().toLocaleString()+"]>>>>>>>>>");
        User model = new User();
        model.setUsername("定时插入"+new Date().toLocaleString());
        model.setPassword(ApplicationUtils.sha256Hex("123456"));
        model.setCreateTime(new Date());
        userService.insert(model);
        System.out.println("    插入数据："+userService.selectByUsername("定时插入"+new Date().toLocaleString()).toString());
        try {
            System.out.println("    >>>>>>暂停10秒，模拟线程操作");
            Thread.sleep(10L * 1000L);
            System.out.println("    <<<<<<模拟线程操作结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("定时任务结束[date:"+new Date().toLocaleString()+"]<<<<<<<<<");

    }

}

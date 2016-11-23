package com.guan.springmvc.test.service;

import com.guan.springmvc.core.feature.test.TestSupport;
import com.guan.springmvc.core.util.ApplicationUtils;
import com.guan.springmvc.web.model.User;
import com.guan.springmvc.web.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author guanxianxiao
 * @Date 2016/11/23 16:23
 */
public class UserServiceTest extends TestSupport {

    @Resource
    private UserService userService;

   @Test
    public void test_insert() {
        User model = new User();
        model.setUsername("guanxianxiao");
        model.setPassword(ApplicationUtils.sha256Hex("123456"));
        model.setCreateTime(new Date());
        userService.insert(model);
    }

    //    @Test
    public void test_10insert() {
        for (int i = 0; i < 10; i++) {
            User model = new User();
            model.setUsername("guanxianxiao" + i);
            model.setPassword(ApplicationUtils.sha256Hex("123456"));
            model.setCreateTime(new Date());
            userService.insert(model);
        }
    }
}

package com.guan.springmvc.web.service;

import com.guan.springmvc.core.generic.GenericService;
import com.guan.springmvc.web.model.User;

/**
 * @Author guanxianxiao
 * @Date 2016/11/23 16:16
 */
public interface  UserService extends GenericService<User, Long> {

    /**
     * 用户验证
     *
     * @param user
     * @return
     */
    User authentication(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

}

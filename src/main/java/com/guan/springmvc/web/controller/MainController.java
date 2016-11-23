package com.guan.springmvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guanxianxiao on 2016/11/22.
 */
//@Controller注解：采用注解的方式，可以明确地定义该类为处理请求的Controller类；
@Controller
//@RequestMapping(value = "/main")
public class MainController {

    //@RequestMapping()注解：用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post；
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}

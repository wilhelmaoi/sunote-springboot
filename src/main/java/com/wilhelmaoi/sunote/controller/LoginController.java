package com.wilhelmaoi.sunote.controller;

import cn.hutool.core.util.StrUtil;
import com.wilhelmaoi.sunote.common.Result;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 提供接口返回数据
 * 作者: wilhelmaoi
 * 目期: 2025/3/31 21:27
 */

@RestController
public class LoginController {

//    @Autowired
//    UserService userService;

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");

        }
        User dbUser = loginService.login(user); // 使用注入的 loginService 实例
        return Result.success(dbUser.getToken()); // 只返回 Token
    }

    @RequestMapping("/hello")
    public Result hello() {
        return Result.success("Hello");

    }


    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length() > 10 || user.getPassword().length() > 20) {
            return Result.error("数据输入不合法");
        }
        user = loginService.register(user);
        return Result.success(user);
    }

//    @AuthAccess
//    @PutMapping("/password")
//    public Result password(@RequestBody User user) {
//        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())) {
//            return Result.error("数据输入不合法");
//        }
//        userService.resetPassword(user);
//        return Result.success();
//    }

}

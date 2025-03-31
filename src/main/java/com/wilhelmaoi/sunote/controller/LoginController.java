package com.wilhelmaoi.sunote.controller;

import com.wilhelmaoi.sunote.common.Result;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.services.LoginService;
import com.wilhelmaoi.sunote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.hutool.core.util.StrUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/31 21:27
 */




/**
 * 功能: 提供接口返回数据
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 09:56
 */


@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    public Result hello() {
        return Result.success("Hello");

    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");

        }
        User dbUser = LoginService.login(user); // 登录
        return Result.success(dbUser.getToken()); // 只返回 Token
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())) {
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length() > 10 || user.getPassword().length() > 20) {
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    @AuthAccess
    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }

}

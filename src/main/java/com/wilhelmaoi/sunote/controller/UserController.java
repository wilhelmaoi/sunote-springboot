package com.wilhelmaoi.sunote.controller;

import com.wilhelmaoi.sunote.common.Result;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/30 22:04
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public Result user() {
        return Result.success();
    }



    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        // 查询数据库中是否存在相同的用户名
        User existingUser = userService.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        } else {
            // 如果不存在，保存用户
            userService.save(user);
            return Result.success();
        }
    }
}

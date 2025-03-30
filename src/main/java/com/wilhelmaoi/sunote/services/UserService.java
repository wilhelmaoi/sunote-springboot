package com.wilhelmaoi.sunote.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wilhelmaoi.sunote.entity.User;


public interface UserService extends IService<User> {
    User selectByUsername(String username);
}

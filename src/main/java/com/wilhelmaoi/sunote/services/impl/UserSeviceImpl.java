package com.wilhelmaoi.sunote.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.mapper.UserMapper;
import com.wilhelmaoi.sunote.services.UserService;
import org.springframework.stereotype.Service;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/30 21:59
 */
@Service
public class UserSeviceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User selectByUsername(String username) {
        return null;
    }
}

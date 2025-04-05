package com.wilhelmaoi.sunote.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.exception.ServiceException;
import com.wilhelmaoi.sunote.mapper.UserMapper;
import com.wilhelmaoi.sunote.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/31 21:45
 */
@Service
public class LoginService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    public  User login(User user) {
        // 查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        // 校验用户名和密码
        if (dbUser == null || !user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }

        // 生成 Token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);

        return dbUser; // 返回包含 Token 的用户信息
    }

    public User register(User user) {
        User dbUser = userService.selectByUsername(user.getUsername());
        if (dbUser != null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
//        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

}




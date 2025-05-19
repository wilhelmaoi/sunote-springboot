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
        // 检查用户名是否已存在
        User dbUser = userService.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new ServiceException("用户名已存在");
        }

        // 检查邮箱是否已存在
        QueryWrapper<User> emailQuery = new QueryWrapper<>();
        emailQuery.eq("email", user.getEmail());
        User emailUser = userMapper.selectOne(emailQuery);
        if (emailUser != null) {
            throw new ServiceException("邮箱已被注册");
        }

        // 设置默认值
        user.setNickname(user.getNickname() != null ? user.getNickname() : user.getUsername());
        user.setAvatar(""); // 设置默认头像为空
        user.setBio(""); // 设置默认简介为空
        user.setBirthday(null); // 设置默认生日为null

        // 插入用户数据
        userMapper.insert(user);
        return user;
    }

}




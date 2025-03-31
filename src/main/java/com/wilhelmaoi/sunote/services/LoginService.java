package com.wilhelmaoi.sunote.services;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.exception.ServiceException;
import com.wilhelmaoi.sunote.mapper.UserMapper;
import com.wilhelmaoi.sunote.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/31 21:45
 */
@Service
public class LoginService extends ServiceImpl<UserMapper, User> {

    @Resource
    UserMapper userMapper;

    public User login(User user) {
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


}




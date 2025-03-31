package com.wilhelmaoi.sunote.services;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.mapper.UserMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2024年9月5日 23:26
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    UserMapper userMapper;


    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username", username);  //  eq => ==   where username = #{username}
        // 根据用户名查询数据库的用户信息
        return getOne(queryWrapper); //  select * from user where username = #{username}
    }

    // 验证用户账户是否合法


}


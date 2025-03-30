package com.wilhelmaoi.sunote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wilhelmaoi.sunote.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

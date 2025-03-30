package com.wilhelmaoi.sunote.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/29 23:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")
public class User {
    String username;
    String password;
    String email;
    String phone;
    String avatar;
    String bio;
    String address;
    String birthday;



}

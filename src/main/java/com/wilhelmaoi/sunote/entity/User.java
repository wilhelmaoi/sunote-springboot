package com.wilhelmaoi.sunote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    @TableId(type = IdType.AUTO)
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String bio;
    private String address;
    private LocalDate birthday;
}

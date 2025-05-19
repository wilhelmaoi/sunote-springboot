package com.wilhelmaoi.sunote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/29 23:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private String bio;
    private String address;
    private LocalDate birthday;
    // 头像更新时间戳
    private Date avatarUpdatedAt;

    @TableField(exist = false)
    private String token;

}

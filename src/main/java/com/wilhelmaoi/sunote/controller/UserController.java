package com.wilhelmaoi.sunote.controller;

import com.wilhelmaoi.sunote.common.Result;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.services.OssService;
import com.wilhelmaoi.sunote.services.UserService;
import com.wilhelmaoi.sunote.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/3/30 22:04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private OssService ossService;


    @GetMapping("/hello")
    public Result user() {
        return Result.success();
    }



    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        // 查询数据库中是否存在相同的用户名
        User existingUser = userService.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        } else {
            // 如果不存在，保存用户
            userService.save(user);
            return Result.success();
        }
    }

    @PostMapping("/info")
    public Result info(@RequestBody User user) {
        // 1. 参数校验
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("用户名或密码不能为空");
        }
    
        // 2. 查询数据库
        User dbUser = userService.selectByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.error("用户不存在");
        }
    
        // 3. 密码校验（这里假设密码是明文存储，实际项目建议加密）
        if (!user.getPassword().equals(dbUser.getPassword())) {
            return Result.error("密码错误");
        }
    
        // 4. 登录成功，返回用户全部信息
        return Result.success(dbUser);
    }
    
    /**
     * 上传用户头像
     * 
     * @param avatar 头像文件
     * @return 上传结果
     */
    
    @PostMapping("/avatar")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            // 获取当前登录用户
            User currentUser = TokenUtils.getCurrentUser();
            if (currentUser == null) {
                return Result.error("用户未登录");
            }
            
            // 检查文件类型
            String contentType = avatar.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只允许上传图片文件");
            }
            
            // 生成OSS存储路径 - 添加时间戳避免缓存问题
            long timestamp = System.currentTimeMillis();
            String objectKey = String.format("userData/%s/avatar_%d.jpg", currentUser.getUsername(), timestamp);
            
            // 上传到OSS
            String avatarUrl = ossService.uploadFile(avatar, objectKey);
            
            // 更新用户头像地址和更新时间戳
            currentUser.setAvatar(avatarUrl);
            // 使用当前时间作为头像更新时间
            currentUser.setAvatarUpdatedAt(new Date());
            userService.updateById(currentUser);
            
            // 返回包含云端URL和时间戳的结果
            Map<String, Object> data = new HashMap<>();
            data.put("avatarUrl", avatarUrl);
            data.put("timestamp", timestamp); // 返回毫秒级时间戳给前端
            
            return Result.success(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("头像上传失败: " + e.getMessage());
        }
    }
}

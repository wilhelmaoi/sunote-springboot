/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/4/1 00:49
 */

package com.wilhelmaoi.sunote.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wilhelmaoi.sunote.entity.User;
import com.wilhelmaoi.sunote.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static UserMapper staticUserMapper;

    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    /**
     * 生成token
     *
     * @return token对象
     */
    public static String createToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        
        // 尝试从多个位置获取token
        String token = request.getHeader("token");
        
        // 如果token为空，尝试从Authorization头获取
        if (StrUtil.isBlank(token)) {
            String authHeader = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // 去掉"Bearer "前缀
            }
        }

        if (StrUtil.isNotBlank(token)) {
            try {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.valueOf(userId));
            } catch (Exception e) {
                e.printStackTrace(); // 添加异常打印以便调试
                return null;
            }
        }
        return null;
    }

}
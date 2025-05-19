package com.wilhelmaoi.sunote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/5/13 00:08
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 生成验证码
    private String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void sendVerificationCode(String toEmail) {
        String code = generateCode();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("3265065519@qq.com");
        message.setTo(toEmail);
        message.setSubject("验证码");
        message.setText("您的验证码是：" + code + "，有效期为5分钟。");

        mailSender.send(message);

        // 存入 Redis，有效期 5 分钟
        redisTemplate.opsForValue().set("email:code:" + toEmail, code, Duration.ofMinutes(5));
    }

    public boolean verifyCode(String toEmail, String inputCode) {
        String key = "email:code:" + toEmail;
        String correctCode = redisTemplate.opsForValue().get(key);

        return inputCode.equals(correctCode);
    }
}

package com.wilhelmaoi.sunote.controller;

import com.wilhelmaoi.sunote.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者: wilhelmaoi
 * 目期: 2025/5/13 00:10
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendCode")
    public String sendCode(@RequestParam String email) {
        emailService.sendVerificationCode(email);
        return "验证码发送成功";
    }

    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean success = emailService.verifyCode(email, code);
        return success ? "验证成功" : "验证码错误或已过期";
    }
}
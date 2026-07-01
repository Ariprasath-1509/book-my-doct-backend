package com.bookmydoct.notification.controller;

import com.bookmydoct.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class EmailController {

    public final EmailService emailService;

    @PostMapping("/email")
    public String testEmail(){

        emailService.sendEmail(
                "avinashraj3923@gmail.com",
                "BookMyDoct Test",
                "Email Configuration Successful");

        return "Email Sent Successfully";
    }
}

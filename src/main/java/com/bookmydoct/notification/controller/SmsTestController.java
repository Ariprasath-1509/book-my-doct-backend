package com.bookmydoct.notification.controller;

import com.bookmydoct.notification.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class SmsTestController {

    private final SmsService smsService;

    @GetMapping("/sms")
    public String sendSms() {

        smsService.sendOtpSms(
                "+918877422051",
                "123456"
        );

        return "SMS Sent";
    }
}

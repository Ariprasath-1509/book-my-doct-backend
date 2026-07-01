package com.bookmydoct.notification.service;

public interface SmsService {

    void sendOtpSms(String mobileNumber, String otp);
}
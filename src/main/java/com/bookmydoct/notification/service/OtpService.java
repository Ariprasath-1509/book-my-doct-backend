package com.bookmydoct.notification.service;

public interface OtpService {

    void sendEmailOtp(String email);

    void sendMobileOtp(String mobileNumber);

    boolean verifyEmailOtp(String email, String otp);

    boolean verifyMobileOtp(String mobileNumber, String otp);

}
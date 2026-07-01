package com.bookmydoct.notification.service.impl;

import com.bookmydoct.notification.service.EmailService;
import com.bookmydoct.notification.service.OtpService;
import com.bookmydoct.notification.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final StringRedisTemplate redisTemplate;

    private final EmailService emailService;

    private final SmsService smsService;

    private static final long OTP_EXPIRY_MINUTES = 10;

    @Override
    public void sendEmailOtp(String email) {

        String otp = generateOtp();

        redisTemplate.opsForValue().set(
                "email-otp:" + email,
                otp,
                OTP_EXPIRY_MINUTES,
                TimeUnit.MINUTES
        );

        redisTemplate.opsForValue().set(
                "email-otp-resend:" + email,
                "LOCKED",
                1,
                TimeUnit.MINUTES
        );

        emailService.sendEmail(
                email,
                "BookMyDoct Email Verification OTP",
                "Your OTP is: " + otp
        );
    }

    @Override
    public void sendMobileOtp(String mobileNumber) {
        
        String otp = generateOtp();

        redisTemplate.opsForValue().set(
                "mobile-otp:" + mobileNumber,
                otp,
                OTP_EXPIRY_MINUTES,
                TimeUnit.MINUTES
        );

        smsService.sendOtpSms(mobileNumber, otp);
    }

    @Override
    public boolean verifyEmailOtp(String email, String otp) {

        String key = "email-otp:" + email;

        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            return false;
        }

        if (!storedOtp.equals(otp)) {
            return false;
        }

        redisTemplate.delete(key);

        return true;
    }

    @Override
    public boolean verifyMobileOtp(String mobileNumber, String otp) {

        String key = "mobile-otp:" + mobileNumber;

        String storedOtp = redisTemplate.opsForValue().get(key);

        if (storedOtp == null) {
            return false;
        }

        if (!storedOtp.equals(otp)) {
            return false;
        }

        redisTemplate.delete(key);

        return true;
    }

    private String generateOtp() {

        return String.valueOf(
                100000 +
                        new java.util.Random()
                                .nextInt(900000));
    }
}
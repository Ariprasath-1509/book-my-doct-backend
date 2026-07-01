package com.bookmydoct.notification.service.impl;

import com.bookmydoct.notification.service.EmailService;
import com.bookmydoct.notification.service.OtpService;
import com.bookmydoct.notification.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory OTP store (replaces Redis). Suitable for MVP — OTPs are lost on restart,
 * which is acceptable since registration OTP sending is disabled and accounts activate
 * immediately.
 */
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final EmailService emailService;
    private final SmsService smsService;

    private final ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();
    private static final Random RANDOM = new Random();

    @Override
    public void sendEmailOtp(String email) {
        String otp = generateOtp();
        otpStore.put("email-otp:" + email, otp);
        emailService.sendEmail(email, "BookMyDoct Email Verification OTP", "Your OTP is: " + otp);
    }

    @Override
    public void sendMobileOtp(String mobileNumber) {
        String otp = generateOtp();
        otpStore.put("mobile-otp:" + mobileNumber, otp);
        smsService.sendOtpSms(mobileNumber, otp);
    }

    @Override
    public boolean verifyEmailOtp(String email, String otp) {
        String key = "email-otp:" + email;
        String stored = otpStore.get(key);
        if (stored == null || !stored.equals(otp)) return false;
        otpStore.remove(key);
        return true;
    }

    @Override
    public boolean verifyMobileOtp(String mobileNumber, String otp) {
        String key = "mobile-otp:" + mobileNumber;
        String stored = otpStore.get(key);
        if (stored == null || !stored.equals(otp)) return false;
        otpStore.remove(key);
        return true;
    }

    private String generateOtp() {
        return String.valueOf(100000 + RANDOM.nextInt(900000));
    }
}

package com.bookmydoct.notification.service.impl;

import com.bookmydoct.notification.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsServiceImpl implements SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendOtpSms(
            String mobileNumber,
            String otp) {

        if (!mobileNumber.startsWith("+")) {
            mobileNumber = "+91" + mobileNumber;
        }


        Message.creator(
                new PhoneNumber(mobileNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your BookMyDoct OTP is: "
                        + otp
                        + ". Valid for 10 minutes."
        ).create();
    }
}
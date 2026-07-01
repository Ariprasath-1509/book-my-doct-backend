package com.bookmydoct.auth.service;

import com.bookmydoct.auth.data.dto.request.*;
import com.bookmydoct.auth.data.dto.response.LoginResponse;
import com.bookmydoct.auth.data.dto.response.UserProfileResponse;
import com.bookmydoct.auth.data.dto.response.UserSummaryResponse;
import com.bookmydoct.auth.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public interface AuthService {

    UserProfileResponse registerPatient(RegisterPatientRequest request, MultipartFile file);

    UserProfileResponse registerDoctor(RegisterDoctorRequest request, MultipartFile file);

    LoginResponse login(LoginRequest request);

    UserSummaryResponse getProfileByUuid(UUID uuid);

    Page<UserSummaryResponse> getUsers(Pageable pageable);

    void verifyAccount(VerifyAccountRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    void resendOtp(ResendOtpRequest request);
}

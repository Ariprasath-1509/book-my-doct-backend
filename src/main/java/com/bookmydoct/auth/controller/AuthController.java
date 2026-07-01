package com.bookmydoct.auth.controller;

import com.bookmydoct.auth.data.dto.request.*;
import com.bookmydoct.auth.data.dto.response.LoginResponse;
import com.bookmydoct.auth.data.dto.response.UserProfileResponse;
import com.bookmydoct.auth.data.dto.response.UserSummaryResponse;
import com.bookmydoct.auth.service.AuthService;
import com.bookmydoct.common.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register/patient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserProfileResponse>> registerPatient(
            @Valid //valid->request validation
            @RequestPart("patientRequest") RegisterPatientRequest patientRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage)
            throws IOException {

        log.info("Registering new patient with email: {}", patientRequest.getEmail());
        UserProfileResponse response = authService.registerPatient(patientRequest, profileImage);
        log.info("Patient registered successfully with UUID: {}", response.getUuid());

        ApiResponse<UserProfileResponse> apiResponse = ApiResponse.<UserProfileResponse>builder()
                .success(true)
                .message("Patient Registered Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/register/doctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserProfileResponse>> registerDoctor(
            @Valid
            @RequestPart("doctorRequest") RegisterDoctorRequest doctorRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage)
            throws IOException {

        UserProfileResponse response = authService.registerDoctor(doctorRequest, profileImage);

        ApiResponse<UserProfileResponse> apiResponse = ApiResponse.<UserProfileResponse>builder()
                .success(true)
                .message("Doctor Registered Successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());
        LoginResponse response = authService.login(request);
        log.info("Login successful for email: {}", request.getEmail());
        ApiResponse<LoginResponse> loginSuccessful = ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(response)
                .build();

        return ResponseEntity.ok(loginSuccessful);
    }

    @GetMapping("/profile/{uuid}")
    public ApiResponse<UserSummaryResponse> getProfileByUuid(@PathVariable UUID uuid) {
        UserSummaryResponse response = authService.getProfileByUuid(uuid);
        return ApiResponse.<UserSummaryResponse>builder()
                .success(true)
                .message("Profile Fetched Successfully")
                .data(response)
                .build();
    }

    @GetMapping("/users")
    public ApiResponse<Page<UserSummaryResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserSummaryResponse> response = authService.getUsers(pageable);

        return ApiResponse.<Page<UserSummaryResponse>>builder()
                .success(true)
                .message("Users Fetched Successfully")
                .data(response)
                .build();
    }

    @PostMapping("/verify-account")
    public ApiResponse<String> verifyAccount(
            @RequestBody VerifyAccountRequest request) {

        log.info("Verifying account for email: {}", request.getEmail());
        authService.verifyAccount(request);
        log.info("Account verified successfully for email: {}", request.getEmail());

        return ApiResponse.<String>builder()
                .success(true)
                .message("Account verified successfully")
                .data("ACTIVE")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(
            @RequestBody
            ForgotPasswordRequest request) {

        authService.forgotPassword(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset OTP sent successfully")
                .data("OTP_SENT")
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(
            @RequestBody
            ResetPasswordRequest request) {

        authService.resetPassword(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset successfully")
                .data("PASSWORD_UPDATED")
                .build();
    }

    @PostMapping("/resend-otp")
    public ApiResponse<Void> resendOtp(
            @Valid
            @RequestBody
            ResendOtpRequest request) {

        authService.resendOtp(request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("OTP sent successfully")
                .build();
    }

}

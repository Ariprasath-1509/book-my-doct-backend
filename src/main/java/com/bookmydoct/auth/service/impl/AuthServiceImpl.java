package com.bookmydoct.auth.service.impl;

import com.bookmydoct.auth.data.dto.request.*;
import com.bookmydoct.auth.data.dto.response.LoginResponse;
import com.bookmydoct.auth.data.dto.response.UserProfileResponse;
import com.bookmydoct.auth.data.dto.response.UserSummaryResponse;
import com.bookmydoct.auth.data.entity.Role;
import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.auth.data.enums.AccountStatus;
import com.bookmydoct.common.exception.customException.InvalidCredentialsException;
import com.bookmydoct.common.exception.customException.RoleNotFoundException;
import com.bookmydoct.common.exception.customException.UserAlreadyExistsException;
import com.bookmydoct.common.exception.customException.UserNotFoundException;
import com.bookmydoct.auth.util.UserMapper;
import com.bookmydoct.auth.repository.RoleRepository;
import com.bookmydoct.auth.repository.UserRepository;
import com.bookmydoct.auth.service.AuthService;
import com.bookmydoct.notification.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OtpService otpService;


    private static final long MAX_IMAGE_SIZE = 2L * 1024 * 1024; // 2MB

    @Override
    public UserProfileResponse registerPatient(RegisterPatientRequest request, MultipartFile profileImage) {

        validateEmailAndPhone(request.getEmail(), request.getPhoneNumber());

        Role patientRole = roleRepository.findByRoleCode("PATIENT")
                .orElseThrow(() -> new RoleNotFoundException("Patient role not found"));

        byte[] imageBytes = processProfileImage(profileImage);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .profileImage(imageBytes)
                .role(patientRole)
                .status(AccountStatus.ACTIVE)
                .emailVerified(false)
                .mobileVerified(false)
                .build();

        User savedUser = userRepository.save(user);
//        otpService.sendEmailOtp(savedUser.getEmail());
//        otpService.sendMobileOtp(savedUser.getPhoneNumber());

        return UserMapper.toUserProfileResponse(savedUser);
    }

    @Override
    public UserProfileResponse registerDoctor(RegisterDoctorRequest request, MultipartFile profileImage) {

        validateEmailAndPhone(request.getEmail(), request.getPhoneNumber());

        Role doctorRole = roleRepository.findByRoleCode("DOC")
                .orElseThrow(() -> new RoleNotFoundException("Doctor role not found"));

        byte[] imageBytes = processProfileImage(profileImage);

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .profileImage(imageBytes)
                .role(doctorRole)
                .status(AccountStatus.ACTIVE)
                .emailVerified(false)
                .mobileVerified(false)
                .build();

        User savedUser = userRepository.save(user);
//        otpService.sendEmailOtp(savedUser.getEmail());
//        otpService.sendMobileOtp(savedUser.getPhoneNumber());

        return UserMapper.toUserProfileResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (user.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidCredentialsException(
                    "Please verify your account first");
        }

        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return LoginResponse.builder()
                .uuid(user.getUuid())
                .email(user.getEmail())
                .role(user.getRole().getRoleName())
                .message("Login successful")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummaryResponse getProfileByUuid(UUID uuid) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserMapper.toUserSummaryResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSummaryResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::toUserSummaryResponse);
    }

    @Override
    public void verifyAccount(VerifyAccountRequest request) {

        User user = userRepository
                .findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        boolean emailVerified =
                otpService.verifyEmailOtp(
                        request.getEmail(),
                        request.getEmailOtp());

        if (!emailVerified) {
            throw new InvalidCredentialsException(
                    "Invalid or expired email OTP");
        }

        boolean mobileVerified =
                otpService.verifyMobileOtp(
                        request.getMobileNumber(),
                        request.getMobileOtp());

        if (!mobileVerified) {
            throw new InvalidCredentialsException(
                    "Invalid or expired mobile OTP");
        }

        user.setEmailVerified(true);
        user.setMobileVerified(true);
        user.setStatus(AccountStatus.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        otpService.sendEmailOtp(user.getEmail());
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        boolean otpVerified = otpService.verifyEmailOtp(request.getEmail(), request.getOtp());

        if (!otpVerified) {
            throw new InvalidCredentialsException("Invalid or expired OTP");
        }

        user.setPassword(request.getNewPassword());

        userRepository.save(user);
    }

    @Override
    public void resendOtp(ResendOtpRequest request) {

        User user = userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new UserNotFoundException("User not found"));

        if (user.getStatus() == AccountStatus.ACTIVE) {
            throw new InvalidCredentialsException(
                    "Account already verified");
        }

        otpService.sendEmailOtp(request.getEmail());
        otpService.sendMobileOtp(request.getMobileNumber());
    }

    // ── Private Helpers ──────────────────────────────────────────

    private void validateEmailAndPhone(String email, String phoneNumber) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new UserAlreadyExistsException("Phone number already exists");
        }
    }

    // handles optional image — registration works fine WITHOUT image too
//    validation of image(business validation)
    private byte[] processProfileImage(MultipartFile profileImage) {

        if (profileImage == null || profileImage.isEmpty()) {
            return null;
        }

        String contentType = profileImage.getContentType();
        if (contentType == null || !(contentType.equalsIgnoreCase("image/jpeg")
                || contentType.equalsIgnoreCase("image/png")
                || contentType.equalsIgnoreCase("image/jpg"))) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        if (profileImage.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("Image size must be less than 2MB");
        }

        try {
            return profileImage.getBytes();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to process profile image", e);
        }
    }
}
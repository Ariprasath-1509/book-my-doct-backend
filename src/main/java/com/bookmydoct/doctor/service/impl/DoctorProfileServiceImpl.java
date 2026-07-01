package com.bookmydoct.doctor.service.impl;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.auth.repository.UserRepository;
import com.bookmydoct.common.exception.customException.*;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.entity.Specialization;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import com.bookmydoct.doctor.data.dto.request.DoctorProfileRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import com.bookmydoct.doctor.util.DoctorProfileMapper;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.repository.SpecializationRepository;
import com.bookmydoct.doctor.service.DoctorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorProfileServiceImpl implements DoctorProfileService {

    private final DoctorProfileRepository doctorProfileRepository;

    private final UserRepository userRepository;

    private final SpecializationRepository specializationRepository;

    private final DoctorProfileMapper doctorProfileMapper;

    @Override
    public DoctorProfileResponse addDoctor(DoctorProfileRequest request) {

        User user = userRepository.findByUuid(request.getUserUuid())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Specialization specialization = specializationRepository.findByUuid(request.getSpecializationUuid())
                        .orElseThrow(() -> new SpecializationNotFoundException("Specialization not found"));

        if (doctorProfileRepository.existsByUserUuid(request.getUserUuid())) {
            throw new DoctorAlreadyExistsException("Doctor profile already exists");
        }

        if (doctorProfileRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new LicenseAlreadyExistsException("License number already exists");
        }

        DoctorProfile doctorProfile = DoctorProfile.builder()
                                                    .user(user)
                                                    .specialization(specialization)
                                                    .experienceYears(request.getExperienceYears())
                                                    .consultationFee(request.getConsultationFee())
                                                    .licenseNumber(request.getLicenseNumber())
                                                    .clinicName(request.getClinicName())
                                                    .address(request.getAddress())
                                                    .city(request.getCity())
                                                    .state(request.getState())
                                                    .pincode(request.getPincode())
                                                    .consultationMode(request.getConsultationMode())
                                                    // New doctor profiles start PENDING. There's no admin-approval
                                                    // UI yet, so approval is done manually via DB until that
                                                    // screen exists — this is the intended gate on doctor visibility.
                                                    .verificationStatus(VerificationStatus.PENDING)
                                                    .averageRating(0.0)
                                                    .totalReviews(0)
                                                    .build();

        DoctorProfile savedDoctor = doctorProfileRepository.save(doctorProfile);

        return doctorProfileMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorProfileResponse updateDoctor(UUID doctorUuid, DoctorProfileRequest request) {

        DoctorProfile doctorProfile = doctorProfileRepository.findByUuid(doctorUuid)
                                            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        Specialization specialization = specializationRepository.findByUuid(request.getSpecializationUuid())
                                                     .orElseThrow(() -> new SpecializationNotFoundException("Specialization not found"));

        doctorProfile.setSpecialization(specialization);
        doctorProfile.setExperienceYears(request.getExperienceYears());
        doctorProfile.setConsultationFee(request.getConsultationFee());
        doctorProfile.setLicenseNumber(request.getLicenseNumber());
        doctorProfile.setClinicName(request.getClinicName());
        doctorProfile.setAddress(request.getAddress());
        doctorProfile.setCity(request.getCity());
        doctorProfile.setState(request.getState());
        doctorProfile.setPincode(request.getPincode());
        doctorProfile.setConsultationMode(request.getConsultationMode());

        DoctorProfile updatedDoctor = doctorProfileRepository.save(doctorProfile);

        return doctorProfileMapper.toResponse(updatedDoctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorProfileResponse getDoctorByUuid(UUID doctorUuid) {

        DoctorProfile doctorProfile = doctorProfileRepository.findByUuid(doctorUuid)
                                                     .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        return doctorProfileMapper.toResponse(doctorProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorProfileResponse> getAllDoctors() {

        return doctorProfileRepository
                .findAll()
                .stream()
                .map(doctorProfileMapper::toResponse)
                .toList();
    }
}
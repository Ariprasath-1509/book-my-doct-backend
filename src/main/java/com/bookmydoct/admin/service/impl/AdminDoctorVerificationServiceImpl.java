package com.bookmydoct.admin.service.impl;

import com.bookmydoct.admin.service.AdminDoctorVerificationService;
import com.bookmydoct.common.exception.customException.DoctorNotFoundException;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.util.DoctorProfileMapper;
import com.bookmydoct.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminDoctorVerificationServiceImpl implements AdminDoctorVerificationService {

    private final DoctorProfileRepository doctorRepository;
    private final DoctorProfileMapper doctorProfileMapper;
    private final EmailService emailService;

    @Override
    public Page<DoctorProfileResponse> getPendingDoctors(Pageable pageable) {

        return doctorRepository
                .findByVerificationStatus(VerificationStatus.PENDING,pageable)
                .map(doctorProfileMapper::toResponse);
    }

    @Override
    public Page<DoctorProfileResponse> getAllDoctors(Pageable pageable) {

        return doctorRepository.findAll(pageable)
                .map(doctorProfileMapper::toResponse);
    }

    @Override
    public DoctorProfileResponse approveDoctor(UUID doctorUuid) {

        DoctorProfile doctor = doctorRepository.findByUuid(doctorUuid)
                        .orElseThrow(() ->
                                new DoctorNotFoundException("Doctor not found"));

        doctor.setVerificationStatus(VerificationStatus.APPROVED);

        DoctorProfile savedDoctor = doctorRepository.save(doctor);

        emailService.sendEmail(savedDoctor.getUser().getEmail(),
                "Doctor Profile Approved",
                """
                Congratulations!
        
                Your BookMyDoct doctor profile has been approved.
        
                You can now appear in doctor search results and start accepting appointments.
        
                Regards,
                BookMyDoct Team
                """
        );

        return doctorProfileMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorProfileResponse rejectDoctor(UUID doctorUuid) {

        DoctorProfile doctor = doctorRepository.findByUuid(doctorUuid)
                        .orElseThrow(() ->
                                new DoctorNotFoundException("Doctor not found"));

        doctor.setVerificationStatus(VerificationStatus.REJECTED);

        DoctorProfile savedDoctor = doctorRepository.save(doctor);

        emailService.sendEmail(savedDoctor.getUser().getEmail(),
                "Doctor Profile Rejected",
                """
                Your BookMyDoct doctor profile verification has been rejected.
        
                Please review your submitted documents and upload valid documents.
        
                Regards,
                BookMyDoct Team
                """
        );

        return doctorProfileMapper.toResponse(savedDoctor);
    }
}
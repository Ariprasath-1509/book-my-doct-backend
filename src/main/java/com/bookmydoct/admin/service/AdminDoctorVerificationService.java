package com.bookmydoct.admin.service;

import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminDoctorVerificationService {

    Page<DoctorProfileResponse> getPendingDoctors(Pageable pageable);

    Page<DoctorProfileResponse> getAllDoctors(Pageable pageable);

    DoctorProfileResponse approveDoctor(UUID doctorUuid);

    DoctorProfileResponse rejectDoctor(UUID doctorUuid);
}
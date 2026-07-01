package com.bookmydoct.doctor.service.impl;

import com.bookmydoct.doctor.data.dto.request.DoctorSearchRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorSearchResponse;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import com.bookmydoct.doctor.util.DoctorProfileMapper;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.service.DoctorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorSearchServiceImpl implements DoctorSearchService {

    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    @Transactional(readOnly=true)
    public Page<DoctorSearchResponse> searchDoctors(DoctorSearchRequest request, Pageable pageable) {

        Page<DoctorProfile> doctorProfiles = doctorProfileRepository.searchDoctors(
                request.getName(),
                request.getSpecialization(),
                request.getCity(),
                VerificationStatus.APPROVED,
                pageable
        );

        return doctorProfiles.map(DoctorProfileMapper::toSearchResponse);
    }
}
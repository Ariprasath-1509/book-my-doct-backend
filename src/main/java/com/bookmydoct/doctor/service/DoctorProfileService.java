package com.bookmydoct.doctor.service;

import com.bookmydoct.doctor.data.dto.request.DoctorProfileRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorProfileService {

    DoctorProfileResponse addDoctor(DoctorProfileRequest request);

    DoctorProfileResponse updateDoctor(UUID doctorUuid, DoctorProfileRequest request);

    DoctorProfileResponse getDoctorByUuid(UUID doctorUuid);

    List<DoctorProfileResponse> getAllDoctors();

    
}
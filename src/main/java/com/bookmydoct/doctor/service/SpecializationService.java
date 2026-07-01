package com.bookmydoct.doctor.service;

import com.bookmydoct.doctor.data.dto.request.SpecializationRequest;
import com.bookmydoct.doctor.data.dto.response.SpecializationResponse;

import java.util.List;
import java.util.UUID;

public interface SpecializationService {

    SpecializationResponse addSpecialization(SpecializationRequest request);

    SpecializationResponse getSpecializationByUuid(UUID specializationUuid);

    List<SpecializationResponse> getAllSpecializations();
}

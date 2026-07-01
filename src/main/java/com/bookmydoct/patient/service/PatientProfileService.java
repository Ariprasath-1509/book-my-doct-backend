package com.bookmydoct.patient.service;


import com.bookmydoct.patient.data.dto.request.PatientProfileRequest;
import com.bookmydoct.patient.data.dto.response.PatientProfileResponse;

import java.util.List;
import java.util.UUID;

public interface PatientProfileService {

    PatientProfileResponse createPatient( PatientProfileRequest request);

    PatientProfileResponse updatePatient(UUID uuid, PatientProfileRequest request);

    PatientProfileResponse getPatientByUuid(UUID uuid);

    List<PatientProfileResponse> getAllPatients();
}

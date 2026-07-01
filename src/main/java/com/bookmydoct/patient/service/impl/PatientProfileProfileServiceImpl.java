package com.bookmydoct.patient.service.impl;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.auth.repository.UserRepository;
//import com.bookmydoct.common.exception.customException.PatientNotFoundException;
import com.bookmydoct.common.exception.customException.UserNotFoundException;
import com.bookmydoct.patient.data.entity.PatientProfile;
import com.bookmydoct.patient.data.dto.request.PatientProfileRequest;
import com.bookmydoct.patient.data.dto.response.PatientProfileResponse;
import com.bookmydoct.patient.util.PatientProfileMapper;
//import com.bookmydoct.patient.repository.PatientProfileRepository;
import com.bookmydoct.patient.repository.PatientProfileRepository;
import com.bookmydoct.patient.service.PatientProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientProfileProfileServiceImpl
        implements PatientProfileService {

    private final PatientProfileRepository patientProfileRepository;
    private final UserRepository userRepository;
    private final PatientProfileMapper mapper;

    @Override
    public PatientProfileResponse createPatient(PatientProfileRequest request) {

        User user = userRepository.findByUuid(request.getUserUuid())
                         .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (patientProfileRepository.existsByUserId(user.getId())) {
            throw new IllegalArgumentException("Patient profile already exists");
        }

        PatientProfile patient = PatientProfile.builder()
                                                .user(user)
                                                .age(request.getAge())
                                                .gender(request.getGender())
                                                .bloodGroup(request.getBloodGroup())
                                                .address(request.getAddress())
                                                .build();

        return mapper.toResponse(patientProfileRepository.save(patient));
    }

    @Override
    public PatientProfileResponse updatePatient(
            UUID uuid,
            PatientProfileRequest request) {

        PatientProfile patient = patientProfileRepository.findByUuid(uuid)
                        .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());

        return mapper.toResponse(patientProfileRepository.save(patient));
    }

    @Override
    @Transactional(readOnly = true)
    public PatientProfileResponse getPatientByUuid(UUID patientUuid) {

        PatientProfile patient = patientProfileRepository.findByUuid(patientUuid)
                                      .orElseThrow(() -> new RuntimeException("Patient not found"));

        return mapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientProfileResponse> getAllPatients() {

        return patientProfileRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
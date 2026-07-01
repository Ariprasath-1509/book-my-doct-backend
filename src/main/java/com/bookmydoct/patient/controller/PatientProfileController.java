package com.bookmydoct.patient.controller;

import com.bookmydoct.patient.data.dto.request.PatientProfileRequest;
import com.bookmydoct.patient.data.dto.response.PatientProfileResponse;
import com.bookmydoct.patient.service.PatientProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientProfileController {

    private final PatientProfileService patientService;

    @PostMapping
    public PatientProfileResponse createPatient(
            @Valid @RequestBody
            PatientProfileRequest request) {

        return patientService.createPatient(request);
    }

    @PutMapping("/{uuid}")
    public PatientProfileResponse updatePatient(
            @PathVariable UUID uuid,
            @Valid @RequestBody
            PatientProfileRequest request) {

        return patientService.updatePatient(uuid, request);
    }

    @GetMapping("/{uuid}")
    public PatientProfileResponse getPatientByUuid(@PathVariable UUID uuid) {

        return patientService.getPatientByUuid(uuid);
    }

    @GetMapping
    public List<PatientProfileResponse> getAllPatients() {

        return patientService.getAllPatients();
    }
}
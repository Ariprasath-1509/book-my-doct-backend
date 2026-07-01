package com.bookmydoct.doctor.controller;

import com.bookmydoct.doctor.data.dto.request.DoctorProfileRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import com.bookmydoct.doctor.service.DoctorProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorProfileService doctorProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorProfileResponse addDoctor(@Valid @RequestBody DoctorProfileRequest request) {

        return doctorProfileService.addDoctor(request);
    }

    @PutMapping("/{doctorUuid}")
    public DoctorProfileResponse updateDoctor(@PathVariable UUID doctorUuid,
            @Valid
            @RequestBody
            DoctorProfileRequest request) {

        return doctorProfileService.updateDoctor(doctorUuid, request);
    }

    @GetMapping("/{doctorUuid}")
    public DoctorProfileResponse getDoctorByUuid(@PathVariable UUID doctorUuid) {

        return doctorProfileService.getDoctorByUuid(doctorUuid);
    }

    @GetMapping
    public List<DoctorProfileResponse> getAllDoctors() {

        return doctorProfileService.getAllDoctors();
    }
}
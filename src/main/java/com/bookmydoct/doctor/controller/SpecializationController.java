package com.bookmydoct.doctor.controller;


import com.bookmydoct.doctor.data.dto.request.SpecializationRequest;
import com.bookmydoct.doctor.data.dto.response.SpecializationResponse;
import com.bookmydoct.doctor.service.SpecializationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecializationResponse addSpecialization(
            @Valid
            @RequestBody
            SpecializationRequest request) {

        return specializationService.addSpecialization(request);
    }

    @GetMapping("/{specializationUuid}")
    public SpecializationResponse getSpecializationByUuid(@PathVariable UUID specializationUuid) {

        return specializationService.getSpecializationByUuid(specializationUuid);
    }

    @GetMapping
    public List<SpecializationResponse> getAllSpecializations() {

        return specializationService.getAllSpecializations();
    }

}

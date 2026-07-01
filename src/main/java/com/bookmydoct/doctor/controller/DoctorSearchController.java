package com.bookmydoct.doctor.controller;

import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.doctor.data.dto.request.DoctorSearchRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorSearchResponse;
import com.bookmydoct.doctor.service.DoctorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorSearchController {

    private final DoctorSearchService doctorSearchService;

    @GetMapping("/search")
    public ApiResponse<Page<DoctorSearchResponse>> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        DoctorSearchRequest request = new DoctorSearchRequest();
        request.setName(name);
        request.setSpecialization(specialization);
        request.setCity(city);

        Pageable pageable = PageRequest.of(page, size);

        Page<DoctorSearchResponse> response = doctorSearchService.searchDoctors(request, pageable);

        return ApiResponse.<Page<DoctorSearchResponse>>builder()
                .success(true)
                .message("Doctors fetched successfully")
                .data(response)
                .build();
    }
}
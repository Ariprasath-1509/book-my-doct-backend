package com.bookmydoct.doctor.service;

import com.bookmydoct.doctor.data.dto.request.DoctorSearchRequest;
import com.bookmydoct.doctor.data.dto.response.DoctorSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorSearchService {

    Page<DoctorSearchResponse> searchDoctors(DoctorSearchRequest request, Pageable pageable);
}
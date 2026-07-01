package com.bookmydoct.admin.controller;

import com.bookmydoct.admin.service.AdminDoctorVerificationService;
import com.bookmydoct.common.document.dto.response.DocumentResponse;
import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.doctor.data.dto.response.DoctorProfileResponse;
import com.bookmydoct.doctor.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/doctors")
public class AdminDoctorVerificationController {


    private final AdminDoctorVerificationService adminDoctorVerificationService;

    private final DocumentService documentService;

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<Page<DoctorProfileResponse>>> getPendingDoctors(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDir) {

        Pageable pageable = PageRequest.of(page, size,
                        Sort.by(
                                Sort.Direction.fromString(sortDir),
                                sortBy));

        Page<DoctorProfileResponse> response =
                adminDoctorVerificationService.getPendingDoctors(pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<DoctorProfileResponse>>builder()
                        .success(true)
                        .message("Pending doctors fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DoctorProfileResponse>>> getAllDoctors(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDir) {

        Pageable pageable =
                PageRequest.of(page, size,
                        Sort.by(
                                Sort.Direction.fromString(
                                        sortDir),
                                sortBy));

        Page<DoctorProfileResponse> response =
                adminDoctorVerificationService.getAllDoctors(pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<DoctorProfileResponse>>builder()
                        .success(true)
                        .message("Doctors fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{doctorUuid}/approve")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> approveDoctor(
            @PathVariable
            UUID doctorUuid) {

        DoctorProfileResponse response =
                adminDoctorVerificationService.approveDoctor(doctorUuid);

        return ResponseEntity.ok(
                ApiResponse.<DoctorProfileResponse>builder()
                        .success(true)
                        .message("Doctor approved successfully")
                        .data(response)
                        .build()
        );
    }

    @PutMapping("/{doctorUuid}/reject")
    public ResponseEntity<ApiResponse<DoctorProfileResponse>> rejectDoctor(
            @PathVariable
            UUID doctorUuid) {

        DoctorProfileResponse response =
                adminDoctorVerificationService.rejectDoctor(doctorUuid);

        return ResponseEntity.ok(
                ApiResponse.<DoctorProfileResponse>builder()
                        .success(true)
                        .message("Doctor rejected successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/{doctorUuid}/documents")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDoctorDocuments(
            @PathVariable
            UUID doctorUuid) {

        List<DocumentResponse> response =
                documentService.getDoctorDocuments(doctorUuid);

        return ResponseEntity.ok(
                ApiResponse.<List<DocumentResponse>>builder()
                        .success(true)
                        .message("Doctor documents fetched successfully")
                        .data(response)
                        .build()
        );
    }
}
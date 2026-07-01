package com.bookmydoct.doctor.controller;

import com.bookmydoct.common.document.dto.request.UploadDocumentRequest;
import com.bookmydoct.common.document.dto.response.DocumentResponse;
import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.doctor.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorDocumentController {

    @Autowired
    private final DocumentService documentService;

    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DocumentResponse>> uploadDocument(
            @RequestPart("file")
            MultipartFile file,
            @RequestPart("request")
            UploadDocumentRequest request) {

        DocumentResponse response = documentService.uploadDocument(file, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DocumentResponse>builder()
                                .success(true)
                                .message("Document uploaded successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping("/{doctorUuid}/documents")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getDoctorDocuments(
            @PathVariable
            UUID doctorUuid) {

        List<DocumentResponse> response = documentService.getDoctorDocuments(doctorUuid);

        return ResponseEntity.ok(
                ApiResponse.<List<DocumentResponse>>builder()
                        .success(true)
                        .message("Documents fetched successfully")
                        .data(response)
                        .build()
        );
    }
}
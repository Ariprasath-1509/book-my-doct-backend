package com.bookmydoct.doctor.service;

import com.bookmydoct.common.document.dto.request.UploadDocumentRequest;
import com.bookmydoct.common.document.dto.response.DocumentResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    DocumentResponse uploadDocument(MultipartFile file, UploadDocumentRequest request);

    List<DocumentResponse> getDoctorDocuments(UUID doctorUuid);

    Resource downloadDocument(UUID documentUuid);
}
package com.bookmydoct.doctor.service.impl;

import com.bookmydoct.common.document.dto.request.UploadDocumentRequest;
import com.bookmydoct.common.document.dto.response.DocumentResponse;
import com.bookmydoct.common.document.entity.Document;
import com.bookmydoct.common.document.repository.DocumentRepository;
import com.bookmydoct.common.document.util.DocumentMapper;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.common.exception.customException.DoctorNotFoundException;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.doctor.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DoctorProfileRepository doctorRepository;

    private static final List<String> ALLOWED_CONTENT_TYPES =
            List.of(
                    "application/pdf",
                    "image/jpeg",
                    "image/png"
            );
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public DocumentResponse uploadDocument(MultipartFile file, UploadDocumentRequest request) {

//        Empty File Validation
        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Document file cannot be empty");
        }

//        File Type Validation
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(
                    "Only PDF, JPG and PNG files are allowed");
        }

//        File Size Validation
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "File size cannot exceed 5 MB");
        }

//        Duplicate Documents Validation
        if (documentRepository.existsByDoctor_UuidAndDocumentType(
                        request.getDoctorUuid(), request.getDocumentType())) {
            throw new IllegalArgumentException(
                    request.getDocumentType() + " already uploaded");
        }

        DoctorProfile doctor = doctorRepository
                        .findByUuid(request.getDoctorUuid())
                        .orElseThrow(() ->
                                new DoctorNotFoundException("Doctor not found"));

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFileName = UUID.randomUUID()
                                    + "_"
                                    + file.getOriginalFilename();

            Path targetLocation = uploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(),
                            targetLocation,
                            StandardCopyOption.REPLACE_EXISTING);

            Document document = new Document();

            document.setDoctor(doctor);
            document.setFileName(uniqueFileName);
            document.setOriginalFileName(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setStoragePath(targetLocation.toString());
            document.setDocumentType(request.getDocumentType());

            Document savedDocument = documentRepository.save(document);

            return DocumentMapper.toResponse(savedDocument);
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to upload document");
        }
    }

    @Override
    public List<DocumentResponse> getDoctorDocuments(
            UUID doctorUuid) {

        return documentRepository
                .findByDoctor_Uuid(
                        doctorUuid)
                .stream()
                .map(DocumentMapper::toResponse)
                .toList();
    }

    @Override
    public Resource downloadDocument(UUID documentUuid) {
        Document document = documentRepository.findByUuid(documentUuid)
                            .orElseThrow(() ->
                                new RuntimeException("Document not found"));

        try {
            Path filePath = Paths.get(document.getStoragePath());

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }
            throw new RuntimeException("File not found");
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to load document");
        }
    }
}
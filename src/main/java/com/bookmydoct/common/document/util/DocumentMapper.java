package com.bookmydoct.common.document.util;

import com.bookmydoct.common.document.dto.response.DocumentResponse;
import com.bookmydoct.common.document.entity.Document;

public class DocumentMapper {

    private DocumentMapper() {
    }

    public static DocumentResponse toResponse(Document document) {

        return DocumentResponse.builder()
                .documentUuid(document.getUuid())
                .doctorUuid(document.getDoctor().getUuid())
                .fileName(document.getFileName())
                .originalFileName(document.getOriginalFileName())
                .contentType(document.getContentType())
                .fileSize(document.getFileSize())
                .storagePath(document.getStoragePath())
                .documentType(document.getDocumentType())
                .build();
    }
}
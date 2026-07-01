package com.bookmydoct.common.document.dto.response;

import com.bookmydoct.common.document.enums.DocumentType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentResponse {

    private UUID documentUuid;

    private UUID doctorUuid;

    private String fileName;

    private String originalFileName;

    private String contentType;

    private Long fileSize;

    private String storagePath;

    private DocumentType documentType;
}
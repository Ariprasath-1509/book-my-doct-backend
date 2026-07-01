package com.bookmydoct.common.document.dto.request;

import com.bookmydoct.common.document.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadDocumentRequest {

    @NotNull(message = "Doctor UUID is required")
    private UUID doctorUuid;

    @NotNull(message = "Document type is required")
    private DocumentType documentType;
}
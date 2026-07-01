package com.bookmydoct.common.document.entity;

import com.bookmydoct.common.document.enums.DocumentType;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="documents")
public class Document extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false
    )
    private DoctorProfile doctor;

    /**
     * Stored filename.
     */
    private String fileName;

    /**
     * Original uploaded filename.
     */
    private String originalFileName;

    /**
     * Example:
     * application/pdf
     */
    private String contentType;

    /**
     * File size in bytes.
     */
    private Long fileSize;

    /**
     * Example:
     * C:/book-my-doc/uploads/license.pdf
     */
    private String storagePath;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

}

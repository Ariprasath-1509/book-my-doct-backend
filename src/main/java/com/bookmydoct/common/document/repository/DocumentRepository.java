package com.bookmydoct.common.document.repository;

import com.bookmydoct.common.document.entity.Document;
import com.bookmydoct.common.document.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByUuid(UUID uuid);

    List<Document> findByDoctor_Uuid(UUID doctorUuid);

    boolean existsByDoctor_UuidAndDocumentType(
            UUID doctorUuid,
            DocumentType documentType);

}
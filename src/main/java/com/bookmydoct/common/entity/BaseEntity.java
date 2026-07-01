package com.bookmydoct.common.entity;

import com.bookmydoct.common.config.JpaAuditingConfig;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, JpaAuditingConfig.class})
public abstract class BaseEntity {
    /**
     * Internal database primary key.
     *
     * Example:
     * 1
     * 2
     * 3
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public identifier exposed through APIs.
     *
     * Example:
     * 550e8400-e29b-41d4-a716-446655440000
     */
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    /**
     * Record creation timestamp.
     *
     * Example:
     * 2026-06-16T10:30:00
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Last modification timestamp.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * User who created record.
     *
     * Example:
     * 101
     */
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    /**
     * User who last modified record.
     *
     * Example:
     * 102
     */
    @Column(nullable = false)
    private Long updatedBy;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (createdBy == null) {
            createdBy = 1L;
        }
        if (updatedBy == null) {
            updatedBy = 1L;
        }
    }

}

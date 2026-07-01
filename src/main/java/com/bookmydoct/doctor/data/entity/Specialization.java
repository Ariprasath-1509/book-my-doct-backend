package com.bookmydoct.doctor.data.entity;

import com.bookmydoct.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="specializations")
public class Specialization extends BaseEntity {

    /**
     * Example:
     * CARDIOLOGY
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * Example:
     * Cardiology
     */
    @Column(nullable = false)
    private String name;

    private String description;

    private Boolean active;

}

package com.bookmydoct.doctor.data.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorSearchResponse {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String specializationName;
    private Integer experienceYears;
    private BigDecimal consultationFee;
    private Double averageRating;
    private Integer totalReviews;
    private String clinicName;
    private String city;
    private String consultationMode;
    private String verificationStatus;
}

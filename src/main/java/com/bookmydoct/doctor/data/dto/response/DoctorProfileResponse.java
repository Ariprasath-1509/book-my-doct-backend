package com.bookmydoct.doctor.data.dto.response;

import com.bookmydoct.doctor.data.enums.ConsultationMode;
import com.bookmydoct.doctor.data.enums.VerificationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class DoctorProfileResponse {

    private UUID doctorUuid;

    private UUID userUuid;

    private String doctorName;

    private UUID specializationUuid;

    private String specializationCode;

    private String specializationName;

    private Integer experienceYears;

    private BigDecimal consultationFee;

    private Double averageRating;

    private Integer totalReviews;

    private String licenseNumber;

    private String clinicName;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private ConsultationMode consultationMode;

    private VerificationStatus verificationStatus;
}
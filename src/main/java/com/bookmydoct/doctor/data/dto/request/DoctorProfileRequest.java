package com.bookmydoct.doctor.data.dto.request;

import com.bookmydoct.doctor.data.enums.ConsultationMode;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class DoctorProfileRequest {

    @NotNull
    private UUID userUuid;

    @NotNull
    private UUID specializationUuid;

    @NotNull
    @Min(0)
    private Integer experienceYears;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal consultationFee;

    @NotBlank
    private String licenseNumber;

    @NotBlank
    private String clinicName;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String pincode;

    @NotNull
    private ConsultationMode consultationMode;
}
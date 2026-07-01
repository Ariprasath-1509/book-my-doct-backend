package com.bookmydoct.patient.data.dto.request;

import com.bookmydoct.patient.data.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientProfileRequest {

    @NotNull(message = "UUID is required")
    private UUID userUuid;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 150, message = "Invalid age")
    private Integer age;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Blood group is required")
    private String bloodGroup;
}

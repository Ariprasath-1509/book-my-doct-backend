package com.bookmydoct.patient.data.dto.response;

import com.bookmydoct.patient.data.enums.Gender;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientProfileResponse {

    private UUID uuid;

    private UUID userUuid;

    private String patientName;

    private Gender gender;

    private Integer age;

    private String address;

    private String bloodGroup;
}


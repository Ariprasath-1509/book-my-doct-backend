package com.bookmydoct.patient.util;

import com.bookmydoct.patient.data.dto.response.PatientProfileResponse;
import com.bookmydoct.patient.data.entity.PatientProfile;
import org.springframework.stereotype.Component;

@Component
public class PatientProfileMapper {

    public PatientProfileResponse toResponse(PatientProfile patientProfile) {

        return PatientProfileResponse.builder()
                .uuid(patientProfile.getUuid())
                .userUuid(patientProfile.getUser().getUuid())
                .patientName(patientProfile.getUser().getFirstName() + " " + patientProfile.getUser().getLastName())
                .age(patientProfile.getAge())
                .gender(patientProfile.getGender())
                .bloodGroup(patientProfile.getBloodGroup())
                .address(patientProfile.getAddress())
                .build();
    }

}

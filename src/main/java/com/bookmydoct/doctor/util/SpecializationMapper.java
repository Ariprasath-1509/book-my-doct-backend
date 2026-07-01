package com.bookmydoct.doctor.util;


import com.bookmydoct.doctor.data.dto.response.SpecializationResponse;
import com.bookmydoct.doctor.data.entity.Specialization;

public class SpecializationMapper {

    private SpecializationMapper() {}

    public static SpecializationResponse toResponse(Specialization specialization) {

        return SpecializationResponse.builder()
                .specializationUuid(specialization.getUuid())
                .code(specialization.getCode())
                .name(specialization.getName())
                .description(specialization.getDescription())
                .active(specialization.getActive())
                .build();

    }
}

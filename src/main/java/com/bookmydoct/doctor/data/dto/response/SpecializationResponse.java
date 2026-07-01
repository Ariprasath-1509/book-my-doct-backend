package com.bookmydoct.doctor.data.dto.response;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecializationResponse {

    private UUID specializationUuid;
    private String code;
    private String name;
    private String description;
    private Boolean active;

}

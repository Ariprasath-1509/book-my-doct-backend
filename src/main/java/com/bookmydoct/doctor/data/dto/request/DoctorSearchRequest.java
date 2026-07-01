package com.bookmydoct.doctor.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSearchRequest {

    private String name;
    private String specialization;
    private String city;
}

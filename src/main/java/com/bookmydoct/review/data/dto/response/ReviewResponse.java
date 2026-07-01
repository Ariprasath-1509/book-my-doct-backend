package com.bookmydoct.review.data.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReviewResponse {

    private UUID reviewUuid;

    private UUID doctorUuid;

    private UUID patientUuid;

    private UUID appointmentUuid;

    private Integer rating;

    private String review;
}
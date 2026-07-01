package com.bookmydoct.review.data.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DoctorRatingResponse {

    private UUID doctorUuid;

    private Double averageRating;

    private Integer totalReviews;
}
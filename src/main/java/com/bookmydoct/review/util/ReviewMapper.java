package com.bookmydoct.review.util;

import com.bookmydoct.review.data.dto.response.ReviewResponse;
import com.bookmydoct.review.data.entity.Review;

public class ReviewMapper {

    private ReviewMapper() {}

    public static ReviewResponse toResponse(Review review) {

        return ReviewResponse.builder()
                .reviewUuid(review.getUuid())
                .doctorUuid(review.getDoctor().getUuid())
                .patientUuid(review.getPatient().getUuid())
                .appointmentUuid(review.getAppointment().getUuid())
                .rating(review.getRating())
                .review(review.getReview())
                .build();
    }
}
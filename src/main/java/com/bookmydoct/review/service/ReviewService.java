package com.bookmydoct.review.service;

import com.bookmydoct.review.data.dto.request.CreateReviewRequest;
import com.bookmydoct.review.data.dto.response.DoctorRatingResponse;
import com.bookmydoct.review.data.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request);

    Page<ReviewResponse> getDoctorReviews(UUID doctorUuid, Pageable pageable);

    DoctorRatingResponse getDoctorRating(UUID doctorUuid);
}
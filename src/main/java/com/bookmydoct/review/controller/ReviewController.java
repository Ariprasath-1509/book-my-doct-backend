package com.bookmydoct.review.controller;

import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.review.data.dto.request.CreateReviewRequest;
import com.bookmydoct.review.data.dto.response.DoctorRatingResponse;
import com.bookmydoct.review.data.dto.response.ReviewResponse;
import com.bookmydoct.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid
            @RequestBody
            CreateReviewRequest request) {

        ReviewResponse response = reviewService.createReview(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ReviewResponse>builder()
                                .success(true)
                                .message("Review submitted successfully")
                                .data(response)
                                .build()
                );
    }

    @GetMapping("/api/doctors/{doctorUuid}/reviews")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> getDoctorReviews(
            @PathVariable
            UUID doctorUuid,
            Pageable pageable) {

        Page<ReviewResponse> response = reviewService.getDoctorReviews(doctorUuid, pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponse>>builder()
                        .success(true)
                        .message("Doctor reviews fetched successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/api/doctors/{doctorUuid}/ratings")
    public ResponseEntity<ApiResponse<DoctorRatingResponse>> getDoctorRating(
            @PathVariable
            UUID doctorUuid) {

        DoctorRatingResponse response = reviewService.getDoctorRating(doctorUuid);

        return ResponseEntity.ok(
                ApiResponse.<DoctorRatingResponse>builder()
                        .success(true)
                        .message("Doctor rating fetched successfully")
                        .data(response)
                        .build()
        );
    }
}
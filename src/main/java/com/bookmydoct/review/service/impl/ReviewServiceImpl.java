package com.bookmydoct.review.service.impl;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.common.exception.customException.AppointmentNotFoundException;
import com.bookmydoct.common.exception.customException.DoctorNotFoundException;
import com.bookmydoct.common.exception.customException.ReviewAlreadyExistsException;
import com.bookmydoct.doctor.data.entity.DoctorProfile;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.review.data.dto.request.CreateReviewRequest;
import com.bookmydoct.review.data.dto.response.DoctorRatingResponse;
import com.bookmydoct.review.data.dto.response.ReviewResponse;
import com.bookmydoct.review.data.entity.Review;
import com.bookmydoct.review.repository.ReviewRepository;
import com.bookmydoct.review.service.ReviewService;
import com.bookmydoct.review.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {

        Appointment appointment = appointmentRepository
                        .findByUuid(request.getAppointmentUuid())
                        .orElseThrow(() ->
                                new AppointmentNotFoundException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Only completed appointments can be reviewed");
        }

        if (reviewRepository.existsByAppointment_Uuid(appointment.getUuid())) {
            throw new ReviewAlreadyExistsException(
                    "Review already submitted for this appointment");
        }

        Review review = Review.builder()
                .appointment(appointment)
                .doctor(appointment.getDoctor())
                .patient(appointment.getPatient())
                .rating(request.getRating())
                .review(request.getReview())
                .build();

        Review savedReview = reviewRepository.save(review);

        updateDoctorRating(appointment.getDoctor(), request.getRating());

        return ReviewMapper.toResponse(savedReview);
    }
//    This is a helper method to support createReview()
    private void updateDoctorRating(DoctorProfile doctor, Integer newRating) {

        Double averageRating =
                doctor.getAverageRating() == null
                        ? 0.0
                        : doctor.getAverageRating();

        Integer totalReviews =
                doctor.getTotalReviews() == null
                        ? 0
                        : doctor.getTotalReviews();

        double newAverageRating =
                ((averageRating * totalReviews)
                        + newRating)
                        / (totalReviews + 1);

        doctor.setAverageRating(newAverageRating);
        doctor.setTotalReviews(totalReviews + 1);

        doctorProfileRepository.save(doctor);
    }

    @Override
    public Page<ReviewResponse> getDoctorReviews(UUID doctorUuid, Pageable pageable) {

        return reviewRepository
                .findByDoctor_Uuid(doctorUuid, pageable)
                .map(ReviewMapper::toResponse);
    }

    @Override
    public DoctorRatingResponse getDoctorRating(UUID doctorUuid) {

        DoctorProfile doctor = doctorProfileRepository
                        .findByUuid(doctorUuid)
                        .orElseThrow(() ->
                                new DoctorNotFoundException("Doctor not found"));

        return DoctorRatingResponse.builder()
                .doctorUuid(doctor.getUuid())
                .averageRating(doctor.getAverageRating() == null
                                ? 0.0
                                : doctor.getAverageRating())
                .totalReviews(doctor.getTotalReviews() == null
                                ? 0
                                : doctor.getTotalReviews())
                .build();
    }
}

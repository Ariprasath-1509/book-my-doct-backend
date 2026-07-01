package com.bookmydoct.review.repository;

import com.bookmydoct.review.data.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUuid(UUID uuid);

    boolean existsByAppointment_Uuid(UUID appointmentUuid);

    Page<Review> findByDoctor_Uuid(UUID doctorUuid, Pageable pageable);

    @Query("""
    SELECT AVG(r.rating)
    FROM Review r
    """)
    Double getAverageRating();
}
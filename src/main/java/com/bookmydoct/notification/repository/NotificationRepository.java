package com.bookmydoct.notification.repository;

import com.bookmydoct.notification.data.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByUuid(UUID uuid);

    Page<Notification> findByUser_Uuid(
            UUID userUuid,
            Pageable pageable);
}

package com.bookmydoct.notification.service;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.notification.data.dto.response.NotificationResponse;
import com.bookmydoct.notification.data.enums.NotificationStatus;
import com.bookmydoct.notification.data.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationService {

    NotificationResponse getNotification(UUID notificationUuid);

    Page<NotificationResponse> getNotifications(Pageable pageable);

    void saveNotification(
            User user,
            NotificationType type,
            String message,
            NotificationStatus status);

    Page<NotificationResponse>
    getUserNotifications(
            UUID userUuid,
            Pageable pageable);
}
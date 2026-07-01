package com.bookmydoct.notification.service.impl;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.common.exception.customException.NotificationNotFoundException;
import com.bookmydoct.notification.data.dto.response.NotificationResponse;
import com.bookmydoct.notification.data.entity.Notification;
import com.bookmydoct.notification.data.enums.NotificationStatus;
import com.bookmydoct.notification.data.enums.NotificationType;
import com.bookmydoct.notification.repository.NotificationRepository;
import com.bookmydoct.notification.service.NotificationService;
import com.bookmydoct.notification.util.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse getNotification(UUID notificationUuid) {

        return notificationRepository
                .findByUuid(notificationUuid)
                .map(NotificationMapper::toResponse)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found"));
    }

    @Override
    public Page<NotificationResponse> getNotifications(Pageable pageable) {

        return notificationRepository
                .findAll(pageable)
                .map(NotificationMapper::toResponse);
    }

    @Override
    public void saveNotification(
            User user,
            NotificationType type,
            String message,
            NotificationStatus status) {

        Notification notification =
                Notification.builder()
                        .user(user)
                        .type(type)
                        .message(message)
                        .status(status)
                        .build();

        notificationRepository.save(notification);
    }

    @Override
    public Page<NotificationResponse> getUserNotifications(
            UUID userUuid,
            Pageable pageable) {

        return notificationRepository
                .findByUser_Uuid(
                        userUuid,
                        pageable)
                .map(NotificationMapper::toResponse);
    }

}
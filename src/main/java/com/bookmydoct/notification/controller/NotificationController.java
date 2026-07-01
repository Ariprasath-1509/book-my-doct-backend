package com.bookmydoct.notification.controller;

import com.bookmydoct.notification.data.dto.response.NotificationResponse;
import com.bookmydoct.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public Page<NotificationResponse> getNotifications(Pageable pageable) {

        return notificationService.getNotifications(pageable);
    }

    @GetMapping("/{notificationUuid}")
    public NotificationResponse getNotification(
            @PathVariable
            UUID notificationUuid) {

        return notificationService.getNotification(notificationUuid);
    }

    @GetMapping("/user/{userUuid}")
    public Page<NotificationResponse> getUserNotifications(
            @PathVariable UUID userUuid,
            Pageable pageable) {

        return notificationService
                .getUserNotifications(
                        userUuid,
                        pageable);
    }
}
package com.bookmydoct.notification.util;

import com.bookmydoct.notification.data.dto.response.NotificationResponse;
import com.bookmydoct.notification.data.entity.Notification;

public class NotificationMapper {

    private NotificationMapper() {
    }

    public static NotificationResponse toResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .uuid(notification.getUuid())
                .type(notification.getType())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
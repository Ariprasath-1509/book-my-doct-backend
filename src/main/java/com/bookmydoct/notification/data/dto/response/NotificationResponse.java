package com.bookmydoct.notification.data.dto.response;

import com.bookmydoct.notification.data.enums.NotificationStatus;
import com.bookmydoct.notification.data.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {

    private UUID uuid;

    private NotificationType type;

    private String message;

    private NotificationStatus status;

    private LocalDateTime createdAt;
}
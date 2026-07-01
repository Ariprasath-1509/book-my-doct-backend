package com.bookmydoct.notification.data.entity;

import com.bookmydoct.auth.data.entity.User;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.notification.data.enums.NotificationStatus;
import com.bookmydoct.notification.data.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="notifications")
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable= false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

}

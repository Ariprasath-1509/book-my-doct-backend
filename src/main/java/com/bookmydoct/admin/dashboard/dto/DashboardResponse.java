package com.bookmydoct.admin.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    // Doctor Statistics
    private long totalDoctors;
    private long approvedDoctors;
    private long pendingDoctors;
    private long rejectedDoctors;

    // Patient Statistics
    private long totalPatients;

    // Appointment Statistics
    private long totalAppointments;
    private long pendingAppointments;
    private long confirmedAppointments;
    private long completedAppointments;
    private long cancelledAppointments;
    private long todayAppointments;

    // Payment Statistics
    private BigDecimal totalRevenue;
    private BigDecimal todayRevenue;
    private long successfulPayments;
    private long pendingPayments;
    private long failedPayments;

    // Review Statistics
    private long totalReviews;
    private Double averageRating;

    // Notification Statistics
    private long totalNotifications;
}
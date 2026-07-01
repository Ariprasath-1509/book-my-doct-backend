package com.bookmydoct.admin.dashboard.service.impl;

import com.bookmydoct.admin.dashboard.dto.DashboardResponse;
import com.bookmydoct.admin.dashboard.service.AdminDashboardService;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.doctor.data.enums.VerificationStatus;
import com.bookmydoct.doctor.repository.DoctorProfileRepository;
import com.bookmydoct.notification.repository.NotificationRepository;
import com.bookmydoct.patient.repository.PatientProfileRepository;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import com.bookmydoct.payment.repository.PaymentRepository;
import com.bookmydoct.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl
        implements AdminDashboardService {

    private final DoctorProfileRepository doctorRepository;

    private final PatientProfileRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    private final PaymentRepository paymentRepository;

    private final ReviewRepository reviewRepository;

    private final NotificationRepository notificationRepository;

    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()

                // -----------------------------
                // Doctor Statistics
                // -----------------------------
                .totalDoctors(doctorRepository.count())
                .approvedDoctors(
                        doctorRepository.countByVerificationStatus(
                                VerificationStatus.APPROVED))
                .pendingDoctors(
                        doctorRepository.countByVerificationStatus(
                                VerificationStatus.PENDING))
                .rejectedDoctors(
                        doctorRepository.countByVerificationStatus(
                                VerificationStatus.REJECTED))

                // -----------------------------
                // Patient Statistics
                // -----------------------------
                .totalPatients(patientRepository.count())

                // -----------------------------
                // Appointment Statistics
                // -----------------------------
                .totalAppointments(appointmentRepository.count())

                .pendingAppointments(
                        appointmentRepository.countByStatus(
                                AppointmentStatus.PENDING))

                .confirmedAppointments(
                        appointmentRepository.countByStatus(
                                AppointmentStatus.CONFIRMED))

                .completedAppointments(
                        appointmentRepository.countByStatus(
                                AppointmentStatus.COMPLETED))

                .cancelledAppointments(
                        appointmentRepository.countByStatus(
                                AppointmentStatus.CANCELLED))

                .todayAppointments(
                        appointmentRepository.countTodayAppointments(
                                LocalDate.now()))

                // -----------------------------
                // Payment Statistics
                // -----------------------------
                .totalRevenue(
                        paymentRepository.getTotalRevenue())

                .todayRevenue(
                        paymentRepository.getTodayRevenue())

                .successfulPayments(
                        paymentRepository.countByStatus(
                                PaymentStatus.SUCCESS))

                .pendingPayments(
                        paymentRepository.countByStatus(
                                PaymentStatus.PENDING))

                .failedPayments(
                        paymentRepository.countByStatus(
                                PaymentStatus.FAILED))

                // -----------------------------
                // Review Statistics
                // -----------------------------
                .totalReviews(
                        reviewRepository.count())

                .averageRating(
                        reviewRepository.getAverageRating())

                // -----------------------------
                // Notification Statistics
                // -----------------------------
                .totalNotifications(
                        notificationRepository.count())

                .build();
    }

}
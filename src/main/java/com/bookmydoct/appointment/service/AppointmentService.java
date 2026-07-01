package com.bookmydoct.appointment.service;

import com.bookmydoct.appointment.data.dto.request.CreateAppointmentRequest;
import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AppointmentService {

    AppointmentResponse createAppointment(CreateAppointmentRequest request);

    AppointmentResponse getAppointment(UUID appointmentUuid);

    Page<AppointmentResponse> getPatientAppointments(UUID patientUuid, Pageable pageable);

    Page<AppointmentResponse> getDoctorAppointments(UUID doctorUuid, Pageable pageable);

}
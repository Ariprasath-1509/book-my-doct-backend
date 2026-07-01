package com.bookmydoct.appointment.service;

import com.bookmydoct.appointment.data.dto.response.AppointmentResponse;

import java.util.UUID;

public interface AppointmentLifecycleService {

    AppointmentResponse acceptAppointment(UUID appointmentUuid);

    AppointmentResponse rejectAppointment(UUID appointmentUuid);

    AppointmentResponse cancelAppointment(UUID appointmentUuid);

    AppointmentResponse completeAppointment(UUID appointmentUuid);

    AppointmentResponse rescheduleAppointment(UUID appointmentUuid);
}
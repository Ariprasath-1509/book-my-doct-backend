package com.bookmydoct.common.exception.customException;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String appointmentReference){
        super(appointmentReference);
    }
}

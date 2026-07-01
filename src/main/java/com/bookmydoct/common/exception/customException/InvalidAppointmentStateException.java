package com.bookmydoct.common.exception.customException;

public class InvalidAppointmentStateException extends RuntimeException{
    public InvalidAppointmentStateException(String message) {
        super(message);
    }
}

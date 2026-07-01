package com.bookmydoct.common.exception.customException;

public class InvalidScheduleException extends RuntimeException{
    public InvalidScheduleException(String message) {
        super(message);
    }
}

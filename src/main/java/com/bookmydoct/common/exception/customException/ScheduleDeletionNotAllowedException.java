package com.bookmydoct.common.exception.customException;

public class ScheduleDeletionNotAllowedException extends RuntimeException{
    public ScheduleDeletionNotAllowedException(String message) {
        super(message);
    }
}

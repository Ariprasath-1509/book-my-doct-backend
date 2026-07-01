package com.bookmydoct.common.exception.customException;

public class PatientAlreadyExistsException extends RuntimeException{
    public PatientAlreadyExistsException(String message){
        super(message);
    }
}

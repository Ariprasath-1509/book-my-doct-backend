package com.bookmydoct.common.exception.customException;

public class LicenseAlreadyExistsException extends RuntimeException{
    public LicenseAlreadyExistsException(String message){
        super(message);
    }
}

package com.bookmydoct.common.exception.customException;

public class PaymentAlreadyExistsException extends RuntimeException{
    public PaymentAlreadyExistsException(String message){
        super(message);
    }
}

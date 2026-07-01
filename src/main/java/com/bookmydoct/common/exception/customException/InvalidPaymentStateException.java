package com.bookmydoct.common.exception.customException;

public class InvalidPaymentStateException extends RuntimeException{
    public InvalidPaymentStateException(String message)
    {
        super(message);
    }
}

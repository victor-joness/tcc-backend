package com.tcc.api.responses.exceptions;

public class PasswordConfirmationException extends RuntimeException{
    public PasswordConfirmationException(String message){
        super(message);
    }
}

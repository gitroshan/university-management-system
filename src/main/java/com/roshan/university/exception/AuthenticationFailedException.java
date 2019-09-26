package com.roshan.university.exception;

public class AuthenticationFailedException extends PetroleumException {

    private static final long serialVersionUID = -2645538853259223878L;

    public AuthenticationFailedException(String message) {
        super(message);
    }

}

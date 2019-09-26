package com.roshan.university.exception;

public class UserAlreadyVerifiedException extends PetroleumException {

    private static final long serialVersionUID = -4463949731831549180L;

    public UserAlreadyVerifiedException(String message) {
        super(message);
    }

}

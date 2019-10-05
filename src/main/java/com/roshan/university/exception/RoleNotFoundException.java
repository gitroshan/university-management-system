package com.roshan.university.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -9146806971135528905L;

    public RoleNotFoundException(String message) {
        super(message);
    }

}

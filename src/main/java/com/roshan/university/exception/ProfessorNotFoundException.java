package com.roshan.university.exception;

public class ProfessorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6937870944639740297L;

    public ProfessorNotFoundException(String message) {
        super(message);
    }

}

package com.roshan.university.exception;

public class LicenseAlreadyUsedException extends PetroleumException {

    private static final long serialVersionUID = -8777783934548227569L;

    public LicenseAlreadyUsedException(String message) {
        super(message);
    }

}

package com.roshan.university.type;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("nls")
public enum ErrorType {

    INVALID_USER("Given user is not found. Please check your username.", 1), 
    NO_LICENSE_USER_FOUND("Your account is not verified with your license. Please provide your license key to continue.", 2), 
    LICENSE_EXPIRED("Your license key is expired. Please enter a valid license key.", 3), 
    AUTHENTICATION_FAILED("Username or Password or both are incorrect.", 4), 
    ILLEGAL_ARGUMENT("Both Username and password are required.", 5), 
    GENERAL_ERROR("Error occurred.", 6);

    private static final Map<String, ErrorType> BY_LABEL = new HashMap<>();

    private static final Map<Integer, ErrorType> BY_NUMBER = new HashMap<>();

    static {
        for (ErrorType errorType : values()) {
            BY_LABEL.put(errorType.message, errorType);
            BY_NUMBER.put(Integer.valueOf(errorType.number), errorType);
        }
    }

    public final String message;

    public final int number;

    private ErrorType(String message, int number) {
        this.message = message;
        this.number = number;
    }

    public static ErrorType valueOfLabel(String message) {
        return BY_LABEL.get(message);
    }

    public static ErrorType valueOfNumber(String number) {
        return BY_NUMBER.get(Integer.valueOf(number));
    }

}
package com.roshan.university.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {

    private static final Logger logger = LoggerFactory.getLogger(ContactNumberValidator.class);

    @Override
    public void initialize(ContactNumberConstraint contactNumber) {
        // initialize
    }

    @Override
    public boolean isValid(String numberStr, ConstraintValidatorContext cxt) {

        if (!StringUtils.hasText(numberStr)) {
            return false;
        }

        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber number = phoneUtil.parseAndKeepRawInput(numberStr, "MA");
            return phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        // return numberStr != null && numberStr.matches("[0-9]+") && (numberStr.length() > 8)
        // && (numberStr.length() < 14);
    }

}

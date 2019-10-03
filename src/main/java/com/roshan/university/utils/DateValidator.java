package com.roshan.university.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {

    private static final Logger logger = LoggerFactory.getLogger(DateValidator.class);

    private String dateFormat = "dd/MM/yyyy";

    @Override
    public void initialize(DateConstraint date) {
        // initialize
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext cxt) {

        logger.info("Validating date string {}", dateStr);

        if (!StringUtils.hasText(dateStr)) {
            logger.info("Date string is empty");
            return false;
        }
        try {
            DateFormat sdf = new SimpleDateFormat(this.dateFormat);
            sdf.setLenient(false);

            sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

}

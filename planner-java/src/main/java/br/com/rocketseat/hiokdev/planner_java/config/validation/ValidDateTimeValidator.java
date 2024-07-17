package br.com.rocketseat.hiokdev.planner_java.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidDateTimeValidator implements ConstraintValidator<ValidDateTime, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}

package org.example.homework2.validation.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.homework2.exception.ValidationErrorsException;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationService {
    private static Validator validator;
// validator
    private ValidationService() {
    }

    public static void getValidator() {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
    }
    public static  <T> void validation(T object) throws ValidationErrorsException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationErrorsException(getValidationErrors(violations));
        }
    }

    private static <T> String getValidationErrors(Set<ConstraintViolation<T>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(". "));
    }
}

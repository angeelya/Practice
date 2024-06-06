package org.example.homework2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameContentValidator implements ConstraintValidator<NameContent,String> {
    @Override
    public void initialize(NameContent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name!=null&&name.matches("^[A-z]+$");
    }
}

package org.example.homework2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameContentValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameContent {
    String message() default "Incorrect name. Use letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

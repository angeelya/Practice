package org.example.homework2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueGroupValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupUnique {
    String message() default "Group already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

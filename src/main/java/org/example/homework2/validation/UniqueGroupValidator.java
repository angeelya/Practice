package org.example.homework2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.homework2.database.dao.GroupDao;
import org.example.homework2.database.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UniqueGroupValidator implements ConstraintValidator<GroupUnique, String> {
    @Override
    public void initialize(GroupUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Group group = null;
        try {
            group=GroupDao.findByName(name);
        } catch (SQLException e) {
            logger.error ("Failed to found group");
        }
        return group==null;
    }
}

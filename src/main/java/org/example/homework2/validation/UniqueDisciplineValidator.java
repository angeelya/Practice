package org.example.homework2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.homework2.database.dao.DisciplineDao;
import org.example.homework2.database.model.Discipline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UniqueDisciplineValidator implements ConstraintValidator<DisciplineUnique,String> {
    @Override
    public void initialize(DisciplineUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Discipline discipline = null;
        try {
           discipline= DisciplineDao.findByName(name);
        } catch (SQLException e) {
           logger.error ("Failed to found discipline");
        }
        return discipline==null;
    }
}

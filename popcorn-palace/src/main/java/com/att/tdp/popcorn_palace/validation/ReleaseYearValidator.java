// ======= Validator for release year =======
package com.att.tdp.popcorn_palace.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class ReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        int currentYear = Year.now().getValue();
        return value >= 1900 && value <= currentYear;
    }
}

// ======= Custom annotation for release year =======
package com.att.tdp.popcorn_palace.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseYearValidator.class)
public @interface ValidReleaseYear {
    String message() default "Release year must be between 1900 and the current year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

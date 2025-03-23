// ======= Custom annotation for validating full Showtime object =======
package com.att.tdp.popcorn_palace.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ShowtimeValidator.class)
public @interface ValidShowtime {
    String message() default "Invalid showtime: movie must exist, start and end time must be valid, and duration must match the movie";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
// ======= Validator for Showtime =======
package com.att.tdp.popcorn_palace.validation;

import com.att.tdp.popcorn_palace.Entity.Showtime;
import com.att.tdp.popcorn_palace.Entity.Movie;
import com.att.tdp.popcorn_palace.Repository.MovieRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Optional;

public class ShowtimeValidator implements ConstraintValidator<ValidShowtime, Showtime> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean isValid(Showtime s, ConstraintValidatorContext context) {
        if (s == null || s.getMovie() == null || s.getMovie().getId() == null) return false;

        //movie must exist
        Optional<Movie> movieOpt = movieRepository.findById(s.getMovie().getId());
        if (movieOpt.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Movie not found").addConstraintViolation();
            return false;
        }
        //start time < end time 
        if (s.getStartTime() == null || s.getEndTime() == null || !s.getEndTime().isAfter(s.getStartTime())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End time must be after start time").addConstraintViolation();
            return false;
        }
        // duration match the movie
        long actualDuration = Duration.between(s.getStartTime(), s.getEndTime()).toMinutes();
        if (actualDuration < movieOpt.get().getDuration()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Duration mismatch between showtime and movie").addConstraintViolation();
            return false;
        }

        return true;
    }
}


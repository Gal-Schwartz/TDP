package com.att.tdp.popcorn_palace.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Entity.Movie;
import com.att.tdp.popcorn_palace.Entity.Showtime;
import com.att.tdp.popcorn_palace.Repository.MovieRepository;
import com.att.tdp.popcorn_palace.Repository.ShowtimeRepository;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime addShowtime(Showtime s) {
        validateShowtimeOrThrow(s);

        List<Showtime> overlaps = showtimeRepository.findByTheater(s.getTheater()).stream()
                .filter(show -> !(show.getEndTime().isBefore(s.getStartTime())
                        || show.getStartTime().isAfter(s.getEndTime())))
                .toList();

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Overlapping showtime");
        }

        return showtimeRepository.save(s);
    }

    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new IllegalArgumentException("Showtime not found");
        }
        showtimeRepository.deleteById(id);
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Showtime not found"));
    }

    public Showtime updateShowtime(Long id, Showtime updated) {
        Showtime existing = getShowtimeById(id);
        validateShowtimeOrThrow(updated);
        List<Showtime> overlapping = showtimeRepository.findByTheater(updated.getTheater())
                .stream()
                .filter(s -> !s.getId().equals(id))
                .filter(s -> !(s.getEndTime().isBefore(updated.getStartTime())
                        || s.getStartTime().isAfter(updated.getEndTime())))
                .toList();

        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Overlapping showtime");
        }

        existing.setMovie(updated.getMovie());
        existing.setTheater(updated.getTheater());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        existing.setPrice(updated.getPrice());

        return showtimeRepository.save(existing);
    }

    private void validateShowtimeOrThrow(Showtime s) {

        if (s == null || s.getMovie() == null || s.getMovie().getId() == null) {
            throw new IllegalArgumentException("Movie must be specified");
        }

        Optional<Movie> movieOpt = movieRepository.findById(s.getMovie().getId());
        if (movieOpt.isEmpty()) {
            throw new IllegalArgumentException("Movie not found");
        }

        if (s.getStartTime() == null || s.getEndTime() == null || !s.getEndTime().isAfter(s.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        long actualDuration = Duration.between(s.getStartTime(), s.getEndTime()).toMinutes();
        if (actualDuration < movieOpt.get().getDuration()) {
            throw new IllegalArgumentException("Duration mismatch between showtime and movie");
        }

    }

}
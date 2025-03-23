package com.att.tdp.popcorn_palace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.Entity.Showtime;
import com.att.tdp.popcorn_palace.Repository.ShowtimeRepository;

@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime addShowtime(Showtime s) {
        List<Showtime> overlaps = getAllShowtimes().stream()
        .filter(show -> show.getTheater().equals(s.getTheater()) &&
                !(show.getEndTime().isBefore(s.getStartTime()) || show.getStartTime().isAfter(s.getEndTime())))
        .toList();
        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Overlapping showtime");
        }
        return showtimeRepository.save(s);

}

    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }
    public Optional<Showtime> getShowtimeById(Long id) {
        return showtimeRepository.findById(id);
    }

    public Showtime updateShowtime(Long id, Showtime updated) {
        Showtime existing = getShowtimeById(id)
            .orElseThrow(() -> new RuntimeException("Showtime not found"));

            List<Showtime> overlapping = showtimeRepository.findByTheater(updated.getTheater())
            .stream()
            .filter(s -> !s.getId().equals(id))
            .filter(s -> !(s.getEndTime().isBefore(updated.getStartTime()) || s.getStartTime().isAfter(updated.getEndTime())))
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
}
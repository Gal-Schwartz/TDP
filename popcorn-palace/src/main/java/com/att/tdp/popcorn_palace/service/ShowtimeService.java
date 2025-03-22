package com.att.tdp.popcorn_palace.service;

import java.util.List;

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

    public Showtime addShowtime(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }




    public void deleteShowtime(Long id) {
        showtimeRepository.deleteById(id);
    }

    public List<Showtime> findOverlappingShowtimes(String theater, LocalDateTime start, LocalDateTime end, Long excludeId) {
        return showtimeRepository.findByTheaterAndStartTimeBetween(theater, start.minusHours(3), end.plusHours(3))
            .stream()
            .filter(s -> !s.getId().equals(excludeId))
            .collect(Collectors.toList());
    }
}